package com.yx.earthworm.session.defaults;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.yx.earthworm.executor.Executor;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.session.Environment;
import com.yx.earthworm.session.ExecutorType;
import com.yx.earthworm.session.SqlSession;
import com.yx.earthworm.session.SqlSessionFactory;
import com.yx.earthworm.transaction.Transaction;

/**
 * 
 * @author yangxin 2019年5月8日 上午9:35:20
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

  private Configuration configuration;

  public DefaultSqlSessionFactory() {
  }

  public DefaultSqlSessionFactory(Configuration configuration) {
    this.configuration = configuration;
  }
  
  

  @Override
  public SqlSession openSqlSession(ExecutorType executorType, Connection connection) {
    boolean autoCommit;
    try {
      autoCommit = connection.getAutoCommit();
    } catch (SQLException e) {
      autoCommit = true;
    }
    final Environment environment = configuration.getEnvironment();
    Transaction transaction = environment.getTransactionFactory().newTransaction(connection);
    Executor executor = configuration.newExecutor(executorType, transaction);
    return new DefaultSqlSession(executor, configuration, autoCommit);
  }

  @Override
  public SqlSession openSqlSession(Connection connection) {
    return openSqlSession(null ,connection);
  }

  @Override
  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  private SqlSession openSqlSessionoForDataSource(ExecutorType executorType, boolean autoCommit) {
    final Environment environment = configuration.getEnvironment();
    DataSource dataSource = environment.getDataSource();
    Transaction transaction = environment.getTransactionFactory().newTransaction(dataSource);
    Executor executor = configuration.newExecutor(executorType, transaction);
    return new DefaultSqlSession(executor, configuration, autoCommit);
  }

  @Override
  public SqlSession openSqlSession(ExecutorType type) {
    return openSqlSessionoForDataSource(type, true);
  }

  @Override
  public SqlSession openSqlSession(ExecutorType type, boolean autoCommit) {
    return openSqlSessionoForDataSource(type, autoCommit);
  }
}
