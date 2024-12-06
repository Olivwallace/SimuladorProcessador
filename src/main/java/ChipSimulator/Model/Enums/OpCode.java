/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model.Enums;

/**
 *
 * @author Walla
 */
public enum OpCode {
    AND,
    OR,
    XOR,
    SLL,
    SLR,
    ADD,
    ADDI,
    SUB,
    SLT,
    LW,
    SW,
    BEQ,
    BNE,
    ORI,
    INVALID_OPCODE;
    
    public static OpCode opCodeFromString(String opCode){
        if (opCode != null) {
            try {
                return OpCode.valueOf(opCode.toUpperCase());
            } catch (IllegalArgumentException e) {
                return INVALID_OPCODE;
            }
        }
        return INVALID_OPCODE;
    }
}
