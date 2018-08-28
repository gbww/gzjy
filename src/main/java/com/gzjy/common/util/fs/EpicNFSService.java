package com.gzjy.common.util.fs;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import com.gzjy.common.exception.BizException;

/**
 * Created by szt on 2016/11/22.
 */
@Service
@EnableConfigurationProperties(EpicNFSConfig.class)
@Conditional(EpicNFSCondition.class)
public class EpicNFSService {
  @Autowired
  private EpicNFSConfig nfsConfig;
  public EpicNFSClient getClient(String name) {
    if (!ArrayUtils.contains(nfsConfig.getUserNames(),name)) {
      throw new BizException("文件服务器用户错误！");
    }
    EpicNFSClient instance = EpicNFSClient.getInstance(nfsConfig.getBaseDir(), name, null, 0);
    return instance;
  }
}
