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


public class DogGenetics {

    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	Random random = new Random();

	String dogName;

	String[] dogBreeds = {"Beagel", "German Sherpard", "Great Dane", "Pit Bull", "Dachshund"};

	int percentTotal = 100;
	int percent = 0;

	System.out.print("What is your dog's name? ");
	dogName = scanner.nextLine();
	System.out.println("Well then, I have this highly reliable report on " + dogName + "'s prestigious background right here.");
	System.out.println("");

	//dog breed print
	for (int i = 0; i < dogBreeds.length; i++) {
	    if (i < dogBreeds.length - 1) {
		percent = random.nextInt(percentTotal);
		percentTotal -= percent;
	    }
	    else {
		percent = percentTotal;
	    }
	    System.out.println(percent + "%\t" + dogBreeds[i]);
	}
	
	System.out.println("");
	System.out.println("Wow, that's QUITE the dog!");
    }
}
