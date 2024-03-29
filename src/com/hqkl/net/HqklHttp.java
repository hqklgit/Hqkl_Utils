package com.hqkl.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Message;

import com.hqkl.net.dao.HqklHttpBack;
import com.hqkl.net.dao.PostHandler;

/**
 * Http类
 * 
 * @author 石文平
 * 
 */
public class HqklHttp {
	private String charsetName = "UTF-8";
	private int timeout = 0;
	private String method = "GET";
	private Map<String, Object> map = new HashMap<String, Object>();
	private boolean isN = true;

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 设置一般请求属性。
	 * 
	 * @param field
	 * @param newValue
	 */
	public void setHeader(String field, String newValue) {
		map.put(field, newValue);
	}

	/**
	 * get方式下载数据
	 * 
	 * @param url
	 *            URL
	 * 
	 * @param back
	 *            回调类
	 */
	public void get(final String url, HqklHttpBack<? extends Object> back) {
		final PostHandler ph = new PostHandler(back);
		new Thread(new Runnable() {
			Message message = null;

			@Override
			public void run() {
				int d = 0, c = 0;
				byte[] bs = null;
				InputStream is = null;
				try {
					URL url2 = new URL(url);
					HttpURLConnection connection = (HttpURLConnection) url2
							.openConnection();
					// download.onStart();
					message = ph.handler.obtainMessage();
					message.what = PostHandler.ON_START;
					ph.handler.sendMessage(message);
					// System.out.println("开始下载");
					connection
							.setRequestProperty("Accept-Encoding", "identity");
					connection.connect();
					c = connection.getContentLength();
					is = connection.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bs = new byte[c <= 0 ? 1024 : c];
					int len = -1;
					long start_time = System.currentTimeMillis();
					int down = 0;
					while ((len = is.read(bs)) != -1) {
						d = d + len;
						down = down + len;
						baos.write(bs, 0, len);
						// download.onDowning(d, c);
						// System.out.println("当前大小:"+d+"，总大小:" +c);
						message = ph.handler.obtainMessage();
						message.what = PostHandler.ONRUNING;
						message.arg1 = d;
						message.arg2 = c;
						ph.handler.sendMessage(message);
						long timeLeas = System.currentTimeMillis() - start_time;
						if (timeLeas >= 1000) {
							start_time = System.currentTimeMillis();
							message = ph.handler
									.obtainMessage(PostHandler.OUTTIME);
							message.arg1 = down;
							ph.handler.sendMessage(message);
							if (down != 0) {
								message = ph.handler
										.obtainMessage(PostHandler.SECOND);
								message.arg1 = c / down;
								ph.handler.sendMessage(message);
							}
							down = 0;
							isN = false;
						}
					}
					if (isN) {
						message = ph.handler.obtainMessage(PostHandler.OUTTIME);
						message.arg1 = down;
						ph.handler.sendMessage(message);
						down = 0;
					}
					message = ph.handler.obtainMessage(PostHandler.SECOND);
					message.arg1 = 0;
					ph.handler.sendMessage(message);
					// System.out.println("下载完成。");
					message = ph.handler.obtainMessage(
							PostHandler.ONSUCCESSDOWNLOAD_INPUT, new String(
									baos.toByteArray(), charsetName));
					ph.handler.sendMessage(message);
					is.close();
				} catch (MalformedURLException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				} catch (IOException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				}

			}
		}).start();

	}

	/**
	 * post方式下载数据
	 * 
	 * @param url
	 *            地址
	 * @param valuePairs
	 *            参数列表
	 * @param back
	 *            回调事件
	 */
	public void post(final String url,
			final List<BasicPostValuePair> valuePairs,
			HqklHttpBack<? extends Object> back) {
		final PostHandler ph = new PostHandler(back);
		isN = true;
		new Thread(new Runnable() {
			InputStream is = null;
			Message message = null;
			int d = 0;
			int count = 0;
			byte[] bs = null;

			@Override
			public void run() {
				try {
					URL url1 = new URL(url);
					HttpURLConnection c = (HttpURLConnection) url1
							.openConnection();
					c.setDoInput(true);
					c.setDoOutput(true);
					c.setRequestMethod("POST");
					c.setUseCaches(false);
					c.setInstanceFollowRedirects(true);
					c.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					c.setRequestProperty("Accept-Encoding", "identity");
					message = ph.handler.obtainMessage();
					message.what = PostHandler.ON_START;
					ph.handler.sendMessage(message);
					c.connect();
					DataOutputStream out = new DataOutputStream(c
							.getOutputStream());
					StringBuffer sb = new StringBuffer();
					sb.append("");
					for (BasicPostValuePair basicPostValuePair : valuePairs) {
						sb.append(basicPostValuePair.getName()
								+ "="
								+ URLEncoder.encode(
										basicPostValuePair.getValue(),
										charsetName));
					}
					out.writeBytes(sb.toString());
					out.flush();
					is = c.getInputStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					count = c.getContentLength();
					bs = new byte[count];
					int len = -1;
					long start_time = System.currentTimeMillis();
					int down = 0;
					while ((len = is.read(bs)) != -1) {
						down = down + len;
						d = d + len;
						bos.write(bs, 0, len);
						message = ph.handler.obtainMessage();
						message.what = PostHandler.ONRUNING;
						message.arg1 = d;
						message.arg2 = count;
						ph.handler.sendMessage(message);
						long end_time = System.currentTimeMillis() - start_time;
						if (end_time >= 1000) {
							start_time = System.currentTimeMillis();
							message = ph.handler
									.obtainMessage(PostHandler.OUTTIME);
							message.arg1 = down;
							ph.handler.sendMessage(message);
							if (down != 0) {
								message = ph.handler
										.obtainMessage(PostHandler.SECOND);
								message.arg1 = count / down;
								ph.handler.sendMessage(message);
							}
							down = 0;
							isN = false;
						}

					}
					if (isN) {
						message = ph.handler.obtainMessage(PostHandler.OUTTIME);
						message.arg1 = down;
						ph.handler.sendMessage(message);
						down = 0;
					}
					message = ph.handler.obtainMessage(PostHandler.SECOND);
					message.arg1 = 0;
					ph.handler.sendMessage(message);
					bos.flush();
					message = ph.handler.obtainMessage(
							PostHandler.ONSUCCESSDOWNLOAD_INPUT,
							new String(bos.toByteArray(), charsetName));
					ph.handler.sendMessage(message);
					is.close();
				} catch (MalformedURLException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				} catch (IOException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				}
			}
		}).start();
	}

	/**
	 * GET方式下载文件
	 * 
	 * @param url
	 * @param path
	 * @param back
	 */
	public void getDownLoad(final String url, final String path,
			final HqklHttpBack<Object> back) {
		final PostHandler ph = new PostHandler(back);
		new Thread(new Runnable() {
			Message message = null;

			@Override
			public void run() {
				int d = 0, c = 0;
				byte[] bs = null;
				InputStream is = null;
				try {
					URL url2 = new URL(url);
					HttpURLConnection connection = (HttpURLConnection) url2
							.openConnection();
					message = ph.handler.obtainMessage();
					message.what = PostHandler.ON_START;
					ph.handler.sendMessage(message);
					connection
							.setRequestProperty("Accept-Encoding", "identity");
					connection.connect();
					c = connection.getContentLength();
					is = connection.getInputStream();
					bs = new byte[c <= 0 ? 1024 : c];
					File file = new File(path);
					if (!file.isFile()) {
						file.createNewFile();
					}
					FileOutputStream baos = new FileOutputStream(file);
					int len = -1;
					long start_time = System.currentTimeMillis();
					int down = 0;
					while ((len = is.read(bs)) != -1) {
						d = d + len;
						down = down + len;
						baos.write(bs, 0, len);
						message = ph.handler.obtainMessage();
						message.what = PostHandler.ONRUNING;
						message.arg1 = d;
						message.arg2 = c;
						ph.handler.sendMessage(message);
						long timeLeas = System.currentTimeMillis() - start_time;
						if (timeLeas >= 1000) {
							start_time = System.currentTimeMillis();
							message = ph.handler
									.obtainMessage(PostHandler.OUTTIME);
							message.arg1 = down;
							ph.handler.sendMessage(message);
							isN = false;
							if (down != 0) {
								message = ph.handler
										.obtainMessage(PostHandler.SECOND);
								message.arg1 = c / down;
								ph.handler.sendMessage(message);
							}
							down = 0;
						}
					}
					if (isN) {
						message = ph.handler.obtainMessage(PostHandler.OUTTIME);
						message.arg1 = down;
						ph.handler.sendMessage(message);
						down = 0;
					}
					baos.flush();
					baos.close();
					// System.out.println("下载完成。");
					message = ph.handler.obtainMessage(PostHandler.SECOND);
					message.arg1 = 0;
					ph.handler.sendMessage(message);
					message = ph.handler.obtainMessage(
							PostHandler.ONSUCCESSDOWNLOAD_INPUT, file);
					ph.handler.sendMessage(message);
					is.close();
				} catch (MalformedURLException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				} catch (IOException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				}
			}
		}).start();
	}
	public void postDownLoad(final String url,final String path,final List<BasicPostValuePair> valuePairs,HqklHttpBack<Object> back){
		final PostHandler ph = new PostHandler(back);
		isN = true;
		new Thread(new Runnable() {
			InputStream is = null;
			Message message = null;
			int d = 0;
			int count = 0;
			byte[] bs = null;

			@Override
			public void run() {
				try {
					URL url1 = new URL(url);
					HttpURLConnection c = (HttpURLConnection) url1
							.openConnection();
					c.setDoInput(true);
					c.setDoOutput(true);
					c.setRequestMethod("POST");
					c.setUseCaches(false);
					c.setInstanceFollowRedirects(true);
					c.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					c.setRequestProperty("Accept-Encoding", "identity");
					message = ph.handler.obtainMessage();
					message.what = PostHandler.ON_START;
					ph.handler.sendMessage(message);
					c.connect();
					DataOutputStream out = new DataOutputStream(c
							.getOutputStream());
					StringBuffer sb = new StringBuffer();
					sb.append("");
					for (BasicPostValuePair basicPostValuePair : valuePairs) {
						sb.append(basicPostValuePair.getName()
								+ "="
								+ URLEncoder.encode(
										basicPostValuePair.getValue(),
										charsetName));
					}
					out.writeBytes(sb.toString());
					out.flush();
					is = c.getInputStream();
					FileOutputStream bos = new FileOutputStream(path);
					count = c.getContentLength();
					bs = new byte[count];
					int len = -1;
					long start_time = System.currentTimeMillis();
					int down = 0;
					while ((len = is.read(bs)) != -1) {
						down = down + len;
						d = d + len;
						bos.write(bs, 0, len);
						message = ph.handler.obtainMessage();
						message.what = PostHandler.ONRUNING;
						message.arg1 = d;
						message.arg2 = count;
						ph.handler.sendMessage(message);
						long end_time = System.currentTimeMillis() - start_time;
						if (end_time >= 1000) {
							start_time = System.currentTimeMillis();
							message = ph.handler
									.obtainMessage(PostHandler.OUTTIME);
							message.arg1 = down;
							ph.handler.sendMessage(message);
							if (down != 0) {
								message = ph.handler
										.obtainMessage(PostHandler.SECOND);
								message.arg1 = count / down;
								ph.handler.sendMessage(message);
							}
							down = 0;
							isN = false;
						}

					}
					if (isN) {
						message = ph.handler.obtainMessage(PostHandler.OUTTIME);
						message.arg1 = down;
						ph.handler.sendMessage(message);
						down = 0;
					}
					message = ph.handler.obtainMessage(PostHandler.SECOND);
					message.arg1 = 0;
					ph.handler.sendMessage(message);
					bos.flush();
					message = ph.handler.obtainMessage(
							PostHandler.ONSUCCESSDOWNLOAD_INPUT,new File(path));
					ph.handler.sendMessage(message);
					bos.close();
					is.close();
				} catch (MalformedURLException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				} catch (IOException e) {
					message = ph.handler.obtainMessage(PostHandler.ONERROE, e);
					ph.handler.sendMessage(message);
				}
			}
		}).start();
	}
}
