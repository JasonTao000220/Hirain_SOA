package com.hirain.hirain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hirain.hirain.fragment.AlluseFragment;
import com.hirain.hirain.fragment.CarsetFragment;
import com.hirain.hirain.fragment.FirstFragment;
import com.hirain.hirain.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //todo 按钮 自定义模式添加按钮， 另存新模式，  保存；
    private Button tianjiaview,newview,baoc;

    //todo 二级联动
    private ViewPager viewPager;
    public RadioGroup radioGroup;
    private List<Fragment> fragmentList=new ArrayList<>();
    private RadioButton r1,r2,r3,r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo 按钮
        tianjiaview=findViewById(R.id.firsttianjia);
        newview=findViewById(R.id.newview);
        baoc=findViewById(R.id.baoc);

        //todo 初始化控件
        //按钮切换
        r1=findViewById(R.id.radio_1);
        r2=findViewById(R.id.radio_2);
        r3=findViewById(R.id.radio_3);
        r4=findViewById(R.id.radio_4);
        //二级联动
        viewPager=findViewById(R.id.viewpaget_main);
        radioGroup=findViewById(R.id.radiogroup_main);

        fragmentList.add(new UserFragment());
        fragmentList.add(new FirstFragment());
        fragmentList.add(new CarsetFragment());
        fragmentList.add(new AlluseFragment());

        //todo viewPager适配器
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        //todo 二级联动 viewpaget绑定radiogrup
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 switch (i){
                     case R.id.radio_1:
                         viewPager.setCurrentItem(0);
                         r1.setBackgroundResource(R.mipmap.meno);
                         r2.setBackgroundResource(R.mipmap.homeno);
                         r3.setBackgroundResource(R.mipmap.carsetno);
                         r4.setBackgroundResource(R.mipmap.viewno);
                         break;
                     case R.id.radio_2:
                         viewPager.setCurrentItem(1);
                         r1.setBackgroundResource(R.mipmap.my);
                         r2.setBackgroundResource(R.mipmap.home);
                         r3.setBackgroundResource(R.mipmap.carsetno);
                         r4.setBackgroundResource(R.mipmap.viewno);
                         break;
                     case R.id.radio_3:
                         viewPager.setCurrentItem(2);
                         r1.setBackgroundResource(R.mipmap.my);
                         r2.setBackgroundResource(R.mipmap.homeno);
                         r3.setBackgroundResource(R.mipmap.carset);
                         r4.setBackgroundResource(R.mipmap.viewno);
                         break;
                     case R.id.radio_4:
                         viewPager.setCurrentItem(3);
                         r1.setBackgroundResource(R.mipmap.my);
                         r2.setBackgroundResource(R.mipmap.homeno);
                         r3.setBackgroundResource(R.mipmap.carsetno);
                         r4.setBackgroundResource(R.mipmap.view);
                         break;
                 }
             }
         });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //todo 隐藏导航栏和状态栏
        if (hasFocus){
            View decorView =getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            //隐藏导航栏和状态栏
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            |View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        newview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo SharedPreferences存储
                SharedPreferences sharedPreferences = getSharedPreferences("sptest",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("view1","1/dsadasda");
                editor.putInt("view101",23123);
                editor.commit();
                Log.e("TAG",sharedPreferences.getString("view1","null"));
            }
        });
    }

    //添加模式
    public void tinajianview(View view) {

    }
}