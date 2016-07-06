package com.wei.bigshow.ui.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wei.bigshow.R;

/**
 * describe
 * created by tindle
 * created time 16/7/6 下午5:32
 */
public class CardRevealFragment extends Fragment{

    ImageView imageView;
    ImageButton imageButton;
    LinearLayout revealView, layoutButtons;
    Animation alphaAnimation;
    float pixelDensity;
    boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_reveal, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pixelDensity = getResources().getDisplayMetrics().density;

        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageButton = (ImageButton) view.findViewById(R.id.launchTwitterAnimation);
        revealView = (LinearLayout) view.findViewById(R.id.linearView);
        layoutButtons = (LinearLayout) view.findViewById(R.id.layoutButtons);

        alphaAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_anim);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(v);
            }
        });
    }

    public void launch(View view) {

        /*
         MARGIN_RIGHT = 16;
         FAB_BUTTON_RADIUS = 28;
         */
        int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());

        if (flag) {

            imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
            imageButton.setImageResource(R.mipmap.image_cancel);

            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                    revealView.getLayoutParams();
            parameters.height = imageView.getHeight();
            revealView.setLayoutParams(parameters);

            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, 0, hypotenuse);
            anim.setDuration(700);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.VISIBLE);
                    layoutButtons.startAnimation(alphaAnimation);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            revealView.setVisibility(View.VISIBLE);
            anim.start();

            flag = false;
        } else {

            imageButton.setBackgroundResource(R.drawable.rounded_button);
            imageButton.setImageResource(R.mipmap.twitter_logo);

            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, hypotenuse, 0);
            anim.setDuration(400);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealView.setVisibility(View.GONE);
                    layoutButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();
            flag = true;
        }
    }
}
