/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Enums.OPCode;
import Enums.Registers;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Walla
 */
public class ProcessThread {

    // ----------------------------- Attributes
    private String id;

    // -------- Components
    private ArrayList<Instruction> instructions;
    private ArrayList<Instruction> dispatchInstruction = new ArrayList<>();
    private HashMap<Registers, BankOfRegister> bankOfRegisters = new HashMap<>();

    // -------- Registers/Counts
    private Integer currentPC = 0;
    private boolean isFinnish = false;

    // ----------------------------- Init
    public ProcessThread() {
        int i = 0;
        for (Registers r : Registers.values()) {
            if (!r.equals(Registers.im) && !r.equals(Registers.invalid_register)) {
                BankOfRegister bd = new BankOfRegister(r, i, true, "");
                bankOfRegisters.put(r, bd);
            }
            i++;
        }
    }

    // ----------------------------- Methods
    public void restart() {
        currentPC = 0;
        dispatchInstruction.clear();
        for (BankOfRegister r : bankOfRegisters.values()) {
            r.restart();
        }
    }

    // ----------------------------- Setters
    public void setID(String id) {
        this.id = id;
    }

    public void setIsFinnish(boolean isFinnish) {
        this.isFinnish = isFinnish;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        for (Instruction i : instructions) {
            i.setThreadID(getID());
        }
        this.instructions = instructions;
    }

    public void addInstruction(Instruction i) {
        if (i != null && instructions != null) {
            i.setThreadID(id);
            instructions.add(i);
        }
    }

    // ----------------------------- Getters
    public String getID() {
        return id;
    }

    public boolean isFinnish() {
        return isFinnish;
    }

    public HashMap<Registers, BankOfRegister> getRegisters() {
        return bankOfRegisters;
    }

    public Integer processSize() {
        return instructions.size();
    }

    // ----------------------------- Pipeline Delegate Methods
    public void decodeInstruction(Instruction instruction) {

        // Ignorar $im no registro R2
        Registers r2R = instruction.getR2();
        if (r2R != Registers.im) {
            BankOfRegister r2 = bankOfRegisters.get(r2R);
            if (r2 != null && !r2.getIsValid()) {
                instruction.getRenameRegister()[1] = r2.getRenameID();
            }
        }

        // Ignorar $im no registro R3
        Registers r3R = instruction.getR3();
        if (r3R != Registers.im) {
            BankOfRegister r3 = bankOfRegisters.get(r3R);
            if (r3 != null && !r3.getIsValid()) {
                instruction.getRenameRegister()[2] = r3.getRenameID();
            }
        }

        if (instruction.getR1() != Registers.im) {
            BankOfRegister r1 = bankOfRegisters.get(instruction.getR1());
            r1.setRenameID(instruction.getRenameRegister()[0]);
            r1.setInvalid();
        }
    }

    public Instruction getNextInstruction(Integer cycle) {
        Instruction i = null;
        if (!isFinnish) {
            i = instructions.get(currentPC);
            i.setStartCycle(cycle);
            dispatchInstruction.add(i);
            currentPC++;
            setIsFinnish(currentPC >= instructions.size());
        }
        return i;
    }

    public void setEndInstruction(Instruction instruction) {
        if (!instruction.isMemoryInstruction()) {
            BankOfRegister bdr = bankOfRegisters.get(instruction.getR1());
            if (bdr != null) {
                bdr.setValue(instruction.getR1Value());
                bdr.setValid();
            }
        }
        instruction.setEndCycle(currentPC);
        dispatchInstruction.remove(instruction);
    }

    public ArrayList<Instruction> getDispatchInstructions() {
        return dispatchInstruction;
    }
}
