/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.Instruction;
import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public interface PipelineDelegate {
    public void incrementCycle();
    public void endInstructions(ArrayList<Instruction> endInstructions);
    public boolean checkDepency(Instruction i);
    public ArrayList<Instruction> fetchInstructions(Integer numOfInstructions);
    public void decodeInstruction(Instruction i);
    public Instruction generateBubble();
}
