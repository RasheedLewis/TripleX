package ra.a2340project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by z_JamesBailey on 3/6/17.
 * Displays list of Source Reports
 */

public class ViewSourceReports extends AppCompatActivity {
    private static final String TAG = "ViewSourceReportActivity";


    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_reports_list);
        ButterKnife.bind(this);

        View recyclerView = findViewById(R.id.view_reports_list);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        setupRecyclerView((RecyclerView) recyclerView);
    }
    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Model model = Model.getInstance();
        recyclerView.setAdapter(new SimpleCourseRecyclerViewAdapter(model.getSourceReportHashMap()));
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     */
    public class SimpleCourseRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleCourseRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the Source Reports to be shown in the view.
         */
        private final HashMap<Integer, SourceReport> reportList;

        /**
         * set the source reports to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleCourseRecyclerViewAdapter(HashMap<Integer, SourceReport> items) {
            reportList = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_reports_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int key) {

            final Model model = Model.getInstance();
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.reportList = reportList.get(key);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.reportID.setText("" + reportList.get(key).getReportNum());
            holder.reportLOC.setText("(" + reportList.get(key).getLat() + ", " + reportList.get(key).getLong() + ")");
            holder.reportTYPE.setText(reportList.get(key).getType());
            holder.reportCOND.setText(reportList.get(key).getCondition());

        }

        @Override
        public int getItemCount() {
            return reportList.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Reports) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView reportID;
            public final TextView reportLOC;
            public final TextView reportTYPE;
            public final TextView reportCOND;
            public SourceReport reportList;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                reportID = (TextView) view.findViewById(R.id.report_User);
                reportLOC = (TextView) view.findViewById(R.id.report_Loc);
                reportTYPE = (TextView) view.findViewById(R.id.report_Type);
                reportCOND = (TextView) view.findViewById(R.id.report_Cond);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}