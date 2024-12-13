/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Enums.CPUSuport;

/**
 *
 * @author Walla
 */
public interface EstatisticasDelegate {
    void setSuport(CPUSuport suport);
    CPUSuport getSuport();
    boolean getIsSuperScalar();
    void setCPUMode(boolean isSuperScalar);
    boolean enableInitMode();
    boolean isStart();
    void importCode();
    void runCycle(boolean isAuto);
    String setInformations();
    void restart();
}
