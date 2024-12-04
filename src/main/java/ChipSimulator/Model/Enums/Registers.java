/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model.Enums;

/**
 *
 * @author Walla
 */
public enum Registers {
    zero,   // x0 - Registrador zero
    ra,     // x1 - Return Address
    sp,     // x2 - Stack Pointer
    t0,     // x5 - Registrador temporário t0
    t1,     // x6 - Registrador temporário t1
    t2,     // x7 - Registrador temporário t2
    t3,     // x28 - Registrador temporário t3
    t4,     // x29 - Registrador temporário t4
    t5,     // x30 - Registrador temporário t5
    t6,     // x31 - Registrador temporário t6
    s0,     // x8 - Saved Register s0 (Frame Pointer)
    s1,     // x9 - Saved Register s1
    s2,     // x18 - Saved Register s2
    s3,     // x19 - Saved Register s3
    s4,     // x20 - Saved Register s4
    s5,     // x21 - Saved Register s5
    s6,     // x22 - Saved Register s6
    s7,     // x23 - Saved Register s7
    s8,     // x24 - Saved Register s8
    s9,     // x25 - Saved Register s9
    s10,    // x26 - Saved Register s10
    s11,    // x27 - Saved Register s11
    v0,     // x10 - Valor de retorno 0
    v1,     // x11 - Valor de retorno 1
    a1,     // x12 - Argumento 1
    a2,     // x13 - Argumento 2
    a3,     // x14 - Argumento 3
    a4,     // x15 - Argumento 4
    im,
    invalid_register;     // Marcar imediado
    
    public static Registers registerFromString(String register){
        if (register != null) {
            try {
                return Registers.valueOf(register);
            } catch (IllegalArgumentException e) {
                return invalid_register;
            }
        }
        return invalid_register;
    }
}

