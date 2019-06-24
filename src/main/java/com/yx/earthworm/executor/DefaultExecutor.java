package com.yx.earthworm.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.yx.earthworm.executor.statement.StatementHandler;
import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.MappedStatement;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.transaction.Transaction;

/**
 * 
 * @author yangxin 2019年5月17日 上午9:40:28
 */
public class DefaultExecutor implements Executor {

  private Configuration configuration;
  private Transaction transaction;
  
  public DefaultExecutor(Configuration configuration, Transaction transaction){
    this.configuration = configuration;
    this.transaction = transaction;
  }

  @Override
  public void commit() throws SQLException {
    transaction.commit();    
  }

  @Override
  public void close() throws SQLException {
    transaction.close();
  }

  @Override
  public void rollback() throws SQLException {
    transaction.rollback();
  }

  @Override
  public <E> List<E> query(MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
    Statement statement = null;
    try {
      StatementHandler statementHandler = configuration.newStatementHandler(boundSql, parameterObject, mappedStatement);
      statement = prepared(statementHandler);
      return statementHandler.query(statement);
    } finally {
      closeStatement(statement);
    }
  }
  
  private void closeStatement(Statement statement) {
    if (null != statement) {
      try {
        statement.close();
      } catch (SQLException e) {
        // 忽略
      }
    }
  }

  private Statement prepared(StatementHandler statementHandler) throws SQLException{
    Statement statement = null;
    statement = statementHandler.prepare(getConnection());
    statementHandler.parameterize((PreparedStatement)statement);
    return statement;
  }

  private Connection getConnection() throws SQLException {
    Connection connection = transaction.getConnection();
    return connection;
  }
  
}
