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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * Created by benhepburn on 3/14/17.
 */

public class SubmitPurityReportActivity extends AppCompatActivity {

    @Bind(R.id.purity_latitude) EditText _latitude;
    @Bind(R.id.purity_longitude) EditText _longitude;
    @Bind(R.id.purity_virusPPM) EditText _virusPPM;
    @Bind(R.id.purity_contaminantPPM) EditText _contaminantPPM;
    @Bind(R.id.purity_condition_spinner) Spinner _conditionSpinner;
    @Bind(R.id.button_submit_purity_report) Button _submitButton;

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

        DateFormat dateFormat =  new SimpleDateFormat("M/dd/yyyy HH:mm:ss");
        Date date = new Date();

        String d = dateFormat.format(date);


        PurityReport report;

        report = new PurityReport(model.getPurityReportNum());
        model.setPurityReportNum(model.getPurityReportNum() + 1);


        double lat = Double.parseDouble(_latitude.getText().toString());
        double longitude = Double.parseDouble(_longitude.getText().toString());
        int virusPPM = Integer.parseInt(_virusPPM.getText().toString());
        int contaminantPPM = Integer.parseInt(_contaminantPPM.getText().toString());

        report.setName(model.getCurrentUser().getName());
        report.setLat(lat);
        report.setLong(longitude);
        report.setVirusPPM(virusPPM);
        report.setContaminantPPM(contaminantPPM);
        report.setTime(d.substring(9));
        report.setDate(d.substring(0,9));
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
