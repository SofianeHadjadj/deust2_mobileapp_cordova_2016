package com.deust.applicationmescontacts;


import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySecond extends Activity {

	String monListName="Liste des contacts: \n";
	String message="";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_second);


		// getIntent() is a method from the started activity
		Intent myIntent = getIntent(); // gets the previously created intent
		final  ArrayList<Contact> myList =myIntent.getParcelableArrayListExtra("les_contacts_selected");


		final TextView tv_listTel = (TextView) findViewById(R.id.listNameContact);
		for(int i=0;i<myList.size();i++){

			//r-cuperer les Nom_prenom
			monListName+="<" + myList.get(i).getNom_prenom() + "> \n ";
		}

		tv_listTel.setText(monListName);



		final TextView monchoix1=(TextView) findViewById(R.id.choix1);
		final TextView monchoix2=(TextView) findViewById(R.id.choix2);
		final TextView monchoix3=(TextView) findViewById(R.id.choix3);
		final TextView monchoix4=(TextView) findViewById(R.id.choix4);

		//Test TextView Message choix1
		monchoix1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				monchoix1.setTextColor(Color.parseColor("blue"));
				monchoix2.setTextColor(Color.parseColor("#000000"));
				monchoix3.setTextColor(Color.parseColor("#000000"));
				monchoix4.setTextColor(Color.parseColor("#000000"));
				Toast.makeText(getApplicationContext(),
						"Choix1",
						Toast.LENGTH_LONG).show();
				message=(String) monchoix1.getText();

			}
		});

		//Test TextView Message choix2
		monchoix2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				monchoix2.setTextColor(Color.parseColor("blue"));
				monchoix1.setTextColor(Color.parseColor("#000000"));
				monchoix3.setTextColor(Color.parseColor("#000000"));
				monchoix4.setTextColor(Color.parseColor("#000000"));
				Toast.makeText(getApplicationContext(),
						"Choix2",
						Toast.LENGTH_LONG).show();
				message=(String) monchoix2.getText();


			}
		});
		//Test TextView Message choix3
		monchoix3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				monchoix3.setTextColor(Color.parseColor("blue"));
				monchoix2.setTextColor(Color.parseColor("#000000"));
				monchoix1.setTextColor(Color.parseColor("#000000"));
				monchoix4.setTextColor(Color.parseColor("#000000"));
				Toast.makeText(getApplicationContext(),
						"Choix3",
						Toast.LENGTH_LONG).show();
				message=(String) monchoix3.getText();


			}
		});

		//Test TextView Message choix4
		monchoix4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				monchoix4.setTextColor(Color.parseColor("blue"));
				monchoix2.setTextColor(Color.parseColor("#000000"));
				monchoix3.setTextColor(Color.parseColor("#000000"));
				monchoix1.setTextColor(Color.parseColor("#000000"));
				Toast.makeText(getApplicationContext(),
						"Choix4",
						Toast.LENGTH_LONG).show();
				message=(String) monchoix4.getText();


			}
		});

		final Button monbuttonValider = (Button) findViewById(R.id.button1);
		monbuttonValider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent intent2 = new Intent(MainActivitySecond.this, MainActivity.class);
				startActivity(intent2);
			}
		});


		final Button monbuttonAnnuler = (Button) findViewById(R.id.button2);
		monbuttonAnnuler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(MainActivitySecond.this, MainActivity.class);
				startActivity(intent1);

			}
		});

		//Etape10 ***********************************
		final Button monbuttonEnvoi = (Button) findViewById(R.id.button3);
		monbuttonEnvoi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivitySecond.this);

				// Setting Dialog Title
				alertDialog.setTitle("Confirmer Envoi...");

				// Setting Dialog Message
				alertDialog.setMessage("Vous souhaitez envoyer le message?");


				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {

						//Envoi des SMS*****

						if(message!=""){

							try {
								SmsManager smsManager = SmsManager.getDefault();
								String sms;

								for(int i=0;i<myList.size();i++){
									sms=message.replaceAll("(prenom)", myList.get(i).getNom_prenom());
									smsManager.sendTextMessage(myList.get(i).getTelephone(), null, sms, null, null);
								}
								Toast.makeText(getApplicationContext(), "SMS envoy-!",

										Toast.LENGTH_LONG).show();


							} catch (Exception e) {

								Toast.makeText(getApplicationContext(),
										"Echec d'envoi",
										Toast.LENGTH_LONG).show();
								e.printStackTrace();


							}
						}
						else{

							Toast.makeText(getApplicationContext(),
									"Il faut bien choisir un message - envoyer!",
									Toast.LENGTH_LONG).show();
						}
						//*******************





					}
				});

				// Setting Negative "NO" Button
				alertDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						Toast.makeText(getApplicationContext(), "Vous avez cliquez sur Non", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

				// Showing Alert Message
				alertDialog.show();
			}
		});



	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

