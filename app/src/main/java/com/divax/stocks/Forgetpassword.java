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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {
  FirebaseAuth auth;
  ProgressDialog pd;

  EditText email;
  Button reset;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgetpassword);

    pd = new ProgressDialog(Forgetpassword.this);
    pd.setTitle("Forget Password");
    pd.setMessage("Sending Email");

    reset = findViewById(R.id.resetPass);
    email = findViewById(R.id.Remail);
    auth = FirebaseAuth.getInstance();

    reset.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            if (email.getText().toString().equals("")) {
              AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
              view.startAnimation(buttonClick);
              Animation shake = AnimationUtils.loadAnimation(Forgetpassword.this, R.anim.shake);
              view.startAnimation(shake);
              Toast.makeText(Forgetpassword.this, "Something Found Empty", Toast.LENGTH_SHORT)
                  .show();
            } else {

              pd.show();

              auth.sendPasswordResetEmail(email.getText().toString())
                  .addOnCompleteListener(
                      new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful()) {
                            Intent intend = new Intent(Forgetpassword.this, login.class);
                            intend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intend);
                            email.setText("");
                            Toast.makeText(Forgetpassword.this, "Email send", Toast.LENGTH_SHORT)
                                .show();
                            pd.dismiss();
                          } else {
                            AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                            view.startAnimation(buttonClick);
                            Animation shake =
                                AnimationUtils.loadAnimation(Forgetpassword.this, R.anim.shake);
                            view.startAnimation(shake);
                            Toast.makeText(
                                    Forgetpassword.this,
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
  }
}
