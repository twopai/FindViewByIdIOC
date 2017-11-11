package com.twopai.findviewbyidioc.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 作者：twopai on 2017/11/11.
 * 邮箱：twopai@hotmail.com
 * 注：重要一点，最后通过bind方法里面findviewbyid去执行
 */

public class ViewUtils {
    public static void bind(Activity activity){
        initBind(new ViewHelper(activity),activity);
    }
    public static void bind(View view){
        initBind(new ViewHelper(view),view);
    }
    public static void bind(View view,Object object){
        initBind(new ViewHelper(view),object);
    }

    /**
     * 1：bind字段
     * 2：bind方法
     * 3：使用辅助类获取统一的上下文进行findviewbyid
     */
    public static void initBind(ViewHelper viewHelper,Object clas){
        bindFields(viewHelper,clas);
        bindMethods(viewHelper,clas);
    }

    /**
     * 绑定方法
     * @param viewHelper
     * @param clas
     */
    private static void bindMethods(ViewHelper viewHelper, Object clas) {
        String checkNetStr="请检查你的网络";
        Class<?> aClass = clas.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] value = onClick.value();
                for (int v : value) {
                    View view = viewHelper.findViewById(v);
                    if (view != null) {
                        int viewId = view.getId();
                        IsCheckNet checkNet = method.getAnnotation(IsCheckNet.class);
                        boolean isCheckNet=checkNet!=null;
                        if (checkNet != null) {
                            int value1 = checkNet.value();
                            if (value1 == viewId) {
                                isCheckNet=true;
                                checkNetStr = checkNet.isCheckNet();
                            }else {
                                isCheckNet=false;
                            }
                        }
                        view.setOnClickListener(new DeclareOnClickListener(method,clas,isCheckNet,checkNetStr));
                    }
                }
            }
        }
    }

    /**
     * 绑定字段
     * @param viewHelper
     * @param clas
     */
    private static void bindFields(ViewHelper viewHelper, Object clas) {
        Class<?> aClass = clas.getClass();
        //注：是DeclaredFields
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的值
                int value = viewById.value();
                //找到该值对应的view
                View view = viewHelper.findViewById(value);
                if (view != null) {
                    //注入属性
                    field.setAccessible(true);
                    try {
                        field.set(clas,view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DeclareOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mClas;
        private boolean mIsCheckNet;
        private String mCheckNetStr;
        public DeclareOnClickListener(Method method, Object clas, boolean isCheckNet,String checkNetStr) {
            this.mMethod=method;
            this.mClas=clas;
            this.mIsCheckNet=isCheckNet;
            this.mCheckNetStr=checkNetStr;
        }

        @Override
        public void onClick(View v) {
            if (mIsCheckNet) {
                if (!isNet(v.getContext())) {
                    Toast.makeText(v.getContext(), mCheckNetStr, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            mMethod.setAccessible(true);
            try {
                mMethod.invoke(mClas,v);
                mMethod.invoke(mClas,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
