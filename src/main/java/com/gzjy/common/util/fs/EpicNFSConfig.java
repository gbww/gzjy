package com.gzjy.common.util.fs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by szt on 2016/11/22.
 */
@ConfigurationProperties("epic.nfs")
public class EpicNFSConfig {
  private String baseDir="/var/lib/docs";
  private String[] userNames={"crab","zebra","nami","moria"};

  public String getBaseDir() {
    return baseDir;
  }

  public void setBaseDir(String baseDir) {
    this.baseDir = baseDir;
  }

  public String[] getUserNames() {
    return userNames;
  }

  public void setUserNames(String[] userNames) {
    this.userNames = userNames;
  }
}
