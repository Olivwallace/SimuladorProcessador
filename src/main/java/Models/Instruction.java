/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.OPCode;
import Enums.PipelineStageEnum;
import Enums.Registers;
import Uteis.StringUteis;

/**
 *
 * @author Walla
 */
public class Instruction {
    
    // Attributes
    private String id;
    private String threadID;
    private boolean isMemoryInstruction;
    private String instructionString;
    
    // Control
    private PipelineStageEnum currentStage;
    private Integer cycleStart;
    private Integer cycleEnd;
    
    // Components
    private OPCode opCode;
    private Registers[] registers = new Registers[3];
    private final Integer[] registersValues = new Integer[3];
    
    
    // Window Intruction
    private boolean isInReorderBuffer = false;
    private String[] renameRegisters = new String[3];
    private boolean readyToEx = false;
    
    // Init
    public Instruction(){
        this.id = "bubble";
    }
    
    public Instruction(String name){
        this.id = name;
    }
    
    // Setters
    public void setInstructionString(String instruction){
        this.instructionString = instruction;
    }
    
    public void setReadyToEx(boolean isReady){
        this.readyToEx = isReady;
    }
    
//    public void setReoderBufferCodeDestination(ReorderBufferItem reoderBufferCodeDestination){
//        this.reoderBufferCodeDestination = reoderBufferCodeDestination;
//    }
    
//    public void setIsReady(){
//        this.isReady = true;
//    }
    
    public void setIsInReorderBuffer(){
        this.isInReorderBuffer = true;
    }
    
    public void setStartCycle(Integer cycle){
        this.cycleStart = cycle;
    }
    
    public void setEndCycle(Integer cycle){
        this.cycleEnd = cycle;
    }
    
    public void setStage(PipelineStageEnum stage){
        this.currentStage = stage;
    }
    
    public void setOpCode(OPCode opCode){
        this.opCode = opCode;
        if(opCode == OPCode.LW || opCode == OPCode.SW) isMemoryInstuction(true);
    }
    
    public void isMemoryInstuction(boolean is){
        this.isMemoryInstruction = is;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public void setThreadID(String id){
        this.threadID = id;
    }
    
    public void setRegisters(Registers[] registers){
        this.registers = registers;
    }
    
    public void setValueR1(Integer r1){
        registersValues[0] = r1;
    }
    
    public void setValueR2(Integer r2){
        registersValues[1] = r2;
    }
    
    public void setValueR3(Integer r3){
        registersValues[2] = r3;
    }
    
    public void setRenameRegisters(String[] rename){
        this.renameRegisters = rename;
    }
    
    
    // Getters
//    public ReorderBufferItem getReoderBufferCodeDestination(){
//        return reoderBufferCodeDestination;
//    }
    
//    public boolean getIsReady(){
//        return isReady;
//    }
    
    public boolean getIsInReorderBuffer(){
        return isInReorderBuffer;
    }
    
    public boolean isReadyToEx(){
        return readyToEx;
    }
  
    public String[] getRenameRegister(){
        return renameRegisters;
    }
    
    public boolean isMemoryInstruction(){
        return isMemoryInstruction;
    }
    
    public PipelineStageEnum getStage(){
        return currentStage;
    }
    
    public OPCode getOpCode(){
        return opCode;
    }
    
    public String getID(){
        return id;
    }
    
    public String getThreadID(){
        return threadID;
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
    
    public Integer getR1Value(){
        return registersValues[0];
    }
    
    public Integer getR2Value(){
        return registersValues[1];
    }
    
    public Integer getR3Value(){
        return registersValues[2];
    }
    
    public String getInstructionString(){
        return instructionString;
    }
    
    public Integer getStartCycle(){
        return cycleStart;
    }
    
    public Integer getEndCycle(){
        return cycleEnd;
    }
    
    public static Instruction instructionForString(String str){
        Instruction i = null;
        String[] exp = StringUteis.parseInstruction(str);
                        
        if(exp.length == 4) {
            OPCode opCode = OPCode.opCodeFromString(exp[0]); 
            Registers r1 = Registers.registerFromString(exp[1]);
            Registers r2 = Registers.registerFromString(exp[2]);
            Registers r3 = Registers.registerFromString(exp[3]);

            if(opCode != OPCode.INVALID_OPCODE && r1 != Registers.invalid_register && 
               r2 != Registers.invalid_register && r3 != Registers.invalid_register) {
                
                i = new Instruction();
                i.setInstructionString(str);
                i.setID(StringUteis.randomID(5));
                i.setOpCode(opCode);
                
                if(i.getOpCode() != OPCode.SW && i.getOpCode() != OPCode.BEQ && i.getOpCode() != OPCode.BNE){
                    i.setRegisters(new Registers[]{r1, r2, r3});
                } else if(i.getOpCode() != OPCode.SW){
                    i.setRegisters(new Registers[]{r3, r1, r2});
                } else{
                    i.setRegisters(new Registers[]{r2, r1, r3});
                }
                
                
            }
        }
        
        return i;
    }
    
    @Override
    public String toString(){
        String value = "";
        if(this.getID().equals("bubble")) {
            value = String.format("""
                                    <html>
                                    <div style='text-align: center; text-color:black;'>
                                    <b>Bolha</b>
                                    </div>
                                    </html>
                                    """);
        } else if(currentStage.getValue() < 1){
            value = String.format("""
                                              <html>
                                              <div style='text-align: center;text-color:black;'>
                                              <b>(%s)</b><br>
                                              %s
                                              </div>
                                              </html>
                                              """, getThreadID(), getInstructionString());
        } else {
            value = String.format("""
                                              <html>
                                              <div style='text-align: center;text-color:black;'>
                                              <b>(%s)</b><br>
                                              %s %s,%s,%s<br>
                                              </div>
                                              </html>
                                              """,
                    getThreadID(),
                    getOpCode().getValue(),
                    getR1().getValue(),
                    getR2().getValue(),
                    getR3().getValue()); 
        }
        
        return value;
    }
}
