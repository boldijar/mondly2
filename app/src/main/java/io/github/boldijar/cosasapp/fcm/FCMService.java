package io.github.boldijar.cosasapp.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Paul
 * @since 2018.10.12
 */
public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null) {
            Log.e("TAG", "GOT NULL SHIET");
        } else {
            EventBus.getDefault().post(new Gson().toJson(remoteMessage.getData()));
        }
    }
}
