package ra.a2340project;

/**
 * Created by z_JamesBailey on 4/2/17.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BuildGraph extends AppCompatActivity {
    private final Model model = Model.getInstance();

    private LatLng location;
    private int year;
    private String type;

    private @Bind(R.id.button_graph_back) Button _backButton;

    private final HashMap<Integer, Month> graphHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_graph);
        ButterKnife.bind(this);

        _backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        location = model.getGraphLocation();
        year = model.getGraphYear();
        type = model.getGraphType();

        for (int i = 1; i < 13; i++) {
            graphHashMap.put(i, new Month());
        }

        Set<Integer> keys = model.getPurityReportHashMap().keySet();

        for (int key : keys) {
            PurityReport report = model.getPurityReportHashMap().get(key);
            LatLng loc = new LatLng(report.getLat(),report.getLong());

            if (loc.equals(location)) {
                String date = report.getDate();
                int len = date.length();

                int repYear = Integer.parseInt(date.substring(len - 4, len));

                if (year == repYear) {
                    Integer month;

                    if (date.charAt(1) == '/') {
                        month = Character.getNumericValue(date.charAt(0));
                    } else {
                        month = Integer.parseInt(date.substring(0,2));
                    }

                    if (type.equals("Virus")) {
                        System.out.println("month:" + month);
                        int virusPPM = report.getVirusPPM();
                        int virusTotal = graphHashMap.get(month).getTotalPPM();
                        virusTotal = virusTotal + virusPPM;
                        int counter = graphHashMap.get(month).getCounter();
                        graphHashMap.get(month).setTotalPPM(virusTotal);
                        graphHashMap.get(month).setCounter(counter + 1);
                        System.out.println("counter increased.");
                    } else {
                        int contaminantPPM = report.getContaminantPPM();
                        int contaminantTotal = graphHashMap.get(month).getTotalPPM();
                        contaminantTotal = contaminantTotal + contaminantPPM;
                        int counter = graphHashMap.get(month).getCounter();
                        graphHashMap.get(month).setTotalPPM(contaminantTotal);
                        graphHashMap.get(month).setCounter(counter + 1);
                    }
                }
            }
        }

        GraphView line_graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] points = new DataPoint[12];

        Set<Integer> set = graphHashMap.keySet();
        for (int key : set) {
            System.out.println("key: " + key);
            int tot = graphHashMap.get(key).getTotalPPM();
            int count = graphHashMap.get(key).getCounter();
            int average;
            if (count == 0) {
                average = tot;
            } else {
                average = tot / count;
            }
            points[key - 1] = new DataPoint(key,average);
        }
        LineGraphSeries<DataPoint> line_series = new LineGraphSeries<>(points);

        line_graph.addSeries(line_series);

        line_graph.getViewport().setXAxisBoundsManual(true);
        line_graph.getViewport().setMinX(0);
        line_graph.getViewport().setMaxX(12);

        line_graph.getViewport().setYAxisBoundsManual(true);
        line_graph.getViewport().setMinY(0);
        line_graph.getViewport().setMaxY(50);

        line_graph.getViewport().setScrollable(true);

    }
}

