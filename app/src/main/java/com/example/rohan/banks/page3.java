package com.example.rohan.banks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class page3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);


        //Get the data from page2
        Bundle bd = getIntent().getExtras();
        String _id = bd.getString("_id");
        //String STATE=bd.getString("STATE");
        String BANK = bd.getString("BANK");        //Show Done
        String IFSC = bd.getString("IFSC");        //Show Done
        String BRANCH = bd.getString("BRANCH");    //Show Done
        String ADDRESS = bd.getString("ADDRESS");  //Show Done
        String CONTACT = bd.getString("CONTACT");  //Show Done
        String CITY = bd.getString("CITY");        //Show Dpne
        String DISTRICT = bd.getString("DISTRICT");
        String MICRCODE = bd.getString("MICR"); //Show Done


        TextView bank = (TextView) findViewById(R.id.tvBANK);
        TextView ifsc = (TextView) findViewById(R.id.tvIFSC);
        TextView branch = (TextView) findViewById(R.id.tvBRANCH);
        TextView address = (TextView) findViewById(R.id.tvADDRESS);
        TextView contact = (TextView) findViewById(R.id.tvCONTACT);
        TextView city = (TextView) findViewById(R.id.tvCITY);
        TextView micrcode = (TextView) findViewById(R.id.tvMICR);

        bank.setText("Bank       : " + BANK);
        ifsc.setText("IFSC Code  : " + IFSC);
        branch.setText("Branch     : " + BRANCH);
        address.setText("Address    : " + ADDRESS);
        contact.setText("Contact    : " + CONTACT);
        city.setText("City       : " + CITY);
        micrcode.setText("MICR code  : " + MICRCODE);

        Toast.makeText(getApplicationContext(), "Fetching Details....", Toast.LENGTH_SHORT).show();
    }
}
