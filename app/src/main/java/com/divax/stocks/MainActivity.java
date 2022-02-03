package com.divax.stocks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  ListView rv;
  Button Add;
  FirebaseAuth Auth;
  FirebaseDatabase database;
  DatabaseReference databaseReference;
  ArrayList<String> arrayList = new ArrayList<>();
  ArrayAdapter<String> arrayAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    rv = findViewById(R.id.listview);
    Add = findViewById(R.id.Add);
    Auth = FirebaseAuth.getInstance();
    database = FirebaseDatabase.getInstance();

    Add.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Addstock.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
          }
        });
    String id = Auth.getCurrentUser().getUid();
    database
        .getReference()
        .child("Users")
        .child(id)
        .get()
        .addOnCompleteListener(
            new OnCompleteListener<DataSnapshot>() {

              @Override
              public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                  databaseReference =
                      FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                  arrayAdapter =
                      new ArrayAdapter<String>(
                          MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                  rv.setAdapter(arrayAdapter);
                  databaseReference.addChildEventListener(
                      new ChildEventListener() {
                        @Override
                        public void onChildAdded(
                            @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                          String name = snapshot.child("name").getValue().toString();
                          String price = snapshot.child("price").getValue().toString();
                          String net = snapshot.child("net").getValue().toString();
                          String quantity = snapshot.child("quantity").getValue().toString();
                          arrayList.add(
                              " Name: "
                                  + name
                                  + "\n Price: "
                                  + price
                                  + "\n Quantity: "
                                  + quantity
                                  + "\n NetPrice: "
                                  + net);
                          arrayAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(
                            @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

                        @Override
                        public void onChildMoved(
                            @NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                      });
                } else {

                  Toast.makeText(
                          MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT)
                      .show();
                }
              }
            });

    rv.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String[] T = adapterView.getItemAtPosition(i).toString().split(" ");
            Toast.makeText(MainActivity.this, T[2].trim() + " SELECTED", Toast.LENGTH_SHORT).show();
            Intent editscreen = new Intent(MainActivity.this, editstocks.class);
            editscreen.putExtra("Name", T[2]);
            editscreen.putExtra("Price", T[4]);
            editscreen.putExtra("Quantity", T[6]);
            editscreen.putExtra("Net", T[8]);
            startActivity(editscreen);
          }
        });
  }
}
