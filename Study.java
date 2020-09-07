/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class for Study session holding name, subject, length of study, date of study, study hour and study minute
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class Study implements Comparable{
    
   /**
    * private variable holding String name of Study
    */
   private String name;
   /**
    * private variable holding the String subject of study
    */
   private String subject;
   /**
    * private variable holding the int number of hours of study
    */
   private int studyHoursLength;
   /**
    * private variable holding the Date scheduled date of the study period
    */
   private Date studyDate;
   /**
    * private variable holding the String start hour of the study period (24-hour clock)
    */
   private String startHour;
   /**
    * private variable holding the String start minute of the study period (minute of hour)
    */
   private String startMin;
   /**
    * private final Date constant holding the current date
    */
   private static final Date CURRENTDATE = new Date();
   
    /**
    * Constructor for the Study class.
    * 
    * @param subject String subject of the study
    * @param studyDate date of the study period
    * @param startHour string represents the hour the study period commences
    * @param startMin string representing the minute the study period commences
    * @param studyHoursLength int number of hours of the study period
    */
   public Study(String subject, int studyHoursLength, String startHour, String startMin, Date studyDate){
       this.name = "Study Time";
       this.subject = subject;
       this.studyHoursLength = studyHoursLength;
       this.studyDate = studyDate;
       this.startHour = startHour;
       this.startMin = startMin;
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
       
   }
   

   /**
    * Accessor method for the object name
    * @return String of study name
    */
   public String getName(){
       return this.name;
   }
   
   /**
    * Accessor method to get study subject
    * @return a string showing the subject name
    */
   public String getSubject(){
       return this.subject;
   }
   /**
    * Accessor method to get the study time in hours
    * @return int representing the number of hours of the study period
    */
   public int getHours(){
       return this.studyHoursLength;
   }
   /**
    * Accessor method to get the date of the study period
    * @return Date showing the date of study
    */
   public Date getStudyDate(){
       return this.studyDate;
   }
   /**
    * Accessor method to retrieve study start hour
    * @return String representing an hour of the day
    */
   public String getStudyHour(){
       
   return this.startHour;
   }
   
   /**
    * Accessor method to retrieve study start minute
    * @return String representing a minute of the day
    */
   public String getStudyMin(){
       return this.startMin;
   }
   /**
    * Method to override compareTo method of the Comparable interface, compares due studies in a day by the earlier time
    * @param d an object of type Study
    * @return 1 if the current study is earlier by time, -1 if it is later, and 0 if the two occur at the same time
    */
   @Override
   public int compareTo(Object study){
        Study b = (Study) study;
          int aHr = Integer.parseInt(this.getStudyHour());
          int bHr = Integer.parseInt(b.getStudyHour());
          int aMin = Integer.parseInt(this.getStudyMin());
          int bMin = Integer.parseInt(b.getStudyMin());
       if( aHr < bHr){
            return 1;          
       } else if(aHr > bHr){
           return -1;
       }else{
           if(aMin < bMin){return 1;}
           else if(aMin > bMin){return -1;}
           else{return 0;}
       }
   }
   
   /**
    * Method to get the difference in time between today and the study date
    * @param d the Date of the study
    * @return a double representing the difference in time in hours.
    * @throws java.text.ParseException for parsing a String to Date
    */
   public static double timeDifference(Date d) throws ParseException{
       //format dates
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String a = sdf.format(CURRENTDATE);
     
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(a);
    //finds the time difference in hours
    double difference =  ((d.getTime() - date.getTime())/3600000.0); 
   
    return difference;
   }
   

  
   
}
