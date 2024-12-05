/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ChipSimulator.Model.Enums;

/**
 *
 * @author Walla
 */
public enum PipelineState {
    OUT(0),
    IF(1),
    ID(2),
    EX(3),
    MEM(4),
    WB(5);
    
    public final int value;
    
    PipelineState(int i){
        this.value = i;
    }
}
