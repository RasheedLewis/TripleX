package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benhepburn on 3/14/17.
 */

public class SubmitPurityReportActivity extends AppCompatActivity {

    private @Bind(R.id.purity_latitude) EditText _latitude;
    private @Bind(R.id.purity_longitude) EditText _longitude;
    private @Bind(R.id.purity_virusPPM) EditText _virusPPM;
    private @Bind(R.id.purity_contaminantPPM) EditText _contaminantPPM;
    private @Bind(R.id.purity_condition_spinner) Spinner _conditionSpinner;
    private @Bind(R.id.button_submit_purity_report) Button _submitButton;

    private PurityReport report;

    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_submit_purity_report);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, PurityReport.conditions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _conditionSpinner.setAdapter(adapter);

        _submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void submit() {
        Model model = Model.getInstance();
        Date date = new Date();

        report = new PurityReport(model.getPurityReportNum());
        model.setPurityReportNum(model.getPurityReportNum() + 1);

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
        int virusPPM = Integer.parseInt(_virusPPM.getText().toString());
        int contaminantPPM = Integer.parseInt(_contaminantPPM.getText().toString());

        report.setName(model.getCurrentUser().getName());
        report.setLat(lat);
        report.setLong(longitude);
        report.setVirusPPM(virusPPM);
        report.setContaminantPPM(contaminantPPM);
        report.setTime(currentTime);
        report.setDate(currentDate);
        report.setCondition((String) _conditionSpinner.getSelectedItem());

        model.addPurityReport(report.getReportNum(),report);
        model.set_currentPurityReport(report);

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
