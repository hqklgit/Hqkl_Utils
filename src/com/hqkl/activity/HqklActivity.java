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
	/** ʵ����@FindView ע��ĳ�Ա*/
    public void injectView(final Activity activity)  
    {
    	System.out.println("injectView");
        Field[] fields = activity.getClass().getDeclaredFields();   //�õ�Activity�е����ж�����ֶ�  
        if (fields != null && fields.length > 0)
        {  
            for (Field field : fields)  
            {  
                if (field.isAnnotationPresent(FindView.class))                    //��������true�����ָ�����͵�ע������ڴ�Ԫ����  
                {
                    Log.i("Field", field.toString());  
                     FindView mInjectView = field.getAnnotation(FindView.class); //��øó�Ա��annotation  
                    if (mInjectView != null) {
                        int viewId = mInjectView.id();                                  //��ø�ע���id  
                        View view=activity.findViewById(viewId);                        //���IDΪviewID���������  
                        final String mString = mInjectView.onClick();
                        final String onTo= mInjectView.onTouch();
                        try  
                        {
                            field.setAccessible(true);  //�������˽�г�Ա�������Ա�����  
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
    									Log.e(tag, "�]���@��������");
    								} catch (IllegalAccessException e) {
    									Log.e(tag, "������˽�з��������public��");
    								} catch (IllegalArgumentException e) {
										Log.e(tag, "�������Ϸ���");
									} catch (InvocationTargetException e) {
										Log.e(tag, "δ֪����");
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
        									Log.e(tag, "�]���@��������");
        								} catch (IllegalAccessException e) {
        									Log.e(tag, "������˽�з��������public��");
        								} catch (IllegalArgumentException e) {
        									Log.e(tag, "�������Ϸ���");
        								} catch (InvocationTargetException e) {
        									Log.e(tag, "δ֪����");
        								}
        								return false;
        							}
        						});
							}
						}
					}else{
						Log.i("Field", "û��ע�⣬Ϊ�ա�");
					}
                    
                }else{  
                    Log.i("Field", "���ֶ�û�б�ע��");  
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
