/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModel;

import Enums.CPUSuport;
import Enums.OPCode;
import Models.CPU;
import Models.Instruction;
import Models.PipelineStage;
import Models.ProcessThread;
import Uteis.StringUteis;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Walla
 */
public class SimuladorViewModel {
    
    private final CPU cpu = new CPU();
    private boolean isStart = false;
    private HashMap<String,Color> threadColor = new HashMap<>(); 
    private final Color[] colors = new Color[]{Color.CYAN, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.RED}; 
    
    // Run
    public void runCycle(boolean isAuto){
        if(!isStart) isStart = true;
        
        if (!isAuto){
            cpu.runCycle();
        }
    }
    
    // Setters
    public void setSuport(CPUSuport suport){
        cpu.setCpuSuport(suport);
    }
    
    public void setCPUMode(boolean isSuperScalar){
        cpu.setIsSuportScalar(isSuperScalar);
    }
    
    // Getters
    public boolean enableInitMode(){
       return cpu.isPossibleStart();
    }
    
    public boolean getIsSuperScalar(){
        return cpu.getIsSuperScalar();
    }
    
    public CPUSuport getSuport(){
        return cpu.getCPUSuport();
    }
    
    public boolean isStart(){
        return isStart;    
    }
    
    public ArrayList<PipelineStage> getStagesValues(){
        return cpu.getStagesValues();
    }
    
    public ArrayList<Instruction> getBuffer(){
        return cpu.getBufferReorder();
    }
    
    // Methods
    
    public void processCode(String path){
        ArrayList<ProcessThread> threads = new ArrayList();
        Integer currentThread = -1;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null && !line.contains(".ini")){
                line = reader.readLine();
            }
            
            if (line != null && line.contains(".ini")){
                line = reader.readLine();
                while(line != null && !line.contains(".fim")){
                    if(line.contains(".thread")){
                        ProcessThread t = new ProcessThread();
                        t.setID(StringUteis.randomID(5));
                        t.setInstructions(new ArrayList());
                        threads.add(t);
                        currentThread++;
                    } else {
                        Instruction i = Instruction.instructionForString(line);
                        if(i != null){
                            threads.get(currentThread).addInstruction(i);
                        }
                    }
                    
                    line = reader.readLine();
                }
            }
        } catch (IOException e) { 
            e.printStackTrace();
        }
        
        if (!threads.isEmpty()){
            cpu.setThreads(threads);
            for(int i = 0; i < threads.size(); i++){
                threadColor.put(threads.get(i).getID(), colors[i % colors.length]);
            }
        }
    }
    
    public Color getColorInstruction(Instruction i){
        return threadColor.get(i.getThreadID());
    }

    public String setInformations() {
        
        return String.format(
        """
        <html>
        <b>Ciclo atual:</b> %d<br>
        <b>CPI: </b> %.2f <br>
        <b>IPC: </b> %.2f <br>    
        <b>Bolhas: </b> %d<br>
        <b>Instruções Despachadas: </b> %d <br>
        <b>Instruções Finalizadas: </b> %d
        </html>
        """, 
        cpu.getCurrentCycle(),
        cpu.getCurrentCPI(),
        cpu.getCurrentIPC(),
        cpu.getNumOfBubble(),
        cpu.getNumOfDispatchInstructions(),
        cpu.getNumOfInstructionEnd());
        
        
    }
    
    public void restart(){
        cpu.restart();
    }
}
