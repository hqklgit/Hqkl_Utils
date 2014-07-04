package com.hqkl.impl;

import java.io.File;

import android.os.Handler;

public interface HttpDownload {
	public Handler handler = null;
	/**
	 * 开始下载
	 */
	public void onStartDownLoad();
	/**
	 * 正在下载
	 * @param p		当前进度
	 * @param l		总大小
	 */
	public void onDownloading(int p,int l);
	/**
	 * 下载结束
	 * @param file	下载的文件
	 */
	public void onSuccessDownLoad(File file);
	/**
	 * 下载出错
	 */
	public void onError();
	/**
	 * 下载成功
	 * @param stream 返回的数据流
	 */
	public void onSuccessDownLoad(Object stream);
}
