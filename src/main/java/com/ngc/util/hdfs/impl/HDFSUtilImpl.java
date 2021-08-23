package com.ngc.util.hdfs.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngc.util.hdfs.HDFSUtil;

@Component
public class HDFSUtilImpl implements HDFSUtil{
	
	private String ip;
	private String port;
	private String basePath;
	private String user;
	private String isBata;
	private String hadoopDll;
	private String hadoopDir;
	private String url;
	
	public HDFSUtilImpl(){
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(
					new File(HDFSUtilImpl.class
								.getResource("/hdfs.properties")
									.getPath()
										.replaceAll("%20", " "))));
			this.ip = p.getProperty("IP");
			if(StringUtils.isEmpty(this.ip)){
				throw new Exception("HADOOP������IP����Ϊ��");
			}
			this.port = p.getProperty("PORT");
			if(StringUtils.isEmpty(this.port)){
				throw new Exception("HADOOP������˿ڡ�����Ϊ��");
			}
			this.url = "hdfs://"+this.ip + ":" + this.port;
			
			this.basePath = p.getProperty("BASE_PATH");
			if(StringUtils.isEmpty(this.basePath)){
				this.basePath = "/";
			}
			
			this.user = p.getProperty("USER");
			if(StringUtils.isEmpty(this.user)){
				this.user = "root";
			}
			
			this.isBata = p.getProperty("BATA");
			if(StringUtils.isEmpty(this.isBata)){
				this.isBata = "0";
			}
			
			if("1".equals(this.isBata)){
				this.hadoopDll = p.getProperty("HADOOP_DLL");
				this.hadoopDir = p.getProperty("HADOOP_DIR");
				System.load(this.hadoopDll);//ǿ�Ƽ���
				System.setProperty("HADOOP_USER_NAME", this.user);//���÷����û�����Ĭ���ǲ���ϵͳ��ǰ�û�
				System.setProperty("hadoop.home.dir", this.hadoopDir);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	
	}
	
	@Override
	public boolean isFile(String path) {
		// TODO Auto-generated method stub
		try {
			// �����ļ����ö���
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", this.url);
			configuration.set("dfs.client.use.datanode.hostname", "true");
			configuration.set("dfs.replication", "1");
			configuration.set("dfs.block.size", "3145728");
			
			// ��ȡ�ļ�ϵͳ
			FileSystem fileSystem = FileSystem.get(configuration);
			Path p = new Path(path);
			boolean result = fileSystem.isFile(p);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isDirectory(String path) {
		// TODO Auto-generated method stub
		try {
			// �����ļ����ö���
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", url);
			configuration.set("dfs.client.use.datanode.hostname", "true");
			configuration.set("dfs.replication", "1");
			configuration.set("dfs.block.size", "3145728");
			
			// ����URL
//			URI uri = URI.create(url);
			// ��ȡ�ļ�ϵͳ
			FileSystem fileSystem = FileSystem.get(configuration);
			Path p = new Path(path);
			boolean result = fileSystem.isDirectory(p);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean exists(String path) {
		// TODO Auto-generated method stub
		try {
			// �����ļ����ö���
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", url);
			configuration.set("dfs.client.use.datanode.hostname", "true");
			configuration.set("dfs.replication", "1");
			configuration.set("dfs.block.size", "3145728");
			
			// ����URL
//			URI uri = URI.create(url);
			// ��ȡ�ļ�ϵͳ
			FileSystem fileSystem = FileSystem.get(configuration);
			Path p = new Path(path);
			boolean result = fileSystem.exists(p);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean mkdirs(String path) {
		// TODO Auto-generated method stub
		try {
			// �����ļ����ö���
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", url);
			configuration.set("dfs.client.use.datanode.hostname", "true");
			configuration.set("dfs.replication", "1");
			configuration.set("dfs.block.size", "3145728");
			
			// ����URL
//			URI uri = URI.create(url);
			// ��ȡ�ļ�ϵͳ
			FileSystem fileSystem = FileSystem.get(configuration);
			Path p = new Path(path);
			boolean result = fileSystem.mkdirs(p);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean copyFromLocalFile(InputStream input, String hdfsPath) {
		// TODO Auto-generated method stub
		FSDataOutputStream outputStream = null;
		try {
			// �����ļ����ö���
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", url);
			configuration.set("dfs.client.use.datanode.hostname", "true");
			configuration.set("dfs.replication", "1");
			configuration.set("dfs.block.size", "3145728");
			
			// ����URL
//			URI uri = URI.create(url);
			// ��ȡ�ļ�ϵͳ
			FileSystem fileSystem = FileSystem.get(configuration);
			Path p = new Path(hdfsPath);
			outputStream = fileSystem.create(p);
			IOUtils.copyBytes(input, outputStream,4096 ,false);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(input);
			IOUtils.closeStream(outputStream);
		}
		return false;
	}

	@Override
	public long copyToLocalFile(String hdfsPath,OutputStream output) throws Exception {
		// TODO Auto-generated method stub
		// �����ļ����ö���
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", url);
		configuration.set("dfs.client.use.datanode.hostname", "true");
		configuration.set("dfs.replication", "1");
		configuration.set("dfs.block.size", "3145728");
		
		// ����URL
//			URI uri = URI.create(url);
		// ��ȡ�ļ�ϵͳ
		FileSystem fileSystem = FileSystem.get(configuration);
		FSDataInputStream fsDataInputStream = fileSystem.open(new Path(hdfsPath));
		IOUtils.copyBytes(fsDataInputStream, output, 4096);
		return fsDataInputStream.available();//�����ļ�����
	}

	@Override
	public String getBasePath() {
		// TODO Auto-generated method stub
		return this.basePath;
	}

	@Override
	public FileStatus getFileStatus(String path) {
		// TODO Auto-generated method stub
		try {
			// �����ļ����ö���
			Configuration configuration = new Configuration();
			configuration.set("fs.defaultFS", url);
			configuration.set("dfs.client.use.datanode.hostname", "true");
			configuration.set("dfs.replication", "1");
			configuration.set("dfs.block.size", "3145728");
			
			// ����URL
//			URI uri = URI.create(url);
			// ��ȡ�ļ�ϵͳ
			FileSystem fileSystem = FileSystem.get(configuration);
			return fileSystem.getFileStatus(new Path(path));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
