/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.ArrayList;

/**
 * Class to Manage User properties containing User, TaskList, and an ArrrayList of Studies
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class UserManagement {
    /**
     * private static User variable to hold current User object
     */
    private static User myUser  = LoginController.createUser();
    /**
     * private static TaskList variable to hold current user's TaskList
     */
    private static TaskList myTasks = LoginController.generateList();
    /**
     * private static ArrayList<Study> variable to hold current user's list of Study periods
     */
    private static ArrayList<Study> myStudies = LoginController.generateStudyList();
    /**
     * Default constructor for the user management class
     */
    public UserManagement(){
    
    }
    /**
     * Method to access the user
     * @return an object User representing the current user
     */
    public static User getUser(){
        return myUser;
    }
    /**
     * Method to access the Task List
     * @return a TaskList representing the current user's TaskList
     */
    public static TaskList currentTaskList(){
        return myTasks;
    }
    /**
     * Method to access the studies list
     * @return an ArrayList of Study object types representing the current user's list of study sessions
     */
    public static ArrayList<Study> currentStudiesList(){
        return myStudies;
    }
   
}
