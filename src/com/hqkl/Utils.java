package com.hqkl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Utils {
	private static final int BUFFER_SIZE = 2048;
	private static int x;
	private  static int x1;
	private static boolean isLeft = false;
	/**
	 * 正则表达式验证Email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 正则表达式验证身份证号码
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...(中国内地)）
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}
	/**
	 * 左右滑动方法
	 * @param listener	需要监听左右滑动的View，设置事件。
	 * @param len		滑动长度
	 * @param id		View的id
	 * @return			返回是否为左，false为左，true为右。
	 */
	public static boolean left_Right(OnTouchListener listener, final int len, final int id) {
		listener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (id == v.getId()) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						x = (int) event.getX();
						System.out.println("按下时X:" + x);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						x1 = (int) event.getX();
						System.out.println("弹起时x1:" + x1);
						if (x == 0) {
							System.out.println("x = 0");
						} else {
							int temp = x - x1;
							System.out.println("结果为：" + temp);
							if (temp > len) {
								isLeft = true;
							} else if (temp < -len) {
								isLeft = false;
							}
						}
					}
				}
				return true;
			}
		};
		return isLeft;
	}
	
	/**
	 *  判断是否为汉字
	 * @param str	字符串
	 * @return	返回一个数组，其中 int[0]代表英文字母数,int[1] 代表文字数，int[2] 代表符号数。
	 * 			注意：如符号为中文符号，则代表一个汉字。
	 */
	public static int[] isCharacters(String str){
		int[] is = new int[3];
		int a = 0,b = 0,c = 0;
		for (int i = 0; i < str.length(); i++) {
			String temp = str.substring(i,i+1);
			if (temp.matches("[a-zA-Z]+")) {
				a = a+1;
				is[0] = a;
			}else{
				if (temp.getBytes().length == 2) {
					b = b+1;
					is[1] = b;
				}else{
					c = c+1;
					is[2] = c;
					
				}
			}
		}
		return is;
	}
	/** 
     * 将InputStream转换成String 
     * @param in InputStream 
     * @return String 
     * @throws Exception 
     *  
     */  
    public static String InputStreamTOString(InputStream in,String charsetName) throws Exception{  
          
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return new String(outStream.toByteArray(),charsetName);  
    }
    /**
     * 取当前时间前一天Date对象
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
}
