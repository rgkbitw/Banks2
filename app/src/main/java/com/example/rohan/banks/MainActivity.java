package com.example.rohan.banks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent i =new Intent(this,page2.class);

        Button inputBtn= (Button)findViewById(R.id.btnInput);
        final EditText editText = (EditText)findViewById(R.id.eTinput);

        inputBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Todo send the input to the next page.
                String send_this=editText.getText().toString();
                i.putExtra("query",send_this);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Loading.."+editText.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
