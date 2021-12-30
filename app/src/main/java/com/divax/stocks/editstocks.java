package com.divax.stocks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class editstocks extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth Auth;

    TextView NAME;
    EditText PRICE,QUANTITY;
    Button BUY,SELL,DELETE;

    private  String selectedName,selectedp,selectedq,net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstocks);

        NAME = findViewById(R.id.Name);
        PRICE = findViewById(R.id.PRICE);
        QUANTITY = findViewById(R.id.QUANTITY);
        BUY = findViewById(R.id.buystock);
        SELL = findViewById(R.id.sellstock);
        DELETE = findViewById(R.id.deletestock);
        Intent receiveIntent = getIntent();

        database = FirebaseDatabase.getInstance();
        Auth = FirebaseAuth.getInstance();

        selectedName = receiveIntent.getStringExtra("Name").trim();
        selectedp=receiveIntent.getStringExtra("Price").trim();
        selectedq=receiveIntent.getStringExtra("Quantity").trim();
        net = receiveIntent.getStringExtra("Net").trim();

        NAME.setText(selectedName);
        PRICE.setText(selectedp);
        QUANTITY.setText(selectedq);
        String id = Auth.getCurrentUser().getUid();


        BUY.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(PRICE.getText().toString().equals("") || QUANTITY.getText().toString().equals("")){
                    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                    view.startAnimation(buttonClick);
                    Animation shake = AnimationUtils.loadAnimation(editstocks.this, R.anim.shake);
                    view.startAnimation(shake);
                    Toast.makeText(editstocks.this, "SOMETHING FOUND EMPTY", Toast.LENGTH_SHORT).show();

                }else{
                Double n = Double.parseDouble(net);


                Double Q1 = Double.parseDouble(QUANTITY.getText().toString());
                Double Q2 = Double.parseDouble(selectedq);
                Double TQ;
                TQ = Q2 + Q1;
                String Quantity = String.valueOf(TQ);

                Double P1 = Double.parseDouble(PRICE.getText().toString());


                Double netsell = Q1*P1;
                Double netv = n+netsell;
                String netvalue = String.valueOf(netv);


                Double P2 = netv/TQ;
                double roundOff = Math.round(P2 * 100.0) / 100.0;

                String Price = String.valueOf(roundOff);

                Stocks stocks = new Stocks(NAME.getText().toString().replace(" ","_"),Price,netvalue,Quantity);
                database.getReference().child("Users").child(id).child(NAME.getText().toString()).setValue(stocks);

                Toast.makeText(editstocks.this, NAME.getText().toString()+"STOCK BOUGHT", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(editstocks.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                NAME.setText("");
                PRICE.setText("");
                QUANTITY.setText("");}
            }
        });

        DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference().child("Users").child(id).child(selectedName).removeValue();
                Toast.makeText(editstocks.this, NAME.getText()+" DELETED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(editstocks.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        SELL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double sq = Double.parseDouble(selectedq);
                if(sq <= 0.0){
                    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                    view.startAnimation(buttonClick);
                    Animation shake = AnimationUtils.loadAnimation(editstocks.this, R.anim.shake);
                    view.startAnimation(shake);
                    Toast.makeText(editstocks.this, "NOTHING TO SELL PLEASE BUY SOME FIRST", Toast.LENGTH_SHORT).show();

                }else{
                if(PRICE.getText().toString().equals("") || QUANTITY.getText().toString().equals("")){

                    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                    view.startAnimation(buttonClick);
                    Animation shake = AnimationUtils.loadAnimation(editstocks.this, R.anim.shake);
                    view.startAnimation(shake);
                    Toast.makeText(editstocks.this, "SOMETHING FOUND EMPTY", Toast.LENGTH_SHORT).show();
                }

                else{

                Double n = Double.parseDouble(net);


                Double Q1 = Double.parseDouble(QUANTITY.getText().toString());
                Double Q2 = Double.parseDouble(selectedq);
                Double TQ;
                if (Q1 <= Q2) { TQ = Q2 - Q1; }
                else { TQ = Q2-Q1;}
                String Quantity = String.valueOf(TQ);

                Double P1 = Double.parseDouble(PRICE.getText().toString());


                Double netsell = Q1*P1;
                Double netv = n-netsell;
                String netvalue = String.valueOf(netv);


                Double P2 = netv/TQ;
                double roundOff = Math.round(P2 * 100.0) / 100.0;

                String Price = String.valueOf(roundOff);

                Stocks stocks = new Stocks(NAME.getText().toString().replace(" ","_"),Price,netvalue,Quantity);
                database.getReference().child("Users").child(id).child(NAME.getText().toString()).setValue(stocks);

                Toast.makeText(editstocks.this, NAME.getText()+" SOLD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(editstocks.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);}

            }}
        });
    }
}