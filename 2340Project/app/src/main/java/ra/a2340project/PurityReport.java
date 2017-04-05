package ra.a2340project;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object class that holds all information for a purity report
 *
 * Created by benhepburn on 3/14/17.
 */

public class PurityReport implements Parcelable {

    private String _name;
    private int _reportNum;
    private double _lat;
    private double _long;
    private String _date;
    private String _time;
    private String _condition;
    private int _virusPPM;
    private int _contaminantPPM;

    public static final List<String> conditions = Arrays.asList("Safe","Treatable","Unsafe");


    public PurityReport() { }

    /**
     * Constructor for Purity Report
     *
     * @param _reportNum the auto-generated report number of the specific report
     */
    public PurityReport(int _reportNum) {this._reportNum = _reportNum; }

    /*****
     * Getters and Setters
     */
    public String getName() {return _name;}
    public void setName(String name) {_name = name;}

    public int getReportNum() {return _reportNum;}
    public void setReportNum(int num) {_reportNum = num;}

    public double getLat() {return _lat;}
    public void setLat(double lat) {_lat = lat;}

    public double getLong() {return _long;}
    public void setLong(double longitude) {_long = longitude;}

    public String getDate() {return _date;}
    public void setDate(String date) {_date = date;}

    public String getTime() {return _time;}
    public void setTime(String time) {_time = time;}

    public String getCondition() {return _condition;}
    public void setCondition(String condition) {_condition = condition;}

    public int getVirusPPM() {return _virusPPM;}
    public void setVirusPPM(int virusPPM) {_virusPPM = virusPPM;}

    public int getContaminantPPM() {return _contaminantPPM;}
    public void setContaminantPPM(int contaminantPPM) {_contaminantPPM = contaminantPPM;}


    private PurityReport(Parcel in) {
        _name = in.readString();
        _reportNum = in.readInt();
        _lat = in.readDouble();
        _long = in.readDouble();
        _date = in.readString();
        _time = in.readString();
        _condition = in.readString();
        _virusPPM = in.readInt();
        _contaminantPPM = in.readInt();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_name);
        dest.writeInt(_reportNum);
        dest.writeDouble(_lat);
        dest.writeDouble(_long);
        dest.writeString(_date);
        dest.writeString(_time);
        dest.writeString(_condition);
        dest.writeInt(_virusPPM);
        dest.writeInt(_contaminantPPM);
    }

    public static final Parcelable.Creator<PurityReport> CREATOR
            = new Parcelable.Creator<PurityReport>() {
        public PurityReport createFromParcel(Parcel in) {
            return new PurityReport(in);
        }

        public PurityReport[] newArray(int size) {return new PurityReport[size];
        }
    };

    @Exclude
    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("name", _name);
        map.put("reportNum", _reportNum);
        map.put("lat", _lat);
        map.put("long", _long);
        map.put("date", _date);
        map.put("time", _time);
        map.put("condition", _condition);
        map.put("virusPPM", _virusPPM);
        map.put("contaminantPPM", _contaminantPPM);

        return map;
    }
}
