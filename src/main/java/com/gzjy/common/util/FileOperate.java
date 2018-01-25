package com.gzjy.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileOperate {
	/**
	 * 复制文件 @param oldPath @param newPath @throws
	 */
	public static void copyFile(String oldPath, String newPath) {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int bytesum = 0;
				int byteread = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			} else
				throw new Exception("模板文件不存在");
		} catch (Exception e) {
			System.out.println("复制文件出错");
			e.printStackTrace();			
		} finally {
			try {
				inStream.close();
				fs.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public static void deleteFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			try {
				file.delete();
			} catch (Exception e) {
				System.out.println("文件" + filePath + "删除失败");
				throw new IOException("临时模板文件删除失败");
			}
		}
	}
	/**
	 * 删除目录下所有文件
	 * @param filePath
	 */
	public static void deleteDir(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				File temp = new File(children[i]);
				temp.delete();
			}
		}
	}

}
