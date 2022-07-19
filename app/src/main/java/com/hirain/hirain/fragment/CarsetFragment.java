package com.hirain.hirain.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;

import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.flatbuffers.FlatBufferBuilder;
import com.hirain.hirain.R;
import com.hirain.hirain.flaterbuffers.hsj.ExtMirrorServicelnfo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import static com.hirain.hirain.R.mipmap.hshijing;
import static com.hirain.hirain.R.mipmap.nodengguang;
import static com.hirain.hirain.R.mipmap.nohshijing;
import static com.hirain.hirain.R.mipmap.yin00;
import static com.hirain.hirain.R.mipmap.yin100;
import static com.hirain.hirain.R.mipmap.yin20;
import static com.hirain.hirain.R.mipmap.yin30;
import static com.hirain.hirain.R.mipmap.yin40;
import static com.hirain.hirain.R.mipmap.yin50;
import static com.hirain.hirain.R.mipmap.yin60;
import static com.hirain.hirain.R.mipmap.yin70;
import static com.hirain.hirain.R.mipmap.yin80;
import static com.hirain.hirain.R.mipmap.yin90;
import static com.hirain.hirain.R.mipmap.yinlianglog0;
import static com.hirain.hirain.R.mipmap.zuoyiquanbi;


public class CarsetFragment extends Fragment {
    //交互
    private FlatBufferBuilder fbb =new FlatBufferBuilder();
    private LocalSocket msocket = new LocalSocket();
    //切换按钮
    private Button yinliang,hoshijing,zuoyi,dengguang;

    //模式调节
    private RelativeLayout rly,rlh,rlz,rld;

    //音量
    private AudioManager am;//音量调节器
    private ImageView yinlianglog;
    private RadioGroup volume,zuoyouhsj;
    private RadioButton vb0,vb1,vb2,vb3,vb4,vb5,vb6,vb7,vb8,vb9;
    private ImageView yin;

    //后视镜
    private Button jingshang,jingzuo,jingxia,jingyou;
    private Button jingshang1,jingzuo1,jingxia1,jingyou1;
    private Button hsjkai,hsjguan;
    private RadioButton zuohsj,youhsj;

    //座椅
    private LinearLayout seat;
    private Button s0,s1,s2,s3;

    //外氛围灯
    private Button kai,guan;

    //内部氛围灯
    private Button lamp1,lamp2,lamp3,lamp4;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carset, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //todo 切换按钮
        yinliang=getActivity().findViewById(R.id.button_volume);
        hoshijing=getActivity().findViewById(R.id.button_rearview);
        zuoyi=getActivity().findViewById(R.id.button_seat);
        dengguang=getActivity().findViewById(R.id.button_lamplight);

        //todo 模式布局
        rly=getActivity().findViewById(R.id.carsetfragment_carset_layout_yinliang);
        rlh=getActivity().findViewById(R.id.carsetfragment_seat_layout_rearview);
        rlz=getActivity().findViewById(R.id.carsetfragment_lamplight_layout_seat);
        rld=getActivity().findViewById(R.id.carsetfragment_lamplight_layout_lemp);

        //todo 音量调节
        yin=getActivity().findViewById(R.id.yin);
        yinlianglog=getActivity().findViewById(R.id.yinlianglog);
        volume=getActivity().findViewById(R.id.volume);
        zuoyouhsj=getActivity().findViewById(R.id.zuoyouhsj);
        vb0=getActivity().findViewById(R.id.volume0);
        vb1=getActivity().findViewById(R.id.volume_10);
        vb2=getActivity().findViewById(R.id.volume_20);
        vb3=getActivity().findViewById(R.id.volume_30);
        vb4=getActivity().findViewById(R.id.volume_40);
        vb5=getActivity().findViewById(R.id.volume_50);
        vb6=getActivity().findViewById(R.id.volume_60);
        vb7=getActivity().findViewById(R.id.volume_70);
        vb8=getActivity().findViewById(R.id.volume_80);
        vb9=getActivity().findViewById(R.id.volume_90);

        //todo 后视镜
        hsjkai=getActivity().findViewById(R.id.hsjkai);
        hsjguan=getActivity().findViewById(R.id.hsjguan);

        //todo 左右后视镜切换
        zuohsj=getActivity().findViewById(R.id.zuohoushijing);
        youhsj=getActivity().findViewById(R.id.youhoushijing);

        //todo 座椅记忆调节
        seat=getActivity().findViewById(R.id.seat);
        s0=getActivity().findViewById(R.id.seat0);
        s1=getActivity().findViewById(R.id.seat1);
        s2=getActivity().findViewById(R.id.seat2);
        s3=getActivity().findViewById(R.id.seat3);

        //TODO 外氛围灯
        kai=getActivity().findViewById(R.id.waibu_kai);
        guan=getActivity().findViewById(R.id.waibu_guan);

        //TODO 内部氛围灯
        lamp1=getActivity().findViewById(R.id.lamp1);
        lamp2=getActivity().findViewById(R.id.lamp2);
        lamp3=getActivity().findViewById(R.id.lamp3);
        lamp4=getActivity().findViewById(R.id.lamp4);

        am= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);//获取音量调节器对象
    }


    @Override
    public void onResume() {
        super.onResume();

        //TODO 调节切换按钮
        //音量
        yinliang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yinliang.setBackgroundResource(R.mipmap.yinliang);
                hoshijing.setBackgroundResource(nohshijing);
                zuoyi.setBackgroundResource(zuoyiquanbi);
                dengguang.setBackgroundResource(nodengguang);
                rly.setVisibility(View.VISIBLE);
                rlh.setVisibility(View.GONE);
                rlz.setVisibility(View.GONE);
                rld.setVisibility(View.GONE);
            }
        });
        //后视镜
        hoshijing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yinliang.setBackgroundResource(R.mipmap.noyinliang);
                hoshijing.setBackgroundResource(hshijing);
                zuoyi.setBackgroundResource(zuoyiquanbi);
                dengguang.setBackgroundResource(nodengguang);
                rly.setVisibility(View.GONE);
                rlh.setVisibility(View.VISIBLE);
                rlz.setVisibility(View.GONE);
                rld.setVisibility(View.GONE);

            }
        });
        //座椅
        zuoyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yinliang.setBackgroundResource(R.mipmap.noyinliang);
                hoshijing.setBackgroundResource(nohshijing);
                zuoyi.setBackgroundResource(R.mipmap.zuoyi);
                dengguang.setBackgroundResource(nodengguang);
                rly.setVisibility(View.GONE);
                rlh.setVisibility(View.GONE);
                rlz.setVisibility(View.VISIBLE);
                rld.setVisibility(View.GONE);
            }
        });
        //灯光
        dengguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yinliang.setBackgroundResource(R.mipmap.noyinliang);
                hoshijing.setBackgroundResource(nohshijing);
                zuoyi.setBackgroundResource(zuoyiquanbi);
                dengguang.setBackgroundResource(R.mipmap.dengguang);
                rly.setVisibility(View.GONE);
                rlh.setVisibility(View.GONE);
                rlz.setVisibility(View.GONE);
                rld.setVisibility(View.VISIBLE);

            }
        });
        //TODO 音量调节
        volume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.volume0:
                        yin.setImageResource(yin00);
                        yinlianglog.setImageResource(yinlianglog0);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,0,AudioManager.FLAG_SHOW_UI);
                        vb0.setBackgroundResource(R.drawable.shape_hubian);
                        vb1.setBackgroundResource(R.color.tm);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_10:
                        yin.setImageResource(yin20);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,20,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);

                        break;
                    case R.id.volume_20:
                        yin.setImageResource(yin30);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,30,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb2.setBackgroundResource(R.drawable.shape_hubian);
                        vb1.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_30:
                        yin.setImageResource(yin40);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,40,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_40:
                        yin.setImageResource(yin50);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,50,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_50:
                        yin.setImageResource(yin60);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,60,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_60:
                        yin.setImageResource(yin70);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,70,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_70:
                        yin.setImageResource(yin80);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,80,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_80:
                        yin.setImageResource(yin90);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,90,0);
                        vb0.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_90:
                        yin.setImageResource(yin100);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,100,AudioManager.FLAG_SHOW_UI);
                        vb0.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.drawable.shape_hubian);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        break;
                }
            }
        });

        //TODO 后视镜调节
        //打开后视镜
        hsjkai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hsjkai.setBackgroundResource(R.drawable.carset_h_z_d_button);
                hsjguan.setBackgroundResource(R.color.tm);
            }
        });
        //关闭后视镜
        hsjguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hsjguan.setBackgroundResource(R.drawable.carset_h_z_d_button);
                hsjkai.setBackgroundResource(R.color.tm);

                try {
                int hsj1 =fbb.createByteVector(ByteBuffer.allocateDirect(0x00));
                int hsj2 =fbb.createByteVector(ByteBuffer.allocateDirect(0x00));
                int hsj3 =fbb.createByteVector(ByteBuffer.allocateDirect(0x01));
                int hsj4 =fbb.createByteVector(ByteBuffer.allocateDirect(0x00));

                int mus = ExtMirrorServicelnfo.createExtMirrorServicelnfo(fbb,hsj1,hsj2,hsj3,hsj4);
                fbb.finish(mus);

                byte[] demarr =fbb.sizedByteArray();
                    Log.e("flatbuffers",demarr+"");

                short sizen =(short)(demarr.length);
                short name =0x01;

                byte[] bufnn =new byte[]{0x52,0x4f,0x41,0x00, (byte)(name >> 8),(byte)name, (byte) ( sizen >> 8), (byte) sizen};

                byte[] musisn = new byte[bufnn.length + demarr.length];
                System.arraycopy(bufnn,0,musisn,0,bufnn.length);
                System.arraycopy(demarr,0,musisn,bufnn.length,demarr.length);

                Log.e("system",musisn+"");


                LocalSocketAddress address=new LocalSocketAddress("/data/data/com.hirain.hirain/defauit.sock", LocalSocketAddress.Namespace.FILESYSTEM);
                    msocket.connect(address);
                    OutputStream data =msocket.getOutputStream();
                    data.write(musisn);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("socket","连接失败");
                }

            }
        });

        //Todo 调节左右后视镜
        zuoyouhsj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.zuohoushijing:
                        jingzuo.setBackgroundResource(R.mipmap.jingleft);
                        jingshang.setBackgroundResource(R.mipmap.jingtop);
                        jingyou.setBackgroundResource(R.mipmap.jingrede);
                        jingxia.setBackgroundResource(R.mipmap.jingbutton);
                        jingzuo1.setVisibility(View.GONE);
                        jingshang1.setVisibility(View.GONE);
                        jingyou1.setVisibility(View.GONE);
                        jingxia1.setVisibility(View.GONE);
                        break;
                    case R.id.youhoushijing:
                        jingzuo.setVisibility(View.GONE);
                        jingshang.setVisibility(View.GONE);
                        jingyou.setVisibility(View.GONE);
                        jingxia.setVisibility(View.GONE);
                        jingzuo1.setBackgroundResource(R.mipmap.jingleft);
                        jingshang1.setBackgroundResource(R.mipmap.jingtop);
                        jingyou1.setBackgroundResource(R.mipmap.jingrede);
                        jingxia1.setBackgroundResource(R.mipmap.jingbutton);
                        break;
                }
            }
        });


        //TODO 座椅调节
        //未调节
        s0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 记忆一
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s0.setBackgroundResource(R.color.tm);
                s1.setBackgroundResource(R.drawable.carset_h_z_d_button);
                s2.setBackgroundResource(R.color.tm);
                s3.setBackgroundResource(R.color.tm);
            }
        });
        //记忆二
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s0.setBackgroundResource(R.color.tm);
                s1.setBackgroundResource(R.color.tm);
                s2.setBackgroundResource(R.drawable.carset_h_z_d_button);
                s3.setBackgroundResource(R.color.tm);
            }
        });
        //记忆三
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s0.setBackgroundResource(R.color.tm);
                s1.setBackgroundResource(R.color.tm);
                s2.setBackgroundResource(R.color.tm);
                s3.setBackgroundResource(R.drawable.carset_h_z_d_button);
            }
        });
        //TODO 氛围灯
        //外部氛围灯开
        kai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guan.setBackgroundResource(R.color.tm);
                kai.setBackgroundResource(R.drawable.carset_h_z_d_button);
            }
        });
        //外部氛围灯关
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kai.setBackgroundResource(R.color.tm);
                guan.setBackgroundResource(R.drawable.carset_h_z_d_button);
            }
        });


        //状态1
        lamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lamp1.setBackgroundResource(R.drawable.carset_h_z_d_button);
                lamp2.setBackgroundResource(R.color.tm);
                lamp3.setBackgroundResource(R.color.tm);
                lamp4.setBackgroundResource(R.color.tm);
            }
        });
        //状态2
        lamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lamp2.setBackgroundResource(R.drawable.carset_h_z_d_button);
                lamp1.setBackgroundResource(R.color.tm);
                lamp3.setBackgroundResource(R.color.tm);
                lamp4.setBackgroundResource(R.color.tm);

            }
        });
        //状态3
        lamp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lamp3.setBackgroundResource(R.drawable.carset_h_z_d_button);
                lamp2.setBackgroundResource(R.color.tm);
                lamp1.setBackgroundResource(R.color.tm);
                lamp4.setBackgroundResource(R.color.tm);
            }
        });
        //状态4
        lamp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lamp4.setBackgroundResource(R.drawable.carset_h_z_d_button);
                lamp2.setBackgroundResource(R.color.tm);
                lamp3.setBackgroundResource(R.color.tm);
                lamp1.setBackgroundResource(R.color.tm);
            }
        });

    }
}