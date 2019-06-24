package com.yx.earthworm.session.defaults;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.yx.earthworm.executor.Executor;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.session.SqlSession;
/**
 * 
 * @author yangxin 2019年5月8日 上午9:53:22
 */
public class DefaultSqlSession implements SqlSession {
  
  private Executor executor;
  private Configuration configuration;
  private boolean autoCommit;
  
  public DefaultSqlSession(Executor executor, Configuration configuration, boolean autoCommit){
    this.executor = executor;
    this.configuration = configuration;
    this.autoCommit = autoCommit;
  }

  @Override
  public void close() throws IOException {
    try {
      executor.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void commit() throws SQLException {
    executor.commit();
  }

  @Override
  public void rollback() throws SQLException {
    executor.rollback();
  }

  @Override
  public <E> List<E> selectList(String statement, Object parameterObject) throws SQLException {
    MappedStatement mappedStatement = configuration.getMappedStatements().get(statement);
    return executor.query(mappedStatement, mappedStatement.getSqlSource().getBoundSql(parameterObject), parameterObject);
  }

}
