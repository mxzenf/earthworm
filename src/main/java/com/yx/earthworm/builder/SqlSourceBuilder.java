package com.yx.earthworm.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yx.earthworm.io.Resources;
import com.yx.earthworm.mapping.ParameterMapping;
import com.yx.earthworm.mapping.SqlSource;
import com.yx.earthworm.parser.GenericTokenParser;
import com.yx.earthworm.parser.ParameterExpression;
import com.yx.earthworm.parser.TokenHandler;
import com.yx.earthworm.reflection.MetaObject;
import com.yx.earthworm.session.Configuration;
import com.yx.earthworm.type.TypeHandler;

/**
 * 
 * @author yangxin 2019年2月27日 下午1:47:11
 */
public class SqlSourceBuilder extends BaseBuilder {

  public SqlSourceBuilder(Configuration configuration) {
    super(configuration);
  }

  public SqlSource parse(String originalSql, Class<?> parameterType, Object parameterObject) {
    ParameterMappingHandler parameterMappingHandler = new SqlSourceBuilder.ParameterMappingHandler(configuration,
        parameterType, parameterObject);
    GenericTokenParser parser = new GenericTokenParser("#{", "}", parameterMappingHandler);

    return new StaticSqlSource(configuration, parser.parse(originalSql),
        parameterMappingHandler.getParametersMappings());
  }

  static class ParameterMappingHandler extends BaseBuilder implements TokenHandler {

    private List<ParameterMapping> parametersMappings = new ArrayList<ParameterMapping>();
    private Class<?> parameterType;
    private MetaObject metaObject;

    public ParameterMappingHandler(Configuration configuration, Class<?> parameterType, Object parameterObject) {
      super(configuration);
      this.parameterType = parameterType;
      this.metaObject = configuration.newMetaObject(parameterObject);
    }

    @Override
    public String handleToken(String content) {
      parametersMappings.add(buildParameterMapping(content));
      return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
      Map<String, String> propertyMap = new ParameterExpression(content);
      ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration);
      String property = propertyMap.get("property");
      Class propertyType = metaObject.getGetterType(property);
      if (null == propertyType) {
        propertyType = Object.class;
      }
      builder.javaType(propertyType);
      builder.mode(resolveParameterMode(propertyMap.get("mode")));
      builder.jdbcType(resolveJdbcType(propertyMap.get("jdbcType")));
      String typeHandlerAlias = propertyMap.get("typeHandler");
      if (null != typeHandlerAlias) {
        builder.typeHandler(resolveTypeHandler(propertyType, typeHandlerAlias));
      }
      
      return builder.build(property);
    }

    private TypeHandler<?> resolveTypeHandler(Class propertyType, String typeHandlerAlias) {
      Class<? extends TypeHandler<?>> typeHandler = (Class<? extends TypeHandler<?>>)Resources.classForName(typeHandlerAlias);
      return resolveTypeHandler(propertyType, typeHandler);
    }

    public List<ParameterMapping> getParametersMappings() {
      return parametersMappings;
    }

    public void setParametersMappings(List<ParameterMapping> parametersMappings) {
      this.parametersMappings = parametersMappings;
    }

  }
}
