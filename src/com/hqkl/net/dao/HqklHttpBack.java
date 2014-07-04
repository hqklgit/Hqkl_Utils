package com.hqkl.net.dao;

import com.hqkl.impl.HttpImpl;

public class HqklHttpBack<T extends Object> implements HttpImpl<T> {
	/**
	 * 开始下载
	 */
	@Override
	public void onStart() {
		
	}
	@Override
	/**
	 * 下载中...
	 * @d 		//当前下载大小
	 * @c		//总大小
	 * 			//单位：字节
	 */
	public void onDown(int d, int c) {
		
	}

	@Override	
	/**
	 * 下载错误
	 */
	public void onError(Exception e) {
		
	}

	@Override
	/**
	 * 下载完成
	 */
	public void onSuccess(Object t) {
		
	}

	@Override
	/**
	 * 每秒下载速度（单位：字节）
	 */
	public void onTime(long kb) {
		
	}

	@Override
	/**
	 * 预计下载完成时间(单位：秒)
	 */
	public void onSecond(int s) {
		
	}

}
