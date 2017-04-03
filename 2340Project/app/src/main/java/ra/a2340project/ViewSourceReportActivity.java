package ra.a2340project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.xml.transform.Source;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benhepburn on 2/22/17.
 */

public class ViewSourceReportActivity extends AppCompatActivity {


    private @Bind(R.id.source_report_view_BackButton) Button _backButton;

    private SourceReport report;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_report_view);
        ButterKnife.bind(this);

        Model model = Model.getInstance();
        report = model.getCurrentSourceReport();

        TextView nameTextView = (TextView) findViewById(R.id.source_report_view_name);
        nameTextView.setText(report.getName());

        TextView latTextView = (TextView) findViewById(R.id.source_report_view_latitude);
        latTextView.setText("Latitude: " + report.getLat());

        TextView longTextView = (TextView) findViewById(R.id.source_report_view_longitude);
        longTextView.setText("Longitude: " + report.getLong());

        TextView numTextView = (TextView) findViewById(R.id.source_report_view_reportNum);
        numTextView.setText(Integer.toString(report.getReportNum()));

        TextView typeTextView = (TextView) findViewById(R.id.source_report_view_type);
        typeTextView.setText(report.getType());

        TextView conditionTextView = (TextView) findViewById(R.id.source_report_view_condition);
        conditionTextView.setText(report.getCondition());

        TextView dateTextView = (TextView) findViewById(R.id.source_report_view_date);
        dateTextView.setText(report.getDate());

        TextView timeTextView = (TextView) findViewById(R.id.source_report_view_time);
        timeTextView.setText(report.getTime());

        _backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
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

