package android.app.sosapplication;

import android.Manifest;
import android.app.sosapplication.modal.NetworkUtil;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;


public class SplashScreen extends AppCompatActivity {
ProgressBar progressBar;
    private  static final int PROGRESS=0*1;
    private int mProgressStatus=0;
    private Handler mHandler=new Handler();
    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /*checking network availibility*/
        if(!NetworkUtil.isNetworkConnected(this))
        {
            Toast.makeText(this,"Your Internet Connection Was Off.It Has been Turn on.", Toast.LENGTH_SHORT).show();
            NetworkUtil.changeNetworkState(true);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1);
            }

        }
       // progressBar=(ProgressBar)findViewById(R.id.progressBar);
       // progressBar.setVisibility(View.VISIBLE);
       // final SharedPreferences userShared=getSharedPreferences("userinfo",MODE_PRIVATE);

        userSession=new UserSession(this);
       new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   Thread.sleep(1000);
                    if(userSession.validateSession()) {
                        startActivity(new Intent(SplashScreen.this,MainActivity.class));

                    }else
                    {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }

                    finish();
                }
                catch (Exception ex)
                 {
                    // Toast.makeText(SplashScreen.this,"Exceptin Occured:"ex.get, Toast.LENGTH_SHORT).show();
                 }
                }

        }).start();

    }
}
