package com.yx.earthworm.executor.resultset;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yx.earthworm.builder.ResultMap;
import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.reflection.MetaObject;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.type.TypeHandler;
import com.yx.earthworm.type.TypeHandlerRegistry;

/**
 * 
 * @author yangxin 2019年6月12日 下午2:11:51
 */
public class DefaultResultSetHandler implements ResultSetHandler {

  private Configuration configuration;
  private BoundSql boundSql;
  private TypeHandlerRegistry typeHandlerRegistry;
  private final MappedStatement mappedStatement;

  public DefaultResultSetHandler(BoundSql boundSql, MappedStatement mappedStatement) {
    this.configuration = mappedStatement.getConfiguration();
    this.boundSql = boundSql;
    this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    this.mappedStatement = mappedStatement;
  }

  @Override
  public List<Object> handleResultSet(Statement statement) throws SQLException {
    ResultSet resultSet = statement.getResultSet();
    ResultSetWrapper rsw = new ResultSetWrapper(resultSet, configuration);
    try {
      return handleRowValues(rsw, mappedStatement.getResultMap());
    } finally {
      close(rsw);
    }

  }

  private static class UnMappedColumnAutoMapping {
    private final String columnName;
    private final TypeHandler<?> typeHandler;
    
    public UnMappedColumnAutoMapping(String columnName, TypeHandler<?> typeHandler){
      this.columnName = columnName;
      this.typeHandler = typeHandler;
    }
  }

  private void close(ResultSetWrapper rsw) {
    try {
      rsw.getResultSet().close();
    } catch (SQLException e) {
    }
  }

  private List<Object> handleRowValues(ResultSetWrapper rsw, ResultMap resultMap) throws SQLException {
    List<Object> resultList = new ArrayList<Object>();
    while (rsw.getResultSet().next()) {
      Object rowValue = getRowValue(rsw, resultMap);
      resultList.add(rowValue);
    }
    return resultList;
  }

  private Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap) throws SQLException {
    Object resultObject = createObject(rsw, resultMap);
    if (!isPromitive(resultMap)) {
      applyAutoMappingProperties(rsw, resultObject);
    }

    return resultObject;
  }

  private boolean isPromitive(ResultMap resultMap) {
    return typeHandlerRegistry.hasTypeHandler(resultMap.getClass());
  }

  private void applyAutoMappingProperties(ResultSetWrapper rsw, Object resultObject) throws SQLException {
    MetaObject metaObject = configuration.newMetaObject(resultObject);
    List<UnMappedColumnAutoMapping> autoMappings = createAutoMappings(rsw, metaObject);
    for (UnMappedColumnAutoMapping autoMapping : autoMappings) {
      metaObject.setValue(autoMapping.columnName,
          autoMapping.typeHandler.getResult(rsw.getResultSet(), autoMapping.columnName));
    }
  }

  private List<UnMappedColumnAutoMapping> createAutoMappings(ResultSetWrapper rsw, MetaObject metaObject) {
    List<UnMappedColumnAutoMapping> autoMappings = new ArrayList<UnMappedColumnAutoMapping>();
    for (String columnName : rsw.getColumnNames()) {
      autoMappings.add(new UnMappedColumnAutoMapping(columnName, rsw.getTypeHandler(columnName, metaObject.getGetterType(columnName))));
    }
    return autoMappings;
  }

  private Object createObject(ResultSetWrapper rsw, ResultMap resultMap) throws SQLException {
    Class<?> javaType = resultMap.getType();
    if (typeHandlerRegistry.hasTypeHandler(javaType)) {
      String columnName = rsw.getColumnNames().get(1);
      TypeHandler<?> typeHandler = rsw.getTypeHandler(columnName, javaType);
      return typeHandler.getResult(rsw.getResultSet(), columnName);
    }
    Object resultObject = createObjectConstructor(resultMap);
    return resultObject;
  }

  private Object createObjectConstructor(ResultMap resultMap) {
    Class<?> javaType = resultMap.getType();
    Constructor<?>[] constructors = javaType.getConstructors();
    for (Constructor<?> constructor : constructors) {
      constructor.setAccessible(true);
      try {
        return constructor.newInstance();
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
//        e.printStackTrace();
      }
    }

    return null;
  }

}
