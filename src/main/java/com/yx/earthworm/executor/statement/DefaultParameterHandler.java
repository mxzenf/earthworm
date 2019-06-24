package com.yx.earthworm.executor.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.yx.earthworm.mapping.BoundSql;
import com.yx.earthworm.mapping.ParameterMapping;
import com.yx.earthworm.reflection.MetaObject;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.type.TypeHandler;
import com.yx.earthworm.type.TypeHandlerRegistry;

public class DefaultParameterHandler implements ParameterHandler {

  private Configuration configuration;
  private BoundSql boundSql;
  private Object parameterObject;
  private TypeHandlerRegistry typeHandlerRegistry;
  private MetaObject metaObject;

  public DefaultParameterHandler(Configuration configuration, BoundSql boundSql, Object parameterObject) {
    this.configuration = configuration;
    this.boundSql = boundSql;
    this.parameterObject = parameterObject;
    this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
    this.metaObject = configuration.newMetaObject(parameterObject);
  }

  @Override
  public void setParameters(PreparedStatement preparedStatement) throws SQLException {
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    int size = parameterMappings.size();
    for (int i = 0; i < size; i++) {
      ParameterMapping parameterMapping = parameterMappings.get(i);
      String propertyName = parameterMapping.getProperty();
      Object value = null;
      if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
        value = parameterObject;
      } else {
        value = metaObject.getValue(propertyName);
      }

      TypeHandler typeHandler = parameterMapping.getTypeHandler();
      typeHandler.setParameter(preparedStatement, i, value, parameterMapping.getJdbcType());
    }

  }

}
