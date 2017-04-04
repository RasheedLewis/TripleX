package ra.a2340project;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Ben Hepburn on 2/23/17.
 */

public class Model {

    private static final Model _instance = new Model();
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
    private User _currentUser;

    /** the currently selected source report */
    private SourceReport _currentSourceReport;

    /** the currently selected purity report */
    private PurityReport _currentPurityReport;

    /** incremented number assigned to new reports  */
    private int reportNum;
    private int purityReportNum;

    /** the input conditions for the historical graph */
    private LatLng graphLocation;
    private int graphYear;
    private String graphType;

    /*******
     * Getters and Setters
     */
    public User getCurrentUser() { return _currentUser; }
    public void setCurrentUser(User user) {
        _currentUser = user;
    }

    public SourceReport getCurrentSourceReport() {return _currentSourceReport;}
    public void setCurrentSourceReport(SourceReport report) {_currentSourceReport = report;}

    public PurityReport get_currentPurityReport() {return _currentPurityReport;}
    public void set_currentPurityReport(PurityReport report) {_currentPurityReport = report;}

    public int getReportNum() {return reportNum;}
    public void setReportNum(int num) {reportNum = num;}

    public int getPurityReportNum() {return purityReportNum;}
    public void setPurityReportNum(int num) {purityReportNum = num;}

    public HashMap<String,User> getUserHashMap() {
        return _userHashMap;
    }

    public HashMap<Integer,SourceReport> getSourceReportHashMap() {return _sourceReportHashMap;}

    public HashMap<Integer, PurityReport> getPurityReportHashMap() {return _purityReportHashMap;}

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
    public void addUser(String username, User user) {
        if (!_userHashMap.containsKey(username)) {
            _userHashMap.put(username, user);
        }
    }

    /**
     *
     * adds a new water source report to the _sourceReportHasMap, with the key being the report's number,
     * and the value being the report object.
     *
     * @param reportNum the report number of the report being added
     * @param report the report object of the report being added
     */
    public void addSourceReport(int reportNum, SourceReport report) {
        if (!_sourceReportHashMap.containsKey(reportNum)) {
            _sourceReportHashMap.put(reportNum, report);
        }
    }

    /**
     *
     * adds a new purity source report to the _purityReportHasMap, with the key being the report's number,
     * and the value being the report object.
     *
     * @param purityReportNum the report number of the report being added
     * @param report the report object of the report being added
     */
    public void addPurityReport(int purityReportNum, PurityReport report) {
        if (!_purityReportHashMap.containsKey(purityReportNum)) {
            _purityReportHashMap.put(purityReportNum, report);
        }
    }

    public void replaceUserData(User user) {
        User existing = _currentUser;
        addUser(user.getUsername(),user);
        _userHashMap.remove(existing.getUsername());
    }
}
