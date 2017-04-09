package ra.a2340project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object class used in the graphing implementation.
 *
 * Created by Ben Hepburn on 4/2/17.
 */

public class Month implements Parcelable {

    private int _totalPPM;
    private int _counter;

    /**
     * A constructor that creates a Month object and sets its default variables
     */
    public Month() {
        _totalPPM = 0;
        _counter = 0;
    }

    /** Getters and Setters */
    public void setTotalPPM(int total) {_totalPPM = total;}
    public int getTotalPPM() {return _totalPPM;}

    public void setCounter(int counter) {_counter = counter;}
    public int getCounter() {return _counter;}


    private Month(Parcel in) {
        _totalPPM = in.readInt();
        _counter = in.readInt();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_totalPPM);
        dest.writeInt(_counter);
    }

    public static final Parcelable.Creator<Month> CREATOR = new Parcelable.Creator<Month>() {
        public Month createFromParcel(Parcel in) {return new Month(in); }

        public Month[] newArray(int size) {return new Month[size]; }
    };
}
