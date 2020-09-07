/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The login controller class containing the login form GUI and the current user
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class LoginController {
    /**
     * Static variable to represent the LoginForm view Swing class
     */
    private static LoginForm login;
    /**
     * Static variable to represent the current user
     */
    private static User currentUser;
    
    /**
     * Constructor for login
     * @param login the login form view
     */
    public LoginController(LoginForm login){
        this.login = login;
    }
    
    /**
     * Finds if the username and password match
     * 
     * @return boolean true if they do match and false if they do not
     */
    public boolean correctLogin(){
        boolean correctLogin = false;
      
        String user = login.getUsername();
        String pass = login.getPassword();
        try{
            //creates statement
            //connects to database
            System.out.println("checking db");
             
            Connection myConn = DriverManager.getConnection(Main.URL);   
            System.out.println("Connected.");
                //creates statement
                Statement myStmt = myConn.createStatement();
                System.out.println("statement initiated");
                //SQL query
                ResultSet myRs = myStmt.executeQuery("select * from LISTOFUSERS ");
                System.out.println("query initiated");
                //process result set
                
                while (myRs.next()){
                    System.out.println("check");
                    if(user.equals(myRs.getString("USERNAME")) && pass.equals(myRs.getString("PASSWORD"))){
                        
                       correctLogin = true;
                        break;
                    }
                }
                myConn.close();
            
            
            
        } catch (Exception e) 
        {
        System.err.println(e.getMessage());
        System.out.println("oh no");
        } 
     return correctLogin;   
    }
    
    /**
     * Creates the a User object of the current user account
     * @return a User object representing the current user
     */
    public static User createUser(){
        currentUser = new User(login.getUsername(), login.getPassword());
        return currentUser;
    }
    
    /**
     * Method to generate the user's taskList by reading all the tasks stored under that username in the SQLite database
     * @return a TaskList of the user's tasks
     */
    public static TaskList generateList(){
        String user = login.getUsername();
        TaskList taskList = new TaskList();
        try{
            //creates statement
            //connects to database
            
            Connection myConn = DriverManager.getConnection(Main.URL);    
            System.out.println("Connected.");
                //creates statement
                Statement myStmt = myConn.createStatement();
              
                System.out.println("statement initiated");
                
                
                //SQL query
                ResultSet myRs = myStmt.executeQuery("SELECT * FROM TASKS WHERE Username = '" + user + "'");
                System.out.println("query initiated");
                //process result set
                
                while (myRs.next()){
                    System.out.println("check");
                    String name = myRs.getString("TASKNAME");
                    String subject = myRs.getString("SUBJECT");
                    
                    String tableDate = myRs.getString("DUEDATE");
                     
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tableDate);
                    
                    String myType = myRs.getString("TYPE");
                    int importance = myRs.getInt("IMPORTANCE");

                

                    //creates object depending on the specified Object tupe in the database

                    switch (myType){

                        case "Exam":
                            
                                taskList.addTask(new Exam(name, subject, date, importance));
                                break;
                            

                        case "Test":
                                                    
                                taskList.addTask(new Test(name, subject, date, importance));
                                 break;
                            
                        case "Quest":
                                                    
                                taskList.addTask(new Quest(name, subject, date, importance));
                                 break;
                            
                        case "Quiz":
                           
                                taskList.addTask(new Quiz(name, subject, date, importance));
                                 break;
                            
                        case "Culminating":
                            
                                taskList.addTask(new Culminating(name, subject, date, importance));
                                 break;
                            
                        case "Project":
                           
                                taskList.addTask(new Project(name, subject, date, importance));
                                 break;
                            
                        case "Assignment":
                            
                                taskList.addTask(new Assignment(name, subject, date, importance));
                                 break;
                            
                        case "Regular Homework":
                            
                                taskList.addTask(new RegularHomework(name, subject, date, importance));
                                 break;
                            

                    }
       
                }
            
            myConn.close();
            
        } catch (Exception e) 
        {
        System.err.println(e.getMessage());
        } 
     return taskList;   
    }
    
    /**
     * Method to generate an ArrayList of all study sessions saved by the user
     * @return an ArrayList of study sessions (Type Study).
     */
    public static ArrayList<Study> generateStudyList(){
        //gets username
        String user = login.getUsername();
        ArrayList<Study> studyList = new ArrayList<Study>();
        String url = Main.URL;
                //HomeScreen.databaseURL;
        Connection myConn = null;
        try{
         myConn = DriverManager.getConnection(url);
                System.out.println("Connected.");
                //creates statement
                Statement myStmt = myConn.createStatement();
                //myStmt.setString(6, user);
                System.out.println("statement initiated");
                
                
                //SQL query
                ResultSet myRs = myStmt.executeQuery("SELECT * FROM STUDIES WHERE Username = '" + user + "'");
                System.out.println("query initiated");
                
         while (myRs.next()){
                    //adds all user's studies from the database
                   
                    String subject = myRs.getString("SUBJECT");
                    int length = myRs.getInt("LENGTH");
                    String hour = myRs.getString("HOUR");
                    String minute = myRs.getString("MINUTE");
                    
                    String tableDate = myRs.getString("DATE");
                     
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tableDate);
                    studyList.add(new Study(subject, length, hour, minute, date));
         }
                    
         myConn.close();
        }catch (Exception e) 
        {
            System.out.println('o');
        System.err.println(e.getMessage());
        } 
        return studyList;
    }
    
    /**
     * Method to clear all study sessions that have already taken place
     */
    public void clearPastStudies(){
        String user = login.getUsername();
        String url = Main.URL;
                
        Connection myConn = null;
        try{
         myConn = DriverManager.getConnection(url);
                System.out.println("Connected.");
                //creates statement
                Statement myStmt = myConn.createStatement();
                //myStmt.setString(6, user);
                System.out.println("statement initiated");
                
                
                //SQL query
                ResultSet myRs = myStmt.executeQuery("SELECT * FROM STUDIES WHERE Username = '" + user + "'");
                System.out.println("query initiated");
                //statement to delete studies from database
                PreparedStatement st = myConn.prepareStatement("DELETE FROM STUDIES WHERE DATE = ? AND USERNAME = '"+user+"'");
                 while (myRs.next()){
                     System.out.println("in loop");
                     
                     String tableDate = myRs.getString("DATE");
                     
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tableDate);
                    //deletes the study from the database if it has passed
                    double diff = Study.timeDifference(date);
                    if(diff < 0){
                        System.out.println("in if");
                         st.setString(1,tableDate);
                         st.executeUpdate();
                       
                    }
                 } myRs.close();
                 myStmt.close();
                 myConn.close();
        } catch (Exception e){
            System.out.println("Did not clear past studies");
            System.err.println(e.getMessage());
        }
    }
    
  }
    
    

