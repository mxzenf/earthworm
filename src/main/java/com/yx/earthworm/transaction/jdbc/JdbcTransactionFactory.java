package com.yx.earthworm.transaction.jdbc;

import java.sql.Connection;

import javax.sql.DataSource;

import com.yx.earthworm.transaction.Transaction;
import com.yx.earthworm.transaction.TransactionFactory;

public class JdbcTransactionFactory implements TransactionFactory {

  @Override
  public Transaction newTransaction(Connection connection) {
    return new JdbcTransaction(connection);
  }

  @Override
  public Transaction newTransaction(DataSource dataSource) {
    return new JdbcTransaction(dataSource);
  }

}
