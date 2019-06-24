package com.yx.earthworm.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author yangxin 2019年2月15日 下午3:13:44
 */
public interface Invoker {

  Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;
  
}
