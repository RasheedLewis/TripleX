package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Screen that allows the current user to import a source report
 *
 * Created by benhepburn on 3/2/17.
 */

public class SubmitSourceReportActivity extends AppCompatActivity {

    private EditText _latitude;
    private EditText _longitude;
    private Spinner _typeSpinner;
    private Spinner _conditionSpinner;
    private Button _submitButton;

    private Map<Integer, SourceReport> map;
    private SourceReport report;
    private FirebaseUser fUser;

    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_submit_source_report);
        ButterKnife.bind(this);

        _latitude = (EditText) findViewById(R.id.source_latitude);
        _longitude = (EditText) findViewById(R.id.source_longitude);
        _typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        _conditionSpinner = (Spinner) findViewById(R.id.condition_spinner);
        _submitButton = (Button) findViewById(R.id.button_submit_source_report);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, SourceReport.types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _typeSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, SourceReport.conditions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _conditionSpinner.setAdapter(adapter1);


        _submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void submit() {
        final Model model = Model.getInstance();
        DateFormat dateFormat =  new SimpleDateFormat("M/dd/yyyy HH:mm:ss", Locale.US);
        Date date = new Date();

        final String d = dateFormat.format(date);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        map = model.getSourceReportHashMap();
                        report = new SourceReport(map.size());
                    }
                }, 3000);


        model.setReportNum(model.getReportNum() + 1);

        final double lat = Double.parseDouble(_latitude.getText().toString());
        final double longitude = Double.parseDouble(_longitude.getText().toString());


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        fUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (fUser != null) {
                            report.setName(fUser.getEmail().split("@")[0]);
                        }
                        report.setLat(lat);
                        report.setLong(longitude);
                        report.setDate(d.substring(0,9));
                        report.setTime(d.substring(9));
                        report.setType((String) _typeSpinner.getSelectedItem());
                        report.setCondition((String) _conditionSpinner.getSelectedItem());
                    }
                }, 3000);




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        model.addSourceReport(report.getReportNum(),report);
                    }
                }, 3000);
        model.setCurrentSourceReport(report);

        _submitButton.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



}
