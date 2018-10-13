package io.github.boldijar.cosasapp.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;

import io.github.boldijar.cosasapp.data.ServerMessage;
import timber.log.Timber;

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
            try {
                Timber.d("Got message: " + remoteMessage.getData().get("message"));
                ServerMessage message = new Gson().fromJson(remoteMessage.getData().get("message"), ServerMessage.class);
                EventBus.getDefault().post(message);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
