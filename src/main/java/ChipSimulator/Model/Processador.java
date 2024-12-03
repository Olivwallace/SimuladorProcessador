/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChipSimulator.Model;

import ChipSimulator.Model.Enums.TypeProcessador;
import ChipSimulator.Model.Enums.TypeSuport;

/**
 *
 * @author Walla
 */
public class Processador {
    // Definições do Processador
    private String nome;
    private TypeProcessador type;
    private TypeSuport suport;
    private int numThreads;
    
    // Pipeline Do Processador
    
    
    
    @Override
    public String toString(){
        return String.format(
        """
            <html> 
                <b>Nome:</b> %s
                <b>Type:</b> %s | <b>Suporte:</b> %s | <b>Threads:</b> %d
            </html>
        """, nome, type.toString(), suport.toString(), numThreads);
    }
    
}
