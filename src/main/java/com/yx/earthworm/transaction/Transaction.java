package com.yx.earthworm.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author yangxin 2019年4月20日 下午6:35:24
 */
public interface Transaction {

  void commit() throws SQLException;
  
  void rollback() throws SQLException;
  
  void close() throws SQLException;
  
  Connection getConnection() throws SQLException;
  
}
