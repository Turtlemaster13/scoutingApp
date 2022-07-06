package com.example.scoutingapp;

import java.io.Serializable;

public class MatchScore implements Serializable {
    //pre match
    public String scoutName = "";
    public int robotNumber = 0;
    public int matchNumber = 0;
    public String startingPosition = "";
    public boolean showedUp = false;
    public boolean taxi = false;
    //scoring
    //auto
    public int autoLowerMissed = 0;
    public int autoUpperMissed = 0;
    public int autoLowerHit = 0;
    public int autoUpperHit = 0;
    //teleop
    public int teleLowerMissed = 0;
    public int teleUpperMissed = 0;
    public int teleLowerHit = 0;
    public int teleUpperHit = 0;
    //fouls
    public int totalFouls = 0;
    public int totalTechFouls = 0;
    //damage/disconects
    boolean damage = false;
    public boolean disconects = false;
    //climbing
    public int climbTime = 0;
    public String climbEndingPosition = "";
    //comments
    public String comments = "";
}
