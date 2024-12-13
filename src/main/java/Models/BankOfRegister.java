/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.Registers;

/**
 *
 * @author Walla
 */
public class BankOfRegister {
    private Registers register;
    private Integer value;
    private String renameID;
    private boolean valid;
     
    public BankOfRegister(Registers register, Integer value, boolean valid, String renameID){
        this.register = register;
        this.value = value;
        this.valid = valid;
        this.renameID = renameID;
    }
    
    public void restart(){
        setValue(0);
        setValid();
        setRenameID("");
    }
    
    // -------- Setters
    
    public void setValid(){
        this.valid = true;
    }
    
    public void setInvalid(){
        this.valid = false;
    }
    
    public void setRenameID(String id){
        this.renameID = id;
    }
    
    public void setValue(Integer value){
        this.value = value;
    }
    
    public void setRegister(Registers register){
        this.register = register;
    }

    // -------- Getters
    
    public Integer getValue(){
        return value;
    }
    
    public boolean getIsValid(){
        return valid;
    }
    
    public String getRenameID(){
        return renameID;
    }
    
    public Registers getRegister(){
        return register;
    }
}
