package com.twopai.findviewbyidioc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.twopai.findviewbyidioc.ioc.IsCheckNet;
import com.twopai.findviewbyidioc.ioc.OnClick;
import com.twopai.findviewbyidioc.ioc.ViewById;
import com.twopai.findviewbyidioc.ioc.ViewUtils;

/**
 * 思路：用类去反射找到该类的所有变量和方法名，再分别获取其注解类，再获取其相应的value值，通过findviewbyId找到该view，然后注入属性或方法
 * 注：使用之前请将assets文件夹下面的ViewIOC.jar当做本地插件导入as中使用
 */
public class IOCActivity extends AppCompatActivity {

    @ViewById(R.id.text1)
    private TextView mText1;
    @ViewById(R.id.text2)
    private TextView mText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioc);
        ViewUtils.bind(this);
//        text1.setText("text1");
    }

    @OnClick({R.id.text1,R.id.text2})
    @IsCheckNet(isCheckNet = "没网",value = R.id.text2)
    private void text1Click(View v) {
        switch (v.getId()){
            case R.id.text1:
                Toast.makeText(this, "Ti", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text2:
                Toast.makeText(this, "T2", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
