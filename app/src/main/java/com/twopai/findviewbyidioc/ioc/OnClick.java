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
 * 运行时
 * 方法
 * 可多个
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    int[] value();
}
