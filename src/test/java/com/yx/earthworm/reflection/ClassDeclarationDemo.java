package com.yx.earthworm.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author yangxin 2019年2月12日 下午4:33:15
 */
public class ClassDeclarationDemo {
  
  enum ClassMember {CONSTRUCTOR, METHOD, FIELD,};

  public static void main(String[] args) {
    Class<?> clazz = ConcurrentHashMap.class;
    //类全称
    System.out.println("全称:" + clazz.getCanonicalName());
    //修饰符
    System.out.println("修饰符:" + Modifier.toString(clazz.getModifiers()));
    //实现接口
    System.out.println("实现接口:" + Arrays.asList(clazz.getInterfaces()));
    //父类
    System.out.println("父类" + Arrays.asList(clazz.getSuperclass()));
    //注解
    System.out.println("注解" + Arrays.asList(clazz.getAnnotations()));
    
    for (ClassMember member : ClassMember.values()) {
      switch (member) {
      case CONSTRUCTOR:
        printMembers(clazz.getConstructors(),"构造函数");
        break;
      case METHOD:
        printMembers(clazz.getMethods(),"声明方法");
        break;
      case FIELD:
        printMembers(clazz.getFields(),"声明属性");
        break;
      default:
        break;
      }
    }
  }
  
  private static void printMembers(Member[] members, String s){
    System.out.println(s);
    for (Member member : members) {
      if (member instanceof Constructor) {
        System.out.println(((Constructor)member).toGenericString());
      } else if (member instanceof Method) {
        System.out.println(((Method)member).toGenericString());
      } else if (member instanceof Field) {
        System.out.println(((Field)member).toGenericString());
      }
    }
  }
}
