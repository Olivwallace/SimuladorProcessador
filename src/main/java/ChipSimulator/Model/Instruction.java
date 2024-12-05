/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.OpCode;
import ChipSimulator.Model.Enums.Registers;

/**
 *
 * @author Walla
 */
public class Instruction {
    private String threadOrigen;
    private OpCode opCode;
    private Registers[] registers;
    private String instructionString;
    private int cycleIni = -1;
    private int cycleFin = -1;
    
    public Instruction(OpCode opCode, Registers[] registers, String instruction){
        this.opCode = opCode;
    
        instructionString = instruction;
        
        this.registers = registers;
    }
    
    public void setThreadOrigem(String thread){
        this.threadOrigen = thread;
    }
    
    public void setStartCycle(int cycle){
        this.cycleIni = cycle;
    }
    
    public void setFinnishCycle(int cycle){
        this.cycleFin = cycle;
    }
    
    public int getStartCycle(){
        return this.cycleIni;
    }
    
    public int getFinnishCycle(){
        return this.cycleFin;
    }
    
    public Registers getR1(){
        return registers[0];
    }
    
    public Registers getR2(){
        return registers[1];
    }
    
    public Registers getR3(){
        return registers[2];
    }
    
    public String getThread(){
        return this.threadOrigen;
    }
    
    public boolean needMemory(){
        return opCode == OpCode.LW || opCode == OpCode.SW;
    }
    
   
    @Override
    public String toString(){
        return this.instructionString;
    }
}
