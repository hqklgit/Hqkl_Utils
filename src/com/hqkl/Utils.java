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
	 * �������ʽ��֤Email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * �������ʽ��֤����֤����
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * ��֤�ֻ����루֧�ֹ��ʸ�ʽ��+86135xxxx...(�й��ڵ�)��
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}
	/**
	 * ���һ�������
	 * @param listener	��Ҫ�������һ�����View�������¼���
	 * @param len		��������
	 * @param id		View��id
	 * @return			�����Ƿ�Ϊ��falseΪ��trueΪ�ҡ�
	 */
	public static boolean left_Right(OnTouchListener listener, final int len, final int id) {
		listener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (id == v.getId()) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						x = (int) event.getX();
						System.out.println("����ʱX:" + x);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						x1 = (int) event.getX();
						System.out.println("����ʱx1:" + x1);
						if (x == 0) {
							System.out.println("x = 0");
						} else {
							int temp = x - x1;
							System.out.println("���Ϊ��" + temp);
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
	 *  �ж��Ƿ�Ϊ����
	 * @param str	�ַ���
	 * @return	����һ�����飬���� int[0]����Ӣ����ĸ��,int[1] ������������int[2] ������������
	 * 			ע�⣺�����Ϊ���ķ��ţ������һ�����֡�
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
     * ��InputStreamת����String 
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
     * ȡ��ǰʱ��ǰһ��Date����
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