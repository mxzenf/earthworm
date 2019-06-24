package com.yx.earthworm.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author yangxin 2019年3月9日 下午1:47:12
 */
public abstract class BaseTypeHandler<T> implements TypeHandler<T> {

  @Override
  public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      if (null == jdbcType) {
        throw new SQLException("参数为空必须制定jdbc的类型");
      } else {
        ps.setNull(i, jdbcType.TYPE_CODE);
      }
    } else {
      setNonNullParameter(ps, i, parameter, jdbcType);
    }
  }

  @Override
  public T getResult(ResultSet rs, int columnIndex) throws SQLException {
    if (rs.wasNull()) {
      return null;
    }

    return getNullableRresult(rs, columnIndex);
  }

  @Override
  public T getResult(ResultSet rs, String columnName) throws SQLException {
    if (rs.wasNull()) {
      return null;
    }

    return getNullableRresult(rs, columnName);
  }
  
  @Override
  public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
    if (cs.wasNull()) {
      return null;
    }

    return getNullableRresult(cs, columnIndex);
  }

  public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
      throws SQLException;

  public abstract T getNullableRresult(ResultSet rs, int columnIndex) throws SQLException;

  public abstract T getNullableRresult(ResultSet rs, String columnName) throws SQLException;
  
  public abstract T getNullableRresult(CallableStatement cs, int columnIndex) throws SQLException;
}
