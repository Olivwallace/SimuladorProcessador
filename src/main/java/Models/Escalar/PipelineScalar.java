/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Escalar;

import Enums.PipelineStageEnum;
import Interfaces.PipelineDelegate;
import Models.Instruction;
import Models.Pipeline;
import Models.PipelineStage;
import java.util.ArrayList;


/**
 *
 * @author Walla
 */
public class PipelineScalar extends Pipeline {
   
   private  PipelineStage IF;
   private  PipelineStage ID;
   private  PipelineStage EX;
   private  PipelineStage MEM;
   private  PipelineStage WB;
   
   public PipelineScalar(PipelineDelegate pd){
       super(pd);
       IF = new PipelineStage(1,PipelineStageEnum.IF,null);
       ID = new PipelineStage(1,PipelineStageEnum.ID,null);
       EX = new PipelineStage(1,PipelineStageEnum.EX,null);
       MEM = new PipelineStage(1,PipelineStageEnum.MEM,null);
       WB = new PipelineStage(1,PipelineStageEnum.WB,null);
   }
   
   @Override
   public void runCycle(){
       Integer MAX_COMMIT = WB.getSize();
       pipelineDelegate.endInstructions(WB.runCycle(MAX_COMMIT)); // WB (Commit)
       
       WB.addInstructions(
               MEM.runCycle(WB.windowStage())
       ); // MEM -> WB
       
       MEM.addInstructions(
               EX.runCycle(MEM.windowStage())
       ); // EX -> MEM
       
       for(Instruction i : ID.runCycle(EX.windowStage())){
           if(!pipelineDelegate.checkDepency(i)){
               EX.addInstruction(i);
           } else {
               
               ID.addInstruction(i); // Atrasa Instrução
               
               // Gera Uma Bolha no Lugar
               Instruction bubble = pipelineDelegate.generateBubble();
               bubble.setThreadID(i.getThreadID());
               EX.addInstruction(bubble);
           }
       }// ID -> EX (Check Dependency)
       
       ID.addInstructions(
               IF.runCycle(ID.windowStage())
       ); // IF -> ID
       
       IF.addInstructions(
              pipelineDelegate.fetchInstructions(IF.windowStage())
       ); // IF
       
       pipelineDelegate.incrementCycle();
   }
   
   // Public Getters
   public ArrayList<PipelineStage> getStages(){
       ArrayList<PipelineStage> stages = new ArrayList<>();
       
       stages.add(IF);
       stages.add(ID);
       stages.add(EX);
       stages.add(MEM);
       stages.add(WB);
       
       return stages;
   }
   
   public void restart(){
       IF = new PipelineStage(1,PipelineStageEnum.IF,null);
       ID = new PipelineStage(1,PipelineStageEnum.ID,null);
       EX = new PipelineStage(1,PipelineStageEnum.EX,null);
       MEM = new PipelineStage(1,PipelineStageEnum.MEM,null);
       WB = new PipelineStage(1,PipelineStageEnum.WB,null);
   }
   
   public ArrayList<Instruction> getIF(){
       return IF.queueInstructions;
   }
   
   public ArrayList<Instruction> getID(){
       return ID.queueInstructions;
   }
   
   public ArrayList<Instruction> getEX(){
       return EX.queueInstructions;
   }
   
   public ArrayList<Instruction> getMEM(){
       return MEM.queueInstructions;
   }
   
   public ArrayList<Instruction> getWB(){
       return WB.queueInstructions;
   }
}
