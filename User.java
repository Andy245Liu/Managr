/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managr;
//import java.util.ArrayList;

/**
 * Class of Users holding a username, password, and TaskList
 * 
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class User {
    /**
     * private variable holding a String username
     */
    private String username;
    /**
     * private variable holding a String password
     */
    private String password;
    /**
     * private variable holding a TaskList list of tasks
     */
    private TaskList tasks;
    
    /**
     * Default constructor for User
     */
    public User(){
      
        
    }
   
   
    /**
     * Constructor initializing the instance variables username and password
     * @param username String username
     * @param password String password
     */
   
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.tasks = new TaskList();
        
    }
    /**
     * To access the user's task list
     * @return the user's task list
     */
    public TaskList getTaskList(){
        return tasks;
    }
    /**
     * Accesses the name of the user
     * @return a String username
     */
    public String getUsername(){
        return username;
    }
    
    
}
