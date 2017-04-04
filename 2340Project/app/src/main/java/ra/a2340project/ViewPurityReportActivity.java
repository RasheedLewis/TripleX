package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Screen that allows the current user to view a list of all the purity reports
 *
 * Created by benhepburn on 3/17/17.
 */

public class ViewPurityReportActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_report_view);
        ButterKnife.bind(this);

        Button _backButton = (Button) findViewById(R.id.purity_report_view_BackButton);

        PurityReport report;

        Model model = Model.getInstance();
        report = model.get_currentPurityReport();

        TextView nameTextView = (TextView) findViewById(R.id.purity_report_view_name);
        nameTextView.setText(report.getName());

        TextView latTextView = (TextView) findViewById(R.id.purity_report_view_latitude);
        latTextView.setText("Latitude: " + report.getLat());

        TextView longTextView = (TextView) findViewById(R.id.purity_report_view_longitude);
        longTextView.setText("Longitude: " + report.getLong());

        TextView numTextView = (TextView) findViewById(R.id.purity_report_view_reportNum);
        numTextView.setText(Integer.toString(report.getReportNum()));

        TextView virusPPMTextView = (TextView) findViewById(R.id.purity_report_view_virusPPM);
        virusPPMTextView.setText(Integer.toString(report.getVirusPPM()));

        TextView contaminantPPMTextView = (TextView) findViewById(R.id.purity_report_view_contaminantPPM);
        contaminantPPMTextView.setText(Integer.toString(report.getContaminantPPM()));

        TextView conditionTextView = (TextView) findViewById(R.id.purity_report_view_condition);
        conditionTextView.setText(report.getCondition());

        TextView dateTextView = (TextView) findViewById(R.id.purity_report_view_date);
        dateTextView.setText(report.getDate());

        TextView timeTextView = (TextView) findViewById(R.id.purity_report_view_time);
        timeTextView.setText(report.getTime());

        _backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
