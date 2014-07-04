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
//					Log.i("PostHandler", "��ʼ����");
					break;
				case ONERROE:
					load.onError((Exception)msg.obj);
//					Log.i("PostHandler", "��ʼ����");
					break;
				case ONRUNING:
					load.onDown(msg.arg1, msg.arg2);
//					Log.i("PostHandler", "��ǰ��С:" + msg.arg1 +"���ܴ�С"+msg.arg2);
					break;
				case OUTTIME:
					load.onTime(msg.arg1);
//					Log.i("PostHandler", "��ǰ�����ٶ�:" + Long.parseLong(msg.obj.toString()) / 1024 + "kb");
					break;
				case ONSUCCESSDOWNLOAD_INPUT:
					load.onSuccess(msg.obj);
//					Log.i("PostHandler", "��ʼ���");
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
