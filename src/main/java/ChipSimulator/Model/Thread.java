/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public class Thread {
    public final String threadID;
    public final ArrayList<Instruction> instructions;
    public Integer pc = 0;
    public Integer numOfInstructions = 0;
    public Integer dispachInstructions = 0;
    public Integer countFinnishInstructions = 0;
    public Integer numOfBolhas = 0;
    public Integer cicloInicioThread = -1;
    public Integer cicloFimThread = -1;
    public Integer cicloFimUltimaInst = -1;
    public boolean isFinnish = false;
    
    public Thread(String threadID, ArrayList<Instruction> instructions){
        this.threadID = threadID;
        this.instructions = instructions;
    }
    
    public void addNewInstruction(Instruction i){
        i.setThreadOrigem(this.threadID);
        instructions.add(i);
        numOfInstructions++;
    }
    
    public Instruction getNextInstruction(Integer ciclo){
        Instruction instruction = null;
        if (pc < numOfInstructions){
            
            if(pc == 0){
                cicloInicioThread = ciclo;
            }
            
            instruction = instructions.get(pc);
            dispachInstructions++;
            pc++;
            isFinnish = (pc >= numOfInstructions);
        }
        return instruction;
    }
    
    public void incrementFinnish(Integer ciclo){
        this.countFinnishInstructions++;
        this.cicloFimUltimaInst = ciclo;
        
        if(pc >= numOfInstructions){
            cicloFimThread = ciclo;
        }
    }
    
    public void incrementBolha(){
        this.numOfBolhas++;
    }
    
    
    // Getters
    public String getCicloIni(){
        return (cicloInicioThread != -1) ? String.valueOf(cicloInicioThread) : "-";
    }
    
    public String getCicloFim(){
        return (cicloFimThread != -1) ? String.valueOf(cicloFimThread) : "-";
    }
    
    public String descricaoNextInstruction(){
        return (pc < numOfInstructions) ? instructions.get(pc).toString() : "Fim Instruções";
    }
    
    public String getCPI(){
        String cpi = "-";
        if(countFinnishInstructions > 0){
            cpi = String.valueOf((cicloFimUltimaInst - cicloInicioThread) / countFinnishInstructions);
        } 
        return cpi;
    }
}
