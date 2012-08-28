package com.great.mytaxi.common.util;

import com.great.mytaxi.common.cond.KeyCond;

public interface KeyDao {

	/**
	 * 获得主键
	 * @param keyCond
	 * @return
	 */
	public String getSeqNo(KeyCond keyCond);
}
