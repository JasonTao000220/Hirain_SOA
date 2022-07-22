package com.hirain.hirain.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hirain.hirain.MainActivity;
import com.hirain.hirain.MusicService;
import com.hirain.hirain.MyAdapter;
import com.hirain.hirain.R;
import com.hirain.hirain.Song;
import com.hirain.hirain.bean.event.EditModeEvent;
import com.hirain.hirain.dialog.DialogUtils;
import com.hirain.hirain.myview.Dialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Context.MODE_PRIVATE;
import static com.hirain.hirain.R.mipmap.ic_launcher_round;

public class FirstFragment extends Fragment {
    //todo 模式按钮
    private Button musis,show;
    //todo 自定义模式
    private RelativeLayout firstfrag;
    private Button tianjia;
    private RelativeLayout view1,view2,view3,view4,view5;
    private TextView textView1,textView2,textView3,textView4,textView5;
    private Button viewset1,viewset2,viewset3,viewset4,viewset5;
    private Button shezhi1,shezhi2,shezhi3,shezhi4,shezhi5;
    private Button shancu1,shancu2,shancu3,shancu4,shancu5;
    private RecyclerView recyclerView;


    //todo 音乐卡片
    private Runnable runnable;


    private List<Integer> listbanner;//轮播图图片
    private ImageButton bofang,zanting,shangyiso,xiayiso;
    private ImageView mv;
    private SeekBar seekBar;  //进度条
    private PopupWindow popupWindow;
    private List<Song> songList=new ArrayList<>();

    private Animation animation;
    private MusicService.MusicBinder binder;
    //绑定服务
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder=(MusicService.MusicBinder) service;


            binder.setOnClick(new MusicService.OnMediaStateListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onPrepared() {
                    Log.i("wxy", "onPrepared: ");
//                mediaPlayerUtil.start();
                    seekBar.setMax(MusicService.mediaPlayer.getDuration());

                    musicDuration.setText(formatTime(MusicService.mediaPlayer.getDuration()));
                    updateProgress();
                    //设置进度条的最大时长

                }

                @Override
                public void onSeekUpdate(int curTimeInt) {
                    seekBar.setProgress(curTimeInt);
                }
                //播放完毕
                @Override
                public void onCompletion() {
                    //自动下一首
                }

                @Override
                public boolean onError() {
                    return true;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            // 展示给进度条和当前时间
            int progress = MusicService.mediaPlayer
                    .getCurrentPosition();
            musicProgress.setText(formatTime(progress));
            seekBar.setProgress(progress);
//            tvPlayTime.setText(parseTime(progress));
            // 继续定时发送数据
            updateProgress();
            return true;
        }
    });
    private TextView musicName;
    private TextView musicProgress;
    private TextView musicDuration;

    private void updateProgress() {
        // 使用Handler每间隔1s发送一次空消息，通知进度条更新
        Message msg = Message.obtain();// 获取一个现成的消息
        // 使用MediaPlayer获取当前播放时间除以总时间的进度
        int progress = MusicService.mediaPlayer.getCurrentPosition();
        msg.arg1 = progress;
        mHandler.sendMessageDelayed(msg, 1000);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //进度条
        Intent intent=new Intent(getActivity(), MusicService.class);
        getActivity().bindService(intent,connection,BIND_AUTO_CREATE);
        songList= MainActivity.getSongList();
        initView();
        ArrayList<Bean> data = new ArrayList<>();

        Bean bean1 = new Bean("自定义模式1",R.mipmap.viewset);
        Bean bean2 = new Bean("自定义模式2",R.mipmap.viewset);
        Bean bean3 = new Bean("自定义模式3",R.mipmap.viewset);
        Bean bean4 = new Bean("自定义模式4",R.mipmap.viewset);
        Bean bean5 = new Bean("自定义模式5",R.mipmap.viewset);
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        data.add(bean5);

        recyclerView = getActivity().findViewById(R.id.my_re_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        MyAdapter myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);

        //获取本地音乐
//        initMusic();
        //点击事件
        initClick();
        //图片动画
        animation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(10000);
        animation.setRepeatCount(Animation.INFINITE);//无限循环
        animation.setInterpolator(new LinearInterpolator());//匀速运动插值器
    }

    private void initClick() {

        //进度条的拖动联动音乐的播放进度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    //获取进度条改变后的位置并播放
                    MusicService.mediaPlayer.seekTo(i);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                MusicService.mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicService.mediaPlayer.start();

            }
        });

        //播放按钮
        bofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.playMusic();
                if(!MusicService.mediaPlayer.isPlaying()){
                    bofang.setImageResource(R.mipmap.play);
                }else {
                    bofang.setImageResource(R.mipmap.pause);
                }

            }
        });

        //上一首
        shangyiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.previousMusic();

                musicName.setText(songList.get(MusicService.getPos()).getSong());

                    bofang.setImageResource(R.mipmap.pause);

            }
        });
        //下一首
        xiayiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binder.nextMusic();
                musicName.setText(songList.get(MusicService.getPos()).getSong());

                bofang.setImageResource(R.mipmap.pause);

            }
        });

    }



    //初始化控件
    public void initView(){
        //模式按钮
        musis=getActivity().findViewById(R.id.musisbutton);
        show=getActivity().findViewById(R.id.showbutton);
        //自定义模式
        tianjia=getActivity().findViewById(R.id.firsttianjia);
        firstfrag=getActivity().findViewById(R.id.firstfragme);


        textView2=getActivity().findViewById(R.id.text2);


        //音乐卡片
        listbanner =new ArrayList<>();//mv图片
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        seekBar=getActivity().findViewById(R.id.mediautil);
        bofang=getActivity().findViewById(R.id.bofang);
        musicName = getActivity().findViewById(R.id.music_name);
        musicProgress = getActivity().findViewById(R.id.music_progress);
        musicDuration = getActivity().findViewById(R.id.music_duration);
        shangyiso=getActivity().findViewById(R.id.shangyis);
        xiayiso=getActivity().findViewById(R.id.xiayiso);
        zanting=getActivity().findViewById(R.id.zanting);
        mv=getActivity().findViewById(R.id.imagemv);
        //获取上一次的播放进度
        SharedPreferences preferences = getActivity().getSharedPreferences("Last Information", MODE_PRIVATE);
        int pos=preferences.getInt("position",0);
        int duration=preferences.getInt("duration",songList.get(0).getDuration());
        seekBar.setMax(duration);

        seekBar.setProgress(preferences.getInt("currentPosition",0));
        musicName.setText(songList.get(pos).getSong());
        int currentPosition = preferences.getInt("currentPosition", 0);
        musicProgress.setText(formatTime(currentPosition));
        musicDuration.setText(formatTime(duration));

    }
    /**
     * 定义一个方法用来格式化获取到的时间
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;

        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(connection);
        mHandler.removeCallbacksAndMessages(null);

    }
    @Override
    public void onResume() {
        super.onResume();

        //随机点击关闭设置，删除框
        firstfrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //自定义条目
                viewset1.setVisibility(View.VISIBLE);
                viewset2.setVisibility(View.VISIBLE);
                viewset3.setVisibility(View.VISIBLE);
                viewset4.setVisibility(View.VISIBLE);
                viewset5.setVisibility(View.VISIBLE);
                //设置按钮
                shezhi1.setVisibility(View.GONE);
                shezhi2.setVisibility(View.GONE);
                shezhi3.setVisibility(View.GONE);
                shezhi4.setVisibility(View.GONE);
                shezhi5.setVisibility(View.GONE);
                //删除按钮
                shancu1.setVisibility(View.GONE);
                shancu2.setVisibility(View.GONE);
                shancu3.setVisibility(View.GONE);
                shancu4.setVisibility(View.GONE);
                shancu5.setVisibility(View.GONE);
            }
        });

        //todo 模式
        //音乐
        musis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(popupWindow==null){
                    popupWindow = new PopupWindow(getContext());
                    popupWindow.setBackgroundDrawable(null);
                    View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null, false);
//                    inflate.findViewById()
                    popupWindow.setContentView(inflate);
                    popupWindow.setHeight(1920);
                    popupWindow.setWidth(720);
                }


                popupWindow.showAtLocation(musis, Gravity.NO_GRAVITY,0,0);

//                com.hirain.hirain.myview.Dialog dialog=new com.hirain.hirain.myview.Dialog(getActivity());
////                dialog.setTitle("");
//                dialog.setMessage("确认开启音乐模式");
//
//                dialog.setCancel("取消", new Dialog.OnCancelListener() {
//                    @Override
//                    public void onCancel(Dialog dialog) {
//
//                    }
//                });
//                dialog.setConfirm("确认", new Dialog.OnConfirmListener() {
//                    @Override
//                    public void onConfirm(Dialog dialog) {
//
//                    }
//                });
//                dialog.show();
//
//                Toast.makeText(getActivity(), "正在启动音乐模式", Toast.LENGTH_SHORT).show();
            }
        });
        //展示
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转到第二个页面
                EventBus.getDefault().post(new EditModeEvent("df",0));

//                Toast.makeText(getActivity(), "正在启动展示模式", Toast.LENGTH_SHORT).show();
             /*   DialogUtils.customView(getActivity(), R.string.start_model, R.string.dialog_cancle, R.string.dialg_start, new DialogUtils.onClickListener() {
                    @Override
                    public void leftClickListener() {

                    }

                    @Override
                    public void rightClickListener(String text) {
                        //开启
                        Toast.makeText(getActivity(), "自定义模式已开启", Toast.LENGTH_SHORT).show();
                    }
                });
                DialogUtils.customEditView(getActivity(), R.string.set_model, R.string.dialog_cancle, R.string.dialg_confirm, new DialogUtils.onClickListener() {
                    @Override
                    public void leftClickListener() {

                    }

                    @Override
                    public void rightClickListener(String text) {
                        //开启
                        Toast.makeText(getActivity(), "自定义模式已开启", Toast.LENGTH_SHORT).show();
                    }
                });
*/

            }
        });

        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//需要做个判断，判断是否超过多少多少条


            }
        });


    }


}