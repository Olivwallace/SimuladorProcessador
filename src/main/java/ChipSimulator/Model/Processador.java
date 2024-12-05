/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Interfaces.CallabackProcessador;
import ChipSimulator.Model.Enums.TypeProcessador;
import ChipSimulator.Model.Enums.TypeSuport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Walla
 */
public class Processador implements CallabackProcessador {
    // Definições do Processador
    public String nome;
    public TypeProcessador type;
    public TypeSuport suport;
    public Integer numThreads;
    
    // Pipeline Do Processador
    public HashMap<String,Integer> indexThread = new HashMap<>();
    public ArrayList<Thread> threads = new ArrayList<>();
    public Pipeline pipeline;
    Integer cicloAtual = 0;
    
    private Integer currentThread = 0;
    private Integer BMTBlock = 0;
    
    // Informações Uteis
    Integer IPC = 0;
    Integer ciclos = 0;
    Integer ciclosBolha = 0;
    Integer instrucoesEx = 0;
    Integer instrucoesFinalizadas = 0;
    Integer threadsFinalizas = 0;
   
    public void reiniciar(){
        IPC = 0;
        ciclos = 0;
        ciclosBolha = 0;
        instrucoesEx = 0;
        threadsFinalizas = 0;
        currentThread = 0;
        instrucoesFinalizadas = 0;
        cicloAtual = 0;
        
        pipeline.reiniciar();
        
        for(Thread t : threads){
            t.reiniciar();
        }
    }
        
    
    public Processador(){
        this.nome = "";
        this.type = TypeProcessador.NONE;
        this.suport = TypeSuport.NONE;
        this.numThreads = 1;
    }
    
    public void setDefinicoes(String nome, TypeProcessador type, TypeSuport suport, Integer numThreads){
        this.nome = nome;
        this.type = type;
        this.suport = suport;
        this.numThreads = numThreads;
        
        pipeline = new Pipeline(numThreads, this);
    }
    
    public void setThreads(ArrayList<Thread> threads){
        this.threads = threads;
        for(int i = 0; i < threads.size(); i++){
            indexThread.put(threads.get(i).threadID, i);
        }
    }
    
    public void execute(){
        switch(this.suport){
            case IMT:
                executeIMT();
                break;
            case BMT:
                executeBMT();
                break;
            case SMT:
                executeSMT();
                break;
            case NONE:
                executeDefault();
                break;
        }
    }
    
    public void executeDefault(){
        Integer IFcapacity = pipeline.runNextCicle(cicloAtual);
        
        int i = 0;

        while(i < IFcapacity && currentThread < threads.size()){
            pipeline.fetch(threads.get(currentThread).getNextInstruction(cicloAtual));
            
            if(threads.get(currentThread).isFinnish){
                currentThread++; 
                threadsFinalizas++;
            }
            i++;
        }
        
        cicloAtual++;
    }
    
    public void executeIMT(){
        Integer IFcapacity = pipeline.runNextCicle(cicloAtual);
        
        int i = 0;

        while(i < IFcapacity && (threadsFinalizas < threads.size())){
            
            if(currentThread == threads.size()){
                currentThread = 0;
            }
            
            if(threads.get(currentThread).isFinnish){
                threadsFinalizas++;
            } else {
                pipeline.fetch(threads.get(currentThread++).getNextInstruction(cicloAtual));
                i++;
            }
            
        }
        
        cicloAtual++;
    }
    
    public void executeBMT(){
         Integer IFcapacity = pipeline.runNextCicle(cicloAtual);
        
        int i = 0;

        while(i < IFcapacity && (threadsFinalizas < threads.size())){
            
            if(currentThread == threads.size()){
                currentThread = 0;
            }
            
            if(threads.get(currentThread).isFinnish){
                threadsFinalizas++;
                BMTBlock = 0;
            } else {
                pipeline.fetch(threads.get(currentThread).getNextInstruction(cicloAtual));
                BMTBlock++;
                i++;
            }
            
            if(BMTBlock == 3){
                BMTBlock = 0;
                currentThread++;
            }
            
        }
        
        cicloAtual++;
    }
    
    public void executeSMT(){
        
    }
    
    public void getCurrentThread(){
        
    }
    
    public void notifyEndInstruction(Instruction i){
        Integer index = indexThread.get(i.getThread());
        if(index < threads.size()){
            threads.get(index).endInstruction(i,cicloAtual);
            instrucoesFinalizadas++;
        }
    }
    
    public boolean hasDependencia(Instruction i){
        boolean has = false;
        Integer index = indexThread.get(i.getThread());
        if(index < threads.size()){
            has = threads.get(index).hasDependecia(i);
        }
        
        return has;
    }
    
    public void notifyBolha(Instruction i){
        Integer index = indexThread.get(i.getThread());
        if(index < threads.size()){
            threads.get(index).notifyBolha();
        }
    }
    
    @Override
    public String toString(){
        return String.format(
        """
        <html> 
        <b>Nome:</b> %s <br>
        <b>Tipo:</b> %s | <b>Suporte:</b> %s | <b>Threads:</b> %d
        </html>
        """, nome, type.toString(), suport.toString(), numThreads);
    }
    
    public String getEstatisticas(){
        return String.format("""
                             <html>
                             <b>IPC:</b> %d <br>
                             <b>Ciclos:</b> %d <br>
                             <b>#Bolhas:</b> %d <br>
                             <b>#Instrucões:</b> %d <br>
                             <b>Threads Concluidas:</b> %d
                             </html>
                             """, IPC, ciclos, ciclosBolha, instrucoesEx, threadsFinalizas);
    }
    
    public String getEstatisticas(Integer thread){
        if(thread < threads.size()){ 
            Thread t = threads.get(thread);
            return String.format("""
                                 <html>
                                 <b>PC:</b> %d | [ %s ] <br>
                                 <b>Execultadas:</b> %d Inst. <br>
                                 <b>Dispachadas:</b> %d Inst. <br>
                                 <b>Ciclo Inicio:</b> %s | <b>Ciclo Fim:</b> %s <br>
                                 <b>CPI:</b> %s  <br>
                                 <b>#Bolhas Geradas:</b> %d
                                 </html>
                                 """, t.pc, t.descricaoNextInstruction(), t.countFinnishInstructions, 
                                 t.dispachInstructions, t.getCicloIni(), t.getCicloFim(), t.getCPI(), t.numOfBolhas);
        } else {
            return  """
                    <html>
                    <b>PC:</b> 0 | [ Sem instruções ] <br>
                    <b>Execultadas:</b> 0 Inst. <br>
                    <b>Dispachadas:</b> 0 Inst. <br>
                    <b>Ciclo Inicio:</b> - | <b>Ciclo Fim:</b> - <br>
                    <b>CPI:</b> 0  <br>
                    <b>#Bolhas Geradas:</b> 0 <br>
                    </html>
                    """;
        }
    }
    
    public String getHardwareDescription(){
        return String.format("""
                             .ini
                             ...name %s
                             ...type %s
                             .suport %s
                             .thread %d
                             .fim
                             """, nome, type.toString(), suport.toString(), numThreads);
    }
    
}
