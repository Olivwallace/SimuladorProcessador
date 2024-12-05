/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.TypeProcessador;
import ChipSimulator.Model.Enums.TypeSuport;
import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public class Processador {
    // Definições do Processador
    public String nome;
    public TypeProcessador type;
    public TypeSuport suport;
    public Integer numThreads;
    
    // Pipeline Do Processador
    ArrayList<Thread> threads = new ArrayList<>();
    Pipeline pipeline;
    Integer cicloAtual;
    
    // Informações Uteis
    Integer IPC = 0;
    Integer ciclos = 0;
    Integer ciclosBolha = 0;
    Integer instrucoesEx = 0;
    Integer threadsFinalizas = 0;
    
    
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
        
        pipeline = new Pipeline(numThreads);
    }
    
    public void setThreads(ArrayList<Thread> threads){
        this.threads = threads;
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
