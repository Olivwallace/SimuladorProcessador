/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ChipSimulator.Interfaces;

import ChipSimulator.Model.Instruction;

/**
 *
 * @author Walla
 */
public interface CallabackProcessador {
    public void notifyEndInstruction(Instruction i);
    public boolean hasDependencia(Instruction i);
    public void notifyBolha(Instruction i);
}
