package com.hqkl.activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hqkl.notes.ContentView;
import com.hqkl.notes.FindView;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
public class HqklActivity extends Activity{
	private String tag = "HqklActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		contentView(this);
		super.onCreate(savedInstanceState);
		System.out.println("HqklActivity");
		injectView(this);
	}
	/** 实例化@FindView 注解的成员*/
    public void injectView(final Activity activity)  
    {
    	System.out.println("injectView");
        Field[] fields = activity.getClass().getDeclaredFields();   //得到Activity中的所有定义的字段  
        if (fields != null && fields.length > 0)
        {  
            for (Field field : fields)  
            {  
                if (field.isAnnotationPresent(FindView.class))                    //方法返回true，如果指定类型的注解存在于此元素上  
                {
                    Log.i("Field", field.toString());  
                     FindView mInjectView = field.getAnnotation(FindView.class); //获得该成员的annotation  
                    if (mInjectView != null) {
                        int viewId = mInjectView.id();                                  //获得该注解的id  
                        View view=activity.findViewById(viewId);                        //获得ID为viewID的组件对象  
                        final String mString = mInjectView.onClick();
                        final String onTo= mInjectView.onTouch();
                        try  
                        {
                            field.setAccessible(true);  //设置类的私有成员变量可以被访问  
                            field.set(activity, view);  //field.set(object,value)===object.fieldValue = value  
                        } catch (Exception e) { e.printStackTrace();}  
                        if (!mString.equals("")) {
                       	 view.setOnClickListener(new OnClickListener() {
    							@Override
    							public void onClick(View v) {
    								try {
    									Method method = activity.getClass().getDeclaredMethod(mString, View.class);
    									method.invoke(activity, v);
    								} catch (NoSuchMethodException e) {
//    									e.printStackTrace();
    									Log.e(tag, "沒有這個方法。");
    								} catch (IllegalAccessException e) {
    									Log.e(tag, "调用了私有方法，请改public。");
    								} catch (IllegalArgumentException e) {
										Log.e(tag, "参数不合法。");
									} catch (InvocationTargetException e) {
										Log.e(tag, "未知错误。");
									}
    							}
    						});
                       	 	if (!onTo.equals("")) {
                               	view.setOnTouchListener(new OnTouchListener() {
        							@Override
        							public boolean onTouch(View arg0, MotionEvent arg1) {
        								try {
        									Method method = activity.getClass().getDeclaredMethod(onTo, View.class,MotionEvent.class);
        									method.invoke(activity, arg0,arg1);
        								}  catch (NoSuchMethodException e) {
        									Log.e(tag, "沒有這個方法。");
        								} catch (IllegalAccessException e) {
        									Log.e(tag, "调用了私有方法，请改public。");
        								} catch (IllegalArgumentException e) {
        									Log.e(tag, "参数不合法。");
        								} catch (InvocationTargetException e) {
        									Log.e(tag, "未知错误。");
        								}
        								return false;
        							}
        						});
							}
						}
					}else{
						Log.i("Field", "没有注解，为空。");
					}
                    
                }else{  
                    Log.i("Field", "该字段没有被注解");  
                }
                Log.i("field:", field.toString());
            }  
        }  
    } 
    public void contentView(Activity activity){
    	System.out.println("contentView");
  	ContentView contentView  =  activity.getClass().getAnnotation(ContentView.class);
  	if (contentView!= null) {
  	Log.i("contentView",contentView.layout()+"");
  	activity.setContentView(contentView.layout());
	}
    }
}
