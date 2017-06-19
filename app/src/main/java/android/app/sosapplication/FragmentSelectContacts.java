package android.app.sosapplication;

import android.app.sosapplication.modal.Helper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * Created by MySelf on 5/14/2017.
 */

public class FragmentSelectContacts extends Fragment {
GridView contactslist;
    Button addcontact,next;
    ArrayAdapter adapter;
    Helper he;
   public static ArrayList records=new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_select_contact,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        he=new Helper(getActivity());
        contactslist=(GridView)getActivity().findViewById(R.id.selected_contact_list);
       try{

           Cursor cr=he.getContactInfo();
           if(cr!=null)
           {
               cr.moveToFirst();
               do{
                   records.add(cr.getString(0));
                   records.add(cr.getString(1));

               }while (cr.moveToNext());

           }
       }catch (Exception ex){
           Log.d("getting_Contact_error",ex.getMessage());
       }
       if(records.size()>=0) {
           adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, records);
           contactslist.setAdapter(adapter);
       }
     /*  else
       {
           adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1);
       }*/
       // records.clear();

        addcontact=(Button)getActivity().findViewById(R.id.contacts);
     // next=(Button)getActivity().findViewById(R.id.next);
        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callcontact=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(callcontact,123);
            }
        });
    /*next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().beginTransaction().replace(R.id.myplace,new GettingAddress()).commit();
        }
    });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==getActivity().RESULT_OK) {

            String[] columns = new String[]{
                    ContactsContract.CommonDataKinds.Phone._ID,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            Cursor cr = getActivity().getContentResolver().query(data.getData(), columns, null, null, null);
            cr.moveToFirst();
           // ArrayList datalist=new ArrayList();
            String name=cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            records.add(name);


            String contact=cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            records.add(contact);
            he.saveContacts(name,contact);
            //adapter.notifyDataSetChanged();
            //records.addAll(datalist);
            adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,records);
            contactslist.setAdapter(adapter);
            cr.close();
        }
    }
}
