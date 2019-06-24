package com.yx.earthworm.session;

import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.yx.earthworm.executor.DefaultExecutor;
import com.yx.earthworm.executor.Executor;
import com.yx.earthworm.executor.resultset.DefaultResultSetHandler;
import com.yx.earthworm.executor.resultset.ResultSetHandler;
import com.yx.earthworm.executor.statement.DefaultStatementHandler;
import com.yx.earthworm.executor.statement.StatementHandler;
import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.reflection.MetaObject;
import com.yx.earthworm.transaction.Transaction;
import com.yx.earthworm.type.TypeHandlerRegistry;

/**
 * 
 * @author yangxin 2019年2月12日 下午1:49:38
 */
public class Configuration {
  
  private Environment environment;
  
  private Set<String> loadedresources = new HashSet<>();
  
  private final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("sql mappers配置集合");
  
  protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

  protected static class StrictMap<V> extends HashMap<String, V> {

    private String name;

    public StrictMap(String name) {
      super();
      this.name = name;
    }

    private static final long serialVersionUID = -8219942600278193118L;

    @Override
    public V put(String key, V value) {
      if (containsKey(key)) {
        throw new IllegalArgumentException("已经存在key对应的值");
      }
      return super.put(key, value);
    }

    @Override
    public V get(Object key) {
      if (null == super.get(key)) {
        throw new IllegalArgumentException("不存在key对应的值");
      }
      return super.get(key);
    }
  }

  public MetaObject newMetaObject(Object o) {
    return MetaObject.forObject(o);
  }

  public Map<String, MappedStatement> getMappedStatements() {
    return mappedStatements;
  }

  public TypeHandlerRegistry getTypeHandlerRegistry() {
    return typeHandlerRegistry;
  }

  public void addMappedStatement(MappedStatement mappedStatement) {
    mappedStatements.put(mappedStatement.getId(), mappedStatement);
  }
  
  public void addLoadedResource(String resource){
    loadedresources.add(resource);
  }
  
  public boolean isResourceLoaded(String resource){
    return loadedresources.contains(resource);
  }

  public Environment getEnvironment() {
    return environment;
  }

  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
  
  public Executor newExecutor(ExecutorType executorType, Transaction transaction){
    return new DefaultExecutor(this, transaction);
  }
  
  public StatementHandler newStatementHandler(BoundSql boundSql, Object parameterObject, MappedStatement mappedStatement){
    return new DefaultStatementHandler(this, boundSql, parameterObject, mappedStatement);
  }
  
  public ResultSetHandler newResultSetHandler(BoundSql boundSql, MappedStatement mappedStatement){
    return new DefaultResultSetHandler(boundSql, mappedStatement);
  }
}
