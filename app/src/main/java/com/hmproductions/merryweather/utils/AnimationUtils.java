package com.hmproductions.merryweather.utils;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Harsh Mahajan on 3/7/2017.
 */

public class AnimationUtils {

    public static void AnimateCircularReveal(View view)
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
        {
            int finalRadius = (int)Math.hypot(view.getWidth()/2,view.getHeight()/2);
            Animator animator = ViewAnimationUtils.createCircularReveal(view, view.getWidth()/2, view.getHeight()/2, 0, finalRadius);
            animator.start();
        }
    }
}
