package ra.a2340project;

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

    /** the currently selected user */
    private User _currentUser;

    /**
     *
     * @return the current user
     */
    public User getCurrentUser() { return _currentUser; }

    /**
     *
     * @param user the user being set as the current user
     */
    public void setCurrentUser(User user) {
        _currentUser = user;
    }

    /*
     * makes new model
     */
    public Model() {
        _userHashMap = new HashMap<>();
    }

    public HashMap<String,User> getHashMap() {
        return _userHashMap;
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

    public void replaceUserData(User user) {
        User existing = _currentUser;
        addUser(user.getUsername(),user);
        _userHashMap.remove(existing.getUsername());
    }
}
