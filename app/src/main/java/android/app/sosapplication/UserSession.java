package android.app.sosapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by MySelf on 5/27/2017.
 */

public class UserSession {
    SharedPreferences sharedPreferences;
    Context ct;
    private String id=null;

    public String getId() {
        id = sharedPreferences.getString("id", null);
        return id;
    }

       public UserSession(Context ct)
    {
        this.ct=ct;
        sharedPreferences=ct.getSharedPreferences("user_session",Context.MODE_PRIVATE);
        Log.d("shared_created:","<<Created>>");
    }
    public void setSession(String user_id,String user_pass)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("id",user_id);
        editor.putString("pass",user_pass);
        editor.commit();
       Log.d("shared_setsession:","<<success>>");
    }
    public boolean validateSession()
    {
        boolean value=false;
       try {
           String id = sharedPreferences.getString("id", null);
           String pass = sharedPreferences.getString("pass", null);
           if ((id.equals(null) || id == null) && pass.equals(null) || pass == null) {
               value = false;
           } else {
               value = true;
           }
           Log.d("validate_session", "validate() executed;");
       }catch (Exception ex)
       {
           Log.d("validating_session_exp:",ex.getMessage()+ex.getCause());
       }
        return value;
    }
    public void invalidateSession()
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("id");
        editor.remove("pass");
        editor.clear();
        editor.commit();
        Log.d("invalidate_session","session deleted;");
    }
}
