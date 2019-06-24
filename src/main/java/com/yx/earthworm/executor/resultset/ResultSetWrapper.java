package com.yx.earthworm.executor.resultset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.support.logging.Resources;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.type.JdbcType;
import com.yx.earthworm.type.TypeHandler;
import com.yx.earthworm.type.TypeHandlerRegistry;

/**
 * 
 * @author yangxin 2019年6月12日 下午1:48:19
 */
public class ResultSetWrapper {
  
  private ResultSet resultSet;
  private List<String> columnNames = new ArrayList<String>();
  private List<String> javaTypes = new ArrayList<String>();
  private List<JdbcType> jdbcTypes = new ArrayList<JdbcType>();
  private TypeHandlerRegistry typeHandlerRegistry;
  
  public ResultSetWrapper(ResultSet resultSet, Configuration configuration){
    this.resultSet = resultSet;
    typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    try {
      ResultSetMetaData rsm = resultSet.getMetaData();
      final int columnCount = rsm.getColumnCount();
      for (int i = 1; i <= columnCount; i++) {
        columnNames.add(rsm.getColumnName(i));
        javaTypes.add(rsm.getColumnClassName(i));
        jdbcTypes.add(JdbcType.forCode(rsm.getColumnType(i)));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet getResultSet() {
    return resultSet;
  }

  public void setResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }
  
  TypeHandler<?> getTypeHandler(String columnName, Class<?> javaType){
    TypeHandler<?> handler = null;
    
    handler = typeHandlerRegistry.getTypeHandler(javaType);
    
    if (handler == null) {
      int index = columnName.indexOf(columnName);
      Class<?> type = resolveClass(javaTypes.get(index));
      JdbcType jdbcType = jdbcTypes.get(index);
      handler = typeHandlerRegistry.getTypeHandler(type, jdbcType);
    }
    
    return handler;
  }

  private Class<?> resolveClass(String className) {
    try {
      return Resources.classForName(className);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<String> getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(List<String> columnNames) {
    this.columnNames = columnNames;
  }

  public List<String> getJavaTypes() {
    return javaTypes;
  }

  public void setJavaTypes(List<String> javaTypes) {
    this.javaTypes = javaTypes;
  }

  public List<JdbcType> getJdbcTypes() {
    return jdbcTypes;
  }

  public void setJdbcTypes(List<JdbcType> jdbcTypes) {
    this.jdbcTypes = jdbcTypes;
  }
  
  
}
