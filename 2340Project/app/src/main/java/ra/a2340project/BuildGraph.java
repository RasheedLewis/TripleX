package ra.a2340project;

/**
 * Created by z_JamesBailey on 4/2/17.
 */


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import com.jjoe64.graphview.DefaultLabelFormatter;
        import com.jjoe64.graphview.GraphView;
        import com.jjoe64.graphview.ValueDependentColor;
        import com.jjoe64.graphview.helper.StaticLabelsFormatter;
        import com.jjoe64.graphview.series.BarGraphSeries;
        import com.jjoe64.graphview.series.DataPoint;
        import com.jjoe64.graphview.series.DataPointInterface;
        import com.jjoe64.graphview.series.LineGraphSeries;
        import com.jjoe64.graphview.series.PointsGraphSeries;

public class BuildGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Line Graph

        GraphView line_graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> line_series =
                new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 1),
                        new DataPoint(1, 5),
                        new DataPoint(2, 3),
                        new DataPoint(3, 2),
                        new DataPoint(4, 6)
                });
        line_graph.addSeries(line_series);

        // set the bound

        // set manual X bounds
        line_graph.getViewport().setXAxisBoundsManual(true);
        line_graph.getViewport().setMinX(0.5);
        line_graph.getViewport().setMaxX(3.5);

        // set manual Y bounds
        line_graph.getViewport().setYAxisBoundsManual(true);
        line_graph.getViewport().setMinY(0.5);
        line_graph.getViewport().setMaxY(8);

        line_graph.getViewport().setScrollable(true);


        // set the dynamically label

        /*line_graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " $";
                }
            }
        });*/

        //set the static label
       /* StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(line_graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "March"});
        staticLabelsFormatter.setVerticalLabels(new String[] {"Sun", "Mon", "Tue"});
        line_graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);*/


        // custom paint to make a dotted line
       /* Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        line_series.setCustomPaint(paint);*/


        // set the radius of data point
       /* line_series.setDrawDataPoints(true);
        line_series.setDataPointsRadius(10);*/


        // set the background color below the line
        /*line_series.setDrawBackground(true);
        line_series.setBackgroundColor(Color.BLUE);*/


        // set the thickness of line
        // line_series.setThickness(20);

    }
}

