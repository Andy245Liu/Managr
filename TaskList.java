/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;

import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
/**
 * Class for TaskList which holds an ArrayList of Tasks
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class TaskList implements Serializable {
    /**
     * private variable holding an ArrayList of Tasks
     */
   private ArrayList taskList;
    /**
     * Constructs the TaskList
     */
  public TaskList(){
     taskList = new ArrayList();
  }
  
  /**
   * constructs a TaskList given a parameter. Sets the TaskList to hold the given ArrayList.
   * @param list an ArrayList of tasks
   */
  public TaskList(ArrayList list){
      taskList = list;
  }
  
 
  /**
   * method that adds to a TaskList
   * @param a a Task object
   */
  public void addTask(Object a){
    taskList.add(a);
  }
  /**
   * Method to retrieve the list of tasks
   * @return the list of tasks
   */
  public ArrayList getTaskList(){
    return taskList;
  }
  /**
   * Method to delete a task from the list
   * @param index an integer representing the index of the task in the arrayList
   */
  public void deleteTask(int index){
     taskList.remove(index);
  }
  /**
   * Method to find the length of the task list
   * @return the integer length of the task list
   */
  public int length(){
    return taskList.size();
  }
  /**
   * Retrieves the task at position i in the taskList
   * @param i an integer representing the index of the task in the arrayList
   * @return the task object at index i of the taskList
   */
  public Task getTask(int i){
      return (Task) taskList.get(i);
  }
  
  /**
   * Swaps two tasks in a Task list
   * @param a the integer of the first position in the list
   * @param b the integer of the second position within the list
   */  
  public void swapTasks(int a, int b){
      Collections.swap(this.getTaskList(), a, b);
  }
  
  /**
   * Sets the index of the TaskList to a task
   * @param i integer index
   * @param aTask the Task to be inserted into position i
   */
  public void setTask(int i, Task aTask){
      taskList.set(i, aTask);
  }

}
