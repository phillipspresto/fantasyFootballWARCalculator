package com.techelevator.display;

import com.techelevator.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserOutput {
    private static List<Player> playerList = new ArrayList<>();


    public static void displayHomeScreen(){
        System.out.println();
        System.out.println("***************************************************");
        System.out.println("                  FF WAR Calculator");
        System.out.println("***************************************************");
        System.out.println();
    }

    public static List<Player> populatePlayerList(String position){
        if (position.equalsIgnoreCase("QB")){
            File inputFile = new File("FantasyPros_Fantasy_Football_Statistics_QB.csv");
            try{
                Scanner scanner = new Scanner(inputFile);
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    List<String> playerDetails = Arrays.asList(line.split(","));
                    String nameAndTeam = playerDetails.get(1);
                    Double weeklyPoints = Double.parseDouble(playerDetails.get(15));
                    Player player = new Player(nameAndTeam, weeklyPoints);
                    playerList.add(player);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        } else if (position.equalsIgnoreCase("RB")){
            File inputFile = new File("FantasyPros_Fantasy_Football_Statistics_RB.csv");
            try{
                Scanner scanner = new Scanner(inputFile);
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    List<String> playerDetails = Arrays.asList(line.split(","));
                    String nameAndTeam = playerDetails.get(1);
                    Double weeklyPoints = Double.parseDouble(playerDetails.get(15));
                    Player player = new Player(nameAndTeam, weeklyPoints);
                    playerList.add(player);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (position.equalsIgnoreCase("WR")){
            File inputFile = new File("FantasyPros_Fantasy_Football_Statistics_WR.csv");
            try{
                Scanner scanner = new Scanner(inputFile);
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    line.replaceAll("\"", "");
                    List<String> playerDetails = Arrays.asList(line.split(","));
                    String nameAndTeam = playerDetails.get(1);
                    Double weeklyPoints = Double.valueOf(playerDetails.get(16));
                    Player player = new Player(nameAndTeam, weeklyPoints);
                    playerList.add(player);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (position.equalsIgnoreCase("TE")){
            File inputFile = new File("FantasyPros_Fantasy_Football_Statistics_TE.csv");
            try{
                Scanner scanner = new Scanner(inputFile);
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    List<String> playerDetails = Arrays.asList(line.split(","));
                    String nameAndTeam = playerDetails.get(1);
                    Double weeklyPoints = Double.parseDouble(playerDetails.get(15));
                    Player player = new Player(nameAndTeam, weeklyPoints);
                    playerList.add(player);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return playerList;
    }
}
