package com.yx.earthworm.reflection.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
/**
 * 
 * @author yangxin 2019年2月15日 下午3:17:11
 */
public class GetFieldInvoker implements Invoker {
  
  private Field field;
  
  public GetFieldInvoker(Field field) {
    this.field = field;
  }

  @Override
  public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
    return field.get(target);
  }

}
