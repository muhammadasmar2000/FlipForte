package com.muhammadasmar.flipforte;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //declare variables
    private static final String TAG = "MainActivity.java";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private MaterialTextView header;
    private TextInputEditText email_input, password_input;
    private TextInputLayout email_layout, password_layout;
    private MaterialButton login_button, register_button;
    private CircularProgressIndicator progressBar;
    private FirebaseAuth mAuth;
    private SignInButton signin;
    private GoogleSignInClient mGoogleSignInClient;
    private LinearLayout mainLayout;
    int RC_SIGN_IN = 0;
    private Animation input_animation, button_animation;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initializeViews();
        queue = Volley.newRequestQueue(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initializeViews() {
        //initialize variables
        mainLayout = findViewById(R.id.mainLayout);
        header = findViewById(R.id.header);
        email_input = (TextInputEditText) findViewById(R.id.email);
        password_input = (TextInputEditText) findViewById(R.id.password);
        email_layout = (TextInputLayout) findViewById(R.id.email_layout);
        password_layout = (TextInputLayout) findViewById(R.id.password_layout);
        login_button = (MaterialButton) findViewById(R.id.loginButton);
        register_button = (MaterialButton) findViewById(R.id.registerButton);
        progressBar = (CircularProgressIndicator) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        //find animation files
        input_animation = AnimationUtils.loadAnimation(this, R.anim.input_field_animation);
        button_animation = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        //start animation
        header.startAnimation(input_animation);
        email_layout.startAnimation(input_animation);
        password_layout.startAnimation(input_animation);
        login_button.startAnimation(button_animation);
        register_button.startAnimation(button_animation);

        //Google Sign in button
        signin = (SignInButton) findViewById(R.id.sign_in_button);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in and if so, go to home screen
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
    }

    //register the user and store account in firebase
    public void register(View view) {
        String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();
        email_layout.setError(null);
        password_layout.setError(null);
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
        //https://www.flipforte.net/api/register-user.php?identifier=
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Snackbar.make(mainLayout, "Account registration successful", Snackbar.LENGTH_SHORT).show();
//                    String userID = FirebaseAuth.getInstance().getUid();
//                    String url = "https://www.flipforte.net/api/register-user.php?identifier=" + userID;
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.d(TAG, "Debug: New userID: " + userID);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d(TAG, "Debug: Account registration failed");
//                        }
//                    });
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                } else {
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
                if (task.isSuccessful()) {
                    //Snackbar.make(mainLayout, "Login successful", Snackbar.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                } else {
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
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(mainLayout, "Login failed", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //request permission for writing to external storage and recording audio with microphone
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}