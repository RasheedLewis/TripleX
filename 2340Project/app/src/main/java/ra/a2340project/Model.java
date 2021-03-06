package ra.a2340project;

import com.google.android.gms.maps.model.LatLng;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * The app model, where information is stored and accessed.
 *
 * Created by Ben Hepburn on 2/23/17.
 */

public class Model {

    private static final Model _instance = new Model();

    /**
     * Provides a current instance of the model class
     *
     * @return An instance of the model class
     */
    public static Model getInstance() {
        return _instance;
    }

    /** HashMap containing all the users */
    private final HashMap<String, User> _userHashMap;

    /** HashMap containing all the water source reports */
    private final HashMap<Integer, SourceReport> _sourceReportHashMap;

    /** HashMap containing all the water purity reports */
    private final HashMap<Integer, PurityReport> _purityReportHashMap;

    /** the currently selected user */
    private static User _currentUser;

    /** the currently selected source report */
    private static SourceReport _currentSourceReport;

    /** the currently selected purity report */
    private static PurityReport _currentPurityReport;

    /** incremented number assigned to new reports  */
    private static int reportNum;

    /** the input conditions for the historical graph */
    private LatLng graphLocation;
    private int graphYear;
    private String graphType;

    private boolean result;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference mSourceReports = database.getReference("source reports");
    private final DatabaseReference mPurityReports = database.getReference("purity reports");
    private final DatabaseReference mUserRef = database.getReference("users");

    /*******
     * Getters and Setters
     */
    public User getCurrentUser() { return _currentUser; }
    public void setCurrentUser(User user) { _currentUser = user; }

    public SourceReport getCurrentSourceReport() {return _currentSourceReport;}
    public void setCurrentSourceReport(SourceReport report) {_currentSourceReport = report;}

    public PurityReport get_currentPurityReport() {return _currentPurityReport;}
    public void set_currentPurityReport(PurityReport report) {_currentPurityReport = report;}

    public int getReportNum() {return reportNum;}
    public void setReportNum(int num) {reportNum = num;}

    public HashMap<String,User> getUserHashMap() {

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String username = (String) snapshot.child("username").getValue();
                    User user = snapshot.getValue(User.class);
                    _userHashMap.put(username, user);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        });

        return _userHashMap;
    }

    public HashMap<Integer,SourceReport> getSourceReportHashMap() {

        mSourceReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    int reportNum = Integer.parseInt(String.valueOf(snapshot.child("reportNum").getValue()));
                    SourceReport report = snapshot.getValue(SourceReport.class);
                    _sourceReportHashMap.put(reportNum, report);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        });
        return _sourceReportHashMap;
    }

    public HashMap<Integer, PurityReport> getPurityReportHashMap() {
        mPurityReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    int reportNum = Integer.parseInt(String.valueOf(snapshot.child("reportNum").getValue()));
                    PurityReport report = snapshot.getValue(PurityReport.class);
                    _purityReportHashMap.put(reportNum, report);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) { }
        });

        return _purityReportHashMap;
    }

    public void setGraphLocation(double lat, double lng) { graphLocation = new LatLng(lat,lng); }
    public LatLng getGraphLocation() {return graphLocation;}

    public void setGraphYear(int year) { graphYear = year; }
    public int getGraphYear() {return graphYear; }

    public void setGraphType(String type) { graphType = type; }
    public String getGraphType() {return graphType; }


    /*
     * makes new model
     */
    private Model() {
        _userHashMap = new HashMap<>();
        _sourceReportHashMap = new HashMap<>();
        _purityReportHashMap = new HashMap<>();

    }

    /**
     * adds a new user to _userHashMap, with the key being the user's username,
     * and the value being the user object.
     *
     * @param username the username of the user being added
     * @param user the user object of the user being added
     */
    public void addUser(final String username, final User user) {


        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(username).exists()) {
                    // run some code
                    result = false;
                } else {
                    mUserRef.child(username).setValue(user);
                    result = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     * adds a new water source report to the _sourceReportHasMap, with the key being the report's number,
     * and the value being the report object.
     *
     * @param reportNum the report number of the report being added
     * @param report the report object of the report being added
     */
    public void addSourceReport(final int reportNum, final SourceReport report) {


        mSourceReports.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(Integer.toString(reportNum)).exists()) {
                    // run some code
                    System.out.println("THIS EXISTS");
                    result = false;
                } else {
                    mSourceReports.child(Integer.toString(reportNum)).setValue(report);
                    result = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     * adds a new purity source report to the _purityReportHasMap, with the key being the report's number,
     * and the value being the report object.
     *
     * @param purityReportNum the report number of the report being added
     * @param report the report object of the report being added
     */
    public void addPurityReport(final int purityReportNum, final PurityReport report) {

        mPurityReports.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(Integer.toString(purityReportNum)).exists()) {
                    // run some code
                    result = false;
                } else {
                    mPurityReports.child(Integer.toString(purityReportNum)).setValue(report);
                    result = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
