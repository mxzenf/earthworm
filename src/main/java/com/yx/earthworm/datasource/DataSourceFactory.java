package com.yx.earthworm.datasource;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * 
 * @author yangxin 2019年4月20日 下午6:31:44
 */
public interface DataSourceFactory {

  DataSource getDataSource();
  
  void setProperties(Properties props);
  
}
