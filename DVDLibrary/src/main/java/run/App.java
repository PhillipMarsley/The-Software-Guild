/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import controller.Controller;
import dao.Dao;
import dao.DaoImpl;
import ui.UserIO;
import ui.UserIOConsoleImpl;
import ui.View;

/**
 *
 * @author Dan
 */
public class App {
    public static void main(String[] args) {
	UserIO io = new UserIOConsoleImpl();
	View view = new View(io);
	Dao dao = new DaoImpl();
	Controller controller = new Controller(view, dao);
	
	controller.run();
    }
}
