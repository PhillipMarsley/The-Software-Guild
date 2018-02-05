/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

/**
 *
 * @author Dan
 */
public class Colors {

    /*
    
    //===========================================================
    //
    //         Supported
    //
    //===========================================================
    Black: \u001b[30m
    Red: \u001b[31m
    Green: \u001b[32m
    Yellow: \u001b[33m
    Blue: \u001b[34m
    Magenta: \u001b[35m
    Cyan: \u001b[36m
    White: \u001b[37m
    
    Bright Black: \u001b[30;1m
    Bright Red: \u001b[31;1m
    Bright Green: \u001b[32;1m
    Bright Yellow: \u001b[33;1m
    Bright Blue: \u001b[34;1m
    Bright Magenta: \u001b[35;1m
    Bright Cyan: \u001b[36;1m
    Bright White: \u001b[37;1m

    Reset: \u001b[0m
    
    //===========================================================
    //
    //      Format for 256-color extended color set
    //
    //              \u001b[38;5;${ID}m
    //
    //           NOT Supported by NetBeans
    //
    //===========================================================
    
    //===========================================================
    //
    //              256 - color
    //
    //         Text & Background (PYTHON)
    //
    //===========================================================
    TEXT
    ----------------------------
    for i in range(0, 16):
	for j in range(0, 16):
	    code = str(i * 16 + j)
	    sys.stdout.write(u"\u001b[38;5;" + code + "m " + code.ljust(4))
	print u"\u001b[0m"


    BACKGROUND
    ---------------------------
    import sys
    for i in range(0, 16):
	for j in range(0, 16):
	    code = str(i * 16 + j)
	    sys.stdout.write(u"\u001b[48;5;" + code + "m " + code.ljust(4))
	print u"\u001b[0m"
    
    
    //===========================================================
    //
    //              256 - color
    //
    //         Text & Background (JAVA)
    //
    //===========================================================
    TEXT
    ----------------------------
    public static String padRight(String s, int n) {
	return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
	return String.format("%1$" + n + "s", s);
    }

    public static void main(String[] args) {
	for (int i = 0; i < 16; i++) {
	    for (int j = 0; j < 16; j++) {
		String code = (i * 16 + j) + "";
		
		System.out.print("\u001b[38;5;" + code + "m " + padRight(code, 4) + "\u001b[0m");
	    }
	    System.out.println("");
	}
    
	for (int i = 0; i < 16; i++) {
	    for (int j = 0; j < 16; j++) {
		String code = (i * 16 + j) + "";
		
		System.out.print("\u001b[48;5;" + code + "m " + padRight(code, 4) + "\u001b[0m");
	    }
	    System.out.println("");
	}
    }
    
    ==========================================================    
     */
    
    public static String padRight(String s, int n) {
	return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
	return String.format("%1$" + n + "s", s);
    }

    public static void main(String[] args) {

	System.out.println("::::::::::");
	System.out.println(":: TEXT ::");
	System.out.println("::::::::::");
	System.out.println("\033[30m Hello" + "\033[31m Hello" + "\033[32m Hello" + "\033[33m Hello" + "\033[0m");
	System.out.println("\033[30;1m Hello" + "\033[31;1m Hello" + "\033[32;1m Hello" + "\033[33;1m Hello" + "\033[0m");

	System.out.println("\033[34m Hello" + "\033[35m Hello" + "\033[36m Hello" + "\033[37m Hello" + "\033[0m");
	System.out.println("\033[34;1m Hello" + "\033[35;1m Hello" + "\033[36;1m Hello" + "\033[37;1m Hello" + "\033[0m");

	System.out.println("");
	System.out.println("::::::::::::::::");
	System.out.println(":: BACKGROUND ::");
	System.out.println("::::::::::::::::");
	System.out.println("\033[41m Hello" + "\033[42m Hello" + "\033[43m Hello" + "\033[44m Hello" + "\033[0m");
	System.out.println("\033[45m Hello" + "\033[46m Hello" + "\033[47m Hello" + "\033[48m Hello" + "\033[0m");
    }
}
