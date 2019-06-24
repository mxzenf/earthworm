package com.yx.earthworm.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import oracle.net.aso.k;
import oracle.net.aso.s;

/**
 * 
 * @author yangxin 2019年3月7日 下午7:56:00
 */
public class ParameterExpression extends HashMap<String, String> {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Properties properties = new Properties();

  public ParameterExpression(String content){
    parse(content);
  }

  private void parse(String content) {
    String[] strings = content.split(",");
    StringBuffer sb = new StringBuffer();
    
    for (int i = 1; i < strings.length; i++) {
      sb.append(strings[i]);
      sb.append(System.lineSeparator());
    }
    try {
      properties.load(IOUtils.toInputStream(sb.toString(), Charset.forName("utf-8")));
    } catch (IOException e) {
    }
    properties.put("property", strings[0]);
    
    properties.keySet().forEach(k -> {
      put((String)k, (String)properties.get(k));
    });
  }

  
}
