/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.Registers;
import Uteis.StringUteis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Walla
 */
public class BufferReorder {
    
    public static BufferReorder instance = new BufferReorder();
    
    private int MAX_SIZE_BUFFER = 64;
    
    private ArrayList<ReorderBufferItem> buffersReorder = new ArrayList<>();
    
    private HashMap<String,ReorderBufferItem> bufferMap = new HashMap<>();
    
    private BufferReorder(){}
    
    public void setBufferSize(Integer size){ this.MAX_SIZE_BUFFER = size; }
    
    public boolean addNewInstruction(Instruction i){
        boolean isAdd = i.getIsInReorderBuffer();
        
        if(!isAdd && buffersReorder.size() < MAX_SIZE_BUFFER){
            String bufferCode = StringUteis.randomID(5);
            ReorderBufferItem reorderItem; 
            reorderItem = new ReorderBufferItem(bufferCode, i.getThreadID(), i, i.getR1());

            buffersReorder.add(reorderItem);
            bufferMap.put(bufferCode, reorderItem);
            
            String[] renameRegisters = i.getRenameRegister();
            renameRegisters[0] = bufferCode;
            i.setRenameRegisters(renameRegisters);
            
            i.setIsInReorderBuffer();
            
            isAdd = true;
        } 
        
        return isAdd;
    }
    
    public ArrayList<Instruction> promoveCommitInstructions(Integer max) {
        ArrayList<Instruction> commitIt = new ArrayList<>();
        if (!buffersReorder.isEmpty()) {
            ArrayList<String> threadsBlocked = new ArrayList<>();
            String firstBlock = "";
            Set<String> processedBuffers = new HashSet<>(); // Mantém o controle dos buffers já processados.

            // Processa os buffers enquanto não atinge o máximo permitido ou a lista não está vazia.
            while (!buffersReorder.isEmpty()) {
                ReorderBufferItem buffer = buffersReorder.remove(0); // Remove o primeiro item da lista.

                // Verifica se o buffer já foi processado (evitando loops).
                if (processedBuffers.contains(buffer.getReorderBufferCode())) {
                    buffersReorder.add(0,buffer); // Reinsere no final para reprocessamento posterior.
                    break; // Sai do loop para evitar processamento infinito.
                }

                // Adiciona o buffer ao conjunto de processados.
                processedBuffers.add(buffer.getReorderBufferCode());

                if (buffer.getIsReady() && buffer.getIsValid() && !threadsBlocked.contains(buffer.getThreadCode())) {
//                    bufferMap.remove(buffer.getReorderBufferCode());
                    commitIt.add(buffer.getInstruction());

                    // Verifica se atingiu o limite máximo de commits.
                    if (commitIt.size() >= max) {
                        break;
                    }
                } else {
                    // Se a instrução não pode ser confirmada, bloqueia a thread e reinserta no buffer.
                    if (firstBlock.isEmpty()) {
                        firstBlock = buffer.getReorderBufferCode();
                    }

                       if (!threadsBlocked.contains(buffer.getThreadCode())) {
                            threadsBlocked.add(buffer.getThreadCode());
                        }
                    buffersReorder.add(buffer); // Reinsere no final para tentar processar mais tarde.
                }
            }
        }
    

    return commitIt;
}

    
    public ArrayList<Instruction> getReadyInstructions(){
        ArrayList<Instruction> instructions = new ArrayList<>();
        
        for(ReorderBufferItem i : buffersReorder){
            if(i.getIsReady()){
               instructions.add(i.getInstruction());
            }
        }
        
        return instructions;
    }
    
    public boolean checkRenamedRegisters(String r2, String r3, Instruction i){
        boolean r2Valid = true, r3Valid = true;
        
        if(r2 != null){
            r2Valid = bufferMap.get(r2).getIsValid();
        }
        
        if(r3 != null){
            r3Valid = bufferMap.get(r3).getIsValid();
        }
        
        
        return r2Valid && r3Valid;
    }
    
    public ReorderBufferItem getBufferPointer(String buffer){
        return bufferMap.get(buffer);
    }
      
    public int windowStage(){
        return MAX_SIZE_BUFFER - buffersReorder.size();
    }
    
    public void print(){
        System.out.println("\n\n------------ BUFFER ----------------");
        for(int i = 0; i < buffersReorder.size(); i++){
            System.out.println(buffersReorder.get(i).toString());
        }
    }
}
