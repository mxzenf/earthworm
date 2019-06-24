package com.yx.earthworm.parser;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yx.earthworm.io.Resources;

/**
 * 
 * @author yangxin 2019年1月6日 下午6:54:47
 */
public class XPathParserTest {

  private InputStream is;
  private static final String FILE_NAME = "com/yx/earthworm/parser/students.xml";
  
  @Before
  public void init() {
    is = Resources.getResourceAsStream(FILE_NAME);
  }
  @Test
  public void resourcesGetAsStringTest(){
    Assert.assertNotNull(is);
  }
  @Test
  public void evalauteNodesTest() {
    XPathParser xpath = new XPathParser(is);
    List<XNode> nodes = xpath.evaluateNodes("/class/student");
    Assert.assertNotNull(nodes);
    Assert.assertTrue(2 == nodes.size());
    Assert.assertNotNull(nodes.get(0).getAttributes().get("id"));
  }
  
}
