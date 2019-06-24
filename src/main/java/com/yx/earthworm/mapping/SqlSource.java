package com.yx.earthworm.mapping;
/**
 * 
 * @author yangxin 2019年2月27日 下午1:54:39
 */
public interface SqlSource {

  BoundSql getBoundSql(Object parameterObject);
  
}
