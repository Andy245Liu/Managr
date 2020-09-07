/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Class for the TaskArea controller holding the current User and the Task Area view Swing GUI
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class TaskAreaController implements Sortable {
    /**
     * private final User variable holding the current user
     */
    private final User user;
    /**
     * private final TaskArea variable holding the Swing Task Area view Swing GUI
     */
    private final TasksArea view;
    
    
    
    /**
     * Constructs a controller
     * @param user User object
     * @param view view UI
     */
    public TaskAreaController(User user, TasksArea view){
         this.user = user;
         this.view = view;
    }
    
    /**
     * Adds Task to TaskList
     */

    public void addTask(){
      //gets current user's Task list
       TaskList taskList = UserManagement.currentTaskList();
       //reads user-inputted Task attributes
       String name = view.getTaskName();
       String subject = view.getTaskSubject();
       Date date = view.getTaskDueDate();
       int dueHour = view.getTaskDueHour();
       int dueMin = view.getTaskDueMin();
       boolean isDefaultButtonSelected = view.isDefaultButtonSelected();
       String myType = view.getTaskType();
       int importance = view.getTaskImportance();
       //formats entered date        
       Calendar calendarDate = Calendar.getInstance();
       calendarDate.setTime(date);
       calendarDate.set(Calendar.HOUR_OF_DAY, dueHour);
       calendarDate.set(Calendar.MINUTE, dueMin);
       date = calendarDate.getTime();
       
       //switch statement to create object type based on the user-entered object type
       
       switch (myType){
       
           case "Exam":
               if (isDefaultButtonSelected){
                    taskList.addTask(new Exam(name, subject, date, 0));
                   ((Exam) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Exam(name, subject, date, importance));
                    break;
               }
              
           case "Test":
              if (isDefaultButtonSelected){
                    taskList.addTask(new Test(name, subject, date, 0));
                   ((Test) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Test(name, subject, date, importance));
                    break;
               }
           case "Quest":
               if (isDefaultButtonSelected){
                    taskList.addTask(new Quest(name, subject, date, 0));
                   ((Quest) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Quest(name, subject, date, importance));
                    break;
               }
           case "Quiz":
               if (isDefaultButtonSelected){
                    taskList.addTask(new Quiz(name, subject, date, 0));
                   ((Quiz) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Quiz(name, subject, date, importance));
                    break;
               }
           case "Culminating":
               if (isDefaultButtonSelected){
                    taskList.addTask(new Culminating(name, subject, date, 0));
                   ((Culminating) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Culminating(name, subject, date, importance));
                    break;
               }
           case "Project":
               if (isDefaultButtonSelected){
                    taskList.addTask(new Project(name, subject, date, 0));
                   ((Project) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Project(name, subject, date, importance));
                    break;
               }
           case "Assignment":
               if (isDefaultButtonSelected){
                    taskList.addTask(new Assignment(name, subject, date, 0));
                   ((Assignment) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new Assignment(name, subject, date, importance));
                    break;
               }
           case "Regular Homework":
               if (isDefaultButtonSelected){
                    taskList.addTask(new RegularHomework(name, subject, date, 0));
                   ((RegularHomework) taskList.getTask(taskList.length()-1)).setDefaultImportance();
                    break;
               }else{
                   taskList.addTask(new RegularHomework(name, subject, date, importance));
                    break;
               }
       
       }
       
       
       
    }
    

    /**
     * To access the task name
     * @return a String of the task name
     */
    public String getTaskName(){
        return view.getTaskName();
    }
    /**
     * To access the due date of the task
     * @return a Date object representing the due date
     */
    public Date getTaskDueDate(){
        //reads user-inputted date info
       Date date = view.getTaskDueDate();
       int dueHour = view.getTaskDueHour();
       int dueMin = view.getTaskDueMin();
       
       //creates date based on entered info
       Calendar calendarDate = Calendar.getInstance();
       calendarDate.setTime(date);
       calendarDate.set(Calendar.HOUR_OF_DAY, dueHour);
       calendarDate.set(Calendar.MINUTE, dueMin);
       date = calendarDate.getTime();
        return date;
    }
    /**
     * To access the added task priority rating
     * @return a double showing the priority rating
     */
    public double getTaskPriorityRating(){
        TaskList myList = user.getTaskList();
        return ((Task)myList.getTask(myList.length()-1)).priorityRating();
    }
    /**
     * Method to see if the inputted task name already exists among the user's other tasks
     * 
     * @param name takes in a string representing the name of the task
     * @return a boolean true if the name exists, or false if it does not.
     */
    public boolean nameExist(String name){

        //gets current user's TaskList
        TaskList myList = UserManagement.currentTaskList();
        boolean nameExists = false;
       
       //iterates through task list and finds if the new nae matches any existing names
        for (int i = 0; i < myList.length(); i++){
            
            if (name.equals(((Task)myList.getTask(i)).getName())){
                
                nameExists = true;
                break;
                } 
           
            }
       
        return nameExists;
    }
    
    /**
     * Stores the task to the database when the user adds it
     */
    public void storeTask(){
        //gets current user
        User currentUser = LoginController.createUser();
        //gets username
        String user = currentUser.getUsername();
        
       String name = view.getTaskName();
       String subject = view.getTaskSubject();
       Date date = view.getTaskDueDate();
       int dueHour = view.getTaskDueHour();
       int dueMin = view.getTaskDueMin();
       boolean isDefaultButtonSelected = view.isDefaultButtonSelected();
       String myType = view.getTaskType();
       int importance = view.getTaskImportance();
       //if default importance button is selected, then the default importance for each task type is stored into the database
       if(isDefaultButtonSelected){
        
        switch(myType){
            case "Exam":
                importance = 10;
                break;
            case "Test":
                importance = 8;
                break;
            case "Quest":
                importance = 5;
                break;
            case "Quiz":
                importance = 3;
                break;
            case "Culminating":
                importance = 10;
                break;
            case "Projet":
                importance = 8;
                break;
            case "Assignment":
                importance = 6;
                break;
            case "Regular Homework":
                importance = 2;
                break;
        }
      
        
       
        //create date       
       Calendar calendarDate = Calendar.getInstance();
       calendarDate.setTime(date);
       calendarDate.set(Calendar.HOUR_OF_DAY, dueHour);
       calendarDate.set(Calendar.MINUTE, dueMin);
       date = calendarDate.getTime();
       
       java.text.SimpleDateFormat sdf = 
        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String thisTime = sdf.format(date);
        //store Task attributes to database
        try{
            //creates statement
            //connects to database
            
            Connection myConn = DriverManager.getConnection(Main.URL);   
            //creates statement
                PreparedStatement myStmt = myConn.prepareStatement("insert into TASKS(TASKNAME, TYPE, SUBJECT, DUEDATE, IMPORTANCE, USERNAME)values(?,?,?,?,?,?)");
                
                System.out.println("query initiated");
                //process result set
                
               
                    System.out.println("check");
                        myStmt.setString(1,name);
                        myStmt.setString(2,myType);
                        myStmt.setString(3,subject);
                        myStmt.setString(4,thisTime);
                        myStmt.setInt(5,importance);
                        myStmt.setString(6,user);


                        
                        
                        int a = myStmt.executeUpdate();
                
                        if(a>0){
                         System.out.println("Row Update");
                        }
                      
                    myConn.close();
                
            
               
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    }
    
    /**
     * Prints the TaskList to the screen
     * @param list the TaskList of tasks
     * @return the String printed tasks
     */
    public static String printList(TaskList list){
        //prints Task info
          String display = "";
          for (int i = 0; i < list.length(); i++){
              //is Task past due date, print ALREADY PAST DUE DATE in front
              if(((Task)list.getTask(i)).urgency() < 0){
                  display = display +"ALREADY PAST DUE DATE "+"Task name: " + ((Task)list.getTask(i)).getName() + "\t Due Date: " + ((Task)list.getTask(i)).getDueDate() + "\t Task Type: " + ((Task)list.getTask(i)).getTaskType() +"\n";
              }
              else{
                 System.out.println(((Task)list.getTask(i)).getName());
                 display = display + "Task name: " + ((Task)list.getTask(i)).getName() + "\t Due Date: " + ((Task)list.getTask(i)).getDueDate() + "\t Task Type: " + ((Task)list.getTask(i)).getTaskType() +"\n";
              }  
          }
          return display;
    }
    /**
     * {@inheritDoc}  
     */
   @Override
  public ArrayList sortByPriority(){
    
     //sort the Task List by the priority index (weighted importance * 1/time until due) decreasing order
     TaskList taskList = UserManagement.currentTaskList();
    
    ArrayList<Task> list = taskList.getTaskList();
    
    
    Collections.sort(list,
                 new Comparator<Task>()
                 {
                     public int compare(Task a, Task b)
                     {
                         Double anum = new Double(a.priorityRating());
                         Double bnum = new Double(b.priorityRating());
                         return bnum.compareTo(anum);
                     }        
                 });
    
    return list;
    
    
}
  /**
   * Bubble Sorts TaskList by task priority rating
   * @param myList the current TaskList of tasks
   * @return the sorted TaskList by task priority rating using bubble sort
   */
 /* public TaskList bubbleSort(TaskList myList){
   
   //int i = 1;
   int length = myList.length();
   boolean hasSwapped;
   do{
     hasSwapped = false;
     for(int i = 1; i<length; i++){
        if(myList.getTask(i).priorityRating() > myList.getTask(i-1).priorityRating()){
          myList.swapTasks(i, i-1);
          hasSwapped = true;

        }

     }

   }while(hasSwapped == true);
  return myList;
  }*/
  /**
   * {@inheritDoc}
   */
  @Override
    public ArrayList sortByDate(){
    //sorts Tasks in order from tasks closest to deadline to tasks farthest from deadline (not considering their weighted importance)
     
     TaskList taskList = UserManagement.currentTaskList();
    
    ArrayList<Task> list = taskList.getTaskList();
    
    
    Collections.sort(list,
                 new Comparator<Task>()
                 {
                     public int compare(Task a, Task b)
                     {
                         Double anum = new Double(a.urgency());
                         Double bnum = new Double(b.urgency());
                         return bnum.compareTo(anum);
                     }        
                 });
    
    return list;
    
    
}
  /**
   * Insertion sort Tasks in taskList by due date
   * @param myList the TaskList of tasks
   * @return the sorted TaskList by due date
   */
    
  /*public TaskList insertionSort(TaskList myList){
    int length = myList.length();

    for(int i = 1; i<length ; i++){

      double urgency = ((Task) myList.getTask(i)).urgency();
      Task object = (Task) myList.getTask(i);
      int j = i-1;

      while(j >=0 && ((Task) myList.getTask(j)).urgency() < urgency){
        myList.setTask(j+1, (Task)myList.getTask(j));
        j -= 1;
      }
      myList.setTask(j+1,object);

    }
  return myList;

  }*/

/**
 * Reads a list of doubles from a .txt file
 * @return an ArrayList of Doubles
 */
  /*
public static ArrayList createList(){
   ArrayList<Double> weightings = new ArrayList<>();
   File file = new File("Doubles.txt");
   BufferedReader reader = null;

try {
    reader = new BufferedReader(new FileReader(file));
    String text = null;

    while ((text = reader.readLine()) != null) {
        weightings.add(Double.parseDouble(text));
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        if (reader != null) {
            reader.close();
        }
    } catch (IOException e) {
    }
} 
return weightings;
}
  */

/**
 * Bubble sorts the file read list of doubles
 */
  /*
 public static void bubble(){
    ArrayList<Double> list = createList();
    int length = list.size();
    boolean hasSwapped;
    System.out.println("start");
   do{
     hasSwapped = false;
     for(int i = 1; i<length; i++){
        if(list.get(i) > list.get(i-1)){
           Double first = list.get(i);
           Double second = list.get(i-1);
           list.set(i, second);
           list.set(i-1, first);
           hasSwapped = true;

        }

     }

   }while(hasSwapped == true);
       System.out.println("end");

  
   for(int i = 0; i < length; i++){
       System.out.println(list.get(i).doubleValue());
   }
     
 } 
 */
 /**
  * Insertion sort for the file read list of doubles
  */
  /*
  public static void insertion(){
    ArrayList<Double> list = createList();
    System.out.println("i");
    int length = list.size();
    
    for(int i = 1; i<length ; i++){
      System.out.println("for entered");
      double urgency = list.get(i);
    
      int j = i-1;

      while(j >=0 && list.get(j) < urgency){
          //System.out.println("While entered");
         list.set(j+1, list.get(j));
        //myList.setTask(j+1, (Task)myList.getTask(j));
        j -= 1;
      }
      list.set(j+1,urgency);

    }
   for(int i = 0; i < length; i++){
       System.out.println(list.get(i).doubleValue());
   }
     
 }
  */
  /**
   * Built in .sort() to sort the file read list of doubles
   */
  /*
  public static void regularSort(){
     ArrayList<Double> list = createList();
     int length = list.size();
     Collections.sort(list);
     for(int i = 0; i < length; i++){
       System.out.println(list.get(i).doubleValue());
   }
  }
  */
  /*
  .Sort() quickest, roughly 3 mins total build time, runs on O(n log n) complexity
  - uses mergesort
  Insertion sort and bubble sort took over an hour, complexity of O(n^2)
  */
  
  
    /**
     * Deletes tasks from a database
     */
    public void deleteTask(){
        //get Task name
        String name = view.getDeleteTaskName();
         
        
        String username = (UserManagement.getUser()).getUsername();
        //deletes task with the specified name from database
         try{
            //creates statement
            //connects to database
             
             Connection myConn = DriverManager.getConnection(Main.URL);  
             //creates statement
                PreparedStatement myStmt = myConn.prepareStatement("DELETE FROM TASKS WHERE Taskname = ? AND USERNAME = '"+username+"'");
               
                System.out.println("query initiated");
                //process result set
                
                
                
                myStmt.setString(1,name);
 
                        
                myStmt.executeUpdate();
             
                 myConn.close();     
                    
                
            
               
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
     
    }
    
    /**
     * Method to clear all past tasks after a certain time period number of days (specified by a double)
     * @param a a double representing the number of days after which all tasks beyond that date will be cleared
     * @throws ParseException parsing simple date format to date
     */
    public void clearPastTasks(double a) throws ParseException{
       
        //gets currentUser's task list
         
        TaskList list = UserManagement.currentTaskList();
        String url = Main.URL;
        
         String username = (UserManagement.getUser()).getUsername();
        if (a != 0){
            int i = 0;
            while(i < list.length()){
                Task current = (Task)list.getTask(i);
                String name = current.getName();
                double diff = current.dayDifference();
                //deletes all tasks beyond a certain number of days past due from the database
                if (diff < -a){
                    Connection myConn = null;
                    try{
                        
                        myConn = DriverManager.getConnection(url);
                        System.out.println("Connected.");
                        //creates statement
                        


                        //SQL query
                        
                        PreparedStatement st = myConn.prepareStatement("DELETE FROM TASKS WHERE TASKNAME = ? AND USERNAME = '"+username+"'");
                         
                        st.setString(1,name);
                        st.executeUpdate();
                        st.close();
                        System.out.println("executed deletion");
                         
                         myConn.close();
                    } catch (Exception e){
                    System.out.println("Did not clear past tasks");
                    System.err.println(e.getMessage());
                    }
                    list.deleteTask(i);
                } else{
                    i++;
                }
            }
           //if the double a == 0, then delete all the tasks that have passed their due date from the database
        }else{
            int i = 0;
            while(i < list.length()){
                Task current = (Task)list.getTask(i);
               String name = current.getName();
                double diff = current.urgency();
                if (diff < 0){
                    Connection myConn = null;
                    try{
                       
                        myConn = DriverManager.getConnection(url);
                        System.out.println("Connected.");
                        


                        //SQL query
                       
                        PreparedStatement st = myConn.prepareStatement("DELETE FROM TASKS WHERE TASKNAME = ? AND USERNAME = '"+username+"'");
                     
                        st.setString(1,name);
                        st.executeUpdate();
                         st.close();
                         myConn.close();
                    } catch (Exception e){
                    System.out.println("Did not clear past Tasks");
                    System.err.println(e.getMessage());
                    }
                    list.deleteTask(i);
                } else{
                    i++;
                }
        
        }
    }
   }
/**
 * Linear search to find and remove a task by name
 */
public void linearSearch(){
       
        //gets current TaskList
        TaskList taskList = UserManagement.currentTaskList();
       
       String name = view.getDeleteTaskName();
       //iterates through list until the task with the specified name is found. If found, it is deleted from the list and database. If not, an error message shows up.
        boolean init = false;
        for (int i = 0; i < taskList.length(); i++){
            if (((Task)taskList.getTask(i)).getName().equals(name)){
                taskList.deleteTask(i);
                deleteTask();
                init = true;
                break;
            }
        }
        if (init == false){
            JOptionPane.showMessageDialog(view.getDeleteTaskDialogue(), "A Task named: \"" + name + "\" is not found in your task list. Please check your spelling (it is case-sensistive!).", "Error", JOptionPane.ERROR_MESSAGE);
        }
}

/**
 * Alphabetically sorts ArrayList of tasks by their names
 * @return a sorted ArrayList of tasks
 */

public ArrayList<Task> sortNames(){
    
     
     TaskList taskList = UserManagement.currentTaskList();
    
    ArrayList<Task> list = taskList.getTaskList();
    
    
    Collections.sort(list,
                 new Comparator<Task>()
                 {
                     public int compare(Task a, Task b)
                     {
                         return a.toString().compareToIgnoreCase(b.toString());
                     }        
                 });
    
    return list;
    
    
}

/**
 * gets the task at a specified position in the ArrayList
 * @param x an integer representing the position of the task
 * @param list an ArrayList of tasks
 * @return the Task at the specified index
 */
public Task getSearchedTask(int x, ArrayList<Task> list){
    return (Task)list.get(x);
}

/**
 * binary search to find the position of the desired task in the ArrayList
 *@return the Task found
 */
public Task binarySearch(){
    ArrayList<Task> myList = this.sortNames();
    String name = view.getSearchedTaskName();
    
    //binary search algorithm to find the Task with the specified name. Returns it if found. Displays error message if not found.
    int i =0;
    int right = myList.size() - 1;
    while(i<=right){
        int m = i + (right-i)/2;
        if (myList.get(m).getName().compareToIgnoreCase(name) == 0){
            return getSearchedTask(m, myList);
            
        } 
        if (myList.get(m).getName().compareToIgnoreCase(name) < 0){
            i = m + 1;
        }
        else{
            right = m - 1;
        }
    }
    
    JOptionPane.showMessageDialog(view.getSearchTaskDialogue(), "A Task named: \"" + name + "\" is not found in your task list. Please check your spelling (it is case-sensistive!).", "Error", JOptionPane.ERROR_MESSAGE);
    return null;
}


/**
 * Linear search algorithm for a task
 * @return the task the user searched for
 */

/*
public Task linear(){
       TaskList taskList = HomeScreen.myTasks;
       ArrayList<Task> myList = taskList.getTaskList();
       String name = view.getSearchedTaskName();
       
        boolean init = false;
        for (int i = 0; i < taskList.length(); i++){
            if (((Task)myList.get(i)).getName().equals(name)){
                return (Task) myList.get(i);
                
            }
        }
        if (init == false){
            JOptionPane.showMessageDialog(view, "A Task named: \"" + name + "\" is not found in your task list. Please check your spelling (it is case-sensistive!).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
}
*/

/**
 * Testing binary search from a 50 MB file
 * @param x the desired double value
 * @return the double value (if found in the list)
 */
/*
public double binaryTest(double x){
    ArrayList<Double> myList = createList();
    Collections.sort(myList);
    int i =0;
    int right = myList.size() - 1;
    while(i<=right){
        int m = i + (right-i)/2;
        if (myList.get(m)== x){
            return x;
            
        } 
        if (myList.get(m)< x){
            i = m + 1;
        }
        else{
            right = m - 1;
        }
    }
    
    JOptionPane.showMessageDialog(view, "A Task named: \"" + x + "\" is not found in your task list. Please check your spelling (it is case-sensistive!).", "Error", JOptionPane.ERROR_MESSAGE);
    return 0;

}
*/
/**
 * Testing linear search from a 50 MB file
 * @param x the double to be searched for
 * @return the double value (if it is in the list
 */
/*
public double linearTest(double x){
        ArrayList<Double> myList = createList();
       
        boolean init = false;
        for (int i = 0; i < myList.size(); i++){
            if (myList.get(i) == x){
                return x;
                
            }
        }
        if (init == false){
            JOptionPane.showMessageDialog(view, "A Task named: \"" + x + "\" is not found in your task list. Please check your spelling (it is case-sensitive!).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
}

*/

/*
.Sort() quickest, roughly 3 mins total build time, runs on O(n log n) complexity
- uses mergesort
Insertion sort and bubble sort took over an hour, complexity of O(n^2), (best case is O(n))
*/
//Binary search tok 4.3s and 4.0s to search for two differetn doubles.
//Linear search took 4.0s and 2.2s to seach for those same two doubles respectively.
//Linear seach comploexity is O(n), Binary search has complextiy of O(log n)
//Linear search was faster, however, as it was not in its worst case (the elements all occurred near the front).


}
