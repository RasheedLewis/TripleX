package ra.a2340project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.Collection;

import butterknife.ButterKnife;

/**
 * The main screen for the app, showing all options possible in app.
 *
 * Created by Rasheed on 2/15/2017.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private final Model model = Model.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Button _logoutButton = (Button) findViewById(R.id.btn_logout);
        Button _profileButton = (Button) findViewById(R.id.button_profile);
        Button _submitReportButton = (Button) findViewById(R.id.button_source_report_submission);
        Button _viewReportsButton = (Button) findViewById(R.id.button_view_reports);
        Button _submitPurityReportButton = (Button) findViewById(R.id.button_purity_report_submission);
        Button _viewPurityReports = (Button) findViewById(R.id.button_view_purity);
        Button _viewHistoryReport = (Button) findViewById(R.id.button_view_historical_report);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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

        // Create On Click Functionality for Purity Reports List
        // Secures only manages can click
        _viewPurityReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!model.getCurrentUser().getStatus().equals("Manager")) {
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
                    Intent intent = new Intent(getApplicationContext(), ViewPurityReports.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
            });


        _submitPurityReportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!model.getCurrentUser().getStatus().equals("Worker") && !model.getCurrentUser().getStatus().equals("Manager")) {
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

        _viewHistoryReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!model.getCurrentUser().getStatus().equals("Manager")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("You are not authorized to view a historical report.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), GraphInputActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMarkerClickListener(this);

        Collection<SourceReport> reports = model.getSourceReportHashMap().values();
        Collection<PurityReport> purityReports = model.getPurityReportHashMap().values();

        Marker marker;
        for (SourceReport report : reports) {
            LatLng latLong = new LatLng(report.getLat(),report.getLong());

            marker = googleMap.addMarker(new MarkerOptions().position(latLong).title("Marker"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
            marker.setTag(report.getReportNum());
        }

        for (PurityReport report : purityReports) {
            LatLng latLong = new LatLng(report.getLat(),report.getLong());

            marker = googleMap.addMarker(new MarkerOptions().position(latLong).title("PurityReport"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
            marker.setTag(report.getReportNum());
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker){
        if (model.getSourceReportHashMap().containsKey(marker.getTag())) {
            SourceReport report = model.getSourceReportHashMap().get(marker.getTag());
            model.setCurrentSourceReport(report);
            Intent intent = new Intent(getApplicationContext(), ViewSourceReportActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        if (model.getPurityReportHashMap().containsKey(marker.getTag())) {
            PurityReport report = model.getPurityReportHashMap().get(marker.getTag());
            model.set_currentPurityReport(report);
            Intent intent = new Intent(getApplicationContext(), ViewPurityReportActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        return false;
    }

}
