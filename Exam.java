/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.Date;

/**
 * Creates a class for exams holding name of task, subject, due date and importance
 * 
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class Exam extends Task {
    /**
     * Constructor for the test class that uses the parent class constructor
     * @param name String name of the exam
     * @param subject String subject of the exam
     * @param dueDate Date due date of the exam
     * @param importance int importance of the exam
     */
    public Exam(String name, String subject, Date dueDate, int importance) {
        super(name, subject, dueDate, importance);
        
    }
    /**
     * {@inheritDoc}
     *  
     */

    @Override
    public String getTaskType() {
        return "Exam";
    }
    /**
     * {@inheritDoc}
     */
    
    @Override
   public void setDefaultImportance(){
       importance = 10;
   }
}
