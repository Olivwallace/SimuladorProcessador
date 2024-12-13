/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.SuperEscalar;

import Enums.PipelineStageEnum;
import Enums.UnitExeOpCode;
import Interfaces.PipelineDelegate;
import Models.BufferReorder;
import Models.Instruction;
import Models.Pipeline;
import Models.PipelineStage;
import Models.ReorderBufferItem;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Walla
 */
public class PipelineSuperScalar extends Pipeline {
    
    private final int MAX_SIZE_BUFFER = 16;
    private final int MAX_SIZE_WINDOW = 4;
    private int   MAX_INSTRUCTIONS_STAGE = 4;
    
    BufferReorder buffer = BufferReorder.instance;
    
    private PipelineStage IF;
    private PipelineStage ID;
    
    private PipelineStage WINDOW_ULA;
    private PipelineStage WINDOW_BEQ;
    private PipelineStage WINDOW_SW_LW;
    private PipelineStage WINDOW_MULT;
    
    private PipelineStage ULA;
    private PipelineStage BEQ;
    private PipelineStage SW_LW_EX;
    private PipelineStage SW_LW_MEM;
    private PipelineStage MULT;
   
    private PipelineStage WB;

    public PipelineSuperScalar(PipelineDelegate pd){
        super(pd);
        setStages();
    }
    
    @Override
    public void setSuportSMT(){
        this.MAX_INSTRUCTIONS_STAGE = 8;
        setStages();
    }
    
    @Override
    public void removeSuportSMT(){
        this.MAX_INSTRUCTIONS_STAGE = 4;
        setStages();
    }
    
    public final void setStages(){
        IF = new PipelineStage(MAX_INSTRUCTIONS_STAGE, PipelineStageEnum.IF);
        ID = new PipelineStage(MAX_INSTRUCTIONS_STAGE, PipelineStageEnum.ID);
        
        buffer.setBufferSize(MAX_SIZE_BUFFER);
        
        WINDOW_ULA = new PipelineStage(MAX_SIZE_WINDOW, PipelineStageEnum.WINDOW_ULA);
        WINDOW_BEQ = new PipelineStage(MAX_SIZE_WINDOW, PipelineStageEnum.WINDOW_BRANCH);
        WINDOW_SW_LW = new PipelineStage(MAX_SIZE_WINDOW, PipelineStageEnum.WINDOW_LSLW);
        WINDOW_MULT = new PipelineStage(MAX_SIZE_WINDOW, PipelineStageEnum.WINDOW_MULT);
        
        ULA = new PipelineStage(1, PipelineStageEnum.ULA);
        BEQ = new PipelineStage(1, PipelineStageEnum.BRANCH);
        SW_LW_EX = new PipelineStage(1, PipelineStageEnum.EX);
        SW_LW_MEM = new PipelineStage(1, PipelineStageEnum.MEM);
        MULT = new PipelineStage(1, PipelineStageEnum.MULT);
        
        WB = new PipelineStage(MAX_INSTRUCTIONS_STAGE, PipelineStageEnum.WB);
    }
    
    @Override
   public void runCycle(){
       Integer MAX_COMMIT = WB.getSize();
       pipelineDelegate.endInstructions(WB.runCycle(MAX_COMMIT)); // WB (Commit)
       
       Integer MAX_WB = WB.windowStage();
       WB.addInstructions(buffer.promoveCommitInstructions(MAX_WB)); // BUFFER -> WB

       // -------------------------------
       // Conclui Instrucçõe
       runCycleUnit(SW_LW_MEM);
       runCycleUnit(ULA);
       runCycleUnit(BEQ);
       runCycleUnit(MULT);
       
       // -------------------------------
       // Move instruções para unidades lógicas
       SW_LW_MEM.addInstructions(SW_LW_EX.runCycle(SW_LW_MEM.windowStage()));
       runCycleWindowInstruction(SW_LW_EX, WINDOW_SW_LW);
       runCycleWindowInstruction(ULA, WINDOW_ULA);
       runCycleWindowInstruction(BEQ, WINDOW_BEQ);
       runCycleWindowInstruction(MULT, WINDOW_MULT);
       
       // ---------------------------------
       // Move instruções para janelas
       runCycleDecodeInstructions();
       
       // ---------------------------------
       // Move instruções para estado de ID
       ID.addInstructions(
               IF.runCycle(ID.windowStage())
       ); // IF -> ID
       
       // ---------------------------------
       // Move instruções para estado de IF
       IF.addInstructions(
              pipelineDelegate.fetchInstructions(IF.windowStage())
       ); // IF
       
       print();
       
       pipelineDelegate.incrementCycle();
   }
   
   void runCycleUnit(PipelineStage Unit){
        for(Instruction unit_inst : Unit.runCycle(1)){
            String bufferRegister = unit_inst.getRenameRegister()[0];
            buffer.getBufferPointer(bufferRegister).setIsReady();
            buffer.getBufferPointer(bufferRegister).setValid();
        }
    }
   
   void runCycleWindowInstruction(PipelineStage Unit, PipelineStage Window){
       int count = 0;
       for(Instruction i : Window.runCycle(MAX_SIZE_WINDOW)){
           Integer MAX_UNIT = Unit.windowStage();
           
           String[] renameRegisters = i.getRenameRegister();
           
           boolean isReady = buffer.checkRenamedRegisters(renameRegisters[1], renameRegisters[2], i);
           
           if((i.isReadyToEx() || isReady) && MAX_UNIT > count){
               Unit.addInstruction(i);
               count++;
           } else {
               Window.addInstruction(i);
           }
       }
   }
   
   void runCycleDecodeInstructions(){
       boolean isAddSuccess = false;
       for(Instruction i : ID.runCycle(MAX_INSTRUCTIONS_STAGE)){
           
           if(buffer.addNewInstruction(i)){
               pipelineDelegate.decodeInstruction(i);
               
               switch(i.getOpCode().unitDestination()){
                    case OPCODE_SWLW -> isAddSuccess = isAddSuccessInstructionInWindow(i, WINDOW_SW_LW);
                    case OPCODE_BEQ -> isAddSuccess = isAddSuccessInstructionInWindow(i, WINDOW_BEQ);
                    case OPCODE_MULT -> isAddSuccess = isAddSuccessInstructionInWindow(i, WINDOW_MULT);
                    case OPCODE_ULA -> isAddSuccess = isAddSuccessInstructionInWindow(i, WINDOW_ULA);
                }

                if(!isAddSuccess){
                    String[] registers = i.getRenameRegister();
                    
                    if(registers[1] != null){
                        buffer.getBufferPointer(registers[1]).addWaitCommit(i);
                    }
                    
                    if(registers[2] != null){
                        buffer.getBufferPointer(registers[2]).addWaitCommit(i);
                    }
                    
                    ID.addInstruction(i);
                }
           }
       }
   }
   
   boolean isAddSuccessInstructionInWindow(Instruction i, PipelineStage Window){
       boolean support = Window.windowStage() > 0;
       
       if(support){
           Window.addInstruction(i);
       }
       
       return support;
   }
   
   
   // -------------------- Getters
   public ArrayList<PipelineStage> getStages(){
       ArrayList<PipelineStage> stages = new ArrayList<>();
       
       stages.add(IF);
       stages.add(ID);
       stages.add(WINDOW_ULA);
       stages.add(WINDOW_BEQ);
       stages.add(WINDOW_SW_LW);
       stages.add(WINDOW_MULT);
       
       stages.add(ULA);
       stages.add(BEQ);
       stages.add(SW_LW_EX);
       stages.add(SW_LW_MEM);
       stages.add(MULT);
       stages.add(WB);
       
       return stages;
   }
   
   public void print(){
       String s = "";
       
       buffer.print();
       
       System.out.println("\n-------- IF ------------");
       printStage(IF);
       System.out.println("\n-------- ID ------------");
       printStage(ID);
       System.out.println("\n-------- WIN_ULA ------------");
       printStage(WINDOW_ULA);
       System.out.println("\n-------- ULA ------------");
       printStage(ULA);
       System.out.println("\n-------- WIN_MULT ------------");
       printStage(WINDOW_MULT);
       System.out.println("\n-------- MULT ------------");
       printStage(MULT);       
       System.out.println("\n-------- WIN_BEQ ------------");
       printStage(WINDOW_BEQ);       
       System.out.println("\n-------- BEQ ------------");
       printStage(BEQ);
       System.out.println("\n-------- WIN_LW_SW ------------");
       printStage(WINDOW_SW_LW);
       System.out.println("\n-------- LW_SW_EX ------------");
       printStage(SW_LW_EX);
       System.out.println("\n-------- LW_SW_MEM ------------");
       printStage(SW_LW_MEM);
       System.out.println("\n-------- WB ------------");
       printStage(WB);
   }
   
   public void printStage(PipelineStage Stage){
       String s = "";
       for(int i = 0; i < Stage.queueInstructions.size(); i++){
           s = String.format(" (Thread: %s) %s  |", Stage.queueInstructions.get(i).getThreadID(), Stage.queueInstructions.get(i).getInstructionString());
           System.out.print(s);
       }
   }
   
   
   public ArrayList<Instruction> getBufferReorder(){
       return buffer.getReadyInstructions();
   }
   
}
