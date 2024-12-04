/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.TypeProcessador;
import java.util.List;

/**
 *
 * @author Walla
 */
public class Pipeline {
    
    private Thread[] threads;
    private Instruction[] IF, OF, EX, MEM, OS;
    private int numUnits;
    
    
    Pipeline(TypeProcessador type, Integer threads){
        this.threads = new Thread[threads];
        
        this.numUnits = (type == TypeProcessador.Escalar) ? 1 : threads;
        
        IF = new Instruction[numUnits];
        OF = new Instruction[numUnits];
        EX = new Instruction[numUnits];
        MEM = new Instruction[numUnits];
        OS = new Instruction[numUnits];
    }
    
    public void setThreads(Thread[] threads){
        this.threads = threads;
    }
    
    
    
    // Getters
    
}
