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
import com.ngc.entity.User;
import com.ngc.entity.UserTrajectory;
import com.ngc.entity.ZTreeNode;
import com.ngc.service.CloudDiskFileSystemService;
import com.ngc.service.UserService;
import com.ngc.service.UtilService;

@Controller
@Scope("prototype")
@RequestMapping("/fs")
public class FileSystemController {
    @Resource
    private UserService userService;
    @Resource
    private CloudDiskFileSystemService fsService;
    @Resource
    private UtilService utilService;
    //通用接口
    @RequestMapping("/showFileSystem")
    public String showFileSystem(CloudDiskFileSystem fs,String fullPath,ModelMap map){
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

        map.put("fsList",fsList);
        map.put("result", result);
        if(fs.getFileIsHidden() == 0 && fs.getFileStatus() == 0){
            return "allFile";
        }else if(fs.getFileIsHidden() == 1 && fs.getFileStatus() == 0){
            return "hiddle";
        }else if(fs.getFileIsHidden() == 0 && fs.getFileStatus() == 1){
            return "drop";
        }else{
            return "error";
        }
    }

    @RequestMapping("/showUpFileSystem")
    public String showUpFileSystem(CloudDiskFileSystem fs,ModelMap map){
        Map<String,Object> result = new HashMap<String,Object>();

        if(StringUtils.isEmpty(fs.getAppID())){
            List<User> us =
                    userService.queryUserByPar(new User().setAppID(fs.getAppID()));
            if(us.size() != 1){
                //验证错误提示
                return "";
            }
        }

        CloudDiskFileSystem currentDir = new CloudDiskFileSystem();

        String fullPath = "/";
        if(fs.getId() == null || fs.getId() == -1){
            fs.setId(-1);
            fs.setFileFullName("//");
            fs.setCurrentPath("/");
            fs.setParentPath("/");
            fs.setParentId(-1);
        }else{
            List<CloudDiskFileSystem> currentDirList = this.fsService.queryCloudDiskFileSystem(fs);
            if(currentDirList.size() == 1){
                currentDir = currentDirList.get(0);
                List<CloudDiskFileSystem> parentDirList = this.fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(currentDir.getParentId()));
                if(parentDirList.size() == 1){
                    fs = parentDirList.get(0);
                    fullPath = fs.getFileFullName();
                }else{
                    fs.setId(-1);
                    fs.setFileFullName("//");
                    fs.setCurrentPath("/");
                    fs.setParentPath("/");
                    fs.setParentId(-1);
                }
            }
        }

        result.put("parentId", fs.getId());
        result.put("fileFullName", fs.getFileFullName());
        result.put("currentPath", fs.getCurrentPath());
        result.put("partenPath", fs.getParentPath());
        result.put("fullPath", fullPath);
        result.put("appID", fs.getAppID());
        result.put("isFileNameDesc", fs.getIsFileNameDesc());


        List<CloudDiskFileSystem> fsList =
                this.fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem()
                        .setAppID(fs.getAppID())
                        .setParentId((currentDir.getParentId() == null)?-1:currentDir.getParentId())
                        .setFileStatus(fs.getFileStatus())
                        .setFileIsHidden(fs.getFileIsHidden())
                        .setIsFileNameDesc(fs.getIsFileNameDesc()));

        map.put("fsList",fsList);
        map.put("result", result);

        if(fs.getFileIsHidden() == 0 && fs.getFileStatus() == 0){
            return "allFile";
        }else if(fs.getFileIsHidden() == 1 && fs.getFileStatus() == 0){
            return "hiddle";
        }else if(fs.getFileIsHidden() == null && fs.getFileStatus() == 1){
            return "drop";
        }else{
            return "error";
        }
    }

    @ResponseBody
    @RequestMapping("/fileUpload")
    public Object fileUpload(String appID,Integer isHidden,
                             String parentPath,
                             Integer parentId,
                             String currentPath,
                             String fullPath,
                             String fileName,
                             HttpServletRequest request){
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            //把Request强转成多部件请求对象
            MultipartHttpServletRequest multipartHttpServletRequest =
                    (MultipartHttpServletRequest) request;
            //根据文件名称获取文件对象
            CommonsMultipartFile commonsMultipartFile =
                    (CommonsMultipartFile) multipartHttpServletRequest.getFile(fileName);
            InputStream inputStream = commonsMultipartFile.getInputStream();//上传文件的输入流

            //1.将数据文件存入HDFS指定目录
            String fn = commonsMultipartFile.getOriginalFilename();//文件名
            String fnm = (new Date().getTime()) + fn;//映射的文件名
            CloudDiskFileSystem cdfs = this.utilService.uploadFileToDFS(appID, inputStream, fnm);
            //2.将数据文件的结构存库
            if(cdfs != null){
                cdfs.setAppID(appID);
                cdfs.setCurrentPath(currentPath);
                cdfs.setParentPath(parentPath);
                cdfs.setFileName(fn);
                cdfs.setFileNameMap(fnm);
                cdfs.setFileFullName(fullPath + "/" + fn);
                cdfs.setFileFullNameMap(fullPath + "/" + fnm);
                cdfs.setFileType(1);
                cdfs.setFileIsHidden(isHidden);
                cdfs.setCreateTime(new Date());
                cdfs.setUpdateTime(new Date());
                cdfs.setFileStatus(0);
                cdfs.setParentId(parentId);
                int r = this.fsService.insertCloudDiskFileSystem(cdfs);
                if(r == 1){
                    result.put("result", "success");
                }
            }else{
                result.put("result", "error");
                result.put("message", "文件上传HDFS失败");
                return result;
            }
            //3.记录行为
            utilService.insertUserTrajectory(
                    new UserTrajectory()
                            .setAppID(appID)
                            .setTrajectoryType(3)
                            .setExplain(fullPath + "/" + fn + ":上传于:"+ cdfs.getCreateTime()));

        } catch (Exception e) {
            result.put("result", "error");
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addDir")
    public Object addDir(String appID,
                         Integer isHidden,
                         String parentPath,
                         Integer parentId,
                         String currentPath,
                         String fullPath,
                         String dirName){
        Map<String,Object> result = new HashMap<String,Object>();

        Date d = new Date();

        CloudDiskFileSystem fs = new CloudDiskFileSystem()
                .setAppID(appID)
                .setFileName(dirName)
                .setFileNameMap((d.getTime())+dirName)
                .setFileFullName(fullPath+"/"+dirName)
                .setFileFullNameMap(fullPath+"/"+(d.getTime())+dirName)
                .setCurrentPath(currentPath)
                .setParentPath(parentPath)
                .setParentId(parentId)
                .setFileType(0)
                .setFileIsHidden(isHidden)
                .setFileStatus(0)
                .setFileSize(0L)
                .setCreateTime(new Date())
                .setUpdateTime(new Date());
        int r = this.fsService.insertCloudDiskFileSystem(fs);
        if(r == 1){
            result.put("result", "success");
        }else{
            result.put("result", "error");
        }
        return result;
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(String appID , Integer id, HttpServletRequest request,HttpServletResponse resp){
        List<CloudDiskFileSystem> fss =
                this.fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(id));
        if(fss.size() == 1){
            CloudDiskFileSystem fs = fss.get(0);
            resp.setContentType("text/html;charset=utf-8");
            BufferedOutputStream bos = null;
            String hdfsPath = this.utilService.getBasePath(appID) + "/" + fs.getFileNameMap();
            try {
                resp.setContentType("application/x-msdownload;");
                resp.setHeader("Content-disposition", "attachment; filename=" + new String(fs.getFileName().getBytes("utf-8"), "ISO8859-1"));
                resp.setHeader("Content-Length", String.valueOf(fs.getFileSize()));
                bos = new BufferedOutputStream(resp.getOutputStream());
                this.utilService.copyToLocalFile(hdfsPath, bos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(bos);
            }
        }
    }


    private List<ZTreeNode> result = new ArrayList<ZTreeNode>();
    List<String> idList = new ArrayList<String>();
    @ResponseBody
    @RequestMapping("/getZTreeeRootNode")
    public Object getZTreeeRootNode(
            String appID,
            Integer parentId,
            String ids){
        List<CloudDiskFileSystem> fsList = this.fsService.queryCloudDiskFileSystem(
                new CloudDiskFileSystem()
                        .setAppID(appID)
                        .setParentId(parentId)
                        .setFileType(0)
                        .setFileStatus(0)
        );
        if(!StringUtils.isEmpty(ids)){
            idList = Arrays.asList(ids.split(","));
        }
        for(CloudDiskFileSystem fs : fsList){
            if(!idList.contains(Integer.toString(fs.getId()))){
                result.add(
                        new ZTreeNode(
                                fs.getId(),
                                fs.getParentId(),
                                fs.getFileName(),
                                true,
                                true));
                getZTreeChildNode(fs.getId());
            }
        }

        return result;
    }
    //递归查根节点下的子节点
    private void getZTreeChildNode(Integer id){
        List<CloudDiskFileSystem> fsList = this.fsService.queryCloudDiskFileSystem(
                new CloudDiskFileSystem()
                        .setParentId(id)
                        .setFileType(0)
                        .setFileStatus(0)
        );
        if(fsList.size() > 0 ){
            for(CloudDiskFileSystem fs : fsList){
                if(!idList.contains(Integer.toString(fs.getId()))){
                    result.add(
                            new ZTreeNode(
                                    fs.getId(),
                                    fs.getParentId(),
                                    fs.getFileName(),
                                    true,
                                    true));
                    getZTreeChildNode(fs.getId());
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping("/getZTreeeNode")
    public Object getZTreeeNode(
            Integer id){
        List<ZTreeNode> result = new ArrayList<ZTreeNode>();
        List<CloudDiskFileSystem> fsList = this.fsService.queryCloudDiskFileSystem(
                new CloudDiskFileSystem()
                        .setParentId(id)
                        .setFileType(0)
                        .setFileStatus(0)
        );
        for(CloudDiskFileSystem fs : fsList){
            result.add(
                    new ZTreeNode(
                            fs.getId(),
                            fs.getParentId(),
                            fs.getFileName(),
                            true,
                            true));
        }
        return result;
    }



    @ResponseBody
    @RequestMapping("/moveOrCpFs")
    public Object moveFs(Integer nodeId,String ids,String appID,String fullPath,Integer flag){
        Map<String,Object> result = new HashMap<String,Object>();
        /*
         * parentID--->选中文件和目录的父节点ID变为指定目标的ID
         * 当前路径--->目录不变；文件变为指定目标
         * 父节点路径--->指定目标的父节点
         * fullName--->fullPath 替换为 目标fullPath
         * fullNameMap--->fullPath 替换为 目标fullPath
         * */
        if("/".equals(fullPath)){
            fullPath = "//";
        }
        List<CloudDiskFileSystem> nodeFs = this.fsService.queryCloudDiskFileSystem(
                new CloudDiskFileSystem().setId(nodeId)
        );
        if(nodeFs.size() == 1){
            CloudDiskFileSystem node = nodeFs.get(0);//目标节点
            //获取ID集合
            List<CloudDiskFileSystem> choseFsList = new ArrayList<CloudDiskFileSystem>();//需要修改的文件集合
            //匹配开始
            if(!StringUtils.isEmpty(ids)){
                String[] idArray = ids.split(",");
                for(String idStr : idArray){
                    if(!StringUtils.isEmpty(idStr)){
                        List<CloudDiskFileSystem> chose = this.fsService.queryCloudDiskFileSystem(
                                new CloudDiskFileSystem().setId(Integer.parseInt(idStr)));
                        if(chose.size() == 1){
                            CloudDiskFileSystem choseFs = chose.get(0);
                            choseFs.setParentId(node.getId());//parentID--->选中文件和目录的父节点ID变为指定目标的ID
                            if(choseFs.getFileType() == 0){//如果是目录，还需要将目录下所有的子路径匹配
                                List<CloudDiskFileSystem> choseChildNodeList = this.fsService.queryCloudDiskFileSystem(
                                        new CloudDiskFileSystem()
                                                .setAppID(appID)//防止冲突
                                                .setFileFullNameLike(choseFs.getFileFullName()));
                                for(CloudDiskFileSystem choseChildNode : choseChildNodeList){
                                    if(choseChildNode.getId() != choseFs.getId()){
                                        choseFsList.add(choseChildNode);
                                    }
                                }

                            }
                            choseFsList.add(choseFs);
                        }
                    }
                }
            }
            //匹配结束
            //移动--->修改/复制---->新增
            for(int i = 0 ; i < choseFsList.size() ; i ++){
                CloudDiskFileSystem fs = choseFsList.get(i);
                /*
                 * 当前路径--->目录不变；文件变为指定目标
                 * 父节点路径--->指定目标的父节点
                 * fullName--->fullPath 替换为 目标fullPath
                 * fullNameMap--->fullPath 替换为 目标fullPath
                 * */
                if(fs.getFileType() == 0){//目录
                    fs.setParentPath(node.getCurrentPath());
                }else{//文件
                    fs.setCurrentPath(node.getCurrentPath());
                    fs.setParentPath(node.getParentPath());
                }
                fs.setFileFullName(fs.getFileFullName().replace(fullPath, node.getFileFullName()+("//".equals(fullPath)?"/":"")));
                fs.setFileFullNameMap(fs.getFileFullNameMap().replace(fullPath, node.getFileFullName()+("//".equals(fullPath)?"/":"")));
            }

            int i = 0;
            if(flag == 0){
                i = this.fsService.moveFsList(choseFsList);
            }else if(flag == 1){
                i = this.fsService.cpFsList(choseFsList);
            }
            if(i == choseFsList.size()){
                result.put("result", "success");
            }else{
                result.put("result", "error");
            }
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeStatus")
    public Object changeStatus(String appID, Integer id){
        System.out.println("appID: " + appID);
        System.out.println("fsid: " + id);

        // 返回结果
        Map<String,Object> result = new HashMap<String,Object>();

        // 找到待删除条目
        List<CloudDiskFileSystem> fsList =
                fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(id));
        if(fsList.size() == 1){
            // 拿到待删除条目
            CloudDiskFileSystem cloudDiskFileSystem = fsList.get(0);

            // 修改status
            cloudDiskFileSystem.setFileStatus(1);

            // 更新至数据库
            if (fsService.updateCloudDiskFileSystem(cloudDiskFileSystem) == 1) {
                result.put("result", "success");
            }else{
                result.put("result", "error");
            }

        }else{
            result.put("result", "error");
        }
        return result;
    }
    @RequestMapping("/gotoIndex")
    public String gotoIndex(CloudDiskFileSystem fs,String fullPath,ModelMap map){
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
        return "index";
    }
    @ResponseBody
    @RequestMapping("/deleteTotally")
    public Object deleteTotally(String appID, Integer id){
        System.out.println("appID: " + appID);
        System.out.println("fsid: " + id);

        // 返回结果
        Map<String,Object> result = new HashMap<String,Object>();

        // 找到待删除条目
        List<CloudDiskFileSystem> fsList =
                fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(id));
        if(fsList.size() == 1){
            // 拿到待删除条目
            CloudDiskFileSystem cloudDiskFileSystem = fsList.get(0);

            // 修改status
            cloudDiskFileSystem.setFileStatus(2);

            // 更新至数据库
            if (fsService.updateCloudDiskFileSystem(cloudDiskFileSystem) == 1) {
                result.put("result", "success");
            }else{
                result.put("result", "error");
            }

        }else{
            result.put("result", "error");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/restoreItem")
    public Object restoreItem(String appID, Integer id){
        System.out.println("appID: " + appID);
        System.out.println("fsid: " + id);

        // 返回结果
        Map<String,Object> result = new HashMap<String,Object>();

        // 找到待恢复条目
        List<CloudDiskFileSystem> fsList =
                fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem().setId(id));
        if(fsList.size() == 1){
            // 拿到待恢复条目
            CloudDiskFileSystem cloudDiskFileSystem = fsList.get(0);

            // 修改status
            cloudDiskFileSystem.setFileStatus(0);

            // 更新至数据库
            if (fsService.updateCloudDiskFileSystem(cloudDiskFileSystem) == 1) {
                result.put("result", "success");
            }else{
                result.put("result", "error");
            }

        }else{
            result.put("result", "error");
        }
        return result;
    }

    @RequestMapping("/search")
    public String search(CloudDiskFileSystem fs,String fullPath,ModelMap map,String searchContent) {
        System.out.println(fs.toString());
        System.out.println(searchContent);
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

        List<CloudDiskFileSystem> fsAll =
                this.fsService.queryCloudDiskFileSystem(new CloudDiskFileSystem()
                        .setAppID(fs.getAppID())
                        .setParentId((fs.getParentId() == null)?-1:fs.getParentId())
                        .setFileStatus(fs.getFileStatus())
                        .setFileIsHidden(fs.getFileIsHidden())
                        .setIsFileNameDesc(fs.getIsFileNameDesc()));

        List<CloudDiskFileSystem> fsList = new ArrayList<CloudDiskFileSystem>();
        for (CloudDiskFileSystem cloudDiskFileSystem :
                fsAll) {
            if (cloudDiskFileSystem.getFileName().contains(searchContent)) {
                fsList.add(cloudDiskFileSystem);
            }
        }

        map.put("fsList",fsList);
        map.put("result", result);
        if(fs.getFileIsHidden() == 0 && fs.getFileStatus() == 0){
            return "allFile";
        }else if(fs.getFileIsHidden() == 1 && fs.getFileStatus() == 0){
            return "hiddle";
        }else if(fs.getFileIsHidden() == 0 && fs.getFileStatus() == 1){
            return "drop";
        }else{
            return "error";
        }
    }
}
