/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.Registers;
import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public class ReorderBufferItem {
    
    private final String reorderBufferCode;
    private final String threadCode;
    private Instruction instruction;
    private Registers register;
    private Integer value;
    private boolean isValid;
    private boolean isReady;
    private ArrayList<Instruction> instructionsWaitCommit = new ArrayList<>();
    public boolean isFlag = false;
    
    public ReorderBufferItem(){
        this.isFlag = true;
        this.reorderBufferCode = "FLAG";
        this.instruction = null;
        this.threadCode = "FLAG";
    }
    
    public ReorderBufferItem(String reorderBufferCode, String threadCode, Instruction instruction, Registers register){
        this.reorderBufferCode = reorderBufferCode;
        this.instruction = instruction;
        this.threadCode = threadCode;
        this.register = register;
        this.value = null;
        this.isValid = false;
        this.isReady = false;
    }
   
    // -------- Setters
    
    public void setIsReady(){
        this.isReady = true;
    }
    
    public void setValid(){
        this.isValid = true;
    }
    
    public void setInvalid(){
        this.isValid = false;
    }
    
    public void setValue(Integer value){
        this.value = value;
    }
    
    public void setRegister(Registers register){
        this.register = register;
    }
    
    public void addWaitCommit(Instruction i){
        
    }

    // -------- Getters
    
    public Integer getValue(){
        return value;
    }
    
    public boolean getIsValid(){
        return isValid;
    }
    
    public boolean getIsReady(){
        return isReady;
    }
    
    public Registers getRegister(){
        return register;
    }
    
    public Instruction getInstruction(){
        return instruction;
    }
    
    public String getReorderBufferCode(){
        return reorderBufferCode;
    }
    
    public String getThreadCode(){
        return threadCode;
    }
    
    public ArrayList<Instruction> getWaitCommit(){
        return instructionsWaitCommit;
    }
    
    @Override
    public String toString(){
        return String.format("|Thread: %s | Inst: %s | Valid: %s | Ready: %s |", 
                threadCode, 
                instruction.getInstructionString(), 
                (isValid) ? "TRUE" : "FALSE",
                (isReady) ? "TRUE" : "FALSE");
    }
    
}
