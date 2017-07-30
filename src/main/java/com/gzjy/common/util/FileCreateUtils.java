package com.gzjy.common.util;

import java.io.File;
import java.io.IOException;

/**
 * �ļ��Ĵ���ɾ����
 * @author chinamobile
 *
 */
public class FileCreateUtils {
	/**
	 * ���·�������ڣ��������Ѿ����ڣ�ֱ�ӷ���file����
	 * @param path
	 * @return
	 */
	public static File createFolderPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	/**
	 * ���·�������ڣ��������Ѿ����ڣ�ֱ�ӷ���file����
	 * @param file
	 * @return
	 */
	public static File createFolderPath(File file) {

		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static File createchildFolder(String parent, String childName) {
		File file = new File(createFolderPath(parent), childName);
		createFolderPath(file);
		return file;
	}

	public static File createchildFolder(File parent, String childName) {
		File file = new File(parent, childName);
		createFolderPath(file);
		return file;
	}

	public static File createFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		String fileName = file.getName();
		if (parent == null) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				
			}
			return file;
		}
		return createFile(parent, fileName);

	}

	public static boolean isExists(String Path) {
		File file = new File(Path);

		return file.exists();
	}

	public static boolean deleteFile(String fullPath) {
		File file = new File(fullPath);
		return deleteFile(file);
	}
	public static boolean deleteFile(File file) {
		if (file.isFile()) {
			return file.delete();
		}
		return false;
	}

	public static File createFile(String parent, String fileName) {
		File file = new File(parent);
		return createFile(file, fileName);
	}

	public static File createFile(File parent, String fileName) {

		File file = new File(parent, fileName);

		try {

			if (!parent.exists()) {
				parent.mkdirs();
			}

			file.createNewFile();
		} catch (IOException e) {
			
			return null;
		}
		return file;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			// �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		// Ŀ¼��ʱΪ�գ�����ɾ��
		return dir.delete();
	}
}
