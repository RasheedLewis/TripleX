package ra.a2340project;

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
    private static final String TAG = "GraphInputActivity";

    @Bind(R.id.graph_input_latitude) EditText _latitude;
    @Bind(R.id.graph_input_longitude) EditText _longitude;
    @Bind(R.id.graph_input_spinner) Spinner _spinner;
    @Bind(R.id.graph_input_button) Button _graphButton;

    public static List<String> choices = Arrays.asList("Virus","Contaminant");

    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_graph_inputs);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spinner.setAdapter(adapter);

        _graphButton.setOnClickListener(new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                graph();


            }
        });
    }

    public void graph() {

    }

}
