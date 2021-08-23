package com.ngc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ngc.dao.CloudDiskFileSystemDao;
import com.ngc.entity.CloudDiskFileSystem;
import com.ngc.service.CloudDiskFileSystemService;

@Service
@Scope("prototype")
public class CloudDiskFileSystemServiceImpl implements CloudDiskFileSystemService{

	@Resource
	private CloudDiskFileSystemDao dao;

	@Override
	public List<CloudDiskFileSystem> queryCloudDiskFileSystem(CloudDiskFileSystem fs) {
		// TODO Auto-generated method stub
		return dao.queryCloudDiskFileSystem(fs);
	}

	public int updateCloudDiskFileSystem(CloudDiskFileSystem fs){
		return dao.updateCloudDiskFileSystem(fs);
	}

	@Override
	public int insertCloudDiskFileSystem(CloudDiskFileSystem fs) {
		// TODO Auto-generated method stub
		return dao.insertCloudDiskFileSystem(fs);
	}

	@Override
	public int moveFsList(List<CloudDiskFileSystem> fsList) {
		// TODO Auto-generated method stub
		int i = 0;
		for(CloudDiskFileSystem fs : fsList){
			i += dao.updateCloudDiskFileSystem(fs.setUpdateTime(new Date()));
		}
		return i;
	}
	@Override
	@SuppressWarnings("unchecked")
	public int cpFsList(List<CloudDiskFileSystem> fsList) {
		// TODO Auto-generated method stub
		Integer sum = 0;

		Map<Integer,List<CloudDiskFileSystem>> tree = new HashMap<Integer,List<CloudDiskFileSystem>>();
		List<Integer> ids = new ArrayList<Integer>();
		Collections.sort(fsList);
		for(CloudDiskFileSystem fs : fsList){
			if(!ids.contains(fs.getParentId())){
				ids.add(fs.getParentId());
			}
			List<CloudDiskFileSystem> fl =
					tree.get(fs.getParentId()) == null ?
							new ArrayList<CloudDiskFileSystem>() :
							tree.get(fs.getParentId());
			fl.add(fs);
			tree.put(fs.getParentId(), fl);
		}
		for(Integer id : ids){
			List<CloudDiskFileSystem> fs = tree.get(id);
			for(CloudDiskFileSystem f : fs){
				String uuid = UUID.randomUUID().toString();
				Integer oldId = f.getId();
				int r = this.dao.insertCloudDiskFileSystem(f.setUuid(uuid));
				if(r == 1){
					Integer newId = this.dao.queryCloudDiskFileSystem(
							new CloudDiskFileSystem().setUuid(uuid)).get(0).getId();
					List<CloudDiskFileSystem> fls = tree.get(oldId);
					if(fls != null){
						for(int i = 0 ; i < fls.size() ; i ++){
							CloudDiskFileSystem cdf = fls.get(i);
							cdf.setParentId(newId);
						}
					}
				}
				sum += r;
			}
		}
		return sum;
	}

}
