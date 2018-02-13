package com.deust.applicationmescontacts;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

    MyContactAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Liste des contacts
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        Contact contact = new Contact("Dupont Thomas","0611111111",false);
        contactList.add(contact);
        contact = new Contact("Dupont Marie","0622222222",false);
        contactList.add(contact);
        contact = new Contact("Laforest Pierre","0633333333",false);
        contactList.add(contact);
        contact = new Contact("Larorest Alex","0644444444",false);
        contactList.add(contact);
        contact = new Contact("Perrez Benjamin","0655555555",false);
        contactList.add(contact);
        contact = new Contact("Laforest St-phane","0666666666",false);
        contactList.add(contact);
        contact = new Contact("Dupont Antoine","0677777777",false);
        contactList.add(contact);


        //Etape 5: R-cup-rer la liste des contacts du t-l-phone et remplacer la liste de contacts - cocher par celle-ci.
        Contact contactel;
        ArrayList<Contact> mylistel = new ArrayList<Contact>();
        ContentResolver cr=getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection=null;
        String selection=null;
        String[] selectionArgs = null;
        String sortOrder=null;
        Cursor cur = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        if(cur.getCount()>0)
        { while(cur.moveToNext())
        {
            String Name=cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            int num=cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if (num ==1)
            {

                Uri uri2=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection2 = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?" ;
                String[] selectionArgs2 = new String[] {id};
                Cursor cur2=cr.query(uri2, projection, selection2, selectionArgs2, sortOrder);
                int i=0;
                while(cur2.moveToNext())
                {
                    String phone=cur2.getString(cur2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactel= new Contact(Name,phone,false);
                    mylistel.add(contactel);

                    i++;
                }
            }
        }

        }
        contactList=mylistel;

        //*****************FIN Etape 5***********************



        displayListView(contactList);
        checkButtonValiderClick();
        checkButtonSelectAllClick();

    }


    private void displayListView(ArrayList <Contact> contactList) {


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyContactAdapter(this,
                R.layout.contact_info, contactList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

    }

    private class MyContactAdapter extends ArrayAdapter<Contact> {

        private ArrayList<Contact> contactList;

        public MyContactAdapter(Context context, int textViewResourceId,
                                ArrayList<Contact> contactList) {
            super(context, textViewResourceId, contactList);
            this.contactList = new ArrayList<Contact>();
            this.contactList.addAll(contactList);
        }



        private class ViewHolder {
            TextView telephone ;
            CheckBox nom_prenom;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.contact_info, null);

                holder = new ViewHolder();
                holder.telephone = (TextView) convertView.findViewById(R.id.text_telephone);
                holder.nom_prenom = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.nom_prenom.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Contact contact = (Contact) cb.getTag();
                        contact.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Contact contact = contactList.get(position);
            holder.telephone.setText(" (" +  contact.getTelephone() + ")");
            holder.nom_prenom.setText(contact.getNom_prenom());
            holder.nom_prenom.setChecked(contact.isSelected());
            holder.nom_prenom.setTag(contact);

            return convertView;

        }

    }

    private void checkButtonValiderClick() {


        Button myButton = (Button) findViewById(R.id.btn_valider1);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("Les contacts selectionn-es:\n");

                // Etape 4 : R-cup-rer la liste des contacts selectionn-s.
                ArrayList<Contact> contactList = dataAdapter.contactList;
                ArrayList<Contact> mylist = new ArrayList<Contact>();

                for(int i=0;i<contactList.size();i++){
                    Contact contact = contactList.get(i);
                    if(contact.isSelected()){
                        responseText.append("\n" + contact.getNom_prenom() + " : " +  contact.getTelephone()  );
                        mylist.add(contact);
                    }
                }

                //*****************FIN Etape 4***********************

                // Etape 6 :
                if(mylist.size()==0)
                {
                    Toast.makeText(getApplicationContext(),
                            "Pas de contacts selectionn-es", Toast.LENGTH_LONG).show();


                }
                else {
                    Toast.makeText(getApplicationContext(),
                            responseText, Toast.LENGTH_LONG).show();



                    Intent intent = new Intent(MainActivity.this, MainActivitySecond.class);

                    intent.putParcelableArrayListExtra("les_contacts_selected",mylist);

                    startActivity(intent);

                }
            }

        });



    }

    private void checkButtonSelectAllClick() {

        Button myButton = (Button) findViewById(R.id.btn_select);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                ArrayList<Contact> contactList = dataAdapter.contactList;



                for(int i=0;i<contactList.size();i++){
                    contactList.get(i).setSelected(true);
                }
                //create an ArrayAdaptar from the String Array
                dataAdapter = new MyContactAdapter(MainActivity.this,R.layout.contact_info, contactList);
                ListView listView = (ListView) findViewById(R.id.listView1);
                // Assign adapter to ListView
                listView.setAdapter(dataAdapter);


            }
        });
    }


/*private void checkButtonSelecAllClick() {
	
	  Button myButton = (Button) findViewById(R.id.btn_seclect);
	  myButton.setOnClickListener(new OnClickListener() {
	 
	   @Override
	   public void onClick(View v) {
		

			    ArrayList<Contact> contactList = dataAdapter.contactList;
			  
			    

			       for(int i=0;i<contactList.size();i++){
			        	contactList.get(i).setSelected(true);
			       }
			        //create an ArrayAdaptar from the String Array
			        dataAdapter = new MyContactAdapter(MainActivity.this,R.layout.contact_info, contactList);
			        ListView listView = (ListView) findViewById(R.id.listView1);
			        // Assign adapter to ListView
			        listView.setAdapter(dataAdapter);


	   }
	  });
	}*/
}

