package android.app.sosapplication;

import android.app.Fragment;
import android.app.sosapplication.modal.Helper;
import android.app.sosapplication.modal.UserInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;




public class Signup extends Fragment {

EditText name,contact,email,password,address;
    RadioButton rdomale,rdofemale,rdo_others;
    Button btnregister;
    String gender=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        name = (EditText) getActivity().findViewById(R.id.name);
        contact = (EditText) getActivity().findViewById(R.id.signupcontact);
        password = (EditText) getActivity().findViewById(R.id.password);
        email = (EditText) getActivity().findViewById(R.id.email);
        address = (EditText) getActivity().findViewById(R.id.address);

        btnregister = (Button) getActivity().findViewById(R.id.register);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals(null) || name.getText().toString() == "" || contact.getText().toString().equals(null) || contact.getText().toString() == "" || password.getText().toString().equals(null) || password.getText().toString() == "" || address.getText().toString().equals(null) || address.getText().toString() == "" || email.getText().toString().equals(null) || email.getText().toString() == " ") {
                    Toast.makeText(getActivity(), "Please Fill all Entry First", Toast.LENGTH_SHORT).show();
                } else if (contact.getText().toString().length() < 10 || contact.getText().toString().length() == 11) {
                    Toast.makeText(getActivity(), "Enter valid Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    Helper he=new Helper(getActivity());
                    UserInfo us=new UserInfo();
                    us.setName(name.getText().toString());
                    us.setAddress(address.getText().toString());
                    us.setContact(contact.getText().toString());
                    us.setEmail(email.getText().toString());
                    us.setPassword(password.getText().toString());
                    String msg=he.registerUser(us);
                    Toast.makeText(getActivity(),""+msg, Toast.LENGTH_SHORT).show();
                    UserSession userSession=new UserSession(getActivity());
                    userSession.setSession(contact.getText().toString(),name.getText().toString());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }

            }
        });
    }
}