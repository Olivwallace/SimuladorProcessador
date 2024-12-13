/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author Walla
 */
public enum PipelineStageEnum {
    IF(0),
    ID(1),
    EX(2),
    MEM(3),
    WB(4),
    OUT(-1),
    
    // SUPER ESCALAR
    WINDOW_ULA(2),
    WINDOW_BRANCH(2),
    WINDOW_MULT(2),
    WINDOW_LSLW(2),
    ULA(3),
    BRANCH(3),
    MULT(3),
    LWSW(3),
    BUFF(4);
    
    private final int value;
    
    PipelineStageEnum(int i){
        this.value = i;
    }
    
    public Integer getValue(){
        return value;
    }
}
