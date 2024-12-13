/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.PipelineStageEnum;
import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public class PipelineStage {
    
    public final int sizeOfStage;
    public final PipelineStageEnum stage;
    public ArrayList<Instruction> queueInstructions = new ArrayList<>();
    
    
    public PipelineStage(int sizeStage, PipelineStageEnum stage){
        this.stage = stage;
        this.sizeOfStage = sizeStage;
    }
    
    public PipelineStage(int sizeStage, PipelineStageEnum stage, PipelineStage extraStage){
        this.stage = stage;
        this.sizeOfStage = sizeStage;
    }
    
    public void addInstructions(ArrayList<Instruction> i){
        if(!i.isEmpty()){
            for(Instruction it : i){
                addInstruction(it);
            }
        }
    }
    
    public Integer addInstruction(Instruction i){
        if(i != null){
            i.setStage(stage);
            queueInstructions.add(i);
        }
        return sizeOfStage - queueInstructions.size();
    }
    
    public Integer windowStage(){
        return sizeOfStage - queueInstructions.size();
    }
    
    public Integer getSize(){
        return sizeOfStage;
    }
    
    public ArrayList<Instruction> runCycle(Integer max){
        ArrayList<Instruction> instructions = new ArrayList<>();
        if(this.queueInstructions.size() >= max){
            for(int i = 0; i < max; i++){
                instructions.add(queueInstructions.remove(0));
            }
        } else if(!this.queueInstructions.isEmpty()){
            while(!this.queueInstructions.isEmpty()){
                instructions.add(queueInstructions.remove(0));
            }
        }
        return instructions;  
    };
    
  
}
