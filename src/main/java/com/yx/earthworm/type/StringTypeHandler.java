package com.yx.earthworm.type;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 
 * @author yangxin 2019年3月9日 下午2:55:52
 */
public class StringTypeHandler extends BaseTypeHandler<String> {

  @Override
  public String getNullableRresult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getString(columnIndex);
  }
  
  @Override
  public String getNullableRresult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getString(columnIndex);
  }
  
  @Override
  public String getNullableRresult(ResultSet rs, String columnName) throws SQLException {
    return rs.getString(columnName);
  }
  
  public void setNonNullParameter(java.sql.PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  };
  
  
}
