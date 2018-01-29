/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.List;
import model.DVD;

/**
 *
 * @author Dan
 */
public class View {

    UserIO io;

    public View(UserIO io) {
	this.io = io;
    }

    //===================================================
    //		    Start Message
    //===================================================
    public void displayBannerStart() {
	io.print(" ::::::::::::::::::::::::::::::::::::::::");
	io.print("::                                      ::");
	io.print("::  Welcome to the DVD Library Program  ::");
	io.print("::                                      ::");
	io.print(" :::::::::::::::::::::::::::::::::::::::: ");
	io.print("");
    }

    //===================================================
    //		    Main Menu
    //===================================================
    public void displayBannerMenuMain() {
	io.print(" .............");
	io.print(":             :");
	io.print(":  Main Menu  :");
	io.print(":             :");
	io.print(" .............");
    }

    public void displayDVDMenuMain() {
	io.print("1 - Add a DVD");
	io.print("2 - Remove a DVD");
	io.print("3 - Edit a DVD");
	io.print("4 - View a DVD");
	io.print("5 - View All DVDs");
	io.print("6 - Exit");
    }

    public int getCommandDVDMenuMain() {
	return io.readInt("Choose an option.");
    }

    //===================================================
    //		    Add DVDs
    //===================================================
    public void displayBannerDVDAdd() {
	io.print("");
	io.print(" .................. ");
	io.print(":                  :");
	io.print(":  Add DVD Option  :");
	io.print(":                  :");
	io.print(" .................. ");
    }
    
    public String getDataDVDTitleAdd() {
	return io.readString("Enter the title of the DVD");
    }

    public DVD getDataDVDAdd(String title) {
	io.print("");
	String releaseDate = io.readString("Enter the release date of the DVD");
	io.print("");
	io.print("G     - General Audiances");
	io.print("PG    - Parental Guidance Suggested");
	io.print("PG-13 - Parents Strongly Cautioned");
	io.print("R     - Restricted");
	io.print("NC-17 - No One Under 17 Allowed");
	io.print("");
	String MPAARating = io.readString("Enter the MPAA Rating of the DVD");
	io.print("");
	String directorName = io.readString("Enter the director name of the DVD");
	io.print("");
	String studio = io.readString("Enter the studio of the DVD");
	io.print("");
	String userNote = io.readString("Enter any additional information about the DVD");
	io.print("");

	DVD dvd = new DVD(title);
	dvd.setReleaseDate(releaseDate);
	dvd.setMPAARating(MPAARating);
	dvd.setDrictorName(directorName);
	dvd.setStudio(studio);
	dvd.setUserNote(userNote);

	return dvd;
    }

    public void displayBannerDVDAddSucess() {
	io.print(" - - - - - - - - - - - -");
	io.print("|  Addition Successful  |");
	io.print(" - - - - - - - - - - - -");
    }

    //===================================================
    //		    Remove DVDs
    //===================================================
    public void displayBannerDVDRemove() {
	io.print(" ..................... ");
	io.print(":                     :");
	io.print(":  Remove DVD Option  :");
	io.print(":                     :");
	io.print(" ..................... ");
    }

    public String getDataDVDTitleRemove() {
	return io.readString("Enter the title of the DVD you want to remove.");
    }

    public void displayBannerDVDRemoveSuccess() {
	io.print(" - - - - - - - - - - -");
	io.print("|  Removal Successful  |");
	io.print(" - - - - - - - - - - -");
    }

    //===================================================
    //		    Edit DVDs
    //===================================================
    public void displayBannerDVDEdit() {
	io.print("");
	io.print(" ................... ");
	io.print(":                   :");
	io.print(":  Edit DVD Option  :");
	io.print(":                   :");
	io.print(" ................... ");
    }

    public String getDataDVDTitleEdit() {
	return io.readString("Enter the title of the DVD you want to edit.");
    }

    public void displayBannerDVDEditMenu() {
	io.print("");
	io.print(" ................. ");
	io.print(":                 :");
	io.print(":  Edit DVD Menu  :");
	io.print(":                 :");
	io.print(" ................. ");
    }

    public void displayDVDMenuEdit() {
	io.print("1. Edit Release Date");
	io.print("2. Edit MPAA Rating");
	io.print("3. Edit Director Name");
	io.print("4. Edit Studio");
	io.print("5. Edit User Rating or Note");
	io.print("6. Exit");
    }

    public int getCommandDVDMenuEdit() {
	return io.readInt("Choose an option.");
    }

    public void displayBannerDVDEditData(String data) {
	io.print("The current data of this dvd is: ");
	io.print(data);
    }

    public String getDataDVDEdit(DVD dvd, String propertyToEdit) {
	return io.readString("Enter a new " + propertyToEdit + ".");
    }

    public void displayBannerDVDEditSuccess() {
	io.print(" - - - - - - - - - -");
	io.print("|  Edit Successful  |");
	io.print(" - - - - - - - - - -");
    }

    //===================================================
    //		    Display single DVD
    //===================================================
    public void displayBannerDVD() {
	io.print("");
	io.print(" ..................... ");
	io.print(":                     :");
	io.print(":  View a DVD Option  :");
	io.print(":                     :");
	io.print(" ..................... ");
    }

    public String getDataDVDTitle() {
	return io.readString("Enter the title of the DVD you want to see.");
    }

    public void displayDVD(DVD dvd) {
	io.print("Title          - " + dvd.getTitle());
	io.print("Rlease Date    - " + dvd.getReleaseDate());
	io.print("MPAA Rating    - " + dvd.getMPAARating());
	io.print("Director Name  - " + dvd.getDrictorName());
	io.print("Studio         - " + dvd.getStudio());
	io.print("Note           - " + dvd.getUserNote());
	io.print("");
    }

    //===================================================
    //		    Display all DVDs
    //===================================================
    public void displayBannerDVDList() {
	io.print("");
	io.print(" ...................... ");
	io.print(":                      :");
	io.print(":  List of DVDs Option :");
	io.print(":                      :");
	io.print(" ...................... ");
    }

    public void displayDVDList(List<DVD> DVDs) {
	for (DVD dvd : DVDs) {
	    displayDVD(dvd);
	}
    }

    //===================================================
    //		    Main Menu Option Repeating
    //===================================================
    public void displayMessageRepeatOption(int userInput) {
	io.print("");
	switch (userInput) {
	    case 1:
		io.print("Would you like to add another DVD?");
		break;
	    case 2:
		io.print("Would you like to remove another DVD?");
		break;
	    case 3:
		io.print("Would you like to edit another DVD?");
		break;
	    case 4:
		io.print("Would you like to see another DVD?");
		break;
	    case 5:
		io.print("Would you like to see the DVD list again?");
		break;
	    default:
		displayMessageUnknownCommand();
		break;
	}
    }

    public String getCommandRepeatOption() {
	return io.readString("y/n");
    }

    //===================================================
    //		    Messages and Errors
    //===================================================
    public void displayMessageError(String errorMessage) {
	io.print(" --------- ");
	io.print("|  Error  |");
	io.print(" --------- ");
	io.print(errorMessage);
    }

    public void displayMessageUnknownCommand() {
	io.print("Unknown Command.");
    }

    public void displayMessageDVDNotFound() {
	io.print(" - - - - - - - - - - - - - - - - - -");
	io.print("|  Unable to find DVD by that title  |");
	io.print(" - - - - - - - - - - - - - - - - - -");
    }

    public void displayMessageNoDVDsInList() {
	io.print(" - - - - - - - - - - - - - - - -");
	io.print("|  The Library Contains no DVDs  |");
	io.print(" - - - - - - - - - - - - - - - -");
    }

    public void displayMessageDVDAlreadyExists(String title) {
	io.print("\"" + title + "\" already exsists in the library.");
    }

    public String getCommandDVDEditExisting(String title) {
	io.print("Would you like to edit \"" + title + "\"?");
	return io.readString("y/n");
    }

    //===================================================
    //		    Exit Message
    //===================================================
    public void displayBannerExit() {
	io.print("");
	io.print(" ::::::::::::::::::::::::::::::::::::::");
	io.print("::                                    ::");
	io.print("::  Thank you for using this program  ::");
	io.print("::                                    ::");
	io.print(" ::::::::::::::::::::::::::::::::::::::");
	io.print("Program Closed.");
    }
}
