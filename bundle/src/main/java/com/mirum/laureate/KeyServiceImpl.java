package com.mirum.laureate;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
 
@Component
@Service
public class KeyServiceImpl implements KeyService {
    //Define a class member named key
    private int key = 0 ; 
     
    public void setKey(int val)
    {
        //Set the key class member
        this.key = val ; 
          
    }
    
    public String getKey()
    {
        //return the value of the key class member
          
        //Convert the int to a String to display it within an AEM web page
        String strI = Integer.toString(this.key);
        return strI; 
    }
}