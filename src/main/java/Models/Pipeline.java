/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Interfaces.PipelineDelegate;

/**
 *
 * @author Walla
 */
public class Pipeline {
    public final PipelineDelegate pipelineDelegate;
    public Pipeline(PipelineDelegate pd){
        this.pipelineDelegate = pd;
    }
    
    public void runCycle(){
        System.out.println("Cycle");
    }
    
    public void setSuportSMT(){
        
    }
    
    public void removeSuportSMT(){
        
    }
    
    public void restart(){}
    
}
