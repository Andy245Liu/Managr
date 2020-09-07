/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

/**
 * HomeScreen controller class - controller for the HomeScreen Swing GUI
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class HomeScreenController {
    
    /**
     * Empty default constructor for the HomeScreenController class
     */
    public HomeScreenController(){
       
    }
    /**
     * Static Method to generate a list of tasks due today
     * @return an ArrayList of Tasks due today sorted by priority rating
     */
    public static ArrayList<Task> getTodaysTasks(){
        //get today's Tasks
        ArrayList<Task> todaysTasks = new ArrayList<Task>();
       
        TaskList totalTasks =  UserManagement.currentTaskList();
       Date today =  new Date();
       Calendar cal = Calendar.getInstance();
       cal.setTime(today);
       int monthSelected = cal.get(Calendar.MONTH);
       int yearSelected = cal.get(Calendar.YEAR);
       int daySelected = cal.get(Calendar.DAY_OF_MONTH);
       //finds all tasks due today
       for(int i = 0; i < totalTasks.length(); i++){
           
           Task thisTask = (Task)totalTasks.getTask(i);
           Date taskDate = thisTask.getDueDate();
           cal.setTime(taskDate);
           int taskYear = cal.get(Calendar.YEAR);
           int taskMonth = cal.get(Calendar.MONTH);
           int taskDay = cal.get(Calendar.DAY_OF_MONTH);
    
           
           if(taskYear == yearSelected && taskMonth == monthSelected && taskDay == daySelected){
               
               todaysTasks.add(thisTask);
           }
       }
        //sort today's Tasks in decreasing order based on priority rating (combination of the Task's weighted importance and due date proximity)
          Collections.sort(todaysTasks,
                 new Comparator<Task>()
                 {
                     public int compare(Task a, Task b)
                             
                     {
                        Double x = a.priorityRating();
                        Double y = b.priorityRating();
                        return y.compareTo(x);
                     }        
                 });
       return todaysTasks;
    }
    
    /**
     * Static method to get all study sessions scheduled for today
     * @return an ArrayList of study sessions scheduled for today sorted in order of scheduled time
     */
    public static ArrayList<Study> getTodaysStudies(){
        //gets user's list of Study sessions
        ArrayList<Study> todaysStudies = new ArrayList<Study>();
      
        ArrayList<Study> totalStudies = UserManagement.currentStudiesList();
       Date today = new Date();
       Calendar cal = Calendar.getInstance();
       cal.setTime(today);
       int monthSelected = cal.get(Calendar.MONTH);
       int yearSelected = cal.get(Calendar.YEAR);
       int daySelected = cal.get(Calendar.DAY_OF_MONTH);
       //finds all study sessions scheduled for today
       for(int i =0; i<totalStudies.size(); i++){
          
           Study thisStudy = (Study)totalStudies.get(i);
           Date studyDate = thisStudy.getStudyDate();
           cal.setTime(studyDate);
           int yr = cal.get(Calendar.YEAR);
           int mon = cal.get(Calendar.MONTH);
           int day = cal.get(Calendar.DAY_OF_MONTH);
           if(yr == yearSelected && mon == monthSelected && day == daySelected){
                
               todaysStudies.add(thisStudy);
           }
           
       }
       //sorts today's study sessions from those scheduled earliest in the day to those scheduled latest in the day
               Collections.sort(todaysStudies,
                 new Comparator<Study>()
                 {
                     public int compare(Study a, Study b)
                             
                     {
                        
                        return b.compareTo(a);
                     }        
                 });
         
       return todaysStudies;
    }
    
    
}
