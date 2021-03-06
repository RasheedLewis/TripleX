package ra.a2340project;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by benhepburn on 2/22/17.
 *
 * Information Holder - holds all the general information for every user of the app.
 *
 */

//Hi, this is a test.
    
public class User implements Parcelable {

    private String _name;
    private String _username;
    private String _email;
    private String _password;
    private String _status;

    public static final List<String> statuses = Arrays.asList("User", "Worker", "Manager", "Administrator");

    /**
     * A constructor creating a new user object
     *
     * @param _username the username of the new user
     */
    public User(String _username) {
        this._username = _username;
    }

    /**
     * An empty constructor used in implementation
     */
    public User(){}

    /* **************
     * Getters and Setters
     */
    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getUsername() { return _username; }
    public void setUsername(String username) { _username = username; }

    public String getEmail() { return _email; }
    public void setEmail(String email) { _email = email; }

    public String getPassword() { return _password; }
    public void setPassword(String password) { _password = password; }

    public String getStatus() { return _status; }
    public void setStatus(String status) { _status = status; }

    @Override
    public String toString() {
        return (_username);
    }


    private User(Parcel in){
        _name = in.readString();
        _username = in.readString();
        _email = in.readString();
        _password = in.readString();
        _status = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_username);
        dest.writeString(_email);
        dest.writeString(_password);
        dest.writeString(_status);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
