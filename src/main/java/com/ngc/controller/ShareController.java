package com.ngc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.ngc.entity.CloudDiskFileSystem;
import com.ngc.entity.ShareFileSystem;
import com.ngc.entity.SharePw;
import com.ngc.entity.User;
import com.ngc.entity.UserTrajectory;
import com.ngc.entity.ZTreeNode;
import com.ngc.service.CloudDiskFileSystemService;
import com.ngc.service.ShareService;
import com.ngc.service.UserService;
import com.ngc.service.UtilService;

@Controller
@Scope("prototype")
@RequestMapping("/share")
public class ShareController {
	@Resource
	private CloudDiskFileSystemService fsService;
	@Resource
	private ShareService shareService;
	@Resource
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/createShare")
	public Object createShare(String appID , Integer id){
		Map<String,Object> result = new HashMap<String,Object>();
		List<CloudDiskFileSystem> fsList = 
				fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(id));
		if(fsList.size() == 1){
			CloudDiskFileSystem fs = fsList.get(0);
			
			String token = UUID.randomUUID().toString();
			
			if(fs.getFileType() == 1){//文件----直接存
				//存文件
				this.shareService.insertShareFileSystem(
							new ShareFileSystem().setId(fs.getId())
							.setToken(token)
							.setFileName(fs.getFileName())
							.setFileNameMap(fs.getFileNameMap())
							.setFileFullName("//"+fs.getFileName())
							.setFileFullNameMap("//"+fs.getFileNameMap())
							.setFileType(1)
							.setParentId(-1)
							.setParentPath("/")
							.setCurrentPath("/")
							.setFileSize(fs.getFileSize())
							.setShareAppId(fs.getAppID())
							.setCreateTime(new Date())
						);
				
			}else if(fs.getFileType() == 0){//找到该目录和该目录以下全部内容
				String fullPath = fs.getFileFullName();
				fsList = this.fsService.queryCloudDiskFileSystem(
						new CloudDiskFileSystem().setAppID(appID)
							.setFileFullNameLike(fullPath)
						);
				String newFullPath = fs.getFileFullNameMap().replace("/"+fs.getFileNameMap(), "");
				for(int i = 0 ; i < fsList.size() ; i ++){
					CloudDiskFileSystem f = fsList.get(i);
					f.setFileFullName(f.getFileFullName().replace(newFullPath, "/"));
					f.setFileFullNameMap(f.getFileFullNameMap().replace(newFullPath, "/"));
					//存目录
					this.shareService.insertShareFileSystem(
							new ShareFileSystem().setId(f.getId())
							.setToken(token)
							.setFileName(f.getFileName())
							.setFileNameMap(f.getFileNameMap())
							.setFileFullName(f.getFileFullName())
							.setFileFullNameMap(f.getFileFullNameMap())
							.setFileType(f.getFileType())
							.setParentId(f.getParentId())
							.setParentPath(f.getParentPath())
							.setCurrentPath(f.getCurrentPath())
							.setFileSize(f.getFileSize())
							.setShareAppId(f.getAppID())
							.setCreateTime(new Date())
						);
				}
			}
			SharePw pw = new SharePw().setAppID(appID)
			.setToken(token)
				.setPw(Integer.toString(new Random().nextInt(10000)));
			int i = this.shareService.insertShareFileSystemPw(pw);
			if(i == 1){
				result.put("result", "success");
				result.put("pw", pw);
			}else{
				result.put("result", "error");
			}
		}
		return result;
	}
	
	@RequestMapping("/unsalfToSharePage")
	public String unsalfToSharePage(CloudDiskFileSystem fs,String fullPath,ModelMap map){
		Map<String,Object> result = new HashMap<String,Object>();

		if(StringUtils.isEmpty(fs.getAppID())){
			List<User> us =
					userService.queryUserByPar(new User().setAppID(fs.getAppID()));
			if(us.size() != 1){
				//验证错误提示
				return "";
			}
		}

		String fileFullName = "//";
		String currentPath = fs.getCurrentPath() == null ? "/" : fs.getCurrentPath();
		String partenPath = fs.getParentPath() == null ? "/" : fs.getParentPath();
		String fp = fullPath == null ? "/" : fullPath;
		if(fs.getParentId()!=null){
			List<CloudDiskFileSystem> fsl = this.fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(fs.getParentId()));
			if(fsl.size() == 1){
				fileFullName = fsl.get(0).getFileFullName();
			}
		}
		result.put("parentId", (fs.getParentId() == null)?-1:fs.getParentId());
		result.put("fileFullName", fileFullName);
		result.put("currentPath", currentPath);
		result.put("partenPath", partenPath);
		result.put("fullPath", fp);
		result.put("appID", fs.getAppID());
		result.put("isFileNameDesc", fs.getIsFileNameDesc());

		List<CloudDiskFileSystem> fsList =
				this.fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem()
						.setAppID(fs.getAppID())
						.setParentId((fs.getParentId() == null)?-1:fs.getParentId())
						.setFileStatus(fs.getFileStatus())
						.setFileIsHidden(fs.getFileIsHidden())
						.setIsFileNameDesc(fs.getIsFileNameDesc()));

		map.put("appID", fs.getAppID());
		map.put("fsList",fsList);
		map.put("result", result);
		return "friend";
	}
}
