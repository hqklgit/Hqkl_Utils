package com.hqkl.view.utils;

import com.hqkl.domain.Wh_Class;

import android.view.View;

public class HqklUtils {
		/**
		 * 获取控件宽高。
		 * @param view	控件view。
		 * @return	返回Wh_Class对象。
		 */
		public static Wh_Class GetViewW_H(View view){
			Wh_Class point = new Wh_Class();
			int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
		      int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
		      view.measure(w, h);
		      int height =view.getMeasuredHeight();
		      int width =view.getMeasuredWidth();
		      point.setWidth(width);
		      point.setHeight(height);
			return point;
		}
}
