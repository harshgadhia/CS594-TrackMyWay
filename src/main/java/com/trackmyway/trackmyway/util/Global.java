package com.trackmyway.trackmyway.util;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Mitul on 6/10/2015.
 */
public class Global extends Application {

    int userId;
    ArrayList<Friends> friends;
    double lat;
    double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Friends> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friends> friends) {
        this.friends = friends;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    ArrayList<TripRecord> tripRecord;
    ArrayList<TrackRecord> trackRecord;

    public Global() {
        friends = new ArrayList<Friends>();
        tripRecord = new ArrayList<TripRecord>();
        trackRecord = new ArrayList<TrackRecord>();

        Friends frd = new Friends();
        frd.setEmail("test@gmailc.om");
        frd.setName("Mitul");
        frd.setId(1);
        friends.add(frd);


        TripRecord triprecord = new TripRecord();
        triprecord.setDestination("LA");
        triprecord.setTripName("Collage");
        triprecord.setFriends(friends);
        tripRecord.add(triprecord);

        TrackRecord trc = new TrackRecord();
        trc.setTrackName("My Track");
        trc.setFriend(frd);
        trackRecord.add(trc);


    }

    public ArrayList<TripRecord> getTripRecord() {
        return tripRecord;
    }

    public void setTripRecord(ArrayList<TripRecord> tripRecord) {
        this.tripRecord = tripRecord;
    }

    public ArrayList<TrackRecord> getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(ArrayList<TrackRecord> trackRecord) {
        this.trackRecord = trackRecord;
    }

    public void addTripRecord(TripRecord trp){
        this.tripRecord.add(trp);
    }
    public void addTrackRecord(TrackRecord trp){
        this.trackRecord.add(trp);
    }
    public void addFriend(Friends frd){
        this.friends.add(frd);
    }
}
