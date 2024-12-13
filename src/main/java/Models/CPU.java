/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.CPUSuport;
import static Enums.CPUSuport.BMT;
import static Enums.CPUSuport.IMT;
import static Enums.CPUSuport.NONE;
import static Enums.CPUSuport.SMT;
import Interfaces.PipelineDelegate;
import Models.Escalar.PipelineScalar;
import Models.SuperEscalar.PipelineSuperScalar;
import Uteis.CheckDependency;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Walla
 */
public class CPU implements PipelineDelegate {
    
    // ----------------------------- Attributes
    private boolean isSuperScalar = false;
    private CPUSuport cpuSuport = CPUSuport.NONE;
    boolean startPossibility = false;
    
    // -------- Components
    private Pipeline pipeline;
    private final HashMap<String,ProcessThread> threads = new HashMap<>();
    
    // -------- Control Threads
    private final int MAX_BLOCK_SIZE = 3;
    private int COUNT_BLOCK = 0;
    private final ArrayList<String> threadsIDs = new ArrayList<>();
    
    // -------- Registers/Counts
    private Integer currentCycle = 0;
    private double currentIPC = 0;
    private Integer ociosa = 0;
    private Integer numOfBubble = 0;
    private Integer numOfDispatchInstructions = 0;
    private Integer numOfInstructionsEnd = 0;
    
    // ----------------------------- Init
    public CPU(){
        this.pipeline = new PipelineScalar(this);
    }
    
    // ----------------------------- Methods
    public boolean hasCompatibility(CPUSuport suport){
        return (suport != CPUSuport.SMT) ? true : isSuperScalar;
    }
    
    public boolean start(){
        startPossibility = !threads.isEmpty() && pipeline != null;
        
        if(startPossibility){
            for(ProcessThread pt : threads.values()){
                if(!pt.isFinnish()){
                    threadsIDs.add(pt.getID());
                }
            }
        }
        
        return startPossibility;
    }
    
    public boolean isPossibleStart(){
        return startPossibility;
    }
    
    public void restart(){
        currentCycle = 0;
        currentIPC = 0;
        numOfBubble = 0;
        numOfDispatchInstructions = 0;
        numOfInstructionsEnd = 0;
        
        threadsIDs.clear();
        
        for(ProcessThread pt : threads.values()){
            pt.restart();
        }
        
        if(pipeline != null && cpuSuport == SMT){
            pipeline.setSuportSMT();
        } 
        
        if(pipeline != null) pipeline.restart();
        
        start();
    }
    
    // ---------------------------- Run Code
    public void runCycle(){
        if(pipeline != null && threads != null){
            pipeline.runCycle();
        }
    }

    // ----------------------------- Setters
    public void setIsSuportScalar(boolean isSuperScalar){
        this.isSuperScalar = isSuperScalar;
//        if(this.isSuperScalar != isSuperScalar){
//        
            if(isSuperScalar)   setPipeline(new PipelineSuperScalar(this));
            else                setPipeline(new PipelineScalar(this));

            this.cpuSuport = CPUSuport.NONE;
//        
//        }
        
        restart();
    }
    
    public void setThreads(ArrayList<ProcessThread> threads){
        for(ProcessThread t : threads){
            this.threads.put(t.getID(),t);
        }
        
        restart();
    }
    
    public void setCpuSuport(CPUSuport suport){
        if(hasCompatibility(suport)){
            this.cpuSuport = suport;
            restart();
        }
    }
    
    public void setPipeline(Pipeline pipeline){
        this.pipeline = pipeline;
    }
    
    // ----------------------------- Getters
    public Integer getCurrentCycle(){
        return currentCycle;
    }
    
    public double getCurrentIPC(){
        double ipc = 0;
        if(currentCycle > 0){
            ipc = (double) numOfDispatchInstructions/ (double) currentCycle;
        }
        return ipc;
    }
    
    public double getCurrentCPI(){
        double cpi = 0;
        if(currentCycle > 0 && this.numOfDispatchInstructions > 0){
            cpi = (double) currentCycle / (double) numOfDispatchInstructions;
        }
        return cpi;
    }
    
    public Integer getNumOfBubble(){
        return numOfBubble;
    }
    
    public Integer getNumOfDispatchInstructions(){
        return numOfDispatchInstructions;
    }
    
    public Integer getNumOfInstructionEnd(){
        return numOfInstructionsEnd;
    }
    
    public boolean getIsSuperScalar(){
        return isSuperScalar;
    }
    
    public CPUSuport getCPUSuport(){
        return cpuSuport;
    }
    
    // ----------------------------- Pipeline Delegate Methods
    public void incrementCycle(){
        currentCycle++;
    }
    
    public void endInstructions(ArrayList<Instruction> endInstructions){
        if(endInstructions != null){
           
            for(Instruction i : endInstructions){
                if(!i.getID().contains("bubble")){
                    numOfInstructionsEnd += 1;
                }
                
                if(!i.getID().contains("Ociosa")){
                    ociosa += 1;
                }
                
                ProcessThread t = threads.get(i.getThreadID());
                t.setEndInstruction(i);
            }
        }
    }
    
    @Override
    public ArrayList<Instruction> fetchInstructions(Integer numOfInstructions){
        ArrayList<Instruction> instructions = (isSuperScalar) ? fetchSuperScalar(numOfInstructions) : fetchScalar(numOfInstructions);
        this.numOfDispatchInstructions += instructions.size();
        return instructions;
    }
    
    public ArrayList<Instruction> fetchScalar(Integer numOfInstructions){
        ArrayList<Instruction> instructions = new ArrayList<>();
        ProcessThread thread = null;
        
        if(threadsIDs.isEmpty()){
            start();
        }
        
        int i = 0;
        while(i < numOfInstructions && !threadsIDs.isEmpty()){
        
            switch(cpuSuport){
                case NONE:            
                    thread = threads.get(threadsIDs.get(0));
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    break;
                case IMT:
                    String threadID = threadsIDs.remove(0); // Remove do Inicio
                    threadsIDs.add(threadID); // Adiciona no fim
                    
                    thread = threads.get(threadID);
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    break;
                case BMT:
                    thread = threads.get(threadsIDs.get(0));
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    COUNT_BLOCK++;
                    
                    if((COUNT_BLOCK % MAX_BLOCK_SIZE) == 0){
                        String pt = threadsIDs.remove(0);
                        threadsIDs.add(pt);
                    }
                    break;
                case SMT:
                    break;
            }
            
            
            if(thread != null && thread.isFinnish()){
                threadsIDs.remove(thread.getID());
            }

            i++;
        }
        
        return instructions;
    }
    
    public ArrayList<Instruction> fetchSuperScalar(Integer numOfInstructions){
        ArrayList<Instruction> instructions = new ArrayList<>();
        ProcessThread thread = null;
        
        if(threadsIDs.isEmpty()){
            start();
        }
        
        boolean FLAG = false;
        int i = 0;
        while(i < numOfInstructions && !threadsIDs.isEmpty() && !FLAG){
        
            switch(cpuSuport){
                case NONE:            
                    thread = threads.get(threadsIDs.get(0));
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    break;
                case IMT:
                    String threadID = threadsIDs.get((currentCycle % threadsIDs.size()));

                    thread = threads.get(threadID);
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    break;
                case BMT:
                    thread = threads.get(threadsIDs.get(0));
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    COUNT_BLOCK++;
                    
                    if(((currentCycle + 1) % MAX_BLOCK_SIZE) == 0){
                        String pt = threadsIDs.remove(0);
                        threadsIDs.add(pt);
                        FLAG = true;
                    }
                    
                    break;
                case SMT:
                    threadID = threadsIDs.remove(0); // Remove do Inicio
                    threadsIDs.add(threadID); // Adiciona no fim
                    
                    thread = threads.get(threadID);
                    instructions.add(thread.getNextInstruction(getCurrentCycle()));
                    break;
            }
            
            
            if(thread != null && thread.isFinnish()){
                threadsIDs.remove(thread.getID());
                if(cpuSuport != SMT) FLAG = true; 
            }

            i++;
        }
        
        return instructions; 
    }

    @Override
    public boolean checkDepency(Instruction i) {
        boolean has = false;
        ProcessThread threadSource = threads.get(i.getThreadID());
        for (Instruction older : threadSource.getDispatchInstructions()) {
            if(older.getStartCycle() < i.getStartCycle()){
                has = CheckDependency.check(i, older);
            }
        }
        return has;
    }

    @Override
    public Instruction generateBubble() {
        numOfBubble++;
        return new Instruction();
    }
    
    
    public ArrayList<PipelineStage> getStagesValues(){
        ArrayList<PipelineStage> stages = null;
        
        if(!isSuperScalar) {
            PipelineScalar p = (PipelineScalar) pipeline;
            stages = p.getStages();
        } else {
            PipelineSuperScalar p = (PipelineSuperScalar) pipeline;
            stages = p.getStages();
        }
        
        return stages;
    }
    
    public ArrayList<Instruction> getBufferReorder(){
        PipelineSuperScalar p = (PipelineSuperScalar) pipeline;
        return p.getBufferReorder();
    }

    @Override
    public void decodeInstruction(Instruction i) {
        ProcessThread t = threads.get(i.getThreadID());
        t.decodeInstruction(i);
    }
    
    public double getOciosas(){
        double media =0;
        
        if (currentCycle > 0){
            media = ociosa / currentCycle;
        }
        
        return media;
    }
        
    
}
