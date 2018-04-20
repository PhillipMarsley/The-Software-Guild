/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.assignment_basicprogrammingconcepts;

import java.util.Scanner;

/**
 *
 * @author Dan
 */

/*
    REQUIRMENTS
    -------------
    Make a simple calculator that asks the user for their age. 
    Then calculate the healthy heart rate range for that age, and display it.

    Their maximum heart rate should be 220 - their age.
    The target heart rate zone is the 50 - 85% of the maximum.

    WHAT YOU SHOULD SEE
    --------------------
    What is your age? 50
    Your maximum heart rate should be 170 beats per minute
    Your target HR Zone is 85 - 145 beats per minute
 */

public class HealthyHearts {

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);

	int age = 0;
	int maxHeartRate = 0;
	double lowRange = 0;
	double highRange = 0;

	System.out.print("Enter your age: ");
	age = Integer.parseInt(scanner.nextLine());
	maxHeartRate = 220 - age;
	System.out.println("Your maximum heart rate should be " + maxHeartRate + " beats per minute");
	highRange = maxHeartRate * .85;
	lowRange = maxHeartRate * .50;
	System.out.println("Your target HR Zone is " + Math.round(lowRange) + " - " + Math.round(highRange) + " beats per minute");
    }
}
