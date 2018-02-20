/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Scanner;

/**
 *
 * @author Dan
 */
public class UserIOConsoleImpl implements UserIO {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
	System.out.print(message + '\n');
    }

    @Override
    public double readDouble(String prompt) {
	print(prompt);
	System.out.print("-> ");
	return Double.parseDouble(scanner.nextLine());
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
	double number;
	do {
	    print(prompt);
	    System.out.print("-> ");
	    number = Double.parseDouble(scanner.nextLine());
	}
	while (number < min || number > max);
	return number;
    }

    @Override
    public float readFloat(String prompt) {
	print(prompt);
	System.out.print("-> ");
	return Float.parseFloat(scanner.nextLine());
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
	float number;
	do {
	    print(prompt);
	    System.out.print("-> ");
	    number = Float.parseFloat(scanner.nextLine());
	}
	while (number < min || number > max);
	return number;
    }

    @Override
    public int readInt(String prompt) {
	print(prompt);
	System.out.print("-> ");
	return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
	int number;
	do {
	    print(prompt);
	    System.out.print("-> ");
	    number = Integer.parseInt(scanner.nextLine());
	}
	while (number < min || number > max);
	return number;
    }

    @Override
    public long readLong(String prompt) {
	while (scanner.hasNextLong() == false) {
	    print(prompt);
	    System.out.print("-> ");
	    scanner.nextLine();
	}
	return Long.parseLong(scanner.nextLine());
    }

    @Override
    public long readLong(String prompt, long min, long max) {
	long number;
	do {
	    print(prompt);
	    System.out.print("-> ");
	    number = Long.parseLong(scanner.nextLine());
	}
	while (number < min || number > max);
	return number;
    }

    @Override
    public String readString(String prompt) {
	print(prompt);
	System.out.print("-> ");
	return scanner.nextLine();
    }

}
