package com.yx.earthworm.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Getter;

public class MethodInvoker implements Invoker {
  @Getter
  private Method method;
  
  public MethodInvoker(Method method) {
    this.method = method;
  }

  @Override
  public Object invoke(Object target, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    return method.invoke(target, args);
  }

}
