package com.hqkl.net.dao;

import com.hqkl.impl.HttpImpl;

public class HqklHttpBack<T extends Object> implements HttpImpl<T> {
	/**
	 * ��ʼ����
	 */
	@Override
	public void onStart() {
		
	}
	@Override
	/**
	 * ������...
	 * @d 		//��ǰ���ش�С
	 * @c		//�ܴ�С
	 * 			//��λ���ֽ�
	 */
	public void onDown(int d, int c) {
		
	}

	@Override	
	/**
	 * ���ش���
	 */
	public void onError(Exception e) {
		
	}

	@Override
	/**
	 * �������
	 */
	public void onSuccess(Object t) {
		
	}

	@Override
	/**
	 * ÿ�������ٶȣ���λ���ֽڣ�
	 */
	public void onTime(long kb) {
		
	}

	@Override
	/**
	 * Ԥ���������ʱ��(��λ����)
	 */
	public void onSecond(int s) {
		
	}

}
