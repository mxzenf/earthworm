package com.yx.earthworm.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.yx.earthworm.reflection.invoker.Invoker;
import com.yx.earthworm.reflection.wrapper.BeanWrapper;

/**
 * 
 * @author yangxin 2019年2月18日 下午7:42:23
 */
public class MetaClassTest {

  @Test
  public void getAndSetInvoker_test() throws IllegalAccessException, InvocationTargetException {
    MetaClass metaClass = MetaClass.forClass(Student.class);
    assertNotNull(metaClass);
    Student student = new Student("杨欣", 11, "男");
    Invoker setInvoker1 = metaClass.getSetInvoker("age");
    setInvoker1.invoke(student, new Object[]{10});
    Invoker setInvoker2 = metaClass.getSetInvoker("name");
    setInvoker2.invoke(student, new Object[]{"yangxin"});
    assertEquals(student.getAge(),10);
    assertEquals(student.getName(),"yangxin");
  }
  
  @Test
  public void getGetterTypeTest(){
    Student student = new Student("杨欣", 11, "男");
    BeanWrapper bw = new BeanWrapper(student);
    System.out.println(bw.getGetterType("age"));
  }
}
