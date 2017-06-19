package android.app.sosapplication.modal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MySelf on 6/11/2017.
 */

public class NetworkUtil {
    private static boolean isOnline = false;

    public static boolean isNetworkConnected(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void changeNetworkState(boolean isTrue) {
        isOnline = isTrue;
    }

    /*public static boolean isOnline() {
        return isNetworkConnected(Sosapplication.getInstance());
    }*/
}
