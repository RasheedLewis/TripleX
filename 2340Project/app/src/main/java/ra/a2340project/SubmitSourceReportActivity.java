package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * Screen that allows the current user to import a source report
 *
 * Created by benhepburn on 3/2/17.
 */

public class SubmitSourceReportActivity extends AppCompatActivity {
    private static final String TAG = "SubmitSourceReportActivity";

    private EditText _latitude;
    private EditText _longitude;
    private Spinner _typeSpinner;
    private Spinner _conditionSpinner;
    private Button _submitButton;

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

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, SourceReport.types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _typeSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, SourceReport.conditions);
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
        Model model = Model.getInstance();
        DateFormat dateFormat =  new SimpleDateFormat("M/dd/yyyy HH:mm:ss", Locale.US);
        Date date = new Date();

        String d = dateFormat.format(date);

        SourceReport report;

        report = new SourceReport(model.getReportNum());
        model.setReportNum(model.getReportNum() + 1);

        double lat = Double.parseDouble(_latitude.getText().toString());
        double longitude = Double.parseDouble(_longitude.getText().toString());


        report.setName(model.getCurrentUser().getName());
        report.setLat(lat);
        report.setLong(longitude);
        report.setDate(d.substring(0,9));
        report.setTime(d.substring(9));
        report.setType((String) _typeSpinner.getSelectedItem());
        report.setCondition((String) _conditionSpinner.getSelectedItem());

        model.addSourceReport(report.getReportNum(),report);
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
