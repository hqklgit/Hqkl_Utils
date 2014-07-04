package com.hqkl.impl;

@SuppressWarnings("hiding")
public interface HttpImpl<T> {
	/**
	 * 开始下载
	 */
	public void onStart();
	/**
	 * 下载中...
	 * @param d 	当前大小
	 * @param c		总共大小
	 * 
	 * 				（单位：字节）
	 */
	public void onDown(int d,int c);
	/**
	 * 下载错误
	 * @param e		错误详情
	 */
	public void onError(Exception e);
	/**
	 * 下载完成
	 * @param t		下载完成
	 */
	public void onSuccess(Object t);
	/**
	 * 每秒下载速度
	 * @param kb	(单位：字节)	需要自己转换
	 */
	public void onTime(long kb);
	/**
	 * 预计下载完成时间
	 * @param s(单位：秒)
	 */
	public void onSecond(int s);
	
}
