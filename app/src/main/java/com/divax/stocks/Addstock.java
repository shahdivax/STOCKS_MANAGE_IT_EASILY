package com.divax.stocks;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;



public class Addstock extends AppCompatActivity {
    FirebaseDatabase Database;
    FirebaseAuth Auth;
    EditText name,price,quantity;
    Button Add;
    ListView rv;


    private static final String TAG = "ListDataActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstock);

        name = findViewById(R.id.STOCKNAME);
        price = findViewById(R.id.PRICE);
        quantity = findViewById(R.id.QUANTITY);
        Add = findViewById(R.id.Addstock);
        rv = findViewById(R.id.listview);
        Database = FirebaseDatabase.getInstance();
        Auth = FirebaseAuth.getInstance();
        String id = Auth.getCurrentUser().getUid();



        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("") || price.getText().toString().equals("") || quantity.getText().toString().equals("")){

                    Toast.makeText(Addstock.this, "SOMETHING FOUND EMPTY", Toast.LENGTH_SHORT).show();

                }else{
                    try {

                        Double net = Double.parseDouble(price.getText().toString()) * Double.parseDouble(quantity.getText().toString());
                        String netvalue = String.valueOf(net);


                        Stocks stocks = new Stocks(name.getText().toString().replace(" ","_"),price.getText().toString(),netvalue,quantity.getText().toString());
                        Database.getReference().child("Users").child(id).child(name.getText().toString()).setValue(stocks);
                        Toast.makeText(Addstock.this, name.getText().toString()+" Stock Added", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        price.setText("");
                        quantity.setText("");

                    }catch (Exception e){
                        Toast.makeText(Addstock.this, "Something", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



}

    }
