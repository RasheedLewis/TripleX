package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benhepburn on 3/2/17.
 */

public class SubmitSourceReportActivity extends AppCompatActivity {
    private static final String TAG = "SubmitSourceReportActivity";

    private @Bind(R.id.source_latitude) EditText _latitude;
    private @Bind(R.id.source_longitude) EditText _longitude;
    private @Bind(R.id.type_spinner) Spinner _typeSpinner;
    private @Bind(R.id.condition_spinner) Spinner _conditionSpinner;
    private @Bind(R.id.button_submit_source_report) Button _submitButton;

    private SourceReport report;

    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_submit_source_report);
        ButterKnife.bind(this);

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
        Date date = new Date();

        report = new SourceReport(model.getReportNum());
        model.setReportNum(model.getReportNum() + 1);

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int year = date.getYear() + 1900;

        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();

        String currentDate = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
        String currentTime = Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(seconds);

        double lat = Double.parseDouble(_latitude.getText().toString());
        double longitude = Double.parseDouble(_longitude.getText().toString());


        report.setName(model.getCurrentUser().getName());
        report.setLat(lat);
        report.setLong(longitude);
        report.setDate(currentDate);
        report.setTime(currentTime);
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
