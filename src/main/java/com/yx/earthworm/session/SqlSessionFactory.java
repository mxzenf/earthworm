package com.yx.earthworm.session;

import java.sql.Connection;

/**
 * 
 * @author yangxin 2019年4月17日 下午4:41:23
 */
public interface SqlSessionFactory {

  SqlSession openSqlSession(ExecutorType executorType, Connection connection);
  
  SqlSession openSqlSession(Connection connection);
  
  Configuration getConfiguration();
  
  SqlSession openSqlSession(ExecutorType executorType);
  
  SqlSession openSqlSession(ExecutorType executorType, boolean autoCommit);
}
