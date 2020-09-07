/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.Date;


/**
 * A class for tests that extends the Task class holding name of task, subject, due date and importance
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class Test extends Task {
    /**
     * Constructor for the test class that uses the parent class constructor
     * @param name String name of the test
     * @param subject String subject of the test
     * @param dueDate Date due date of the test
     * @param importance int importance of the test
     */
    public Test(String name, String subject, Date dueDate, int importance) {
        super(name, subject, dueDate, importance);
        
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String getTaskType() {
        return "Test";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
   public void setDefaultImportance(){
       importance = 8;
   }
}
