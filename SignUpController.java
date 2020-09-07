/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managr;
import java.sql.*;

/**
 * Class for controller for sign up, holding the SignupForm view GUI
 * @author Andy
 * @since JDK 7u80
 * @version 7.0
 */
public class SignUpController {
    /**
     * private constant variable holding the signup form view Swing class
     */
    private final SignupForm signup;
   

    /**
     * Constructor of the sign up controller
     * @param signup the sign up view
     */
    public SignUpController(SignupForm signup){
        this.signup = signup;
    }
    
    /**
     * Checks if the password is confirmed correctly
     * @return returns boolean true if the password is confirmed, false if it is not
     */
    public boolean reconcilePassword(){
        if((signup.getFirstPassword()).equals(signup.getSecondPassword())){
            
            return true;
            
        }
        else{
            
            return false;
        }
    }
    
    /**
     * Checks if the user name already exists
     * @return a boolean true if the username exists, false if it does not
     */
    public boolean userNameExists(){
        boolean userNameExists = false;
        //reads username and password
        String user = signup.getUsername();
        String pass = signup.getFirstPassword();
        try{
            //creates statement
            //connects to database
             
            Connection myConn = DriverManager.getConnection(Main.URL);   
            
                //creates statement
                Statement myStmt = myConn.createStatement();
                System.out.println("statement initiated");
                //SQL query
                ResultSet myRs = myStmt.executeQuery("select * from LISTOFUSERS ");
                System.out.println("query initiated");
                //process result set
                //checks to see if the username already exists in the database
                while (myRs.next()){
                    System.out.println("check");
                    if(user.equals(myRs.getString("USERNAME"))){
                        
                        userNameExists = true;
                        break;
                    }
                }
                myConn.close();
            
            
            
        } catch (Exception e) 
        {
        System.err.println(e.getMessage());
        } 
     return userNameExists;   
    }
    
    /**
     * Adds a username and password to the database
     */
    public void addUser(){
        //gets entered username and password
        String user = signup.getUsername();
        String pass = signup.getFirstPassword();
        try{
            
            //connects to database
          
                //creates statement
            Connection myConn = DriverManager.getConnection(Main.URL);
            //adds username and password into database
                PreparedStatement myStmt = myConn.prepareStatement("insert into LISTOFUSERS(USERNAME, PASSWORD)values(?,?)");
                myStmt.setString(1,user);
                myStmt.setString(2,pass);
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

/*
Limitations of Java datatypes
byte:
    - 8-bit signed two's complement integer (way computer represents integers (reverses binary digits then add one for negative)
    - range of -128 (-2^7) to 127 inclusive (2^7 -1) 
    - defaults to 0
    -saves space (four times smaller than int)
short:
    - 16-bit signed two's complement integer
    - range of -32 768 (-2^15) to 32 767 inclusive (2^15 -1)
    - defaults to 0
    - saves space (two times smaller than int)
int:
    - 32-bit signed two's complement integer
    - range of - 2 147 483 648 (-2^31) to 2 147 483 647(inclusive) (2^31 -1)
    - default to 0

long:
    - 64-bit signed two's complement integer
    - range of -9,223,372,036,854,775,808(-2^63) to 9,223,372,036,854,775,807 (inclusive)(2^63 -1)
    - default to 0

float:
    - 32-bit floating point
    - default 0.0f
    - range: roughly -3.4E+38 to +3.4E+38 (about 7 decimal digits)

double:
    - 64-bit floating point
    - default 0.0d
    - range: roughly -1.7E+308 to +1.7E+308 (about 16 decimal digits)

boolean:
    - one bit of info
    - defaults to false
    - can be either true or false

char:
    - 16-bit unicode character
    - min value of \u0000 (or 0)
    - max value of \uffff (or 65 535 inclusive)
    - stores any character

Reference datatypes:
    - created using defined constructors of classes
    - defaults to null

Object:
    - typical size of 16 bytes on a modern 64-bit JDK
    - Integers size of 24 bytes 
String:
    - array of characters
    - uses total of 40 bytes
    - max length is 2^31 -1 (max int)
Date:
    - 32 bytes
Arrays:
    - stores multiple primitive datatypes 
    - has a predefined length
    - 24 bytes of header information, 16 bytes of overhead, 4 bytes for the length, and 4 bytes of padding
   
Sources:
    - Oracle Java SE 7 Edition Language Specification
    - Two's complement Cornell cs
    - Tutorialspoint Java - Basic Datatypes
    - Central Connecticut State University - Size of Floats
    - https://ordepdev.me/posts/size-of-an-object-in-java
*/


