/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.Dependencias;
import ChipSimulator.Model.Enums.OpCode;
import ChipSimulator.Model.Enums.PipelineState;
import ChipSimulator.Model.Enums.Registers;

/**
 *
 * @author Walla
 */
public class Instruction {
    private String threadOrigen;
    public String id;
    public PipelineState state = PipelineState.OUT;
    private OpCode opCode;
    private Registers[] registers;
    private String instructionString;
    public boolean isBolha = false;
    private int cycleIni = -1;
    private int cycleFin = -1;
    
    public void reiniciar(){
        state = PipelineState.OUT;
        cycleIni = -1;
        cycleFin = -1;
        isBolha = false;
    }
    
    public Instruction(String thread){
        this.threadOrigen = thread;
        instructionString = "Bolha";
        isBolha = true;
    }
    
    public Instruction(String id,OpCode opCode, Registers[] registers, String instruction){
        this.opCode = opCode;
        
        instructionString = instruction;
        
        this.registers = registers;
        this.id = id;
    }
    
    public void setThreadOrigem(String thread){
        this.threadOrigen = thread;
    }
    
    public void setStartCycle(int cycle){
        this.cycleIni = cycle;
    }
    
    public void setFinnishCycle(int cycle){
        this.cycleFin = cycle;
    }
    
    public int getStartCycle(){
        return this.cycleIni;
    }
    
    public int getFinnishCycle(){
        return this.cycleFin;
    }
    
    public Registers getR1(){
        return registers[0];
    }
    
    public Registers getR2(){
        return registers[1];
    }
    
    public Registers getR3(){
        return registers[2];
    }
    
    public String getThread(){
        return this.threadOrigen;
    }
    
    public boolean needMemory(){
        return opCode == OpCode.LW || opCode == OpCode.SW;
    }
    
    public Dependencias comparaDependecia(Instruction i){
        Dependencias d = Dependencias.SemDependencia;
        
        if (!i.id.equals(this.id)){
            boolean escrita_leitura = this.getR2().equals(i.getR1()) || this.getR3().equals(i.getR1());
            boolean escrita_escrita = this.getR1().equals(i.getR1());
            boolean leitura_escrita = this.getR1().equals(i.getR2()) || this.getR1().equals(i.getR3());

            if(escrita_leitura){
                d = Dependencias.Verdadeira;
            } else if(escrita_escrita){
                d = Dependencias.DependenciaSaida;
            } else if(leitura_escrita){
                d = Dependencias.AntiDependencia;
            }
        }
        
        return d;
    }
    
    public boolean hasAdiantamento(Instruction i){
        boolean mem_id = (i.needMemory() && ((i.state.value - this.state.value) > 2));
        boolean ex_id = (!i.needMemory() && ((i.state.value - this.state.value) > 1));
        
        return mem_id || ex_id;
    }
    
   
    @Override
    public String toString(){
        return this.instructionString;
    }
}
