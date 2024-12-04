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
    NONE,
    Escalar,
    SuperEscalar;
    
    public static TypeProcessador typeProcess(String type){
        if(type.toLowerCase().contains("super escalar")){
            return SuperEscalar;
        } else if(type.toLowerCase().contains("escalar")) {
            return Escalar;
        }
        return NONE;
    }
    
    @Override
    public String toString(){
        switch(this){
            case Escalar:
                return "Escalar";
            case SuperEscalar:
                return "Super Escalar";
        }
        return "Indefinido";
    }
}
