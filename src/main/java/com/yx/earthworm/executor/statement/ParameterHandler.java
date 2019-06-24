package com.yx.earthworm.executor.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author yangxin 2019年5月15日 上午11:14:55
 */
public interface ParameterHandler {

  void setParameters(PreparedStatement preparedStatement) throws SQLException;
  
}
