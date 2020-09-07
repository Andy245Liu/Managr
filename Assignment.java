/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.Date;

/**
 * Creates class for Assignments, holds name of task, subject, due date and importance
 * 
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class Assignment extends Task{
    /**
     * Constructor for the test class that uses the parent class constructor
     * @param name String name of the person
     * @param subject String name of subject
     * @param dueDate Date due date of task
     * @param importance int importance of task
     */
    public Assignment(String name, String subject, Date dueDate, int importance) {
        super(name, subject, dueDate, importance);
        
    }
    /**
     * {@inheritDoc} 
     */
    @Override
    public String getTaskType() {
        return "Assignment";
    }
    /**
     * {@inheritDoc}
     * 
     */
    @Override
   public void setDefaultImportance(){
       importance = 6;
   }
    
}
