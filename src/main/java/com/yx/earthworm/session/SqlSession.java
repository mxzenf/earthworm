package com.yx.earthworm.session;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;
public interface SqlSession extends Closeable {

  void commit() throws SQLException;
  void rollback() throws SQLException;
  <E> List<E> selectList(String mappedStatement, Object parameterObject) throws SQLException;
}
