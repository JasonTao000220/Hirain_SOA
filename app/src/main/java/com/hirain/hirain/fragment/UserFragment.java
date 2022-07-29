package com.hirain.hirain.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hirain.hirain.R;


public class UserFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView car = getActivity().findViewById(R.id.car);
        ImageView light1 = getActivity().findViewById(R.id.light1);
        ImageView light2 = getActivity().findViewById(R.id.light2);
        ImageView light3 = getActivity().findViewById(R.id.light3);
        ImageView light4 = getActivity().findViewById(R.id.light4);
        ImageView light5 = getActivity().findViewById(R.id.light5);
        ImageView light6 = getActivity().findViewById(R.id.light6);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) light2.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) light4.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) light6.getLayoutParams();

        Log.i("wxy", "onActivityCreated: "+light2.getWidth()+"==="+layoutParams.topMargin);
        Log.i("wxy", "onActivityCreated: "+light4.getWidth()+"==="+layoutParams3.topMargin);
        Log.i("wxy", "onActivityCreated: "+light6.getWidth()+"==="+layoutParams5.topMargin);

        lightLeftAnim(light1,0,0);
//        lightLeftAnim(light3,0,0);
//        lightLeftAnim(light5,0,0);
        lightRightAnim(light2,849,0);
//        lightRightAnim(light4,522,0);
//        lightRightAnim(light6,413,0);


        startPropertyAnim(car);
    }

    private void lightRightAnim(ImageView imageView, int x, int y) {

        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation", 0f, -30f, 0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f, 1f);
        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(3000);

        animationSet.start();
    }


    public void lightLeftAnim(ImageView imageView,int x,int y,float... values){
        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation", 0f,30,0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f, 1f);
        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(3000);

        animationSet.start();
    }

    // 动画实际执行安然
    private void startPropertyAnim(ImageView car) {
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。
        AnimationSet aset=new AnimationSet(true);
        AlphaAnimation aa=new AlphaAnimation(1,0);
        aa.setDuration(2000);
        aset.addAnimation(aa);
        car.startAnimation(aset);
        aset.setFillAfter(true);

        // 正式开始启动执行动画
        aset.start();
    }

}