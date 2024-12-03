/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public class Thread {
    private String threadID;
    private ArrayList<Instruction> instructions;
    private boolean isRunning;
    
    Thread(String threadID, ArrayList<Instruction> instructions, boolean isRunning){
        this.threadID = threadID;
        this.instructions = instructions;
        this.isRunning = isRunning;
    }
    
    Instruction getInstruction(int pc){
        Instruction instruction = null;
        if (pc < instructions.size()){
            instruction = instructions.get(pc);
        }
        return instruction;
    }
}
