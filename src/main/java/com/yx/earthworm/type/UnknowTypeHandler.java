package com.yx.earthworm.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.yx.earthworm.io.Resources;

import lombok.Getter;

/**
 * 
 * @author yangxin 2019年3月11日 下午2:22:38
 */
public class UnknowTypeHandler extends BaseTypeHandler<Object> {
  @Getter
  private static final ObjectTypeHandler OBJECT_TYPE_HANDLER = new ObjectTypeHandler();
  private TypeHandlerRegistry typeHandlerRegistry;

  public UnknowTypeHandler(TypeHandlerRegistry typeHandlerRegistry) {
    this.typeHandlerRegistry = typeHandlerRegistry;
  }

  @SuppressWarnings("all")
  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
      throws SQLException {
    TypeHandler handler = resolveTypeHandler(parameter, jdbcType);
    handler.setParameter(ps, i, parameter, jdbcType);
  }

  private TypeHandler resolveTypeHandler(Object parameter, JdbcType jdbcType) {
    TypeHandler handler = getDefaultObjectHandler(typeHandlerRegistry.getTypeHandler(parameter.getClass()));
    return handler;
  }

  @Override
  public Object getNullableRresult(ResultSet rs, int columnIndex) throws SQLException {
    TypeHandler handler = getDefaultObjectHandler(resolveTypeHandler(rs.getMetaData(), columnIndex));
    return handler.getResult(rs, columnIndex);
  }

  private TypeHandler resolveTypeHandler(ResultSetMetaData metaData, int columnIndex) {
    JdbcType jdbcType = safeGetJdbcTypeForCol(metaData, columnIndex);
    Class<?> javaType = safeGetJavaTypeForCol(metaData, columnIndex);
    return typeHandlerRegistry.getTypeHandler(javaType, jdbcType);
  }

  private Class<?> safeGetJavaTypeForCol(ResultSetMetaData metaData, int columnIndex) {
    try {
      Resources.classForName(metaData.getColumnClassName(columnIndex));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private JdbcType safeGetJdbcTypeForCol(ResultSetMetaData metaData, int columnIndex) {
    try {
      return JdbcType.forCode(metaData.getColumnType(columnIndex));
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Object getNullableRresult(ResultSet rs, String columnName) throws SQLException {
    TypeHandler handler = getDefaultObjectHandler(resolveTypeHandler(rs.getMetaData(), columnName));
    return handler.getResult(rs, columnName);
  }

  private TypeHandler getDefaultObjectHandler(TypeHandler handler) {
    if (null == handler) {
      return OBJECT_TYPE_HANDLER;
    }
    return handler;
  }

  private TypeHandler resolveTypeHandler(ResultSetMetaData metaData, String columnName) throws SQLException {
    String indexOfColName = null;
    for (int i = metaData.getColumnCount(); i > 0; i--) {
      indexOfColName = metaData.getColumnName(i);
      if (indexOfColName.equals(columnName)) {
        return resolveTypeHandler(metaData, i);
      }
    }
    return null;
  }

  @Override
  public Object getNullableRresult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getObject(columnIndex);
  }

}
