package com.hirain.hirain.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hirain.hirain.R;


public class UserFragment extends Fragment {


    float leftRote= 20;
    float leftRote2= -20;
    float rightRote= -20;
    float rightRote2= 20;
    int  duration= 4000;

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
        ImageView light11 = getActivity().findViewById(R.id.light1_2);
        ImageView light2 = getActivity().findViewById(R.id.light2);
        ImageView light22 = getActivity().findViewById(R.id.light2_2);
        ImageView light3 = getActivity().findViewById(R.id.light3);
        ImageView light33 = getActivity().findViewById(R.id.light3_3);
        ImageView light4 = getActivity().findViewById(R.id.light4);
        ImageView light44 = getActivity().findViewById(R.id.light4_4);
        ImageView light5 = getActivity().findViewById(R.id.light5);
        ImageView light55 = getActivity().findViewById(R.id.light5_5);
        ImageView light6 = getActivity().findViewById(R.id.light6);
        ImageView light66 = getActivity().findViewById(R.id.light6_6);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) light2.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) light4.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) light6.getLayoutParams();

        lightLeftAnim(light1,0,0);
        lightLeftAnim(light3,0,0);
        lightLeftAnim(light5,0,0);
        //view 宽度暂时写死，应该动态获取
        lightRightAnim(light2,1062,0);
        lightRightAnim(light4,545,0);
        lightRightAnim(light6,365,0);

        lightAlpha(light11);
        lightAlpha(light22);
        lightAlpha(light33);
        lightAlpha(light44);
        lightAlpha(light55);
        lightAlpha(light66);

        startPropertyAnim(car);

    }




    private void lightRightAnim(ImageView imageView, float x, float y) {

        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation", 0f, rightRote, rightRote2,0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f, 1f);
        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(duration);

        animationSet.start();
    }


    public void lightAlpha(ImageView imageView){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f,0f,0f,0f, 1f);
        alpha.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(alpha);
        animationSet.setDuration(duration);

        animationSet.start();
    }

    public void lightLeftAnim(ImageView imageView,int x,int y,float... values){
        imageView.setPivotX(x);
        imageView.setPivotY(y);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView, "rotation", 0f,leftRote,leftRote2,0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f, 1f);
        rotation.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(rotation);
        animationSet.setDuration(duration);

        animationSet.start();
    }

    // 动画实际执行安然
    private void startPropertyAnim(ImageView imageView) {
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。



        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f,0.4f, 1f,0.4f);
        alpha.setRepeatCount(Animation.INFINITE);

        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(alpha);
        animationSet.setDuration(duration);
        animationSet.start();
    }

}