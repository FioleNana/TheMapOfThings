package org.fiole.mapofthings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import org.fiole.mapofthings.adapters.MotInfoWindowAdapter;
import org.fiole.mapofthings.enums.MarkerType;
import org.fiole.mapofthings.models.MarkerInfos;
import org.fiole.mapofthings.models.UserModel;
import org.fiole.mapofthings.utils.ImageUtils;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity
        extends
            AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            OnMapReadyCallback,
            GoogleMap.OnMapClickListener,
            GoogleMap.OnMyLocationButtonClickListener{

    private static int REQUEST_CODE_CHOOSE_MARKER = 10;

    private GoogleMap map;
    private SharedPreferences userData;
    private ImageUtils imageUtils;
    private FloatingActionButton fab;
    private LatLng lastLatLng;
    boolean editMode;
    private Map<Marker, MarkerInfos> markerInfoMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editMode = false;

        // Userdaten in NavigationDrawer speichern
        imageUtils = new ImageUtils();
        userData = getSharedPreferences("org.fiole.mapofthings.USERDATA", Context.MODE_PRIVATE);
        getUserModelFromPreferences();

        // Runder button rechts unten
        makeFAB();

        // Menü ohne Header
        makeMenu(toolbar);

        // Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // MarkerInfos
        markerInfoMap = new HashMap<>();
    }

    private void makeMenu(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void makeFAB() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditMode(!editMode);
            }
        });
    }

    private void getUserModelFromPreferences() {
        new AsyncTask<Void, Void, UserModel>() {

            @Override
            protected UserModel doInBackground(Void... params) {
                UserModel userModel = new UserModel();
                userModel.setDisplayName(userData.getString("displayName", "none"));
                userModel.setEmail(userData.getString("email", "none"));
                userModel.setImageUri(userData.getString("imageUri", ""));
                Uri imageUri = Uri.parse(userModel.getImageUri());
                Drawable dr = imageUtils.getImageFromUriAndMakeRound(MapsActivity.this, imageUri);
                userModel.setProfilePic(dr);
                return userModel;
            }

            @Override
            protected void onPostExecute(UserModel userModel) {
                showUserinformationInNavigationDrawer(userModel);
            }
        }.execute();
    }

    private void showUserinformationInNavigationDrawer(UserModel userModel) {
        ImageView userPic = (ImageView) findViewById(R.id.navigationDrawerPicture);
        TextView userName = (TextView) findViewById(R.id.navigationDrawerName);
        TextView userMail = (TextView) findViewById(R.id.navigationDrawerMail);

        userPic.setImageDrawable(userModel.getProfilePic());
        userName.setText(userModel.getDisplayName());
        userMail.setText(userModel.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            // Handle the camera action
        } else if (id == R.id.nav_favourites) {

        } else if (id == R.id.nav_infos) {

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.setOnMapClickListener(this);
        map.setInfoWindowAdapter(new MotInfoWindowAdapter(this));

        UiSettings ui = map.getUiSettings();
        ui.setCompassEnabled(true);
        ui.setMyLocationButtonEnabled(true);
        ui.setAllGesturesEnabled(true);
        ui.setMapToolbarEnabled(false);

        zoomToUser(false);
        map.setOnMyLocationButtonClickListener(this);
    }

    private void zoomToUser(boolean animated) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Eigene Position zentrieren
                        .zoom(20)                   // Zoomlevel
                        .bearing(0)                 // Nach Norden ausrichten
                        .tilt(0)                    // 30° Kameraschwenken
                        .build();                   // Kameraposition erstellen

                if(animated){
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                } else {
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(editMode){
            Intent chooseMarker = new Intent(this, ChooseMarkerActivity.class);
            lastLatLng = latLng;
            startActivityForResult(chooseMarker, REQUEST_CODE_CHOOSE_MARKER);
            setEditMode(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_CODE_CHOOSE_MARKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                createMarker(data.getIntExtra("markerId", 0));
            }
        }
    }

    private void createMarker(Integer markerId) {
        // Get Drawable of Markertype and set Size
        MarkerType marker = MarkerType.getMarkerFromMarkerId(markerId);
        int markerColorRed = marker.getColor()[0];
        int markerColorGreen = marker.getColor()[1];
        int markerColorBlue = marker.getColor()[2];
        Drawable iconAsDrawable = getDrawable(marker.getPictureId());
        int markerSize = iconAsDrawable.getIntrinsicWidth() / 4;
        iconAsDrawable.setBounds(10, 10, markerSize, markerSize);
        iconAsDrawable.setTint(Color.rgb(markerColorRed, markerColorGreen, markerColorBlue));

        // Create a Bitmap of Drawable
        Bitmap bitmap = Bitmap.createBitmap(markerSize + 10, markerSize + 10, Bitmap.Config.ARGB_8888);

        // Create a canvas and draw Bitmap
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);

        Paint circlePaint = new Paint();
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        circlePaint.setColor(Color.WHITE);
        canvas.drawCircle(markerSize / 2 + 5,markerSize / 2 + 5,markerSize / 2 + 5, circlePaint);
        iconAsDrawable.draw(canvas);

        // Create BitmapDescriptor from Bitmap
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

        // Add Marker to map
        map.addMarker(new MarkerOptions()
                .position(lastLatLng)
                .title(getString(MarkerType.getNameIdFromMarkerId(markerId)))
                .icon(icon)
                .draggable(false)
                .visible(true));
    }

    private void setEditMode(boolean editMode){
        if(!editMode){
            this.editMode = false;
            fab.setImageDrawable(getDrawable(R.drawable.ic_add_location));
        } else {
            this.editMode = true;
            fab.setImageDrawable(getDrawable(R.drawable.ic_do_not_disturb));
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        zoomToUser(true);
        return true;
    }

    public Map<Marker, MarkerInfos> getMarkerInfoMap() {
        return markerInfoMap;
    }
}
