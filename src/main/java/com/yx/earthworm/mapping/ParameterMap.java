package com.yx.earthworm.mapping;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author yangxin 2019年3月27日 下午2:41:55
 */
public class ParameterMap {

  private String id;
  private Class<?> type;
  private List<ParameterMapping> parameterMappings;
  
  public static class Builder{
    
    ParameterMap parameterMap = new ParameterMap();
    public Builder(String id, Class<?> type, List<ParameterMapping> parameterMappings) {
      parameterMap.id = id;
      parameterMap.type = type;
      parameterMap.parameterMappings = parameterMappings;
    }
    
    public ParameterMap  build() {
      parameterMap.parameterMappings = Collections.unmodifiableList(parameterMap.parameterMappings);
      return parameterMap;
    }
  }

  public String getId() {
    return id;
  }

  public Class<?> getType() {
    return type;
  }

  public List<ParameterMapping> getParameterMappings() {
    return parameterMappings;
  }
  
  
}
