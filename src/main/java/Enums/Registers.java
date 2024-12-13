/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author Walla
 */
public enum Registers {
    zero("$zero"),     // Registrador zero
    ra("$ra"),         // Return Address
    sp("$sp"),         // Stack Pointer
    t0("$t0"),         // Registrador temporário t0
    t1("$t1"),         // Registrador temporário t1
    t2("$t2"),         // Registrador temporário t2
    t3("$t3"),         // Registrador temporário t3
    t4("$t4"),         // Registrador temporário t4
    t5("$t5"),         // Registrador temporário t5
    t6("$t6"),         // Registrador temporário t6
    s0("$s0"),         // Saved Register s0 (Frame Pointer)
    s1("$s1"),         // Saved Register s1
    s2("$s2"),         // Saved Register s2
    s3("$s3"),         // Saved Register s3
    s4("$s4"),         // Saved Register s4
    s5("$s5"),         // Saved Register s5
    s6("$s6"),         // Saved Register s6
    s7("$s7"),         // Saved Register s7
    s8("$s8"),         // Saved Register s8
    s9("$s9"),         // Saved Register s9
    s10("$s10"),       // Saved Register s10
    s11("$s11"),       // Saved Register s11
    v0("$v0"),         // Valor de retorno 0
    v1("$v1"),         // Valor de retorno 1
    a1("$a1"),         // Argumento 1
    a2("$a2"),         // Argumento 2
    a3("$a3"),         // Argumento 3
    a4("$a4"),         // Argumento 4
    im("$im"),         // Imediato
    invalid_register("null"); // Registrador inválido
    
    String register;
    
    Registers(String r){
        this.register = r;
    }
    
    public String getValue(){
        return register;
    }
    
    public static Registers registerFromString(String register){
        if (register != null) {
            try {
                return Registers.valueOf(register);
            } catch (IllegalArgumentException e) {
                try {
                    return Registers.im;

                } catch (NumberFormatException er){
                    return invalid_register;   
                }
            }
        }
        return invalid_register;
    }
}
