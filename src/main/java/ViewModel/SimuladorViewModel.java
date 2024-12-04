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
import java.util.stream.Stream;

/**
 *
 * @author Walla
 */
public class SimuladorViewModel {
    
    public static SimuladorViewModel instance = new SimuladorViewModel();
    
    public Processador processador;
    
    private SimuladorViewModel(){
        this.processador = new Processador();
    }
    
    public void saveProcessador(){
        String filePath = processador.nome + ".arq";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(processador.getHardwareDescription());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadProcessador(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null && !line.contains(".ini")){
                line = reader.readLine();
            }
            
            if (line.contains(".ini")){
                line = reader.readLine();
                while(line != null && !line.contains(".fim")){
                    switch(line.substring(0, 7)){
                        case "...name":
                            processador.nome = line.substring(8);
                            break;
                        case "...type":
                            processador.type = TypeProcessador.typeProcess(line.substring(8));
                            break;
                        case ".suport":
                            processador.suport = TypeSuport.typeSuport(line.substring(8));
                            break;
                        case ".thread":
                            Integer n = Integer.parseInt(line.substring(8));
                            processador.numThreads = (n > 0 && n < 5) ? n : 1;
                            break;
                    }
                    
                    line = reader.readLine();
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadCode(String path){
        ArrayList<Thread> threads = new ArrayList();
        ArrayList<Instruction> instructions = new ArrayList();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null && !line.contains(".ini")){
                line = reader.readLine();
            }
            
            if (line.contains(".ini")){
                line = reader.readLine();
                String currentThreadId =  StringUteis.randomID(5);
                while(line != null && !line.contains(".fim")){
                    if(line.contains(".thread")){
                        threads.add(new Thread(currentThreadId, instructions, false));
                        currentThreadId = StringUteis.randomID(5);
                    } else {
                        String[] exp = line.replaceAll("\\(", ",").replaceAll(",", " ").split(" ");
                        
                        if(exp.length == 4) {
                        
                            OpCode opCode = OpCode.opCodeFromString(exp[0]);
                            Registers r1 = Registers.registerFromString(exp[1]);
                            Registers r2 = Registers.registerFromString(exp[2]);
                            Registers r3 = Registers.registerFromString(exp[3]);
                            
                            if(opCode != OpCode.INVALID_OPCODE && r1 != Registers.invalid_register && 
                               r2 != Registers.invalid_register && r3 != Registers.invalid_register) {
                                
                                instructions.add(new Instruction(currentThreadId, opCode, r1, r2, r3));
                            }
                        }
                    }
                    
                    line = reader.readLine();
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
