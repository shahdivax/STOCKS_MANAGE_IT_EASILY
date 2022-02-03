package com.divax.stocks;

import android.app.ProgressDialog;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
  FirebaseAuth auth;
  FirebaseDatabase database;
  ProgressDialog pd;

  EditText email, pass, user;
  Button signin;
  TextView forget, signup;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    pd = new ProgressDialog(login.this);
    pd.setMessage("Signing You In");

    auth = FirebaseAuth.getInstance();
    database = FirebaseDatabase.getInstance();
    email = findViewById(R.id.Lemail);
    pass = findViewById(R.id.Lpass);
    signin = findViewById(R.id.LOGIN);
    signup = findViewById(R.id.signup);
    forget = findViewById(R.id.Forget_Pass);

    signin.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if (email.getText().toString().equals("") || pass.getText().toString().equals("")) {
              AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
              view.startAnimation(buttonClick);
              Animation shake = AnimationUtils.loadAnimation(login.this, R.anim.shake);
              view.startAnimation(shake);
              Toast.makeText(login.this, "Something Found Empty", Toast.LENGTH_SHORT).show();
            } else {
              pd.show();

              auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                  .addOnCompleteListener(
                      new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                          if (task.isSuccessful()) {

                            Intent intend = new Intent(login.this, MainActivity.class);
                            startActivity(intend);
                            email.setText("");
                            pass.setText("");

                            Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT)
                                .show();
                            pd.dismiss();

                          } else {
                            AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                            view.startAnimation(buttonClick);
                            Animation shake =
                                AnimationUtils.loadAnimation(login.this, R.anim.shake);
                            view.startAnimation(shake);
                            Toast.makeText(
                                    login.this,
                                    task.getException().getMessage(),
                                    Toast.LENGTH_SHORT)
                                .show();
                            pd.dismiss();
                          }
                        }
                      });
            }
          }
        });

    signup.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(login.this, Signup.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
          }
        });

    forget.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(login.this, Forgetpassword.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
          }
        });
  }
}
