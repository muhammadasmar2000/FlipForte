package com.muhammadasmar.flipforte;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //declare variables
    private TextInputEditText email_input, password_input;
    private TextInputLayout email_layout, password_layout;
    private MaterialButton login_button, register_button;
    private CircularProgressIndicator progressBar;
    private FirebaseAuth mAuth;
    private SignInButton signin;
    private GoogleSignInClient mGoogleSignInClient;
    private LinearLayout mainLayout;
    int RC_SIGN_IN = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize variables
        mainLayout = findViewById(R.id.mainLayout);
        email_input = (TextInputEditText)findViewById(R.id.email);
        password_input = (TextInputEditText)findViewById(R.id.password);
        email_layout = (TextInputLayout)findViewById(R.id.email_layout);
        password_layout = (TextInputLayout)findViewById(R.id.password_layout);
        login_button = (MaterialButton)findViewById(R.id.loginButton);
        register_button = (MaterialButton)findViewById(R.id.registerButton);
        progressBar = (CircularProgressIndicator)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        signin = (SignInButton)findViewById(R.id.sign_in_button);
        signin.setSize(SignInButton.SIZE_WIDE);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in and if so, go to home screen
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }
    }

    //register the user and store account in firebase
    public void register(View view) {
        String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();
        //check for valid email
        if (!isValidEmail(email)) {
            email_layout.setError("Invalid email address");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        //check for non-empty password
        if (password.length() < 6) {
            password_layout.setError("Password must be at least six characters");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        //valid username and password
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //create Snackbar for successful account registration
                    Snackbar.make(mainLayout, "Account registration successful", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                    //finish();
                }
                else {
                    Snackbar.make(mainLayout, "Account registration failed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        progressBar.setVisibility(View.INVISIBLE);
    }
    //login the user by validating account in firebase
    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();

        //signin to account stored in firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //create Snackbar for successful account registration
                    Snackbar.make(mainLayout, "Login successful", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
                else {
                    Snackbar.make(mainLayout, "Login failed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        progressBar.setVisibility(View.INVISIBLE);
    }

    //check if the email is valid
    private Boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //login success
            Snackbar.make(mainLayout, "Login successful", Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        } catch (ApiException e) {
            //login failure
            Snackbar.make(mainLayout, "Login failed", Snackbar.LENGTH_SHORT).show();
        }
    }
}