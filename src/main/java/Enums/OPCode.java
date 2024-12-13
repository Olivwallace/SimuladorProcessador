/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 * Define Operações Disponiveis No Simulador
 * @author Walla
 */
public enum OPCode {
    AND("and"),
    OR("or"),
    XOR("xor"),
    SLL("sll"),
    SLR("slr"),
    ADD("add"),
    ADDI("addi"),
    SUB("sub"),
    SLT("slt"),
    LW("lw"),
    SW("sw"),
    BEQ("beq"),
    BNE("bne"),
    ORI("ori"),
    MULT("mult"),
    INVALID_OPCODE("null");
    
    String opCode;
    
    OPCode(String op){
        this.opCode = op;
    }
    
    public String getValue(){
        return opCode;
    }
    
    public static OPCode opCodeFromString(String opCode){
        if (opCode != null) {
            try {
                return OPCode.valueOf(opCode.toUpperCase());
            } catch (IllegalArgumentException e) {
                return INVALID_OPCODE;
            }
        }
        return INVALID_OPCODE;
    }
    
    public UnitExeOpCode unitDestination(){
        return switch (this) {
            case LW, SW -> UnitExeOpCode.OPCODE_SWLW;
            case BEQ, BNE -> UnitExeOpCode.OPCODE_BEQ;
            case MULT -> UnitExeOpCode.OPCODE_MULT;
            default -> UnitExeOpCode.OPCODE_ULA;
        };
    }
}
