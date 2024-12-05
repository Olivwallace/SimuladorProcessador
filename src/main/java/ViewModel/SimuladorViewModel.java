/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModel;

import ChipSimulator.Model.Enums.OpCode;
import ChipSimulator.Model.Enums.Registers;
import ChipSimulator.Model.Enums.TypeProcessador;
import ChipSimulator.Model.Enums.TypeSuport;
import ChipSimulator.Model.Instruction;
import ChipSimulator.Model.Processador;
import ChipSimulator.Model.Thread;
import ChipSimulator.Uteis.StringUteis;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Walla
 */
public class SimuladorViewModel {
    
    public static SimuladorViewModel instance = new SimuladorViewModel();
    
    public boolean hasArquitetura = false;
    public boolean hasCode = false;
    
    public Processador processador;
    
    private SimuladorViewModel(){
        this.processador = new Processador();
    }
    
    // --------------------------------------------- LOAD ARQS
    
    public void saveProcessador(){
        String filePath = processador.nome + ".arq";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(processador.getHardwareDescription());
            setHasArquitetura();
        } catch (IOException e) {
        }
    }
    
    public void loadProcessador(String path){
        String name = "", type = "", suport = "";
        Integer num_threads = 1;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null && !line.contains(".ini")){
                line = reader.readLine();
            }
            
            if (line != null && line.contains(".ini")){
                line = reader.readLine();
                while(line != null && !line.contains(".fim")){
                    switch(line.substring(0, 7)){
                        case "...name" -> name = line.substring(8);
                        case "...type" -> type = line.substring(8);
                        case ".suport" -> suport = line.substring(8);
                        case ".thread" -> {
                            Integer n = Integer.valueOf(line.substring(8));
                            num_threads = (n > 0 && n < 5) ? n : 1;
                        }
                    }
                    
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
        }
        
        processador.setDefinicoes(
                name, 
                TypeProcessador.typeProcess(type), 
                TypeSuport.typeSuport(suport), 
                num_threads);
        
        setHasArquitetura();
    }
    
    public void loadCode(String path){
        ArrayList<Thread> threads = new ArrayList();
        ArrayList<Instruction> instructions = new ArrayList();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null && !line.contains(".ini")){
                line = reader.readLine();
            }
            
            if (line != null && line.contains(".ini")){
                line = reader.readLine();
                while(line != null && !line.contains(".fim")){
                    if(line.contains(".thread")){
                        instructions = new ArrayList();
                        threads.add(new Thread(StringUteis.randomID(5), instructions));
                    } else {
                        
                        String[] exp = StringUteis.parseInstruction(line);
                        
                        if(exp.length == 4) {
                        
                            OpCode opCode = OpCode.opCodeFromString(exp[0]);
                            Registers r1 = Registers.registerFromString(exp[1]);
                            Registers r2 = Registers.registerFromString(exp[2]);
                            Registers r3 = Registers.registerFromString(exp[3]);
                            
                            if(opCode != OpCode.INVALID_OPCODE && r1 != Registers.invalid_register && 
                               r2 != Registers.invalid_register && r3 != Registers.invalid_register) {
                                
                                instructions.add(new Instruction(opCode, new Registers[]{r1, r2, r3}, line));
                            }
                        }
                    }
                    
                    line = reader.readLine();
                }
            }
        } catch (IOException e) { 
        }
        
        if (!threads.isEmpty()){
            processador.setThreads(threads);
            setHasCode();
        }
    }
    
    public void setHasArquitetura(){
        this.hasArquitetura = true;
    }
    
    public void setHasCode(){
        this.hasCode = true;
    }
    
    
}
