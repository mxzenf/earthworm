package com.yx.earthworm.executor;

import java.sql.SQLException;
import java.util.List;

import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.MappedStatement;

/**
 * 
 * @author yangxin 2019年4月17日 下午4:48:47
 */
public interface Executor {
  
  void commit() throws SQLException;
  void close() throws SQLException;
  void rollback() throws SQLException;
  <E> List<E> query(MappedStatement mappedStatement ,BoundSql boundSql, Object parameterObject) throws SQLException;

}
