package com.yx.earthworm.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 
 * @author yangxin 2019年5月15日 上午10:26:12
 */
public interface ResultSetHandler {

  <E> List<E> handleResultSet(Statement statement) throws SQLException; 
  
}
