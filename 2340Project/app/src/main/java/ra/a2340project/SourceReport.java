package ra.a2340project;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.Arrays;
import java.util.List;
import java.util.Date;

/**
 * Created by benhepburn on 3/2/17.
 */

public class SourceReport implements Parcelable {

    private String _name;
    private String _location;
    private String _type;
    private String _condition;
    private int _reportNum;
    private String _date;
    private String _time;

    public static List<String> types = Arrays.asList("Bottled","Well","Stream","Lake","Spring","Other");
    public static List<String> conditions = Arrays.asList("Waste","Treatable-Clear","Treatable-Muddy","Potable");

    /**
     *
     * Constructor for Source Report
     *
     * @param _reportNum the automated report number assigned to each report
     */
    public SourceReport(int _reportNum) {
        this._reportNum = _reportNum;
    }

    /* ********
     * Getters and Setters
     */
    public String getName() {return _name;}
    public void setName(String name) {_name = name;}

    public String getLocation() {return _location;}
    public void setLocation(String location) {_location = location;}

    public String getType() {return _type;}
    public void setType(String type) {_type = type;}

    public String getCondition() {return _condition;}
    public void setCondition(String condition) {_condition = condition;}

    public int getReportNum() {return _reportNum;}
    public void setReportNum(int num) {_reportNum = num;}

    public String getDate() {return _date;}
    public void setDate(String date) {_date = date;}

    public String getTime() {return _time;}
    public void setTime(String time) {_time = time;}


    private SourceReport(Parcel in) {
        _name = in.readString();
        _location = in.readString();
        _type = in.readString();
        _condition = in.readString();
        _reportNum = in.readInt();
        _date = in.readString();
        _time = in.readString();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeString(_location);
        dest.writeString(_type);
        dest.writeString(_condition);
        dest.writeInt(_reportNum);
        dest.writeString(_date);
        dest.writeString(_time);
    }

    public static final Parcelable.Creator<SourceReport> CREATOR
            = new Parcelable.Creator<SourceReport>() {
        public SourceReport createFromParcel(Parcel in) {
            return new SourceReport(in);
        }

        public SourceReport[] newArray(int size) {
            return new SourceReport[size];
        }
    };
}