package com.wei.bigshow.ui.vandor.keyevent;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 *
 */
public class KeyboardVisibilityEvent {

    private final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;

    public static int KEYBOARD_HEIGHT = 0;


    /**
     * Set keyboard visibility change event listener.
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     */
    public static void setEventListener(final Activity activity,
                                        final KeyboardVisibilityEventListener listener) {

        if (activity == null) {
            throw new NullPointerException("Parameter:activity must not be null");
        }

        if (listener == null) {
            throw new NullPointerException("Parameter:listener must not be null");
        }

        final View activityRoot = getActivityRoot(activity);

        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    private final Rect r = new Rect();

                    private final int visibleThreshold = Math.round(
                            KeyEventUtil.convertDpToPx(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));

                    private boolean wasOpened = false;

                    @Override
                    public void onGlobalLayout() {
                        activityRoot.getWindowVisibleDisplayFrame(r);

                        int heightDiff = activityRoot.getRootView().getHeight() - r.height();
                        KEYBOARD_HEIGHT = heightDiff;

                        boolean isOpen = heightDiff > visibleThreshold;

                        if (isOpen == wasOpened) {
                            // keyboard state has not changed
                            return;
                        }

                        wasOpened = isOpen;

                        listener.onVisibilityChanged(isOpen);
                    }
                });
    }

    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();

        View activityRoot = getActivityRoot(activity);
        int visibleThreshold = Math
                .round(KeyEventUtil.convertDpToPx(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));

        activityRoot.getWindowVisibleDisplayFrame(r);

        int heightDiff = activityRoot.getRootView().getHeight() - r.height();

        return heightDiff > visibleThreshold;
    }

    private static View getActivityRoot(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }
}
