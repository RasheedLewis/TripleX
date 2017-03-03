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

    @Bind(R.id.street) EditText _locationStreet;
    @Bind(R.id.city) EditText _locationCity;
    @Bind(R.id.state) EditText _locationState;
    @Bind(R.id.zip_code) EditText _locationZip;
    @Bind(R.id.type_spinner) Spinner _typeSpinner;
    @Bind(R.id.condition_spinner) Spinner _conditionSpinner;
    @Bind(R.id.button_submit_source_report) Button _submitButton;

    private SourceReport report;
    public int reportNum = 0;

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

    public void submit() {
        Model model = Model.getInstance();
        Date date = new Date();

        report = new SourceReport(reportNum);
        reportNum++;

        int month = date.getMonth();
        int day = date.getDay();
        int year = date.getYear();

        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();

        String currentDate = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
        String currentTime = Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(seconds);

        String street = _locationStreet.getText().toString();
        String city = _locationCity.getText().toString();
        String state = _locationState.getText().toString();
        String zipCode = _locationZip.getText().toString();


        report.setName(model.getCurrentUser().getName());
        report.setLocation(street + city + state + zipCode);
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
