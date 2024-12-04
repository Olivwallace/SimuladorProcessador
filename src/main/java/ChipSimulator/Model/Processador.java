/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.TypeProcessador;
import ChipSimulator.Model.Enums.TypeSuport;

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
    Pipeline pipeline;
    
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
        
        pipeline = new Pipeline(type, numThreads);
    }
    
    public void setThreads(Thread[] threads){
        if(pipeline != null){
            pipeline.setThreads(threads);
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
