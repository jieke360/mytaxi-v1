/**
 * 
 */
package com.great.mytaxi.fe.common.config;

import java.util.List;

import com.great.mytaxi.fe.common.dao.SysInfoDao;
import com.great.mytaxi.fe.entity.SysCodeInfo;

/**
 * @author xu.jianpu
 * 
 * 2012-8-23  下午06:21:31
 */
public class SysInfo {
	
	private SysInfoDao sysInfoDao ;

	public boolean init(){
		System.err.println(sysInfoDao);
		List<SysCodeInfo> infoList = sysInfoDao.getAll();
		System.err.println(infoList);
		return false;
		
	}
	
	/**
	 * 通过配置码获取配置值
	 * @param code	配置码
	 * @param defaultMsg	如果没有对应配置码的值，返回该默认值
	 * @return
	 */
	public static String getValueByCode(String code,String defaultMsg){
		return "";
	}

	public void setSysInfoDao(SysInfoDao sysInfoDao) {
		this.sysInfoDao = sysInfoDao;
	}

	public SysInfoDao getSysInfoDao() {
		return sysInfoDao;
	}
}
