package com.twopai.findviewbyidioc.ioc;

import android.app.Activity;
import android.view.View;

/**
 * 作者：twopai on 2017/11/11.
 * 邮箱：twopai@hotmail.com
 */

public class ViewHelper {
    private Activity mActivity;
    private View mView;

    public ViewHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public ViewHelper(View mView) {
        this.mView = mView;
    }
    public View findViewById(int res){
        return mActivity!=null?mActivity.findViewById(res):mView.findViewById(res);
    }
}
