package lt.reactseals.akademija.filmuapp_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //kas atsitiks kai paspausim ant Don't have account? Sign Up
        switch (view.getId()) {
            case R.id.textViewSignup:

                //prasideda actvities
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}
