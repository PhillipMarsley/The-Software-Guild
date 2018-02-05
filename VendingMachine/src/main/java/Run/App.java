/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Run;

import Controller.Controller;
import Dao.Dao;
import Dao.DaoImpl;
import Service.Service;
import Ui.UserIO;
import Ui.UserIOConsoleImpl;
import Ui.View;

/**
 *
 * @author Dan
 */
public class App {
    public static void main(String[] args) {
	UserIO io = new UserIOConsoleImpl();
	View view = new View(io);
	Dao dao = new DaoImpl();
	Service service = new Service(dao);
	Controller controller = new Controller(service, view);
	
	controller.run();
    }
}
