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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout nameInput;
    private TextInputLayout phoneInput;
    private TextInputLayout emailInput;
    private TextInputLayout passInput;
    private TextInputLayout cpassInput;
    private Button btn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        nameInput = (TextInputLayout) findViewById(R.id.sign_name_field);
        phoneInput=findViewById(R.id.sign_phone_field);
        emailInput = (TextInputLayout) findViewById(R.id.sign_email_field);
        passInput = (TextInputLayout) findViewById(R.id.sign_pass_field);
        btn = (Button) findViewById(R.id.sign_register_button);
        cpassInput = (TextInputLayout) findViewById(R.id.sign_cpass_field);

        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup_user();
            }
        });
    }



    public void signup_user() {
        final String name = nameInput.getEditText().getText().toString();
        final String phone=phoneInput.getEditText().getText().toString();
        String mail = emailInput.getEditText().getText().toString();
        String password = passInput.getEditText().getText().toString();
        String cpassword = cpassInput.getEditText().getText().toString();

        if (!name.isEmpty() && !phone.isEmpty() &&!mail.isEmpty() && !password.isEmpty() && !cpassword.isEmpty()) {

            if (!isValidMobile(phone)) {
                Toast.makeText(SignupActivity.this, "Please Enter A Valid Phone Number", Toast.LENGTH_SHORT).show();
            }
              else if (!isValidMail(mail)) {
                Toast.makeText(SignupActivity.this, "Please Enter A Valid E-Mail Address", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Password Should Be Atleast 6 Characters Long", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(cpassword)) {
                Toast.makeText(SignupActivity.this, "Passsword Doesn't Match", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("Signing Up...");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.hide();

                            insertInDatabase(name,phone);

                            Intent mainIntent = new Intent(SignupActivity.this, ChooseActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            progressDialog.hide();
                            Toast.makeText(SignupActivity.this, "Signup Failed::Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            progressDialog.hide();
            Toast.makeText(SignupActivity.this, "Please Enter Complete Details", Toast.LENGTH_SHORT).show();
        }

    }

    public void insertInDatabase(String name,String phone) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", name);
        hashMap.put("phone",phone);
        mDatabase.setValue(hashMap);
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
