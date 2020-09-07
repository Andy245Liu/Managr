/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.drda.NetworkServerControl;



/**
 * Main class holding the database URL as a global constant (final) variable
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class Main {
  /**
   * Global constant variable holding database URL
   */
 //public static final String URL = "jdbc:sqlite:C:/Users/Andy/Documents/NetBeansProjects/ManagrData.db";   
// public static final String URL = "jdbc:sqlite:S:/IN/Seidel/4U - AM/Programming Concepts/=== CULMINATING ===/Managr3/ManagrData.db";   
 public static final String URL = "jdbc:sqlite:D:/Managr3.1/Managr3/ManagrData.db";

    /**
     * main method
     * @param args the command line arguments
     */
    public static void main(String[] args){
     try {
         Class.forName("org.sqlite.JDBC");
         
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
     }
        
        
         //displays the login window
         LoginForm form = new LoginForm();
         form.setVisible(true);
         
    
    }
}
