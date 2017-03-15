package ra.a2340project;

import android.content.Intent;

import java.util.HashMap;

/**
 * Created by benhepburn on 2/23/17.
 */

public class Model {

    public static final Model _instance = new Model();
    public static Model getInstance() {
        return _instance;
    }

    /** HashMap containing all the users */
    private HashMap<String, User> _userHashMap;

    /** HashMap containing all the water source reports */
    private HashMap<Integer, SourceReport> _sourceReportHashMap;

    /** HashMap containing all the water purity reports */
    private HashMap<Integer, PurityReport> _purityReportHashMap;

    /** the currently selected user */
    private User _currentUser;

    /** the currently selected source report */
    private SourceReport _currentSourceReport;

    /** the currently selected purity report */
    private PurityReport _currentPurityReport;

    /** incremented number assigned to new reports  */
    public int reportNum;

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

    public HashMap<String,User> getUserHashMap() {
        return _userHashMap;
    }

    public HashMap<Integer,SourceReport> getSourceReportHashMap() {return _sourceReportHashMap;}

    public HashMap<Integer, PurityReport> getPurityReportHashMap() {return _purityReportHashMap;}


    /*
     * makes new model
     */
    public Model() {
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
     * @return true if the username didn't already exist in the hashmap and the key, value pair is added
     *         false if the username is already in the hashmap and the user isn't added
     */
    public boolean addUser(String username, User user) {
        if (!_userHashMap.containsKey(username)) {
            _userHashMap.put(username,user);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * adds a new water source report to the _sourceReportHasMap, with the key being the report's number,
     * and the value being the report object.
     *
     * @param reportNum the report number of the report being added
     * @param report the report object of the report being added
     * @return true if the reportNumber didn't already exist in the HashMap and report is added,
     *         false otherwise.
     */
    public boolean addSourceReport(int reportNum, SourceReport report) {
        if (!_sourceReportHashMap.containsKey(reportNum)) {
            _sourceReportHashMap.put(reportNum, report);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * adds a new purity source report to the _purityReportHasMap, with the key being the report's number,
     * and the value being the report object.
     *
     * @param reportNum the report number of the report being added
     * @param report the report object of the report being added
     * @return true if the reportNumber didn't already exist in the HashMap and report is added,
     *         false otherwise.
     */
    public boolean addPurityReport(int reportNum, PurityReport report) {
        if (!_purityReportHashMap.containsKey(reportNum)) {
            _purityReportHashMap.put(reportNum, report);
            return true;
        } else {
            return false;
        }
    }

    public void replaceUserData(User user) {
        User existing = _currentUser;
        addUser(user.getUsername(),user);
        _userHashMap.remove(existing.getUsername());
    }
}
