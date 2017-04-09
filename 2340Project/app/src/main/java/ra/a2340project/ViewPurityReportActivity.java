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
        String lat = Double.toString(report.getLat());
        latTextView.setText(lat);

        TextView longTextView = (TextView) findViewById(R.id.purity_report_view_longitude);
        String lng = Double.toString(report.getLong());
        longTextView.setText(lng);

        TextView numTextView = (TextView) findViewById(R.id.purity_report_view_reportNum);
        String reportNum = Integer.toString(report.getReportNum());
        numTextView.setText(reportNum);

        TextView virusPPMTextView = (TextView) findViewById(R.id.purity_report_view_virusPPM);
        String vPPM = Integer.toString(report.getVirusPPM());
        virusPPMTextView.setText(vPPM);

        TextView contaminantPPMTextView = (TextView) findViewById(R.id.purity_report_view_contaminantPPM);
        String cPPM = Integer.toString(report.getContaminantPPM());
        contaminantPPMTextView.setText(cPPM);

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
