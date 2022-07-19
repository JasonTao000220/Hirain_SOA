package com.hirain.hirain.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hirain.hirain.MediaPlayerUtil;
import com.hirain.hirain.R;
import com.hirain.hirain.myview.Dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    //todo 音乐卡片
    private Runnable runnable;
    private Handler handler=new Handler();
    private MediaPlayerUtil mediaPlayerUtil=new MediaPlayerUtil();
    private List<Integer> listbanner;//轮播图图片
    private ImageButton bofang,zanting,shangyiso,xiayiso;
    private ImageView mv;
    private SeekBar seekBar;
    private String url1,url2,url3;//当前音乐，上一首，下一首


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //模式按钮
        musis=getActivity().findViewById(R.id.musisbutton);
        show=getActivity().findViewById(R.id.showbutton);
        //自定义模式
        tianjia=getActivity().findViewById(R.id.firsttianjia);
        firstfrag=getActivity().findViewById(R.id.firstfragme);
        view1=getActivity().findViewById(R.id.view1);
        view2=getActivity().findViewById(R.id.view2);
        view3=getActivity().findViewById(R.id.view3);
        view4=getActivity().findViewById(R.id.view4);
        view5=getActivity().findViewById(R.id.view5);
        textView1=getActivity().findViewById(R.id.text1);
        textView2=getActivity().findViewById(R.id.text2);
        textView3=getActivity().findViewById(R.id.text3);
        textView4=getActivity().findViewById(R.id.text4);
        textView5=getActivity().findViewById(R.id.text5);
        viewset1=getActivity().findViewById(R.id.view1_set);
        viewset2=getActivity().findViewById(R.id.view2_set);
        viewset3=getActivity().findViewById(R.id.view3_set);
        viewset4=getActivity().findViewById(R.id.view4_set);
        viewset5=getActivity().findViewById(R.id.view5_set);
        shezhi1=getActivity().findViewById(R.id.shezhi1);
        shezhi2=getActivity().findViewById(R.id.shezhi2);
        shezhi3=getActivity().findViewById(R.id.shezhi3);
        shezhi4=getActivity().findViewById(R.id.shezhi4);
        shezhi5=getActivity().findViewById(R.id.shezhi5);
        shancu1=getActivity().findViewById(R.id.shanchu1);
        shancu2=getActivity().findViewById(R.id.shanchu2);
        shancu3=getActivity().findViewById(R.id.shanchu3);
        shancu4=getActivity().findViewById(R.id.shanchu4);
        shancu5=getActivity().findViewById(R.id.shanchu5);

        //音乐卡片
        listbanner =new ArrayList<>();//mv图片
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        listbanner.add(ic_launcher_round);
        seekBar=getActivity().findViewById(R.id.mediautil);
        bofang=getActivity().findViewById(R.id.bofang);
        shangyiso=getActivity().findViewById(R.id.shangyis);
        xiayiso=getActivity().findViewById(R.id.xiayiso);
        zanting=getActivity().findViewById(R.id.zanting);
        mv=getActivity().findViewById(R.id.imagemv);
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
                //弹框
//                com.hirain.hirain.myview.Dialog dialog=new com.hirain.hirain.myview.Dialog(getActivity());
////                dialog.setTitle("");
//                dialog.setMessage("确认开启音乐模式");
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

                Toast.makeText(getActivity(), "正在启动音乐模式", Toast.LENGTH_SHORT).show();
            }
        });
        //展示
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "正在启动展示模式", Toast.LENGTH_SHORT).show();
                com.hirain.hirain.myview.Dialog dialog=new com.hirain.hirain.myview.Dialog(getActivity());
                dialog.setTitle("提示");
                dialog.setMessage("确认开启展示模式");
                dialog.setCancel("取消", new Dialog.OnCancelListener() {
                    @Override
                    public void onCancel(Dialog dialog) {

                    }
                });
                dialog.setConfirm("确认", new Dialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(Dialog dialog) {

                    }
                });
            }
        });

        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//需要做个判断，判断是否超过多少多少条


            }
        });

        //todo 自定义模式1
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewset1.setVisibility(View.GONE);
                shezhi1.setVisibility(View.VISIBLE);
                shancu1.setVisibility(View.VISIBLE);
            }
        });
        shezhi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        shancu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //todo 自定义模式2
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewset2.setVisibility(View.GONE);
                shezhi2.setVisibility(View.VISIBLE);
                shancu2.setVisibility(View.VISIBLE);

            }
        });
        shezhi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        shancu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //todo 自定义模式3
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewset3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewset3.setVisibility(View.GONE);
                shezhi3.setVisibility(View.VISIBLE);
                shancu3.setVisibility(View.VISIBLE);

            }
        });
        shezhi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        shancu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //todo 自定义模式4
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewset4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewset4.setVisibility(View.GONE);
                shezhi4.setVisibility(View.VISIBLE);
                shancu4.setVisibility(View.VISIBLE);

            }
        });
        shezhi4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        shancu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //todo 自定义模式5
        view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                //todo 动画效果测试
//                ObjectAnimator  aAnimator= ObjectAnimator.ofFloat(view5,"rotation",0f,30f,0f);
//                aAnimator.setRepeatCount(3);
//                ObjectAnimator bAnimator= ObjectAnimator.ofFloat(view5,"alpha",1f,0.5f,1f);
//                bAnimator.setRepeatCount(3);
//                   AnimatorSet animatorSet=new AnimatorSet();
//                   animatorSet.playTogether(aAnimator,bAnimator);
//                   animatorSet.start();
            }
        });
        viewset5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewset5.setVisibility(View.GONE);
                shezhi5.setVisibility(View.VISIBLE);
                shancu5.setVisibility(View.VISIBLE);

            }
        });
        shezhi5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        shancu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //todo 音乐卡片
        //进度条
        seekBar.setMax(20);
        mediaPlayerUtil.init();
        mediaPlayerUtil.setOnMediaStateListener(new MediaPlayerUtil.OnMediaStateListener() {
            @Override
            public void onPrepared() {

            }

            @Override
            public void onSeekUpdate(int curTimeInt) {
                runnable=new Runnable() {
                    @Override
                    public void run() {
                        Log.e("播放进度",""+curTimeInt);
                        seekBar.setProgress(curTimeInt);
                        handler.postDelayed(runnable,2000);
                    }
                };
            }
            //播放完毕
            @Override
            public void onCompletion() {

            }

            @Override
            public boolean onError() {
                return true;
            }
        });

        //播放按钮
        bofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (url1!=null){
                        mediaPlayerUtil.prepare(url1);

                        //图片动画
                        Animation animation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        animation.setDuration(10000);
                        animation.setRepeatCount(Animation.INFINITE);//无限循环
                        animation.setInterpolator(new LinearInterpolator());//匀速运动插值器
                        mv.startAnimation(animation);

                    }else {
                        Toast.makeText(getActivity(), "\t暂\t无\t音\t", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayerUtil.start();


            }
        });

        //上一首
        shangyiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayerUtil.prepare(url2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayerUtil.start();

            }
        });
        //下一首
        xiayiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayerUtil.prepare(url3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayerUtil.start();

            }
        });
        //暂停
        zanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayerUtil.pause();


//                mv.startAnimation(null);//旋转动画
            }
        });
    }
}