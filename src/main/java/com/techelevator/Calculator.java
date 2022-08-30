/*
State of the code:
-Just added in userinput and useroutput
-Data is hard-coded in
-formulas are working, just slightly off when comparing to the FFBallers article and the Kupp WAR calculations
-Deleted the Main method in order to clean up code
-In the future:
    -bring in API or DB
    -create a DB to keep player WAR data
    -Be able to adjust data as the season starts and stats change
    -Flex position does not work yet

 */



package com.techelevator;

import com.techelevator.display.UserInput;
import com.techelevator.display.UserOutput;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class Calculator {
    private static double [] qbWeeklyScores= {22.0, 25.3, 25.1, 10.1, 13.4, 28.5, 13.2, 21.6, 17.5, 25.2, 15.5, 20.2, 14.0, 10.5, 16.3, 21.9}; // Kirk Cousins avg 18.8
    private static double [] rbWeeklyScores= {19.3, 7.9, 14.6, 7.7, 5.3, 8.8, 10.2, 17.7, 16.5, 8.8, 9.3, 23.1, 6.6, 0.5, 8.7, 16.1}; // Melvin Gordon avg 11.3
    private static double [] wrWeeklyScores= {32.4, 3.9, 4.1, 14.4, 13.5, 8.0, 22.2, 4.7, 7.1, 5.1, 13.6, 1.8, 18.0, 9.3, 10.4}; //Amari Cooper avg 11.2
    private static double [] teWeeklyScores= {4.4, 1.1, 13.3, 9.0, 1.2, 10.9, 14.5, 6.2, 4.2, 6.6, 24.8, 1.5, 6.7, 10.4, 9.4, 7.6, 11.9}; // Zach Ertz avg. 8.5
    private static double [] flexWeeklyScores= {2.9, 0.8, 0.4, 5.0, 11.3, 11.4, 15.3, 7.3, 17.0, 6.5, 18.2, 9.7, 2.9, 9.0, 8.2, 3.7}; //#82 flex Devonta Freeman (NOT #72 due to TE's included in flex list)
    private static double qbReplacementValue = 18.3;
    private static double rbReplacementValue = 11.75;
    private static double wrReplacementValue = 11.0;
    private static double teReplacementValue = 8.8;
    private static double flexReplacementValue = 8.1;
    private static double replacementLineupValue = 91.8;
    private static double qbVar = getVarianceForPosition("QB");
    private static double rbVar = getVarianceForPosition("RB");
    private static double wrVar = getVarianceForPosition("WR");
    private static double teVar = getVarianceForPosition("TE");
    private static double flexVar = getVarianceForPosition("Flex");
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        UserOutput.displayHomeScreen();
        String position = UserInput.getPosition();
//        List<Player> playerList = UserOutput.populatePlayerList(position);
        Player player = UserInput.getPlayerInfo();
//        Player player = UserInput.checkPlayerInfo(playerList);
        double y = getMyLineupValue(position, player);
        double mu = y - replacementLineupValue;
        double sigmaY = getStandardDeviationForPosition(position, player);
        double sigmaX = Math.sqrt(qbVar + rbVar + wrVar + teVar + flexVar);
        double sigma = Math.sqrt((sigmaX * sigmaX) + (sigmaY + sigmaY));
        double playerWar = getWAR(mu, sigma);

        System.out.println("WAR for " + Player.getNameAndTeam() + ": " + df.format(playerWar));




    }

    public static double getMyLineupValue(String position, Player player) {
        double totalReplacementValue = qbReplacementValue + (rbReplacementValue * 2) + (wrReplacementValue * 3) + teReplacementValue + flexReplacementValue; //91.8
        double lineupWithNewPlayer = 0.0;

        if (position.equals("QB")) {
            lineupWithNewPlayer = (totalReplacementValue - qbReplacementValue + player.getWeeklyPoints()); //qb 1-12
        } else if (position.equals("RB")) {
            lineupWithNewPlayer = (totalReplacementValue - rbReplacementValue + player.getWeeklyPoints()); //2x for lineup total (2 rb's per team) rb 1-24
        } else if (position.equals("WR")) {
            lineupWithNewPlayer = (totalReplacementValue - wrReplacementValue + player.getWeeklyPoints()); //3x for lineup total (3 wr's per team) wr 1-36
        } else if (position.equals("TE")) {
            lineupWithNewPlayer = (totalReplacementValue - teReplacementValue + player.getWeeklyPoints());
            ; //te 1-12
        } else if (position.equals("Flex")) {
            lineupWithNewPlayer = (totalReplacementValue - flexReplacementValue + player.getWeeklyPoints()); //combine rb's and wr's #48-72
        }
        return lineupWithNewPlayer;
    }

    public static double getStandardDeviationForPosition(String position, Player player){
        double std = 0.0;
        DescriptiveStatistics stats = new DescriptiveStatistics();
        if (position.equals("QB")){
            for( int i = 0; i < qbWeeklyScores.length; i++) {
                stats.addValue(qbWeeklyScores[i]);
            }
            std = stats.getStandardDeviation();
        } else if (position.equals("RB")) {
            for (int i = 0; i < rbWeeklyScores.length; i++) {
                stats.addValue(rbWeeklyScores[i]);
            }
            std = stats.getStandardDeviation();
        } else if (position.equals("WR")){
            for (int i = 0; i < wrWeeklyScores.length; i++) {
                stats.addValue(wrWeeklyScores[i]);
            }
            std = stats.getStandardDeviation();
        } else if (position.equals("TE")){
            for (int i = 0; i < teWeeklyScores.length; i++) {
                stats.addValue(teWeeklyScores[i]);
            }
            std = stats.getStandardDeviation();
        } else if (position.equals("Flex")){
            for (int i = 0; i < flexWeeklyScores.length; i++) {
                stats.addValue(flexWeeklyScores[i]);
            }
            std = stats.getStandardDeviation();
        }
        return std;
    }

    public static double getVarianceForPosition(String position){
        double variance = 0.0;
        DescriptiveStatistics stats = new DescriptiveStatistics();
        if (position.equals("QB")){
            for( int i = 0; i < qbWeeklyScores.length; i++) {
                stats.addValue(qbWeeklyScores[i]);
            }
            variance = stats.getVariance();
        } else if (position.equals("RB")) {
            for (int i = 0; i < rbWeeklyScores.length; i++) {
                stats.addValue(rbWeeklyScores[i]);
            }
            variance = stats.getVariance();
        } else if (position.equals("WR")){
            for (int i = 0; i < wrWeeklyScores.length; i++) {
                stats.addValue(wrWeeklyScores[i]);
            }
            variance = stats.getVariance();
        } else if (position.equals("TE")){
            for (int i = 0; i < teWeeklyScores.length; i++) {
                stats.addValue(teWeeklyScores[i]);
            }
            variance = stats.getVariance();
        } else if (position.equals("Flex")){
            for (int i = 0; i < flexWeeklyScores.length; i++) {
                stats.addValue(flexWeeklyScores[i]);
            }
            variance = stats.getVariance();
        }
        return variance;
    }

    public static double getWAR(double mean, double differenceInStandardDeviation){
        NormalDistribution normal = new NormalDistribution(mean,differenceInStandardDeviation);
        double war = (1 - normal.cumulativeProbability(0) - 0.5);
        return war;
    }




}
