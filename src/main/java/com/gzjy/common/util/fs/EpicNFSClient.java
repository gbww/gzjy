package com.gzjy.common.util.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.FileCreateUtils;

/**
 * Created by szt on 2016/10/12.
 */
public class EpicNFSClient {
  private static Logger logger = LoggerFactory.getLogger(EpicNFSClient.class);
  //  private FTPClient ftpClient;
  private String localPaht;

  // private String baseLocalPath;
  private InputStream downStream;

  private EpicNFSClient() {
  }

  protected static EpicNFSClient getInstance(String hostDir, String userName, String password, int port) {
    EpicNFSClient epcFtpClient = new EpicNFSClient();
    epcFtpClient.localPaht = hostDir + "/" + userName;

    return epcFtpClient;
  }

  /**
   * 注：io自己记得关闭
   */
  public void upload(InputStream localInputStream, String remote) throws IOException {
    remote = preDealPath(remote);

    File file = FileCreateUtils.createchildFolder(localPaht, getParentPath(remote));
    File newFile = FileCreateUtils.createFile(file, getFileName(remote));

    FileUtils.writeByteArrayToFile(newFile, IOUtils.toByteArray(localInputStream));
  }
  public String getPath(String filePath)throws IOException {
      filePath = preDealPath(filePath);
      return localPaht+"/"+filePath;
  }

  public InputStream download(String filePath) throws IOException {
    filePath = preDealPath(filePath);
    File file = new File(localPaht, filePath);
    if (file.exists()) {
      FileInputStream inputStream = new FileInputStream(file);
      downStream = inputStream;
      return inputStream;
    } else {
      throw new BizException("文件系统中找不到下载文件");
    }
  }

  /**
   * 关闭ftp连接，调用后，本实例所有方法失效
   */
  public void close() {
    if (downStream != null) {
      try {
        downStream.close();
        downStream = null;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 创建远程目录结构,创建成功或者已存在返回 true
   */
  public boolean createRemoteDir(String path) {
    path = preDealPath(path);
    logger.debug("创建{}目录", path);
    File folderPath = FileCreateUtils.createchildFolder(this.localPaht, path);

    return true;


  }

  public boolean deleteRemoteFile(String remotePath) {
    remotePath = preDealPath(remotePath);
    File file = new File(this.localPaht, remotePath);
    return FileCreateUtils.deleteFile(file);

  }

  public boolean deleteRemoteDir(String remotePath) {
    remotePath = preDealPath(remotePath);
    File file = new File(this.localPaht, remotePath);
    return FileCreateUtils.deleteDir(file);
  }

  public boolean hasRemoteFile(String remotePath) {
    // 检查远程文件是否存在
    remotePath = preDealPath(remotePath);
    return FileCreateUtils.isExists(this.localPaht + "/" + remotePath);
  }

  protected String getParentPath(String path) {
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    int index = path.lastIndexOf("/") + 1;
    String dirName = path.substring(index);
    String substring = path.substring(0, index);
    if (StringUtils.isNotEmpty(substring) && !substring.equals("./")) {
      return substring;
    }
    return "";
  }

  protected String getFileName(String path) {

    int index = path.lastIndexOf("/") + 1;
    String fileName = path.substring(index, path.length());

    return fileName;
  }

  public boolean hasRemoteDir(String remotePath) {
    remotePath = preDealPath(remotePath);
    return FileCreateUtils.isExists(this.localPaht + "/" + remotePath);

  }

  protected String preDealPath(String path) {
    path = path.replaceAll("^(/|\\\\)*", "");
    path = path.replace("\\", "/");
    return path;
  }
}
