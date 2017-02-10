package org.fiole.mapofthings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.Session;

import org.fiole.mapofthings.models.UserModel;
import org.fiole.mapofthings.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private TextView displayName, email;
    private ImageView userImage;
    private SharedPreferences userData;
    private ImageUtils imageUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        makeGoogleLoginButton();

        userData = getSharedPreferences("org.fiole.mapofthings.USERDATA", Context.MODE_PRIVATE);
        imageUtils = new ImageUtils();

        displayName = (TextView) findViewById(R.id.loginDisplayName);
        email = (TextView) findViewById(R.id.loginEmail);
        userImage = (ImageView) findViewById(R.id.loginUserImage);

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);

        if(userData.getBoolean("loggedIn", false)){
            getUserModelFromPreferences();
            signInButton.setVisibility(View.GONE);
        }
    }

    private void makeGoogleLoginButton(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.logoutButton:
                signOut();
                break;
            case R.id.continueButton:
                continueToMap();
                break;
        }
    }

    private void continueToMap() {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        removeUserInformation();
                        removeUserInformationFromPreferences();
                        signInButton.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(final GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            new AsyncTask<Void, Void, UserModel>(){

                @Override
                protected UserModel doInBackground(Void... params) {
                    GoogleSignInAccount acct = result.getSignInAccount();

                    Drawable dr = imageUtils.getImageFromUriAndMakeRound(LoginActivity.this, acct.getPhotoUrl());

                    UserModel userModel = new UserModel();
                    userModel.setId(acct.getId());
                    userModel.setDisplayName(acct.getDisplayName());
                    userModel.setEmail(acct.getEmail());
                    userModel.setImageUri(String.valueOf(acct.getPhotoUrl()));
                    userModel.setProfilePic(dr);
                    return userModel;
                }

                @Override
                protected void onPostExecute(UserModel userModel) {
                    signInButton.setVisibility(View.GONE);
                    showUserInformation(userModel);
                    saveUserInformation(userModel);
                }
            }.execute();
        } else {
            Snackbar.make(findViewById(R.id.snackbarPosition), "Konnte nicht einloggen", Snackbar.LENGTH_LONG).show();
        }
    }

    private void saveUserInformation(UserModel userModel) {
        SharedPreferences.Editor editor = userData.edit();
        editor.putBoolean("loggedIn", true);
        editor.putString("id", userModel.getId());
        editor.putString("displayName", userModel.getDisplayName());
        editor.putString("email", userModel.getEmail());
        editor.putString("imageUri", userModel.getImageUri());
        editor.commit();
    }



    private void getUserModelFromPreferences() {
        new AsyncTask<Void, Void, UserModel>(){

            @Override
            protected UserModel doInBackground(Void... params) {
                UserModel userModel = new UserModel();
                userModel.setId(userData.getString("id", "none"));
                userModel.setDisplayName(userData.getString("displayName", "none"));
                userModel.setEmail(userData.getString("email", "none"));
                userModel.setImageUri(userData.getString("imageUri", ""));
                Uri imageUri = Uri.parse(userModel.getImageUri());
                Drawable dr = imageUtils.getImageFromUriAndMakeRound(LoginActivity.this, imageUri);
                userModel.setProfilePic(dr);
                return userModel;
            }

            @Override
            protected void onPostExecute(UserModel userModel) {
                showUserInformation(userModel);
            }
        }.execute();
    }

    private void showUserInformation(UserModel userModel){
        displayName.setText(userModel.getDisplayName());
        email.setText(userModel.getEmail());
        userImage.setImageDrawable(userModel.getProfilePic());
    }

    private void removeUserInformation(){
        Resources res = getResources();
        displayName.setText("");
        email.setText("");
        userImage.setImageDrawable(res.getDrawable(R.drawable.appicon, getTheme()));
    }

    private void removeUserInformationFromPreferences() {
        SharedPreferences.Editor editor = userData.edit();
        editor.remove("loggedIn");
        editor.remove("id");
        editor.remove("displayName");
        editor.remove("email");
        editor.remove("imageUri");
        editor.commit();
    }
}
