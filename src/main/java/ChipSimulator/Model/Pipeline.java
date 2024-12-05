/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.TypeProcessador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walla
 */
public class Pipeline {
    
    private Instruction[] IF, OF, EX, MEM, OS;
 
    public Pipeline(Integer numUnits){
        IF = new Instruction[numUnits];
        OF = new Instruction[numUnits];
        EX = new Instruction[numUnits];
        MEM = new Instruction[numUnits];
        OS = new Instruction[numUnits];
    }
    
    public void execultarCiclo(){
        
    }
    
    
    
    
    // Getters
    
}
