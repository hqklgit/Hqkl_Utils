package com.hqkl.notes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 获取控件
 * @author 石文平
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FindView {
	/**
	 * 控件ID
	 * @return
	 */
	int id() default -1;
	/**
	 * 事件
	 * @return
	 */
	String onClick() default "";
	/**
	 * 事件
	 * @return
	 */
	String onTouch() default "";
}
