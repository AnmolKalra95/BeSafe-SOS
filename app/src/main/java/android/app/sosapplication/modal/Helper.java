package android.app.sosapplication.modal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by MySelf on 6/11/2017.
 */

public class Helper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public Helper(Context context) {
        super(context,"users",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userinfo(id integer not null primary key autoincrement,name text,contact text,password text,email text,address text)");
        db.execSQL("create table contact_info(id integer not null primary key autoincrement,name text,contact text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("drop table if exist userinfo");
        onCreate(db);
    }
    public String registerUser(UserInfo user)
    {
        String msg=null;
       try {
           db = this.getWritableDatabase();
           ContentValues cv = new ContentValues();
           cv.put("name", user.getName());
           cv.put("contact", user.getContact());
           cv.put("email", user.getEmail());
           cv.put("password", user.getPassword());
           cv.put("address", user.getAddress());
           long result = db.insert("userinfo", null, cv);
           if (result > 0) {
               msg = "User Registered Successfully.";
           }
       }catch (Exception ex)
       {
           Log.d("registering_error",ex.getMessage());
       }
        return msg;
    }
    public boolean validateUser(String userid,String pwd)
    {
        boolean result=false;
        try{
             db=this.getReadableDatabase();
            Cursor cr=db.query("userinfo",new String[]{"id","name","contact"},"contact=? and password=?",new String[]{userid,pwd},null,null,null);
            cr.moveToFirst();
            if(cr.getCount()>0)
            {
              result=true;
            }
        }catch (Exception ex)
        {
        Log.d("getting_address",ex.getMessage());
        }
        return result;
    }
    public void saveContacts(String name,String contact)
    {
        try {
            db =this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put("name",name);
            contentValues.put("contact",contact);
            db.insert("contact_info",null,contentValues);
        }catch (Exception ex)
        {
            Log.d("save_cont_error",ex.getMessage());
        }
    }
    public Cursor getContactInfo()
    {
        Cursor cr=null;
        db=getReadableDatabase();
        return cr=db.query(true,"contact_info",new String[]{"name","contact"},null,null,null,null,null,null);
    }

}
