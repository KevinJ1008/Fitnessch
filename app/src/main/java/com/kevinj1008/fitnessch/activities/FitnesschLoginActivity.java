package com.kevinj1008.fitnessch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.UserExistCallback;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class FitnesschLoginActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 9001;
    public SharedPreferencesManager mSharedPreferencesManager;

    private String mGoogleIdToken;
    private String mGoogleId;
    private String mGoogleName;
    private String mGoogleMail;
    private String mGooglePhoto;
    private Uri mGooglePhotoUri;
    private ConstraintLayout mGoogleLogInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLoginStatusBar();

        mGoogleLogInBtn = findViewById(R.id.google_login_btn);
        mGoogleLogInBtn.setOnClickListener(clickListener);

        configureGoogleSignIn();

        mFirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();

        if (isUserTokenExist()) {
            if (isNetworkAvailable()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FitnesschLoginActivity.this, FitnesschActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            } else {
                Toast.makeText(FitnesschLoginActivity.this, "登入失敗，請檢查網路連線。", Toast.LENGTH_SHORT).show();
            }
        } else {
            mGoogleLogInBtn.setVisibility(View.VISIBLE);
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
//                    createUserInFirestore();
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {

                }
            }
        };

    }

    private void setLoginStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.google_login_btn) {
                if (isNetworkAvailable()) {
                    googleSignIn();
                } else {
                    Toast.makeText(FitnesschLoginActivity.this, "登入失敗，請檢查網路連線。", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void configureGoogleSignIn() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
        mGoogleApiClient.connect();
    }

    private void googleSignIn() {
        Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(googleSignInIntent, RC_SIGN_IN);
    }

    private void createUserInFirestore() {
        final String uid = mFirebaseAuth.getCurrentUser().getUid();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        userExist(uid, new UserExistCallback() {
            @Override
            public void onCallback(boolean isExist) {
                if (!isExist) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", mGoogleName);
                    user.put("photo", mGooglePhoto);
                    user.put("email", mGoogleMail);
                    user.put("id", mGoogleId);
                    user.put("id_token", mGoogleIdToken);
                    user.put("joined_time", FieldValue.serverTimestamp());
                    user.put("db_uid", uid);
                    user.put("height", "0 CM");
                    user.put("weight", "0 KG");
                    user.put("info", "...");

                    db.collection("users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(Constants.TAG, "Success create new user in Firestore " + aVoid);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(Constants.TAG, "Fail to create new user in Firestore " + e.getMessage());
                        }
                    });
                }
            }
        });

        //Save user google info to SharedPreference
        mSharedPreferencesManager = new SharedPreferencesManager(mContext);
        mSharedPreferencesManager.saveUserIdToken(mContext, mGoogleIdToken);
        mSharedPreferencesManager.saveUserId(mContext, mGoogleId);
        mSharedPreferencesManager.saveUserName(mContext, mGoogleName);
        mSharedPreferencesManager.saveUserPhoto(mContext, mGooglePhoto);
        mSharedPreferencesManager.saveUserEmail(mContext, mGoogleMail);
        mSharedPreferencesManager.saveUserDbUid(mContext, uid);

    }

    private void userExist(final String uid, final UserExistCallback callback) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userRef = db.collection("users");
        userRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean isExisting = false;
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String id = ds.getString("db_uid");
                    if (id != null) {
                        if (id.equals(uid)) {
                            isExisting = true;
                            break;
                        }
                    }
                } callback.onCallback(isExisting);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();

                mGoogleIdToken = account.getIdToken();
                mGoogleId = account.getId();

                mGoogleName = account.getDisplayName();
                mGoogleMail = account.getEmail();
                mGooglePhotoUri = account.getPhotoUrl();
                if (mGooglePhotoUri != null) {
                    mGooglePhoto = mGooglePhotoUri.toString();
                }

                AuthCredential credential = GoogleAuthProvider.getCredential(mGoogleIdToken, null);
                firebaseAuthWithGoogle(credential);
            } else {
                Log.d(Constants.TAG, "Login Unsuccessful. ");
                Toast.makeText(this, "登入失敗。", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {
        //TODO: show progress dialog in BaseActivity
        showProgressBar();
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d(Constants.TAG, "Sign in with credential " + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(FitnesschLoginActivity.this, "認證失敗", Toast.LENGTH_SHORT).show();
                        } else {
                            createUserInFirestore();
                            Toast.makeText(FitnesschLoginActivity.this, "登入成功。", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FitnesschLoginActivity.this, FitnesschActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        hideProgressBar();
                        //TODO: Hide progress dialog
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().signOut();
        }
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isUserTokenExist() {
        SharedPreferences userLoginStatus = getSharedPreferences("User_Profile", MODE_PRIVATE);
        return !userLoginStatus.getString("access_token", "").equals("");
    }
}
