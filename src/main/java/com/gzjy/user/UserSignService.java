package com.gzjy.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gzjy.common.exception.BizException;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;
import com.gzjy.user.mapper.UserSignMapper;
import com.gzjy.user.model.User;
import com.gzjy.user.model.UserSign;

@Service
public class UserSignService {

@Autowired
private UserSignMapper fileMapper;

@Autowired
private UserService userClient;

@Autowired
private EpicNFSService epicNFSService;


@Transactional
public UserSign upload(MultipartFile file) throws IOException {
    User currentUser=new User();
    currentUser=userClient.getCurrentUser();
  // 使用文件系统      
        if (file.getSize() / (1024 * 1024) > 10) {
                throw new BizException("文件[" + file.getOriginalFilename()
                        + "]过大，请上传大小不超过10M的文件");
            }
       

        EpicNFSClient epicNFSClient = epicNFSService.getClient("gzjy");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String parentPath = "sign" ;
        if (!epicNFSClient.hasRemoteDir(parentPath)) {
            epicNFSClient.createRemoteDir(parentPath);
        }

       
        // 保存文件记录
        UserSign sign = new UserSign();
        sign.setId(currentUser.getId());
        sign.setName(file.getOriginalFilename());
        // 判断文件名的后缀
        String filename = file.getOriginalFilename();
        String type = filename.indexOf(".") != -1 ? filename.substring(
                filename.lastIndexOf(".")+1, filename.length()) : null;

        sign.setPath(parentPath + "/" + file.getOriginalFilename());
        sign.setType(type);
        sign.setCreatedAt(new Date());
        UserSign oldsign = new UserSign();
        
        oldsign=fileMapper.selectByPrimaryKey(currentUser.getId());
        if(oldsign!=null) {
            epicNFSClient.deleteRemoteFile(oldsign.getPath());
            fileMapper.updateByPrimaryKey(sign);
            epicNFSClient.upload(file.getInputStream(),
                    sign.getPath());
        }
        else {
          
            fileMapper.insert(sign);
            epicNFSClient.upload(file.getInputStream(),
                    sign.getPath());
        }          
        epicNFSClient.close();
        return sign;
    }

public String getPathByCurrentUser() {
    String path="";
    User currentUser=new User();
    currentUser=userClient.getCurrentUser();
    String id=currentUser.getId();
    UserSign signFile = fileMapper
            .selectByPrimaryKey(id);
    if (signFile == null) {
        throw new BizException("文件不存在，或者已经被删除");
    }
    EpicNFSClient epicNFSClient = null;
    epicNFSClient = epicNFSService.getClient("gzjy");
    try {
        path=epicNFSClient.getPath(signFile.getPath());
    } catch (IOException e) {
        throw new BizException("文件查看失败"+e.getMessage());
    }
    return path;
    
    
}


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ResponseEntity<byte[]> download(String id) throws IOException {

        // 使用文件系统
        UserSign signFile = fileMapper
                .selectByPrimaryKey(id);
        if (signFile == null) {
            throw new BizException("文件不存在，或者已经被删除");
        }

        EpicNFSClient epicNFSClient = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            epicNFSClient = epicNFSService.getClient("gzjy");
            inputStream = epicNFSClient.download(signFile.getPath());
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new BizException("文件下载失败");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (epicNFSClient != null) {
                    epicNFSClient.close();
                }
            } catch (IOException e) {
                throw new BizException("IO流关闭失败");
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       
        headers.setContentDispositionFormData("attachment",
                signFile.getName());
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers,
                HttpStatus.CREATED);
    }
    
    public UserSign getUserSign(String id) {
        UserSign sign=fileMapper.selectByPrimaryKey(id);
        if(sign==null) {
            throw new BizException("为上传电子签名");
        }
        return sign;
        
    }
    
    public List<HashMap<String, String>> getUserSignList(ArrayList<String> userIdList) {
    	return fileMapper.getSignList(userIdList);       
    }

}
