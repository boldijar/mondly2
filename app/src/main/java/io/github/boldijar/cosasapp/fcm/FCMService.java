package io.github.boldijar.cosasapp.fcm;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;

import io.github.boldijar.cosasapp.data.MessageType;
import io.github.boldijar.cosasapp.data.ServerMessage;
import io.github.boldijar.cosasapp.util.Prefs;
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
                if (message.mType == MessageType.CHALLENGE) {
                    challange(message);
                }
                EventBus.getDefault().post(message);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void challange(ServerMessage message) {
        if (Prefs.getUser().mId == message.mWhoWasChallanged) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toast.makeText(FCMService.this, "Pssst! Check the rooms, you were challenged by " + message.mChallengedBy, Toast.LENGTH_SHORT).show());

        }
    }
}
