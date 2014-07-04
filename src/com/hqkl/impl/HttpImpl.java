package com.hqkl.impl;

@SuppressWarnings("hiding")
public interface HttpImpl<T> {
	/**
	 * ��ʼ����
	 */
	public void onStart();
	/**
	 * ������...
	 * @param d 	��ǰ��С
	 * @param c		�ܹ���С
	 * 
	 * 				����λ���ֽڣ�
	 */
	public void onDown(int d,int c);
	/**
	 * ���ش���
	 * @param e		��������
	 */
	public void onError(Exception e);
	/**
	 * �������
	 * @param t		�������
	 */
	public void onSuccess(Object t);
	/**
	 * ÿ�������ٶ�
	 * @param kb	(��λ���ֽ�)	��Ҫ�Լ�ת��
	 */
	public void onTime(long kb);
	/**
	 * Ԥ���������ʱ��
	 * @param s(��λ����)
	 */
	public void onSecond(int s);
	
}
