/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Interfaces.CallabackProcessador;
import ChipSimulator.Model.Enums.PipelineState;
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
    private CallabackProcessador delegate;
    private Integer numUnits;
    
    public void reiniciar(){
        IF.clear();
        ID.clear();
        EX.clear();
        MEM.clear();
        WB.clear();
    }
    
    public Pipeline(Integer numUnits, CallabackProcessador delegate){
        IF = new ArrayList<>();
        ID = new ArrayList<>();
        EX = new ArrayList<>();
        MEM = new ArrayList<>();
        WB = new ArrayList<>();
        
        this.numUnits = numUnits;
        this.delegate = delegate;
    }
    
    public Integer fetch(Instruction inst){
        if(IF.size() < numUnits){
            inst.state = PipelineState.IF;
            IF.add(inst);
        }
        return numUnits - IF.size();
    }
    
    private Integer decode(Instruction inst){
        if(ID.size() < numUnits){
            inst.state = PipelineState.ID;
            ID.add(inst);
        }
        return numUnits - ID.size();
    }
    
    private Integer execute(Instruction inst){
        if(EX.size() < numUnits){
            inst.state = PipelineState.EX;
            EX.add(inst);
        }
        return numUnits - EX.size();
    }
    
    private Integer memory(Instruction inst){
        if(MEM.size() < numUnits){
            inst.state = PipelineState.MEM;
            MEM.add(inst);
        }
        return numUnits - IF.size();
    }
    
    private Integer wb(Instruction inst){
        if(WB.size() < numUnits){
            inst.state = PipelineState.WB;
            WB.add(inst);
        }
        return numUnits - WB.size();
    }
    
    public Integer runNextCicle(Integer cicle){
        while(!WB.isEmpty()){
            Instruction i = WB.remove(0);
            i.setFinnishCycle(cicle);
            delegate.notifyEndInstruction(i);
        }
        
        while(!MEM.isEmpty()){
            Instruction i = MEM.remove(0);
            wb(i);
        }
        
        while(!EX.isEmpty() && MEM.size() != numUnits){
            Instruction i = EX.remove(0);
            memory(i);
        }
        
        while(!ID.isEmpty() && EX.size() != numUnits){
            Instruction i = ID.remove(0);
            if(!delegate.hasDependencia(i)){
                execute(i);
            } else {
                execute(new Instruction(i.getThread()));
                delegate.notifyBolha(i);
                decode(i);
            }
            
        }
        
        while(!IF.isEmpty() && ID.size() != numUnits){
            Instruction i = IF.remove(0);
            decode(i);
        }
        
        return numUnits - IF.size();
    }
    
    // Getters
    public ArrayList<Instruction> getIF(){
        return IF;
    }
    public ArrayList<Instruction> getID(){
        return ID;
    }
    public ArrayList<Instruction> getEX(){
        return EX;
    }
    public ArrayList<Instruction> getMEM(){
        return MEM;
    }
    public ArrayList<Instruction> getWB(){
        return WB;
    }
}
