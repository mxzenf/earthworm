package com.yx.earthworm.builder;
/**
 * 
 * @author yangxin 2019年4月10日 下午3:10:22
 */
public class ResultMap {

  private String id;
  private Class<?> type;
  
  private ResultMap(){}

  public String getId() {
    return id;
  }
  
  public static class Builder{
    private ResultMap resultMap = new ResultMap();
    
    public Builder(String id, Class<?> type){
      if (null == id || 0 == id.length()) {
        throw new BuilderException("ResultMap的ID不能为空");
      }
      resultMap.id = id;
      resultMap.type = type;
    }
    
    public ResultMap build(){
      return resultMap;
    }
  }

  public void setId(String id) {
    this.id = id;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }
  
}
