package android.app.sosapplication;

import android.app.sosapplication.modal.Helper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    Map<String,String> param=new HashMap<String,String>();
    private String id=null;
    private String name=null;
    private String email=null;
    private String gender=null;
    private String local_address=null;

    Button login,signup;
    EditText user_id,user_pass;

    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_id = (EditText) findViewById(R.id.loginid);
        user_pass = (EditText) findViewById(R.id.loginpassword);

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
    }

    public void callSignUpFragment(View view) {
        getFragmentManager().beginTransaction().replace(R.id.container, new Signup()).commit();
        CardView crd_id, crd_nm, crd_btn_login, crd_btn_signup;
        crd_id = (CardView) findViewById(R.id.crd_id);
        crd_id.setVisibility(View.GONE);
        crd_nm = (CardView) findViewById(R.id.crd_num);
        crd_nm.setVisibility(View.GONE);
        crd_btn_login = (CardView) findViewById(R.id.crd_btn_login);
        crd_btn_login.setVisibility(View.GONE);
        crd_btn_signup = (CardView) findViewById(R.id.crd_btn_signup);
        crd_btn_signup.setVisibility(View.GONE);
        login.setVisibility(View.GONE);
        signup.setVisibility(View.GONE);
    }

    public void login(View view) {
        final String login_id = user_id.getText().toString();
        final String login_pwd = user_pass.getText().toString();
        if (login_id.length() == 0 || login_id.isEmpty() || login_pwd.length() == 0 || login_pwd.isEmpty()) {

            Toast.makeText(this, "User Id Or Password should not Empty", Toast.LENGTH_SHORT).show();

        } else if (login_id.length() < 10) {
            Toast.makeText(this, "Enter valid Id", Toast.LENGTH_SHORT).show();
        } else {
            Helper he=new Helper(this);
            boolean result=he.validateUser(user_id.getText().toString(),user_pass.getText().toString());
            if(result)
            {
                UserSession userSession=new UserSession(this);
                userSession.setSession(user_id.getText().toString(),user_pass.getText().toString());
                startActivity(new Intent(this, MainActivity.class));

            }else
            {
                Toast.makeText(this,"User Id or Password Invalid.", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
