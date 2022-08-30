package com.techelevator;

public class Player {
   private static String nameAndTeam;
   private static double weeklyPoints;


    public Player(String nameAndTeam, double weeklyPoints) {
        this.nameAndTeam = nameAndTeam;
        this.weeklyPoints = weeklyPoints;
    }

    public Player(){};

    public static String getNameAndTeam() {
        return nameAndTeam;
    }

    public static void setNameAndTeam(String nameAndTeam) {
        Player.nameAndTeam = nameAndTeam;
    }

    public static double getWeeklyPoints() {
        return weeklyPoints;
    }

    public static void setWeeklyPoints(double weeklyPoints) {
        Player.weeklyPoints = weeklyPoints;
    }


}
