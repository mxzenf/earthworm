package com.yx.earthworm.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yangxin 2019年3月4日 下午1:32:22
 */
public enum JdbcType {
  
  CHAR(Types.CHAR),
  VARCHAR(Types.VARCHAR),
  NULL(Types.NULL),
  BLOB(Types.BLOB),
  CLOB(Types.CLOB),
  DATE(Types.DATE),
  BOOLEAN(Types.BOOLEAN)
  ;
  
  public final int TYPE_CODE;
  public final static Map<Integer, JdbcType> codeLookup = new HashMap<>();
  
  static {
    for (JdbcType jdbcType : JdbcType.values()) {
      codeLookup.put(jdbcType.TYPE_CODE, jdbcType);
    }
  }

  private JdbcType(int TYPE_CODE){
    this.TYPE_CODE = TYPE_CODE;
  }
  
  public static JdbcType forCode(int code) {
    return codeLookup.get(code);
  }
}
