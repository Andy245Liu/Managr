/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;
//import java.util.Calendar;
//import java.util.GregorianCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * An abstract class creating a task holding the name, subject, dueDate, importance
 * 
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public abstract class Task {
   /**
    * private variable holding the String name of the Task
    */
   private String name;
   /**
    * private variable holding the String subject of the Task
    */
   private String subject;
   /**
    * protected variable holding the int weighted importance of the Task
    */
   protected int importance;
   /**
    * private variable holding the Date due date of the Task
    */
   private Date dueDate;
   /**
    * private final Date variable holding the current date
    */
   private final Date CURRENTDATE = new Date();
 
   /**
    * Constructor for the Task abstract class.
    * 
    * @param name String name of the task
    * @param subject String subject of the task
    * @param dueDate Date due date of the task
    * @param importance int importance of the task
    */

   public Task(String name, String subject, Date dueDate, int importance){
       this.name = name;
       this.subject = subject;
        this.dueDate = dueDate;
       this.importance = importance;
       //set time zone to Toronto
       TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
     
     }
   /**
    * Overwrites toString() method of the class to print the task name
    * @return the string name of the task
    */
   @Override
   public String toString(){
     return name;  
   }
   /**
    * method for accessing the object name
    * 
    * @return the name of the task object of the form string
    */
   public String getName(){
       return this.name;
   }

   /**
    * Accessing the task subject
    * @return a string showing the task subject
    */
   public String getSubject(){
       return subject;
   }
 
   /**
    * This method sets the time zone of the assignment
    */
   public void setTimeZone(){
       TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
       
    }
   /**
    * Method to access the due date of the task
    * 
    * @return the due date of the task converted into the Date object
    */
    public Date getDueDate(){

     
      return dueDate;
    }
    
   /**
    * Method to get the difference in days between the task due date and today
    * @return a double representing the difference in days
    * @throws ParseException for parsing simple date format to date
    */
    public double dayDifference() throws ParseException{
        //formats dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String a = sdf.format(CURRENTDATE);
        String due = sdf.format(this.getDueDate());
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(a);
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due);
        //finds time difference in days
    double difference =  ((dueDate.getTime() - date.getTime())/86400000.0); 
   
    return difference;
    }
    /**
     * Calculates the urgency of the task in terms of how close its due date is from the current date
     * Finds the difference in hours from the current date and time to the due date, and finds the urgencyFactor by calculating the reciprocal of that time difference
     *
     * @return a double representing the urgencyFactor of the task
     */
    public double urgency(){ 
        double difference =  ((this.getDueDate().getTime() - CURRENTDATE.getTime())/3600000.0); // to calculate the number of hours until the deadline
        //special case: adds 0.000000001 milliseconds if time difference is 0 to avoid dividing by 0
        if(difference == 0){
            difference += 0.000000001;
        }
        double urgencyFactor = 1/difference;
        return urgencyFactor;
   
 }
  /**
   * Calculates a priority rating by multiplying the importance rating of the task by its urgency factor.
   * @return a double representing the priority rating of the task
   */
  public double priorityRating() {
    return this.urgency() * importance;

  }

  /**
   * Accessor Method to get task importance
   * @return int representing the set task importance
   */
  public int getImportance(){
      return this.importance;
  }

  /**
   * Abstract method to show the task type
   * @return a string showing the task type
   */
  public abstract String getTaskType();
  /**
   * Abstract method that sets a default importance to the importance instance variable
   */
  public abstract void setDefaultImportance();
  

  
}




