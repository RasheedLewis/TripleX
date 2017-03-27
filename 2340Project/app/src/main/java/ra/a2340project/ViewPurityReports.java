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


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by z_JamesBailey on 3/26/17.
 * Uses recycler view to run thourgh purity report Hashmap and
 * display reports
 */

public class ViewPurityReports extends AppCompatActivity {
    private static final String TAG = "ViewSourceReportActivity";


    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_purity_list);
        ButterKnife.bind(this);

        View recyclerView = findViewById(R.id.view_purity_list);
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
        recyclerView.setAdapter(new SimpleCourseRecyclerViewAdapter(model.getPurityReportHashMap()));
    }

    /**
     * This inner class is custom to handle purity reports in recylers.
     * It takes our basic model information and
     * converts Purity Report view.
     *
     */
    public class SimpleCourseRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleCourseRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the Purity Reports to be shown in the view.
         */
        private final HashMap<Integer, PurityReport> purityList;

        /**
         * set the source reports to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleCourseRecyclerViewAdapter(HashMap<Integer, PurityReport> items) {
            purityList = items;
        }

        @Override
        public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_purity_content, parent, false);
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
            holder.purityList = purityList.get(key);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.purityID.setText("" + purityList.get(key).getReportNum());
            holder.purityCOND.setText("(" + purityList.get(key).getCondition());
            holder.purityVIRUS.setText(purityList.get(key).getVirusPPM());
            holder.purityCONT.setText(purityList.get(key).getContaminantPPM());

        }

        @Override
        public int getItemCount() {
            return purityList.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Reports) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView purityID;
            public final TextView purityCOND;
            public final TextView purityVIRUS;
            public final TextView purityCONT;
            public PurityReport purityList;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                purityID = (TextView) view.findViewById(R.id.purity_Num);
                purityCOND = (TextView) view.findViewById(R.id.purity_Cond);
                purityVIRUS = (TextView) view.findViewById(R.id.purity_virusPPM);
                purityCONT = (TextView) view.findViewById(R.id.purity_contaminantPPM);
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
