/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interface to sort events (Tasks and Studies)
 * 
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 *
 */
public interface Sortable {
    /**
     * Method sorts an ArrayList by their priority attribute (combination of task weighting and due date proximity)
     * 
     * @return and ArrayList with Tasks sorted by their priority index
     */
    public ArrayList sortByPriority();
    

    /**
     * Sorts an ArrayList by their date attribute
     * 
     * @return an ArrayList of Tasks or Studies sorted by dates
     */
    public ArrayList sortByDate();
  
    
}
