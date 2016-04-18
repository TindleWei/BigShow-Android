package com.gamebuddy.bigshow.core;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.squareup.leakcanary.LeakCanary;

/**
 * describe
 * created by tindle
 * created time 16/2/26 下午3:09
 */
public class App extends Application {

    public static App instance;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initLeanCloud();
        LeakCanary.install(this);
    }

    public void initLeanCloud() {
        AVOSCloud.useAVCloudCN();
        AVOSCloud.initialize(this,
                "ja8IkbhRpJTx7MbtvT3HEDX4-gzGzoHsz",
                "fkIJXAcgdKeM1KJMwkfub3L4");
//        AVIMMessageManager.registerAVIMMessageType(AVIMOperationMessage.class);
//        AVObject.registerSubclass(User.class);
//        AVObject.registerSubclass(ChatMessage.class);
//        AVObject.registerSubclass(AddRequest.class);
//        AVObject.registerSubclass(GroupMessage.class);
//        AVObject.registerSubclass(ChatGroup.class);
//        AVInstallation.getCurrentInstallation().saveInBackground();
////        PushService.setDefaultPushCallback(this, MsgActivity.class);
//        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);

        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new AVIMMessageHandler());
    }
}
