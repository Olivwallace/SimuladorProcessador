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
    private int cycleIni;
    private int cycleFin;
    
    public Instruction(String threadID, OpCode opCode, Registers out, Registers value1, Registers value2){
        this.threadOrigen = threadID;
        this.opCode = opCode;
        
        registers = new Registers[3];
        
        registers[0] = out;
        registers[1] = value1;
        registers[2] = value2;
    }
    
    void setStartCycle(int cycle){
        this.cycleIni = cycle;
    }
    
    void setFinnishCycle(int cycle){
        this.cycleFin = cycle;
    }
    
    int getStartCycle(){
        return this.cycleIni;
    }
    
    int getFinnishCycle(){
        return this.cycleFin;
    }
}
