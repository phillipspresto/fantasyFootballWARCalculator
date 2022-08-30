package com.techelevator.display;

import com.techelevator.Player;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInput {
    private static Scanner scanner = new Scanner(System.in);



    public static String getPosition(){
        System.out.println("Enter position of player (capital letter abbreviation): ");
        Scanner userInput = new Scanner(System.in);
        String position = userInput.nextLine();
        return position;
    }

    public static Player getPlayerInfo(){
        System.out.println("Enter player name: ");
        Scanner userInput = new Scanner(System.in);
        String name = userInput.nextLine();
        System.out.println("Enter team abbreviation: ");
        String team = userInput.nextLine();
        String nameAndTeam = name + " (" + team + ") ";
        System.out.println("Enter average fantasy points per week: ");
        Double weeklyPoints = Double.valueOf(userInput.nextLine());
        Player player = new Player(nameAndTeam, weeklyPoints);
        return player;
    }

    public static Player checkPlayerInfo(List<Player> playerList){
        System.out.println("Enter player name: ");
        String name = (scanner.nextLine());
        String lowerCaseName = name.toLowerCase();
        for (Player player: playerList){
            if (Player.getNameAndTeam().contains(lowerCaseName)) {
                return player;
            }
        }
        return null;
    }


}
