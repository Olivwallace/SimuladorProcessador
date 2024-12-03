/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ChipSimulator.Model.Enums;

/**
 *
 * @author Walla
 */
public enum TypeProcessador {
    Escalar,
    SuperEscalar;
    
    @Override
    public String toString(){
        switch(this){
            case Escalar:
                return "Escalar";
            case SuperEscalar:
                return "Super Escalar";
        }
        return "";
    }
}
