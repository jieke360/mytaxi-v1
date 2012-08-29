/**
 * 
 */
package com.great.mytaxi.fe.common.dao;

import java.util.List;
import java.util.Map;

import com.great.mytaxi.fe.entity.SysCodeInfo;



/**
 * 
 * 	系统配置参数等数据库操作
 * 
 * @author xu.jianpu
 * 
 * 2012-8-23  下午06:39:17
 */
public interface SysInfoDao {
	
	public List<SysCodeInfo> getAll();
	
	public List<Map<String, Object>> getNameAndValueByCode(List<Object> parameters);
	
	
}
