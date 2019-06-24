package com.yx.earthworm.transaction;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * 
 * @author yangxin 2019年4月20日 下午6:35:04
 */
public interface TransactionFactory {

  Transaction newTransaction(Connection connection);
  
  Transaction newTransaction(DataSource dataSource);
}
