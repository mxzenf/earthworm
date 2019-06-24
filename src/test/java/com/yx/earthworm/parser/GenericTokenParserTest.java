package com.yx.earthworm.parser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class GenericTokenParserTest {

  @Test
  public void parse_test() {
    GenericTokenParser parser = new GenericTokenParser("#{", "}", new TokenHandler() {

      @Override
      public String handleToken(String content) {
        System.out.println(content);
        return "?";
      }
    });
    String originalSql = "select aac002 from ac01 where aac001 = #{aac001,javaType=java.lang.String}";
    System.out.println(parser.parse(originalSql));
  }

  @Test
  public void properties_test() throws IOException {
    ParameterExpression pe = new ParameterExpression(
        "aac001,javaType=java.lang.String");
    System.out.println(pe);
  }
}
