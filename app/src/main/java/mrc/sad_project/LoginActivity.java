package mrc.sad_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mailInput;
    private TextInputLayout passInput;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mailInput = (TextInputLayout) findViewById(R.id.login_email_field);
        passInput = (TextInputLayout) findViewById(R.id.login_pass_field);
        loginButton = (Button) findViewById(R.id.login_button);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth = FirebaseAuth.getInstance();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login_user();
            }
        });
    }



    public void login_user() {
        String email = mailInput.getEditText().getText().toString();
        final String password = passInput.getEditText().getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {
            if (!isValidMail(email)) {
                Toast.makeText(LoginActivity.this, "Invalid E-mail Addrress", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("Logging In...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.hide();
                            Intent mainIntent = new Intent(LoginActivity.this, ChooseActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Incorrect E-mail/Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        } else {

            Toast.makeText(LoginActivity.this, "Please Enter E-mail & Password", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
