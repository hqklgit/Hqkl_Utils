package com.hqkl.net.dao;

import java.io.File;

import com.hqkl.impl.HttpDownload;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PostHandler{
	public static final int ON_START = 0;
	public static final int ONRUNING = 1;
	public static final int ONERROE = 2;
	public static final int OUTTIME = 3;
	public static final int ONSUCCESSDOWNLOAD_INPUT = 4;
	public static final int SECOND = 5;
	public Handler handler;
	@SuppressLint("HandlerLeak")
	public PostHandler( final HqklHttpBack<?> load) {
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case ON_START:
					load.onStart();
//					Log.i("PostHandler", "开始下载");
					break;
				case ONERROE:
					load.onError((Exception)msg.obj);
//					Log.i("PostHandler", "开始出错");
					break;
				case ONRUNING:
					load.onDown(msg.arg1, msg.arg2);
//					Log.i("PostHandler", "当前大小:" + msg.arg1 +"，总大小"+msg.arg2);
					break;
				case OUTTIME:
					load.onTime(msg.arg1);
//					Log.i("PostHandler", "当前下载速度:" + Long.parseLong(msg.obj.toString()) / 1024 + "kb");
					break;
				case ONSUCCESSDOWNLOAD_INPUT:
					load.onSuccess(msg.obj);
//					Log.i("PostHandler", "开始完成");
					break;
					case SECOND:
						load.onSecond(msg.arg1);
						break;
				default:
					break;
				}
			}
		};
	}
	
}
