/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.assignment_basicprogrammingconcepts;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Dan
 */
public class RockPaperScissors {

    static int playerWins = 0;
    static int computerWins = 0;
    static int ties = 0;

    private static void reset() {
	playerWins = 0;
	computerWins = 0;
	ties = 0;
    }

    private static int compareWeapons(int weapon1, int weapon2) {
	int decision = 0;
	//Rock = 1
	//Paper = 2
	//Scissors = 3
	
	System.out.println("----------------------------");

	if (weapon1 == 1 && weapon2 == 3
		|| weapon1 == 2 && weapon2 == 1
		|| weapon1 == 3 && weapon2 == 2) {
	    decision = 1;
	    System.out.println("You win! Computer loses");
	}
	else if (weapon2 == 1 && weapon1 == 3
		|| weapon2 == 2 && weapon1 == 1
		|| weapon2 == 3 && weapon1 == 2) {
	    decision = 2;
	    System.out.println("You lose. Computer wins!");
	}
	else if (weapon1 == weapon2) {
	    decision = 3;
	    System.out.println("Tie!");
	}
	else {
	    System.out.println("Error in compareWeapons method");
	}

	System.out.println("----------------------------");
	System.out.println("");
	return decision;
    }

    private static void printWeaponChoice(int choice) {
	switch (choice) {
	    case 1:
		System.out.print("ROCK");
		break;
	    case 2:
		System.out.print("PAPER");
		break;
	    case 3:
		System.out.print("SCISSORS");
		break;
	    default:
		System.out.println("Error in printWeaponChoice method");
		break;
	}
    }

    private static void tallyResults(int rounds, int battleDecision) {
	if (rounds == 0) {
	    System.out.println("Your wins: " + playerWins + "\t Computer wins: " + computerWins);
	    System.out.println("Ties: " + ties);
	    System.out.println("");
	    System.out.println(" :::::::::::::::::::::::");
	    System.out.println("::   Overall Winner    :: ");
	    System.out.println("::---------------------::");

	    if (playerWins > computerWins) {
		System.out.println("::         You         ::");
	    }
	    else if (computerWins > playerWins) {
		System.out.println("::       Computer      ::");
	    }
	    else {
		System.out.println("::        Draw         ::");
	    }

	    System.out.println(" :::::::::::::::::::::::");
	}
	else {
	    switch (battleDecision) {
		case 1:
		    playerWins++;
		    break;
		case 2:
		    computerWins++;
		    break;
		case 3:
		    ties++;
		    break;
		default:
		    System.out.println("Error in tallyResults method");
		    break;
	    }
	}
    }

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	Random random = new Random();

	boolean gameActive = true;
	String keepPlaying = "";
	String resetResults = "";

	int rounds = 0;
	int wrongRoundInputCounter = 3;
	int playerChoice;
	int computerChoice;
	int battleDecision = 0;

	System.out.println("Welcome to...");
	System.out.println(" ::::::::::::::::::::::::::::::");
	System.out.println("::                             ::");
	System.out.println("::    ROCK, PAPER, SCISSORS    ::");
	System.out.println("::                             ::");
	System.out.println(" ::::::::::::::::::::::::::::::");
	System.out.println("");

	//===============================================================
	//	    Start of Game Loop
	//===============================================================
	while (gameActive = true) {
	    System.out.println("How many rounds would you like to play?");
	    System.out.print("-> ");
	    rounds = Integer.parseInt(scanner.nextLine());
	    System.out.println("");

	    while (rounds < 1 || rounds > 10) {
		if (wrongRoundInputCounter == 0) {
		    gameActive = false;
		    break;
		}
		else {
		    System.out.println("Invalid rounds, rounds must be between 1 and 10");
		    System.out.println(wrongRoundInputCounter + " attempts left:");
		    System.out.print("Round: ");
		    rounds = Integer.parseInt(scanner.nextLine());
		    System.out.println("");

		    wrongRoundInputCounter--;
		}
	    }
	    if (gameActive == false) {
		System.out.println("To many invalid entries. Game has closed.");
		break;
	    }
	    //===============================================================
	    //	    Start of Rounds Loop
	    //===============================================================
	    while (rounds > 0) {

		//===============================================================
		//	    Choose Weapon
		//===============================================================
		System.out.println("Choose your weapon!");
		System.out.println("1) ROCK \t 2). PAPER \t 3). SCISSORS");
		System.out.print("-> ");
		playerChoice = Integer.parseInt(scanner.nextLine());
		computerChoice = (random.nextInt(3) + 1);
		System.out.println("");

		//===============================================================
		//	    Battle
		//===============================================================
		printWeaponChoice(playerChoice);
		System.out.print(" x ");
		printWeaponChoice(computerChoice);
		System.out.println("");
		battleDecision = compareWeapons(playerChoice, computerChoice);

		//===============================================================
		//	    Results
		//===============================================================
		tallyResults(rounds, battleDecision);

		rounds--;
		if (rounds == 0) {
		    System.out.println(" :::::::::::::::::::::::::");
		    System.out.println("::       Game End        ::");
		    System.out.println(" :::::::::::::::::::::::::");
		    tallyResults(rounds, battleDecision);
		    gameActive = false;
		    break;
		}
	    }
	    //===============================================================
	    //	    End of Rounds Loop
	    //===============================================================

	    if (gameActive == false) {
		System.out.println("");
		System.out.println(" :::::::::::::::::::::");
		System.out.println("::                   ::");
		System.out.println("::    Play again?    ::");
		System.out.println("::                   ::");
		System.out.println(" ::::::::::::::::::::: ");
		System.out.print("y/n: ");

		keepPlaying = scanner.nextLine();
		if (keepPlaying.equals("Y") || keepPlaying.equals("y")) {
		    gameActive = true;

		    System.out.println("Would you like to reset the results?");
		    System.out.print("y/n: ");
		    resetResults = scanner.nextLine();
		    if (resetResults.equals("Y") || resetResults.equals("y")) {
			reset();
		    }
		}
		else {
		    gameActive = false;
		    System.out.println(":::::::::::::::::::::::::::::::::");
		    System.out.println("::                             ::");
		    System.out.println("::     Thanks For Playing      ::");
		    System.out.println("::                             ::");
		    System.out.println(":::::::::::::::::::::::::::::::::");
		    break;
		}
	    }
	}
	//===============================================================
	//	    End of Game Loop
	//===============================================================
    }//end main
}//end class
