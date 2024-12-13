/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uteis;

import Models.Instruction;

/**
 *
 * @author Walla
 */
public class CheckDependency {
    /**
     * Avalia Dependêcia de Saída
     * @param latest Instrucção Entrando no Pipeline
     * @param older Instrução Já Avançada no Pipeline
     * @return Se há Dependêcia de Saída
     */
    public static boolean hasWAW(Instruction latest, Instruction older){
        boolean has = false;
        if (!latest.getID().equals(older.getID())){
            
            has = latest.getR1().equals(older.getR1()); 
            
        }
        return has;
    }
    
    /**
     * Avalia Dependêcia Verdadeira
     * @param latest Instrucção Entrando no Pipeline
     * @param older Instrução Já Avançada no Pipeline
     * @return Se há Dependêcia de Escrita antes de Leitura
     */
    public static boolean hasWAR(Instruction latest, Instruction older){
        boolean has = false;
        if (!latest.getID().equals(older.getID())){
            
            has = latest.getR2().equals(older.getR1()) ||
                  latest.getR3().equals(older.getR1());
            
        }
        return has;
    }
    
    /**
     * Avalia Anti-Dependêcia
     * @param latest Instrucção Entrando no Pipeline
     * @param older Instrução Já Avançada no Pipeline
     * @return Se há Dependêcia falsa de Leitura antes de Escrita
     */
    public static boolean hasRAW(Instruction latest, Instruction older){
        boolean has = false;
        if (!latest.getID().equals(older.getID())){
            
            has = latest.getR1().equals(older.getR2()) || 
                  latest.getR1().equals(older.getR3());
            
        }
        return has;
    }
    
    /**
     * Avalia Adiantamento de Dados
     * @param latest Instrucção Entrando no Pipeline
     * @param older Instrução Já Avançada no Pipeline
     * @return Se Há o Adiantamento entre Estagios MEM -> ID | EX -> ID
     */
    public static boolean hasForwarding(Instruction latest, Instruction older){
        boolean mem_id = (older.isMemoryInstruction() && ((older.getStage().getValue() - latest.getStage().getValue()) > 2));
        boolean ex_id = (!older.isMemoryInstruction() && ((older.getStage().getValue() - latest.getStage().getValue()) > 1));
        
        return mem_id || ex_id;
    }
    
    /**
     * Avalia Dependêcia Pipeline Escalar
     * @param latest Instrucção Entrando no Pipeline
     * @param older Instrução Já Avançada no Pipeline
     * @return Se há TRUE | Se não há FALSE
     */
    public static boolean check(Instruction latest, Instruction older){
        boolean has = false;
        if(!latest.getID().equals(older.getID())){
            if(!hasForwarding(latest, older) && hasWAR(latest, older)){
                has = true; 
            }
        }
        return has;
    }
}
