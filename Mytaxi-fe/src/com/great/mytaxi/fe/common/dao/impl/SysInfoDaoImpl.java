/**
 * 
 */
package com.great.mytaxi.fe.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.great.mytaxi.fe.common.dao.BaseDAO;
import com.great.mytaxi.fe.common.dao.SysInfoDao;
import com.great.mytaxi.fe.entity.SysCodeInfo;

/**
 * @author xu.jianpu
 * 
 * 2012-8-29  下午03:08:14
 */
@Repository
public class SysInfoDaoImpl extends BaseDAO<SysCodeInfo> implements SysInfoDao{
	
	private static final Log log = LogFactory.getLog(SysInfoDaoImpl.class);


	@SuppressWarnings("unchecked")
	public List<SysCodeInfo> getAll() {
		List<SysCodeInfo> result = null;
		String sql = " FROM SysCodeInfo ";
		result = super.getJpaTemplate().find(sql);
		return result;
	}

	public List<Map<String, Object>> getNameAndValueByCode(
			List<Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
