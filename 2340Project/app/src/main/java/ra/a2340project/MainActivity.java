package ra.a2340project;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Camera;
import android.location.Geocoder;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.location.Address;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.drive.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.support.v4.app.FragmentActivity;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
/**
 * Created by Rasheed on 2/15/2017.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    @Bind(R.id.btn_logout) Button _logoutButton;
    @Bind(R.id.button_profile) Button _profileButton;
    @Bind(R.id.button_source_report_submission) Button _submitReportButton;
    @Bind(R.id.button_view_reports) Button _viewReportsButton;
    @Bind(R.id.button_purity_report_submission) Button _submitPurityReportButton;

    Model model = Model.getInstance();


    GoogleMap googleMap;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */,
//                        this /* OnConnectionFailedListener */)
//                .addApi(Drive.API)
//                .addScope(Drive.SCOPE_FILE)
//                .build();

        _logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        _profileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        _submitReportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubmitSourceReportActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        _viewReportsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewSourceReports.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        _submitPurityReportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (model.getCurrentUser().getStatus() != "Worker" && model.getCurrentUser().getStatus() != "Manager") {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("You are not authorized to submit water purity reports.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SubmitPurityReportActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));

//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        googleMap.setOnMarkerClickListener(this);

        Collection<SourceReport> reports = model.getSourceReportHashMap().values();
        Collection<PurityReport> purityReports = model.getPurityReportHashMap().values();

        Marker marker;
        for (SourceReport report : reports) {
            LatLng latLong = new LatLng(report.getLat(),report.getLong());
            if (latLong != null) {
                marker = googleMap.addMarker(new MarkerOptions().position(latLong).title("Marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
                marker.setTag(report);
            }
        }

        for (PurityReport report : purityReports) {
            LatLng latLong = new LatLng(report.getLat(),report.getLong());
            if (latLong != null) {
                marker = googleMap.addMarker(new MarkerOptions().position(latLong).title("PurityReport"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
                marker.setTag(report);
            }
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker){
        SourceReport report = (SourceReport) marker.getTag();
        model.setCurrentSourceReport(report);
        Intent intent = new Intent(getApplicationContext(), ViewSourceReportActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return false;
    }



//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        if (mResolvingError) {
//            // Already attempting to resolve an error.
//            return;
//        } else if (result.hasResolution()) {
//            try {
//                mResolvingError = true;
//                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
//            } catch (IntentSender.SendIntentException e) {
//                // There was an error with the resolution intent. Try again.
//                mGoogleApiClient.connect();
//            }
//        } else {
//            // Show dialog using GoogleApiAvailability.getErrorDialog()
//            showErrorDialog(result.getErrorCode());
//            mResolvingError = true;
//        }
//    }

    // The rest of this code is all about building the error dialog

    /* Creates a dialog for an error message */
//    private void showErrorDialog(int errorCode) {
//        // Create a fragment for the error dialog
//        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
//        // Pass the error that should be displayed
//        Bundle args = new Bundle();
//        args.putInt(DIALOG_ERROR, errorCode);
//        dialogFragment.setArguments(args);
//        dialogFragment.show(getSupportFragmentManager(), "errordialog");
//    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
//    public void onDialogDismissed() {
//        mResolvingError = false;
//    }

    /* A fragment to display an error dialog */
//    public static class ErrorDialogFragment extends DialogFragment {
//        public ErrorDialogFragment() {
//        }
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Get the error code and retrieve the appropriate dialog
//            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
//            return GoogleApiAvailability.getInstance().getErrorDialog(
//                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
//        }
//
//        @Override
//        public void onDismiss(DialogInterface dialog) {
//            ((MainActivity) getActivity()).onDialogDismissed();
//        }
//    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }



}
