package com.hirain.hirain.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.hirain.hirain.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.hirain.hirain.R.mipmap.hshijing;
import static com.hirain.hirain.R.mipmap.nodengguang;
import static com.hirain.hirain.R.mipmap.nohshijing;
import static com.hirain.hirain.R.mipmap.zuoyiquanbi;


public class AlluseFragment extends Fragment implements View.OnClickListener {

    //左右连个视频播放器
    private VideoView leftVideo;
    private VideoView rightVideo;
    //线路启动和停止
    private TextView modeStart;
    private TextView modeStop;
    //线路 1,2,3,4
    private Button line1;
    private Button line2;
    private Button line3;
    private Button line4;
    public boolean isPlay = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alluse, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        rightVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                rightVideo.start();
            }
        });
        leftVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                leftVideo.start();
            }
        });
    }

    private void initData() {

        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1);
        leftVideo.setVideoURI(uri);

//        leftVideo.start();
        Uri uri2 = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video3);
        rightVideo.setVideoURI(uri2);

//        rightVideo.start();
    }

    private void initView() {

        leftVideo = getActivity().findViewById(R.id.video_left);
        rightVideo = getActivity().findViewById(R.id.video_right);

        modeStart = getActivity().findViewById(R.id.drive_mode_start);
        modeStop = getActivity().findViewById(R.id.drive_mode_stop);
        line1 = getActivity().findViewById(R.id.drive_mode_line1);
        line2 = getActivity().findViewById(R.id.drive_mode_line2);
        line3 = getActivity().findViewById(R.id.drive_mode_line3);
        line4 = getActivity().findViewById(R.id.drive_mode_line4);
        modeStart.setOnClickListener(this);
        modeStop.setOnClickListener(this);
        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Object event){
        Log.i("wxy", "getMessage: AASDASDA");
        /*if(event==1){
            playVideo();
        }else {
            pauseVideo();
        }*/
    }

    public void pauseVideo() {

        rightVideo.pause();
        leftVideo.pause();

    }

    public void playVideo() {
        if (!isPlay) {
            leftVideo.requestFocus();
            rightVideo.requestFocus();
            isPlay = true;
            rightVideo.start();
            leftVideo.start();
        }else {
            rightVideo.start();
            leftVideo.start();
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("wxy", "onStop: aa");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drive_mode_start:

                rightVideo.start();
                leftVideo.start();
                break;
            case R.id.drive_mode_stop:
                rightVideo.pause();
                leftVideo.pause();
                break;
            case R.id.drive_mode_line1:
                line1.setBackgroundResource(R.mipmap.yinliang);
                line2.setBackgroundResource(nohshijing);
                line3.setBackgroundResource(zuoyiquanbi);
                line4.setBackgroundResource(nodengguang);
                break;
            case R.id.drive_mode_line2:
                line1.setBackgroundResource(R.mipmap.noyinliang);
                line2.setBackgroundResource(hshijing);
                line3.setBackgroundResource(zuoyiquanbi);
                line4.setBackgroundResource(nodengguang);
                break;
            case R.id.drive_mode_line3:
                line1.setBackgroundResource(R.mipmap.noyinliang);
                line2.setBackgroundResource(nohshijing);
                line3.setBackgroundResource(R.mipmap.zuoyi);
                line4.setBackgroundResource(nodengguang);
                break;
            case R.id.drive_mode_line4:
                line1.setBackgroundResource(R.mipmap.noyinliang);
                line2.setBackgroundResource(nohshijing);
                line3.setBackgroundResource(zuoyiquanbi);
                line4.setBackgroundResource(R.mipmap.dengguang);
                break;
        }
    }
}