package com.twopai.findviewbyidioc.ioc;

/**
 * 作者：twopai on 2017/11/11.
 * 邮箱：twopai@hotmail.com
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 位置定位：字段
 * 运行时调用
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    //一个
    int value();
}
