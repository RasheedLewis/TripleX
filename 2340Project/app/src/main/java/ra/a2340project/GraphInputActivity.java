package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;


import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benhepburn on 4/2/17.
 */

public class GraphInputActivity extends AppCompatActivity{

    @Bind(R.id.graph_input_latitude) EditText _latitude;
    @Bind(R.id.graph_input_longitude) EditText _longitude;
    @Bind(R.id.graph_input_year) EditText _year;
    @Bind(R.id.graph_input_spinner) Spinner _spinner;
    @Bind(R.id.graph_input_button) Button _graphButton;

    private final static List<String> choices = Arrays.asList("Virus","Contaminant");

    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_graph_inputs);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinner.setAdapter(adapter);

        _graphButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                graph();

                Intent intent = new Intent(getApplicationContext(), BuildGraph.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void graph() {
        Model model = Model.getInstance();

        double lat = Double.parseDouble(_latitude.getText().toString());
        double lng = Double.parseDouble(_longitude.getText().toString());

        model.setGraphLocation(lat,lng);
        model.setGraphType((String) _spinner.getSelectedItem());
        model.setGraphYear(Integer.parseInt(_year.getText().toString()));

    }

}
