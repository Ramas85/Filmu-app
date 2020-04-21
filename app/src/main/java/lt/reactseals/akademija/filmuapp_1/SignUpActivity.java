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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    //lauku pavadinimai EMAIL,PASSWORD
    EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth; // firebase zingsnis 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //inicijuojam(paleidziam) email lauka ir kastinam i android widget'a  EditText

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();  // firebase zingsnis 2
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
    }

    private void registerUser() {

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
            editTextPassword.setError("Minimum lenght of password should be 6 word");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // firebase zingsnis 3
        //kvieciam metoda firebase createUserWithEmailAndPassword jis uztikrina ir baigia userio registracija
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User Registered successfull", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Some error ocurred", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.textViewLogin:
                //jei spaudziam Login gryzta atgal i musu maina.
                startActivity(new Intent(this, Main2Activity.class));

                break;
        }

    }
}
