package com.hirain.hirain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hirain.hirain.bean.event.EditModeEvent;
import com.hirain.hirain.dialog.DialogUtils;
import com.hirain.hirain.fragment.AlluseFragment;
import com.hirain.hirain.fragment.CarsetFragment;
import com.hirain.hirain.fragment.FirstFragment;
import com.hirain.hirain.fragment.UserFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //todo 按钮 自定义模式添加按钮， 另存新模式，  保存；
    private Button tianjiaview,newview,baoc;

    //todo 二级联动
    private ViewPager viewPager;
    public RadioGroup radioGroup;
    private List<Fragment> fragmentList=new ArrayList<>();
    private RadioButton r1,r2,r3,r4;
    //是否是正在编辑自定义模式
    private boolean isEdit=false;
    public static List<Song> songList=new ArrayList<>();
    //系统时间
    private TextView systemTime;
    private Timer timer;

    public static List<Song> getSongList(){

        return songList;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object event){
        if(event instanceof EditModeEvent){

            EditModeEvent editModeEvent= (EditModeEvent) event;
            switch (editModeEvent.getEditState()) {
                case 0:

                    r1.setBackgroundResource(R.mipmap.meno);
                    r2.setBackgroundResource(R.mipmap.home);
                    r3.setBackgroundResource(R.mipmap.carsetno);
                    r4.setBackgroundResource(R.mipmap.viewno);
                    viewPager.setCurrentItem(1);
                    isEdit=true;
                    break;
                case 1:
                    isEdit=false;
                    viewPager.setCurrentItem(0);
                    r1.setBackgroundResource(R.mipmap.my);
                    r2.setBackgroundResource(R.mipmap.homeno);
                    r3.setBackgroundResource(R.mipmap.carsetno);
                    r4.setBackgroundResource(R.mipmap.viewno);

                    break;
                case 2:
                    isEdit=false;
                    break;
                case 3:
                    isEdit=false;
                    break;

            }



        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.i("wxy", "handleMessage:adads ");
            init();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }


        initMusic();
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
        systemTime = findViewById(R.id.system_time);
        //二级联动
        viewPager=findViewById(R.id.viewpaget_main);
        radioGroup=findViewById(R.id.radiogroup_main);

        fragmentList.add(new FirstFragment());
        fragmentList.add(new CarsetFragment());
        fragmentList.add(new AlluseFragment());
        fragmentList.add(new UserFragment());

        viewPager.setOffscreenPageLimit(3);
        //todo viewPager适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                 if(isEdit){
                     //在编辑模式下，弹窗提示
                     DialogUtils.customView(MainActivity.this, R.string.edit_mode_tip, R.string.dialog_cancle,
                             R.string.dialg_confirm, new DialogUtils.onClickListener() {
                                 @Override
                                 public void leftClickListener() {
                                     EventBus.getDefault().post(new EditModeEvent("aa",2));
                                     isEdit=false;
                                     selectMenu (i);
                                 }

                                 @Override
                                 public void rightClickListener(String text) {
                                     EventBus.getDefault().post(new EditModeEvent("aa",1));
                                     isEdit=false;
                                     selectMenu (i);
                                 }
                             });

                 }else {
                     selectMenu (i);
                 }

             }
         });
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,1000,30000);

    }
    public  void selectMenu(int i){
        switch (i){
            case R.id.radio_1:
                systemTime.setVisibility(View.GONE);
                viewPager.setCurrentItem(0);
                r1.setBackgroundResource(R.mipmap.my);
                r2.setBackgroundResource(R.mipmap.homeno);
                r3.setBackgroundResource(R.mipmap.carsetno);
                r4.setBackgroundResource(R.mipmap.viewno);
                break;
            case R.id.radio_2:
                systemTime.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1);
                r1.setBackgroundResource(R.mipmap.meno);
                r2.setBackgroundResource(R.mipmap.home);
                r3.setBackgroundResource(R.mipmap.carsetno);
                r4.setBackgroundResource(R.mipmap.viewno);
                break;
            case R.id.radio_3:
                systemTime.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2);
                r1.setBackgroundResource(R.mipmap.meno);
                r2.setBackgroundResource(R.mipmap.homeno);
                r3.setBackgroundResource(R.mipmap.carset);
                r4.setBackgroundResource(R.mipmap.viewno);
                break;
            case R.id.radio_4:
                systemTime.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(3);
                r1.setBackgroundResource(R.mipmap.meno);
                r2.setBackgroundResource(R.mipmap.homeno);
                r3.setBackgroundResource(R.mipmap.carsetno);
                r4.setBackgroundResource(R.mipmap.view);
                break;
        }
    }

    private void initMusic() {

        Song song1 = new Song();
        song1.setMusicPath(R.raw.aa);
        song1.setSinger("解忧邵帅");
        song1.setSong("你是人间四月天");
        Song song2 = new Song();
        song2.setMusicPath(R.raw.bb);
        song2.setSinger("李荣浩");
        song2.setSong("戒烟");
        Song song3 = new Song();
        song3.setMusicPath(R.raw.cc);
        song3.setSinger("漆柚");
        song3.setSong("封刀不为峥嵘");
        Song song4 = new Song();
        song4.setMusicPath(R.raw.dd);
        song4.setSinger("王同学Able");
        song4.setSong("什么是逍遥");


        songList.add(song1);
        songList.add(song2);
        songList.add(song3);
        songList.add(song4);
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

    private void init() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(currentTime);
        systemTime.setText(formatter.format(date));
    }
    //添加模式
    public void tinajianview(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler=null;
        timer.cancel();
        timer=null;
    }
}