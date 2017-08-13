package com.example.rohan.banks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohan.banks.models.BankModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class page2 extends AppCompatActivity {

    private TextView tvJson;
    private String query;
    private ListView lvBanks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        Bundle bd = getIntent().getExtras();
        String query = bd.getString("query");

        Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
        //TextView show_text=(TextView) findViewById(R.id.show_text);
        //show_text.setText(query);

        ///Now Create the List View

        final String URL_TO_HIT = "https://api.techm.co.in/api/branch/" + query;

        //Calling the task
        new Json_task().execute(URL_TO_HIT);

        lvBanks = (ListView) findViewById(R.id.lvBanks);
        //----------------------------------------------------
        //lvBanks.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        //    @Override
        //    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //        Intent myintent= new Intent(page2.this,page3.class);
        //        myintent.putExtra("BANK",)
        //    }
        //});


        //----------------------------------------------------
        //Button btn = (Button) findViewById(R.id.hit_url);

        //tvJson = (TextView) findViewById(R.id.tvJson);


        //btn.setOnClickListener(new View.OnClickListener() {
        //                           @Override
        //                           public void onClick(View view) {
        //

        //                           }
        //                       }
        //);

    }

    private class Json_task extends AsyncTask<String, String, List<BankModel>> {


        @Override
        protected List<BankModel> doInBackground(String... urls) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {

                //final String URL_TO_HIT = "api.techm.co.in/api/branch/" + query;
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }
                String final_json=buffer.toString();
                JSONObject grand_parent_object= new JSONObject(final_json);
                JSONObject parent_object=grand_parent_object.getJSONObject("data");
                JSONArray banks=parent_object.getJSONArray("banks");


                StringBuffer data=new StringBuffer();

                //Holder of all the objects
                List<BankModel> bankModelList = new ArrayList<>();

                for(int i=0;i<banks.length();i++) {
                    JSONObject final_object = banks.getJSONObject(i);
                    BankModel bankModel = new BankModel();
                    bankModel.setIFSC(final_object.getString("IFSC"));
                    bankModel.setBRANCH(final_object.getString("BRANCH"));
                    bankModel.setBANK(final_object.getString("BANK"));
                    bankModel.setSTATE(final_object.getString("STATE"));
                    bankModel.set_id(final_object.getString("_id"));
                    bankModel.setCITY(final_object.getString("CITY"));
                    bankModel.setADDRESS(final_object.getString("ADDRESS"));
                    bankModel.setCONTACT(final_object.getString("CONTACT"));
                    bankModel.setMICRCODE(final_object.getString("MICRCODE"));
                    bankModel.setDISTRICT(final_object.getString("DISTRICT"));

                    bankModelList.add(bankModel);
                    //String bank_name = final_object.getString("BANK");
                    //String bank_ifsc = final_object.getString("IFSC");
                    //data.append(bank_name+" -> "+ bank_ifsc+"\n");
                }

                return bankModelList;
                //return data.toString();
                //return bank_name+" -> "+bank_ifsc;
                //return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(final List<BankModel> s) {
            super.onPostExecute(s);
            //tvJson.setText(s);
            //Todo need to set the list
            BankAdapter adapter = new BankAdapter(getApplicationContext(),R.layout.row,s);
            lvBanks.setAdapter(adapter);

            lvBanks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BankModel bankModel = s.get(position);//Getting the model
                    Intent intent = new Intent(page2.this, page3.class);
                    intent.putExtra("BANK", bankModel.getBANK());
                    intent.putExtra("BRANCH", bankModel.getBRANCH());
                    intent.putExtra("ADDRESS", bankModel.getADDRESS());
                    intent.putExtra("IFSC", bankModel.getIFSC());
                    intent.putExtra("_id", bankModel.get_id());
                    intent.putExtra("DISTRICT", bankModel.getDISTRICT());
                    intent.putExtra("CONTACT", bankModel.getCONTACT());
                    intent.putExtra("CITY", bankModel.getCITY());
                    intent.putExtra("MICR", bankModel.getMICRCODE());
                    startActivity(intent);
                }
            });
        }
    }

    public class BankAdapter extends ArrayAdapter{

        private List<BankModel> bankModelList;
        private int resource;
        private LayoutInflater inflater;

        public BankAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BankModel> objects) {
            super(context, resource, objects);
            bankModelList=objects;
            this.resource=resource;
            inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView==null){
                convertView=inflater.inflate(resource,null);
            }
            TextView bank;
            TextView branch;
            bank=(TextView) convertView.findViewById(R.id.bank);
            branch=(TextView) convertView.findViewById(R.id.branch);

            bank.setText(bankModelList.get(position).getBANK());
            branch.setText(bankModelList.get(position).getBRANCH());


            return convertView;
        }
    }
}
