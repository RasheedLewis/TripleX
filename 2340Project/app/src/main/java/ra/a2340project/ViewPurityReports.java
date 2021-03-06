package ra.a2340project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.ButterKnife;
/**
 * Created by z_JamesBailey on 3/26/17.
 * Uses recycler view to run through purity report Hashmap and
 * display reports
 */

public class ViewPurityReports extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_purity_list);
        ButterKnife.bind(this);

        View recyclerView = findViewById(R.id.view_purity_list);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        Log.d("Recycler View Thingy",recyclerView.toString());
        setupRecyclerView((RecyclerView) recyclerView);
    }
    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
        final Model model = Model.getInstance();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        // onLoginFailed();
                        //progressDialog.dismiss();
                        recyclerView.setAdapter(new SimpleCourseRecyclerViewAdapter(model.getPurityReportHashMap()));
                    }
                }, 3000);

    }

    /**
     * This inner class is custom to handle purity reports in recyclers.
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

            String purityIDD = ("   " + purityList.get(key).getReportNum());
            String purityCond = ("      " + purityList.get(key).getCondition());
            String purityVirus = ("    " + purityList.get(key).getVirusPPM());
            String purityCont = ("    " + purityList.get(key).getContaminantPPM());

            holder.purityID.setText(purityIDD);
            holder.purityCOND.setText(purityCond);
            holder.purityVIRUS.setText(purityVirus);
            holder.purityCONT.setText(purityCont);

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

            /**
             * Binds the viewHolder layouts to TextView objects
             *
             * @param view The view being used
             */
            public ViewHolder(View view) {
                super(view);
                mView = view;
                purityID = (TextView) view.findViewById(R.id.purity_Num);
                purityCOND = (TextView) view.findViewById(R.id.purity_Cond);
                purityVIRUS = (TextView) view.findViewById(R.id.purity_Virus);
                purityCONT = (TextView) view.findViewById(R.id.purity_Cont);
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
