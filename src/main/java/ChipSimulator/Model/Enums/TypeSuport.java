/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ChipSimulator.Model.Enums;

/**
 *
 * @author Walla
 */
public enum TypeSuport {
    NONE,
    IMT,
    BMT,
    SMT;
    
    public static TypeSuport typeSuport(String type){
        if (type != null) {
            try {
                return TypeSuport.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                return NONE;
            }
        }
        return NONE;
    }
    
    @Override
    public String toString(){
        switch(this){
            case IMT:
                return "IMT";
            case BMT:
                return "BMT";
            case SMT:
                return "SMT";
        }
        return "Sem Suporte";
    }
}
