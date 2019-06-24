package com.yx.earthworm.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author yangxin 2019年1月25日 下午2:49:08
 */
public class ClassDemo {

  @Test
  public void objectClass_test(){
    Assert.assertTrue( "".getClass() == String.class );
    Assert.assertTrue("" instanceof String);
    byte[] bytes = new byte[10];
    Assert.assertTrue(bytes.getClass() == byte[].class);
    Set<String> sets = new HashSet<String>();
    Assert.assertTrue(sets.getClass() == HashSet.class);
  }
  @Test
  public void constructor_test() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    Constructor construct1 = Student.class.getConstructor();
    Constructor construct2 = Student.class.getConstructor(Class.class);
    Student student1 = (Student)construct1.newInstance();
    Student student2 = (Student)construct2.newInstance();
    student1.setName("1号");
    student2.setName("2号");
    System.err.println(student1);
    System.err.println(student2);
  }
  public static void main(String[] args) {
    Float float1 = .01f;
    System.out.println(float1.toString());
    System.out.println(System.nanoTime());
    System.out.println(System.currentTimeMillis());
  }
}
