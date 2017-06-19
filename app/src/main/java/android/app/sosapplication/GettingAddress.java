package android.app.sosapplication;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by MySelf on 5/8/2017.
 */
public class GettingAddress extends Fragment {
    double lati, longi;

    LocationManager locationManager;
    LocationListener locationListener;
    ProgressBar pb;
    //TextView tv1;
    TextView tv2;
    Button user_location, send;
    //ImageButton btnreferese;
    String user_address = null;
    int l = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.custom_address_layout, container, false);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        pb = (ProgressBar) getActivity().findViewById(R.id.pb);
        // pb.setVisibility(View.GONE);
        //tv1=(TextView)getActivity().findViewById(R.id.latlong);
        tv2 = (TextView) getActivity().findViewById(R.id.locationview);
        send = (Button) getActivity().findViewById(R.id.BeSafe);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendSms();
                } catch (Exception ex) {
                    Log.d("sending_sms_error", ex.getMessage());
                }
            }
        });
        // btnreferese=(ImageButton)getActivity().findViewById(R.id.btnreferece);
        //btnreferese.setBackgroundResource(R.drawable.pageloader);
        user_location = (Button) getActivity().findViewById(R.id.curent_location);

        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                lati = location.getLatitude();
                longi = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Toast.makeText(GettingAddress.this,"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getActivity(), "Provider enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {

                //Toast.makeText(getActivity(),"Provider disabled", Toast.LENGTH_SHORT).show();
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(getActivity(), "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                } else {
                    showGPSDisabledAlertToUser();
                }
            }

            private void showGPSDisabledAlertToUser() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                        .setCancelable(false)
                        .setPositiveButton("Goto Settings Page To Enable GPS",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent callGPSSettingIntent = new Intent(
                                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(callGPSSettingIntent);
                                    }
                                });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        };
        getLocation();
        user_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();


            }
        });
     /*   tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                getLocation();

                pb.setVisibility(View.GONE);
            }
        });*/
      /*  btnreferese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 121: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(FragmentSelectContacts.records.get(l).toString(), null, user_address, null, null);
                    Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        }
    }
    private void sendSms() {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_address", getActivity().MODE_PRIVATE);
        for (int i = 1; i < FragmentSelectContacts.records.size(); i = i + 2) {
            //String number = "12346556";  // The number on which you want to send SMS
           // startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",FragmentSelectContacts.records.get(i).toString(), null)));
            if(user_address==null)
            {
                user_address=sp.getString("address",null);
            }
           Intent in=new Intent(Intent.ACTION_SENDTO,Uri.parse("sms:"+FragmentSelectContacts.records.get(i).toString()));
            in.putExtra("sms_body","I'm in danger!Help me at: "+user_address+" Send by BeSafe SOS");
            startActivity(in);

           // Toast.makeText(getActivity(),"Sms Sent", Toast.LENGTH_SHORT).show();




          /*  try {


                String SENT = "SMS_SENT";
                String DELIVERED = "SMS_DELIVERED";

                PendingIntent sentPI = PendingIntent.getBroadcast(getContext(), 0,
                        new Intent(SENT), 0);

                PendingIntent deliveredPI = PendingIntent.getBroadcast(getContext(), 0,
                        new Intent(DELIVERED), 0);
/*
            //---when the SMS has been sent---
            getActivity().registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getActivity().getBaseContext(), "SMS sent",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(getActivity().getBaseContext(), "Generic failure",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(getActivity().getBaseContext(), "No service",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(getActivity().getBaseContext(), "Null PDU",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(getActivity().getBaseContext(), "Radio off",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            //---when the SMS has been delivered---
            getActivity().registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getActivity().getBaseContext(), "SMS delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(getActivity().getBaseContext(), "SMS not delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(DELIVERED));

            SmsManager sms = SmsManager.getDefault();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                }

            }

            //ArrayList<String> texts = sms.divideMessage("hi");
           // sms.sendMultipartTextMessage(FragmentSelectContacts.records.get(i).toString(), null, texts, null, null)
            sms.sendTextMessage(FragmentSelectContacts.records.get(i).toString(), null,user_address, sentPI, deliveredPI);

           // int name = 0;
               /* if (simID == 0) {
                    name = "isms";
                    // for model : "Philips T939" name = "isms0"
                } else if (simID == 1) {
                    name = "isms2";
                } else {
                    throw new Exception("can not get service which for sim '" + simID + "', only 0,1 accepted as values");
                }
               /* Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
                method.setAccessible(true);
                Object param = method.invoke(null,"isms");

               /* method = Class.forName("com.android.internal.telephony.ISms$Stub").getDeclaredMethod("asInterface", IBinder.class);
                method.setAccessible(true);
                Object stubObj = method.invoke(null, param);
                if (Build.VERSION.SDK_INT < 18) {
                    method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                    method.invoke(stubObj, FragmentSelectContacts.records.get(i).toString(), null, user_address,sentPI, deliveredPI);
                } else {
                    method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                    method.invoke(stubObj, getContext().getPackageName(),FragmentSelectContacts.records.get(i).toString(),null,user_address, sentPI, deliveredPI);
                }
*/
         /*   } catch (ClassNotFoundException e) {
                Log.e("apipas", "ClassNotFoundException:" + e.getMessage());
            } catch (NoSuchMethodException e) {
                Log.e("apipas", "NoSuchMethodException:" + e.getMessage());
            } catch (InvocationTargetException e) {
                Log.e("apipas", "InvocationTargetException:" + e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e("apipas", "IllegalAccessException:" + e.getMessage());
            } catch (Exception e) {
                Log.e("apipas", "Exception:" + e.getMessage());
            }*/

    }
}


    public void getLocation() {
        pb.setVisibility(View.VISIBLE);
        StringBuilder stringBuilder = new StringBuilder();


        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
        }else
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10,0, locationListener);
        }
            Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
        try {
            //lati=28.644800;
            //longi=77.216721;
            List<Address> addresses=geocoder.getFromLocation(lati,longi,1);
          for(Address address:addresses) {
              // Address address=addresses.get(0);
              stringBuilder.append(address.getAddressLine(0) + ","+address.getSubLocality()+",");
              stringBuilder.append(address.getLocality()+",");
              stringBuilder.append(address.getAdminArea()+ "-"+address.getPostalCode()+","+address.getCountryName());
              user_address=String.valueOf(stringBuilder);
              if(user_address!=null) {
                  SharedPreferences sp = getActivity().getSharedPreferences("sp_address", getActivity().MODE_PRIVATE);
                  SharedPreferences.Editor editor = sp.edit();
                  editor.putString("address", String.valueOf(stringBuilder));
                  editor.commit();
              }
          }tv2.setText(user_address);
        }catch (Exception ex)
        {
            stringBuilder.append(ex.getMessage());
        }
       // tv1.setText("lat="+lati+",longi="+longi);
        pb.setVisibility(View.GONE);
    }
}
