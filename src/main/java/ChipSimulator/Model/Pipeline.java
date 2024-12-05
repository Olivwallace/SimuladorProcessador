/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.Registers;
import ChipSimulator.Model.Enums.TypeProcessador;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Walla
 */
public class Pipeline {
    
    private ArrayList<Instruction> IF, ID, EX, MEM, WB;
    private Integer numUnits;
    
    public Pipeline(Integer numUnits){
        IF = new ArrayList<>();
        ID = new ArrayList<>();
        EX = new ArrayList<>();
        MEM = new ArrayList<>();
        WB = new ArrayList<>();
        
        this.numUnits = numUnits;
    }
    
    public Integer fetch(Instruction inst){
        if(IF.size() < numUnits){
            IF.add(inst);
        }
        return numUnits - IF.size();
    }
    
    private Integer decode(Instruction inst){
        if(ID.size() < numUnits){
            ID.add(inst);
        }
        return numUnits - ID.size();
    }
    
    private Integer execute(Instruction inst){
        if(EX.size() < numUnits){
            EX.add(inst);
        }
        return numUnits - EX.size();
    }
    
    private Integer memory(Instruction inst){
        if(MEM.size() < numUnits){
            MEM.add(inst);
        }
        return numUnits - IF.size();
    }
    
    private Integer wb(Instruction inst){
        if(WB.size() < numUnits){
            WB.add(inst);
        }
        return numUnits - WB.size();
    }
    
    public Integer runNextCicle(Integer cicle){
        while(WB.size() > 0){
            Instruction i = WB.remove(0);
            i.setFinnishCycle(cicle);
        }
        
        while(MEM.size() > 0){
            Instruction i = MEM.remove(0);
            WB.add(i);
        }
        
        while(EX.size() > 0){
            Instruction i = EX.remove(0);
            if(i.needMemory()){
                MEM.add(i);
            } else {
                WB.add(i);
            }
        }
        
        while(EX.size() != numUnits){
            Instruction i = ID.remove(0);
            EX.add(i);
        }
        
        while(ID.size() != numUnits){
            Instruction i = IF.remove(0);
            ID.add(i);
        }
        
        return numUnits - ID.size();
    }
    
    // Getters
    
}
