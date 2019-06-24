package com.yx.earthworm.transaction.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.yx.earthworm.transaction.Transaction;
/**
 * 
 * @author yangxin 2019年4月20日 下午7:15:24
 */
public class JdbcTransaction implements Transaction {
  
  private Connection connection;
  private DataSource dataSource;
  
  public JdbcTransaction(Connection connection){
    this.connection = connection;
  }
  
  public JdbcTransaction(DataSource dataSource){
    this.dataSource = dataSource;
  }

  @Override
  public void commit() throws SQLException {
    if (null != connection && !connection.isClosed() && !connection.getAutoCommit()) {
      connection.commit();
    }
  }

  @Override
  public void rollback() throws SQLException {
    if (null != connection && !connection.isClosed() && !connection.getAutoCommit()) {
      connection.rollback();
    }
  }

  @Override
  public void close() throws SQLException {
    if (null != connection && !connection.isClosed()) {
      connection.close();
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    if (null == connection) {
      return dataSource.getConnection();
    }
    return connection;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }
  
}
