package com.hirain.hirain.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.icu.lang.UProperty;
import android.media.AudioManager;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Parcelable;
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
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.flatbuffers.FlatBufferBuilder;
import com.hirain.hirain.MainActivity;
import com.hirain.hirain.MyApp;
import com.hirain.hirain.R;
import com.hirain.hirain.bean.CustomMode;
import com.hirain.hirain.bean.event.EditModeEvent;
import com.hirain.hirain.dialog.DialogUtils;
import com.hirain.hirain.flaterbuffers.hsj.ExtMirrorServicelnfo;
import com.hirain.hirain.utils.MMkvUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.hirain.hirain.R.mipmap.hshijing;
import static com.hirain.hirain.R.mipmap.nodengguang;
import static com.hirain.hirain.R.mipmap.nohshijing;
import static com.hirain.hirain.R.mipmap.yin00;
import static com.hirain.hirain.R.mipmap.yin10;
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
        private ImageView yin;

    //切换按钮
    private Button yinliang,hoshijing,zuoyi,dengguang;

    //模式调节
    private RelativeLayout rly,rlh,rlz,rld;

    //音量
    private AudioManager am;//音量调节器
    private ImageView yinlianglog;
    private RadioGroup volume,zuoyouhsj;
    private RadioButton vb0,vb1,vb2,vb3,vb4,vb5,vb6,vb7,vb8,vb9,vb10;

    //后视镜
    private Button jingshang,jingzuo,jingxia,jingyou;
    private RadioButton zuohsj,youhsj;
    private int rearview_position;
    private ImageView select_position;
    //后视镜状态
    private String[] rmType = {"打开", "折叠"};
    //左右后视镜
    private String[] rmDirection = {"左", "右"};
    //座椅
    private String[] chairType = {"自由状态", "位置一","位置二","位置三"};
    //灯光模式
    private String[] lightType = {"关闭", "mode1","mode2","mode3"};
    //氛围灯模式
    private String[] atmosphereLamp = {"关闭", "mode1","mode2","mode3"};

    private SegmentTabLayout rmTypeTab;
    private SegmentTabLayout rmDirectionTab;
    private SegmentTabLayout chairTypeTab;
    private SegmentTabLayout lightTypeTab;
    private SegmentTabLayout atmosphereLampTab;
    private TextView newMode;
    private TextView upDateMode;
    //用于记录选择的状态，方便保存模式
    private CustomMode customMode;
    private String modeName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carset, container, false);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object event){
        if(event instanceof EditModeEvent){
            EditModeEvent editModeEvent= (EditModeEvent) event;
            modeName = editModeEvent.getModeName();
            switch (editModeEvent.getEditState()) {
                case 0:
                    yinliang.setBackgroundResource(R.mipmap.yinliang);
                    hoshijing.setBackgroundResource(nohshijing);
                    zuoyi.setBackgroundResource(zuoyiquanbi);
                    dengguang.setBackgroundResource(nodengguang);
                    rly.setVisibility(View.VISIBLE);
                    rlh.setVisibility(View.GONE);
                    rlz.setVisibility(View.GONE);
                    rld.setVisibility(View.GONE);
                    upDateMode.setVisibility(View.VISIBLE);
                    configMode(editModeEvent.getModeName());
                    break;
                case 1:
                    //保存编辑
                    Log.i("wcu", "getMessage: "+customMode);
                    MMkvUtils.getmInstance().encodeParcelable(modeName,customMode);
                    break;
                case 2:
                    //取消编辑
                    upDateMode.setVisibility(View.GONE);
                    break;

            }

        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        initView();
        customMode=new CustomMode();

        initListener();

    }

    public void configMode(String key){
        customMode = MMkvUtils.getmInstance().decodeParcelable(key);
        if(customMode==null){
            customMode = new CustomMode();
        }else {
            lightTypeTab.setCurrentTab(customMode.getLightMode());
            atmosphereLampTab.setCurrentTab(customMode.getAtmosphereLamp());
            chairTypeTab.setCurrentTab(customMode.getChairMode());
            rmTypeTab.setCurrentTab(customMode.getRmType());
            rmDirectionTab.setCurrentTab(customMode.getRmStates());
            Log.i("wxy", "configMode: "+customMode.getRmType()+"==="+customMode.getRmStates());
            switchVolume(customMode.getVolume());
        }
    }

    public void switchVolume(int volume){
        vb0.setBackgroundResource(R.color.tm);
        switch (volume) {
            case 0:
                vb0.setBackgroundResource(R.drawable.shape_hubian);
                yin.setImageResource(yin00);
                break;
            case 10:
                vb1.setBackgroundResource(R.drawable.shape_hubian);
                yin.setImageResource(yin10);
                break;
            case 20:
                yin.setImageResource(yin20);
                vb2.setBackgroundResource(R.drawable.shape_hubian);
                break;
            case 30:
                yin.setImageResource(yin30);
                vb3.setBackgroundResource(R.drawable.shape_hubian);
                break;
            case 40:
                yin.setImageResource(yin40);
                vb4.setBackgroundResource(R.drawable.shape_hubian);
                break;
            case 50:
                yin.setImageResource(yin50);
                vb5.setBackgroundResource(R.drawable.shape_hubian);
                break;
            case 60:
                vb6.setBackgroundResource(R.drawable.shape_hubian);
                yin.setImageResource(yin60);
                break;
            case 70:
                vb7.setBackgroundResource(R.drawable.shape_hubian);
                yin.setImageResource(yin70);
                break;
            case 80:
                yin.setImageResource(yin80);
                vb8.setBackgroundResource(R.drawable.shape_hubian);
                break;
            case 90:
                yin.setImageResource(yin90);
                vb9.setBackgroundResource(R.drawable.shape_hubian);
                break;
            case 100:
                vb10.setBackgroundResource(R.drawable.shape_hubian);
                yin.setImageResource(yin100);
                break;

        }
    }

    private void initListener() {
        //后视镜选择
        rmDirectionTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Log.i("wxy", "onTabSelect: "+position);
                customMode.setRmStates(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //后视镜打开/关闭
        rmTypeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                rearview_position=position+1;
                customMode.setRmType(position);
                try {
                    int hsj1 =fbb.createByteVector(ByteBuffer.allocateDirect(0x00));
                        int hsj2 =fbb.createByteVector(ByteBuffer.allocateDirect(0x00));
                    int hsj3 =fbb.createByteVector(ByteBuffer.allocateDirect(0x01));
                    int hsj4 =fbb.createByteVector(ByteBuffer.allocateDirect(0x00));

                    int mus = ExtMirrorServicelnfo.createExtMirrorServicelnfo(fbb,hsj1,hsj2,hsj3,hsj4);
                    fbb.finish(mus);

                    byte[] demarr =fbb.sizedByteArray();
                    Log.e("system",demarr+"");

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

            @Override
            public void onTabReselect(int position) {

            }
        });
        //座椅模式
        chairTypeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                customMode.setChairMode(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //灯光
        lightTypeTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                customMode.setLightMode(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //氛围灯
        atmosphereLampTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                customMode.setAtmosphereLamp(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        //另存新模式
        newMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.customEditView(getActivity(), R.string.save_mode, R.string.dialog_cancle, R.string.dialg_confirm,
                        new DialogUtils.onClickListener() {
                            @Override
                            public void leftClickListener() {

                            }

                            @Override
                            public void rightClickListener(String text) {
                                Set<String> mode = MMkvUtils.getmInstance().decodeSet(MMkvUtils.MODE);
                                if(mode==null){
                                    mode=new HashSet<>();
                                }
                                boolean add = mode.add(text);
                                if(!add){
                                    Random random = new Random(10);
                                    int i = random.nextInt();
                                    mode.add(text+i);
                                }
                                MMkvUtils.getmInstance().encodeSet(MMkvUtils.MODE,mode);

                                MMkvUtils.getmInstance().encodeParcelable(text,customMode);
                                EventBus.getDefault().post(new EditModeEvent(text,3));
                            }
                        });

            }
        });
        //保存模式
        upDateMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.customView(getActivity(), R.string.edit_mode_tip, R.string.dialog_cancle,
                        R.string.dialg_confirm, new DialogUtils.onClickListener() {
                            @Override
                            public void leftClickListener() {
                                EventBus.getDefault().post(new EditModeEvent(modeName,2));


                            }

                            @Override
                            public void rightClickListener(String text) {
                                EventBus.getDefault().post(new EditModeEvent(modeName,1));

                            }
                        });
            }
        });
    }

    private void initView() {

        //todo 切换按钮
        yinliang=getActivity().findViewById(R.id.button_volume);
        hoshijing=getActivity().findViewById(R.id.button_rearview);
        zuoyi=getActivity().findViewById(R.id.button_seat);
        dengguang=getActivity().findViewById(R.id.button_lamplight);

        rmTypeTab = getActivity().findViewById(R.id.rm_type);
        rmDirectionTab = getActivity().findViewById(R.id.rm_direction);
        chairTypeTab = getActivity().findViewById(R.id.chair_type);
        lightTypeTab = getActivity().findViewById(R.id.light_type);
        atmosphereLampTab = getActivity().findViewById(R.id.atmosphere_lamp);
        newMode = getActivity().findViewById(R.id.save_new_mode);
        upDateMode = getActivity().findViewById(R.id.update_mode);

        rmTypeTab.setTabData(rmType);
        rmDirectionTab.setTabData(rmDirection);
        chairTypeTab.setTabData(chairType);
        lightTypeTab.setTabData(lightType);
        atmosphereLampTab.setTabData(atmosphereLamp);



        //todo 模式布局
        rly=getActivity().findViewById(R.id.carsetfragment_carset_layout_yinliang);
        rlh=getActivity().findViewById(R.id.carsetfragment_seat_layout_rearview);
        rlz=getActivity().findViewById(R.id.carsetfragment_lamplight_layout_seat);
        rld=getActivity().findViewById(R.id.carsetfragment_lamplight_layout_lemp);
        rly.setVisibility(View.VISIBLE);
        rlh.setVisibility(View.GONE);
        rlz.setVisibility(View.GONE);
        rld.setVisibility(View.GONE);
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
        vb10=getActivity().findViewById(R.id.volume_100);

        //todo 后视镜



        //todo 左右后视镜切换
        zuohsj=getActivity().findViewById(R.id.zuohoushijing);
        youhsj=getActivity().findViewById(R.id.youhoushijing);
        jingxia=getActivity().findViewById(R.id.jingxia);




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
                int a=0;
                switch (i){
                    case R.id.volume0:
                        a=0;
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_10:
                        a=10;
                        yin.setImageResource(yin10);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,10,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_20:
                        a=20;
                        yin.setImageResource(yin20);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,20,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_30:
                        a=30;
                        yin.setImageResource(yin30);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,30,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_40:
                        a=40;
                        yin.setImageResource(yin40);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog50);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,40,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_50:
                        a=50;
                        yin.setImageResource(yin50);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,50,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_60:
                        a=60;
                        yin.setImageResource(yin60);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,60,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_70:
                        a=70;
                        yin.setImageResource(yin70);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,70,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_80:
                        a=80;
                        yin.setImageResource(yin80);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,80,0);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_90:
                        a=90;
                        yin.setImageResource(yin90);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,90,AudioManager.FLAG_SHOW_UI);
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
                        vb10.setBackgroundResource(R.color.tm);
                        break;
                    case R.id.volume_100:
                        a=100;
                        yin.setImageResource(yin100);
                        yinlianglog.setImageResource(R.mipmap.yinlianglog100);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC,100,AudioManager.FLAG_SHOW_UI);
                        vb0.setBackgroundResource(R.color.tm);
                        vb9.setBackgroundResource(R.color.tm);
                        vb2.setBackgroundResource(R.color.tm);
                        vb3.setBackgroundResource(R.color.tm);
                        vb4.setBackgroundResource(R.color.tm);
                        vb5.setBackgroundResource(R.color.tm);
                        vb6.setBackgroundResource(R.color.tm);
                        vb7.setBackgroundResource(R.color.tm);
                        vb8.setBackgroundResource(R.color.tm);
                        vb1.setBackgroundResource(R.color.tm);
                        vb10.setBackgroundResource(R.drawable.shape_hubian);
                        break;
                }
                customMode.setVolume(a);
            }
        });

        //TODO 后视镜调节


        //Todo 调节左右后视镜
        //rearview_position=1代表选择左后视镜 2代表选择右后视镜
        zuoyouhsj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.zuohoushijing:
                      rearview_position=1;
                    break;
                    case R.id.youhoushijing:
                      rearview_position=2;
                    break;
                }
            }
        });










    }
}