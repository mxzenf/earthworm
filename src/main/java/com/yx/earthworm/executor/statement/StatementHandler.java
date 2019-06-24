package com.yx.earthworm.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 
 * @author yangxin 2019年5月15日 上午10:22:10
 */
public interface StatementHandler {

  Statement prepare(Connection connection) throws SQLException;
  
  void parameterize(Statement statement)throws SQLException; 
  
  <E> List<E> query(Statement statement) throws SQLException; 
}
