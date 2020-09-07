/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.Date;

/**
 * Creates class for Projects holding name of task, subject, due date and importance
 * 
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class Project extends Task{
    /**
     * Constructor for the test class that uses the parent class constructor
     * @param name String name of the project
     * @param subject String subject of the project
     * @param dueDate Date due date of the project
     * @param importance int importance of the project
     */
    public Project(String name, String subject, Date dueDate, int importance) {
        super(name, subject, dueDate, importance);
        
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String getTaskType() {
        return "Project";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
   public void setDefaultImportance(){
       importance = 8;
   }
    
}
