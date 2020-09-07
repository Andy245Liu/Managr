/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

/**
 * Controller class for the Calendrier GUI holding the current User and the Calendrier view class
 * implements the Sortable interface
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class CalendrierController implements Sortable {
    /**
     * private variable for a user
     */
    private User user;
    /**
     * private variable for a Calendar Swing GUI
     */
    private Calendrier view;
    
    
    
    /**
     * Constructs the Calendrier controller
     * @param user User object
     * @param view calendar(Calendrier) view UI
     */
    public CalendrierController(User user, Calendrier view){
         //initializes private variables through contsuctor
         this.view = view;
         this.user = user;
    }
   
    /**
     * Method to generate a list of all tasks on a selected date
     * @return an ArrayList of Tasks on the chosen date
     */
    public ArrayList<Task> getDailyTasks(){
        // creates new arrayList of Tasks
       ArrayList<Task> todaysTasks = new ArrayList<Task>();
       //TaskList totalTasks =  HomeScreen.myTasks;
       //retrieves the current user's tasklist
       TaskList totalTasks =  UserManagement.currentTaskList();
       //reads the selected date
       Date selected =  view.getSelectedDate();
       Calendar cal = Calendar.getInstance();
       cal.setTime(selected);
       int monthSelected = cal.get(Calendar.MONTH);
       int yearSelected = cal.get(Calendar.YEAR);
       int daySelected = cal.get(Calendar.DAY_OF_MONTH);
       
       //iterates through task list and adds the tasks due on the selected date to "todaysTasks"
       
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
         
       return todaysTasks;
    }
    /**
     * {@inheritDoc} - inherited method from the Sortable interface
     */
    @Override
    public ArrayList sortByPriority(){
        //sorts the current day's tasks by priority index and returns the list sorted by priority
        ArrayList<Task> todaysTasks = getDailyTasks();
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
     * Method to generate list of all study sessions scheduled for that day
     * @return an ArrayList of studies scheduled for the selected date
     */
    public ArrayList<Study> getDailyStudies(){
        //creates ArrayList of Studies
       ArrayList<Study> todaysStudies = new ArrayList<Study>();
      //gets the user's list of studies
       ArrayList<Study> totalStudies = UserManagement.currentStudiesList();
       Date selected = view.getSelectedDate();
       Calendar cal = Calendar.getInstance();
       cal.setTime(selected);
       int monthSelected = cal.get(Calendar.MONTH);
       int yearSelected = cal.get(Calendar.YEAR);
       int daySelected = cal.get(Calendar.DAY_OF_MONTH);
       //iterates through user's list of studies and adds the studies scheduled for the selected day to the ArrayList todaysStudies
       for(int i =0; i<totalStudies.size(); i++){
           System.out.println("GObble");
           Study thisStudy = (Study)totalStudies.get(i);
           Date studyDate = thisStudy.getStudyDate();
           cal.setTime(studyDate);
           int yr = cal.get(Calendar.YEAR);
           int mon = cal.get(Calendar.MONTH);
           int day = cal.get(Calendar.DAY_OF_MONTH);
           if(yr == yearSelected && mon == monthSelected && day == daySelected){
                System.out.println("yes");
               todaysStudies.add(thisStudy);
           }
           
       }
              
         
       return todaysStudies;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList sortByDate(){
        //Sorts the study periods scheduled for the selected day by their scheduled times, from earliest to latest in the day
        ArrayList<Study> todaysStudies = getDailyStudies();
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
    
    /**
     * Adds a newly created study to the study ArrayList
     */
    public void addStudy(){
        //reads user-entered study attributes and creates a new Study object based on those attributes
         Date selected =  view.getSelectedDate();
         String subject = view.getEnteredStudySubject();
         int length = view.getSelectedStudyLength();
         String hour = view.getSelectedStudyHourStart();
         String min = view.getSelectedStudyMinuteStart();
        
         ArrayList<Study> studies = UserManagement.currentStudiesList();
         
         Study newStudy = new Study(subject, length, hour, min, selected);
         //adds the new study to the user's Study list
         studies.add(newStudy);
         
    }
    
    /**
     * Method that stores study data by writing it to an SQLite database
     */
    public void storeStudy(){
        //reads the user-etered study attributes and stores them in the database
        String username = user.getUsername();
         Date selected =  view.getSelectedDate();
         String subject = view.getEnteredStudySubject();
         int length = view.getSelectedStudyLength();
         String hour = view.getSelectedStudyHourStart();
         String min = view.getSelectedStudyMinuteStart();
         
          java.text.SimpleDateFormat sdf = 
        new java.text.SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(selected);
        
        //connecting to database
         Connection conn = null;
        try {
           
            // db parameters
            String url = Main.URL;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement myStmt = conn.prepareStatement("insert into STUDIES(USERNAME, SUBJECT, LENGTH, HOUR, MINUTE, DATE)values(?,?,?,?,?,?)");
                
                System.out.println("query initiated");
                
                
                //stores the Study attributes in the database
                    System.out.println("check");
                        myStmt.setString(1,username);
                        myStmt.setString(2,subject);
                        myStmt.setInt(3,length);
                        myStmt.setString(4,hour);
                        myStmt.setString(5,min);
                        myStmt.setString(6,date);


                        
                        
                        int a = myStmt.executeUpdate();
                
                        if(a>0){
                         System.out.println("Row Update");
                        }
                        
                        conn.close();
                      
                    
                
            
               
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
            
        }
    
    /**
     * Method to indicate which dates on the calendar have events on them. 
     * If the date has a Task due on it, it is coloured blue. If it has a study session scheduled, it is coloured yellow. If there are both Tasks due and study sessions scheduled on that date, then it is coloured red.
     * @throws ParseException parsing simple date formats to dates
     */
    public void showEventDates() throws ParseException{
        //get user's current Task List
        TaskList taskList = UserManagement.currentTaskList();
        //get user's current study list
         ArrayList<Study> studiesList = UserManagement.currentStudiesList();
         //create lists to store dates with studies, tasks, and both studies and tasks
        ArrayList<Calendar> taskDates = new ArrayList<Calendar>();
        ArrayList<Calendar> studyDates = new ArrayList<Calendar>();
        ArrayList<Calendar> bothDates = new ArrayList<Calendar>();
         java.text.SimpleDateFormat sdf = 
        new java.text.SimpleDateFormat("yyyy-MM-dd");
         //iterates through task list, stores all the different due dates into the taskDates ArrayList
        for(int i = 0; i<taskList.length(); i++){
         
            Date rawDate = ((Task)taskList.getTask(i)).getDueDate();
            String strDate = sdf.format(rawDate);
            Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(finalDate);
            if(!taskDates.contains(calDate)){
          
                
                taskDates.add(calDate);
            }
        }
        //iterates through the Study list due dates and stores the date into bothDates ArrayList if it is also present in the taskDates ArrayList. Stores it in the studyDates ArrayList otherwise.
          for(int i = 0; i<studiesList.size(); i++){
            
            Date rawDate = ((Study)studiesList.get(i)).getStudyDate();
            String strDate = sdf.format(rawDate);
            Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(finalDate);
            if(taskDates.contains(calDate)){
                bothDates.add(calDate);
            }
            else if(!studyDates.contains(calDate)){
               
                
                studyDates.add(calDate);
            }
            
        }
        JCalendar calendar = view.getCal();
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendar.getDate());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        //gets the date panels of the calendar
        JPanel jpanel = calendar.getDayChooser().getDayPanel();
        Component component[] = jpanel.getComponents();

        //iterates through the days of the month. If the day has only a task scheduled on it, colour it blue
        for(int i = 0; i < taskDates.size(); i++) {
            //selected month and year on JCalendar
           
            if(month == (taskDates.get(i)).get(Calendar.MONTH)&& year == taskDates.get(i).get(Calendar.YEAR))
            {
                
                    cal.set(Calendar.DAY_OF_MONTH,1);
                  int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
                //this value will differ from each month due to first days of each month
               
                 component[ taskDates.get(i).get(Calendar.DAY_OF_MONTH)+offset+6].setBackground(Color.blue); 
            }
        }
        //iterates through the days of the month. Colours days with only studies scheduled on it yellow
        for(int i = 0; i < studyDates.size(); i++) {
            //selected month and year on JCalendar
            
            if(month == (studyDates.get(i)).get(Calendar.MONTH)&& year == studyDates.get(i).get(Calendar.YEAR))
            {
               
                    cal.set(Calendar.DAY_OF_MONTH,1);
                  int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
                //this value will differ from each month due to first days of each month
               
                 component[ studyDates.get(i).get(Calendar.DAY_OF_MONTH)+offset+6].setBackground(Color.yellow); 
            }
        }
        //iterates through the days of the month. Colours days with Tasks due and studies scheduled on it red
          for(int i = 0; i < bothDates.size(); i++) {
            //selected month and year on JCalendar
            
            if(month == (bothDates.get(i)).get(Calendar.MONTH)&& year == bothDates.get(i).get(Calendar.YEAR))
            {
                
                    cal.set(Calendar.DAY_OF_MONTH,1);
                  int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
                //this value will differ from each month due to first days of each month
               
                 component[ bothDates.get(i).get(Calendar.DAY_OF_MONTH)+offset+6].setBackground(Color.red); 
            }
        }
    }
    
        
    }
    
    

