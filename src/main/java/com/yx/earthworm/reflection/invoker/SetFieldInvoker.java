package com.yx.earthworm.reflection.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
/**
 * 
 * @author yangxin 2019年2月15日 下午3:18:22
 */
public class SetFieldInvoker implements Invoker {

  private Field field;
  
  public SetFieldInvoker(Field field) {
    this.field = field;
  }
  
  @Override
  public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
    field.set(target, args[0]);
    return null;
  }

}
