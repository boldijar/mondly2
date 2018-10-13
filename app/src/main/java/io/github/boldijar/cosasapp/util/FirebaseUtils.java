package io.github.boldijar.cosasapp.util;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * @author Paul
 * @since 2018.10.13
 */
public class FirebaseUtils {

    public static void registerToRoom(int room) {
        FirebaseMessaging.getInstance().subscribeToTopic("room_" + room);
    }

    public static void unregisterToRoom(int room) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("room_" + room);
    }
}
