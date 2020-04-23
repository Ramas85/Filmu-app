package lt.reactseals.akademija.filmuapp_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityTwo extends AppCompatActivity implements View.OnClickListener {
    // Sukuriam Firebase object jis bus reikalingas kai kursim Login.
    FirebaseAuth mAuth;
    //bus naudojama metode userLogin
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    private void userLogin() {
//pasiemam lauku ivestas reiksmes
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //tikrinam lauku reiksmes ar kazkas ivesta. Emaile ir passworde neturetu but laukas tuscias
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        //tikrinam ar atitinka email adresas duombazej uzregistruota
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        //ar netuscias laukas
        if (password.isEmpty()) {
            editTextPassword.setError("Email is required");
            editTextPassword.requestFocus();
            return;
        }
        //ar ne maziau nei 6 skaitmenys
        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6 symbols");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    //finish();
                    Intent i = new Intent(MainActivityTwo.this, ProfileActivity.class);
//isvalom kitus actyvicius kurie yra stack'e ir atidarom nauja activity.
//jeigu mes to nepadarytume tada,kai user paspaustu back mygtuka profile'e jis vel gryztu atgal i login Activity.
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        //kas atsitiks kai paspausim ant Don't have account? Sign Up
        switch (view.getId()) {
            case R.id.textViewSignup:
                //prasideda actvities
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }
}
