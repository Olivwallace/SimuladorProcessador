/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import static Enums.PipelineStageEnum.EX;
import static Enums.PipelineStageEnum.ID;
import static Enums.PipelineStageEnum.IF;
import static Enums.PipelineStageEnum.MEM;
import static Enums.PipelineStageEnum.WB;
import Interfaces.PipelineViewDelegate;
import Models.Instruction;
import Models.PipelineStage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Walla
 */
public class PipelineSuperEscalar extends javax.swing.JPanel {

    PipelineViewDelegate viewDelegate;
    
    
    /**
     * Creates new form PipelineSuperEscalar
     */
    public PipelineSuperEscalar(PipelineViewDelegate viewDelegate) {
        initComponents();
        this.viewDelegate = viewDelegate;
    }
    
    public void clearAllStages(){
        if_panel.removeAll();
        if_panel.setBackground(Color.WHITE);
        
        id_panel.removeAll();
        id_panel.setBackground(Color.WHITE);
        
        janela_ula.removeAll();
        janela_ula.setBackground(Color.WHITE);
        
        ula.removeAll();
        ula.setBackground(Color.WHITE);
        
        janela_branch.removeAll();
        janela_branch.setBackground(Color.WHITE);
        
        branch.removeAll();
        branch.setBackground(Color.WHITE);
        
        janela_mult.removeAll();
        janela_mult.setBackground(Color.WHITE);
        
        mult_div.removeAll();
        mult_div.setBackground(Color.WHITE);
        
        janela_lwsw.removeAll();
        janela_lwsw.setBackground(Color.WHITE);
        
        ex_swlw.removeAll();
        ex_swlw.setBackground(Color.LIGHT_GRAY);
        
        mem_lwsw.removeAll();
        mem_lwsw.setBackground(Color.LIGHT_GRAY);
        
        wb_panel.removeAll();
        wb_panel.setBackground(Color.WHITE);
    }
    
    
    public void setBufferStage(ArrayList<Instruction> buffer){
        buffer_panel.removeAll();
        buffer_panel.setBackground(Color.WHITE);
        setPanelVertical(buffer,buffer_panel);
    }
    
    public void setStagesValues(ArrayList<PipelineStage> stages,ArrayList<Instruction> buffer){
        setStagesValues(stages);
        setBufferStage(buffer);
    }
    
    public void setStagesValues(ArrayList<PipelineStage> stages){
        clearAllStages();
        for(PipelineStage s : stages){ 
            switch(s.stage){
                case IF:
                    setPanelVertical(s.queueInstructions, if_panel);
                    break;
                case ID:
                    setPanelVertical(s.queueInstructions, id_panel);
                    break;
                case WINDOW_ULA:
                    setPanelHorizontal(s.queueInstructions,janela_ula);
                    break;
                case WINDOW_BRANCH:
                    setPanelHorizontal(s.queueInstructions,janela_branch);
                    break;
                case WINDOW_LSLW:
                    setPanelHorizontal(s.queueInstructions,janela_lwsw);
                    break;
                case WINDOW_MULT:
                    setPanelHorizontal(s.queueInstructions,janela_mult);
                    break;
                case ULA:
                    setPanel(s.queueInstructions, ula);
                    break;
                case BRANCH:
                    setPanel(s.queueInstructions, branch);
                    break;
                case MULT:
                    setPanel(s.queueInstructions, mult_div);
                    break;
                case EX:
                    setPanel(s.queueInstructions, ex_swlw);
                    break;
                case MEM:
                    setPanel(s.queueInstructions, mem_lwsw);
                    break;
                case WB:
                    setPanelVertical(s.queueInstructions, wb_panel);
                    break;
            }
        }
    }
    
    public void setPanelVertical(ArrayList<Instruction> instructions, JPanel panel){
        int line = 0, colum = 0;
        
        if(!instructions.isEmpty()){
            for(Instruction i : instructions){
                Color c = viewDelegate.getColorInstruction(i);
                
                JPanel intern = new JPanel();
                intern.setBackground(c);
                JLabel label = new JLabel(i.toString(), SwingConstants.CENTER);
                intern.add(label);
                
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = colum; // Coluna 0
                gbc.gridy = line++; // Linha 0
                gbc.weightx = 1.0; // Expansão horizontal
                gbc.weighty = 1.0; // Expansão vertical
                
                gbc.ipady = 12;
                gbc.fill = GridBagConstraints.BOTH; // Preenche espaço disponível
                
                panel.add(intern, gbc);
            }
        }
        
        this.repaint();
    }
    
    public void setPanel(ArrayList<Instruction> instructions, JPanel panel){
        
        if(!instructions.isEmpty()){
            for(Instruction i : instructions){
                Color c = viewDelegate.getColorInstruction(i);
                panel.setBackground(c);
                JLabel label = new JLabel(i.toString(), SwingConstants.CENTER);
                label.setForeground(Color.BLACK);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(label, BorderLayout.CENTER);
            }
        }
        
        this.repaint();
    }
    
    public void setPanelHorizontal(ArrayList<Instruction> instructions, JPanel panel){
        int line = 0, colum = 0;
        
        if(!instructions.isEmpty()){
            for(Instruction i : instructions){
                Color c = viewDelegate.getColorInstruction(i);
                
                JPanel intern = new JPanel();
                intern.setBackground(c);
                
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = colum++; // Coluna 0
                gbc.gridy = line; // Linha 0
                gbc.weightx = 1.0; // Expansão horizontal
                gbc.weighty = 1.0; // Expansão vertical
                gbc.fill = GridBagConstraints.BOTH; // Preenche espaço disponível
                
                panel.add(intern, gbc);
            }
        }
        
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel25 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        wb_panel = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        id_panel = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        janela_ula = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(6, 32767));
        ula = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        janela_mult = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(6, 32767));
        mult_div = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        janela_lwsw = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(6, 32767));
        jPanel37 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        ex_swlw = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        mem_lwsw = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        janela_branch = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(12, 0), new java.awt.Dimension(12, 0), new java.awt.Dimension(6, 32767));
        branch = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        buffer_panel = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        if_panel = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(1, 0));
        setPreferredSize(new java.awt.Dimension(680, 352));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {124, 8, 124, 8, 248, 8, 124, 8, 124};
        layout.rowHeights = new int[] {0};
        setLayout(layout);

        jPanel25.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel25.setMaximumSize(new java.awt.Dimension(136, 288));
        jPanel25.setMinimumSize(new java.awt.Dimension(124, 384));
        jPanel25.setPreferredSize(new java.awt.Dimension(122, 384));

        jLabel12.setBackground(new java.awt.Color(51, 51, 51));
        jLabel12.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("WB");
        jLabel12.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel12.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel12.setPreferredSize(new java.awt.Dimension(19, 24));

        wb_panel.setBackground(new java.awt.Color(255, 255, 255));
        wb_panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        wb_panel.setMinimumSize(new java.awt.Dimension(106, 320));
        wb_panel.setPreferredSize(new java.awt.Dimension(108, 319));
        wb_panel.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(wb_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wb_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel25, gridBagConstraints);

        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel26.setMaximumSize(new java.awt.Dimension(136, 288));
        jPanel26.setMinimumSize(new java.awt.Dimension(124, 384));
        jPanel26.setPreferredSize(new java.awt.Dimension(122, 384));

        jLabel13.setBackground(new java.awt.Color(51, 51, 51));
        jLabel13.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ID");
        jLabel13.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel13.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel13.setPreferredSize(new java.awt.Dimension(19, 24));

        id_panel.setBackground(new java.awt.Color(255, 255, 255));
        id_panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        id_panel.setMinimumSize(new java.awt.Dimension(106, 320));
        id_panel.setPreferredSize(new java.awt.Dimension(108, 319));
        id_panel.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(id_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel26, gridBagConstraints);

        jPanel27.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel27.setMaximumSize(new java.awt.Dimension(136, 288));
        jPanel27.setMinimumSize(new java.awt.Dimension(124, 384));
        jPanel27.setPreferredSize(new java.awt.Dimension(122, 384));

        jLabel14.setBackground(new java.awt.Color(51, 51, 51));
        jLabel14.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("JANELA + EX");
        jLabel14.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel14.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel14.setPreferredSize(new java.awt.Dimension(19, 24));

        jPanel3.setMinimumSize(new java.awt.Dimension(106, 100));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {240};
        jPanel3.setLayout(jPanel3Layout);

        jPanel6.setMaximumSize(new java.awt.Dimension(32767, 74));
        jPanel6.setMinimumSize(new java.awt.Dimension(240, 80));
        jPanel6.setPreferredSize(new java.awt.Dimension(212, 80));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel17.setBackground(new java.awt.Color(51, 51, 51));
        jLabel17.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("ULA");
        jLabel17.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel17.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel17.setPreferredSize(new java.awt.Dimension(19, 24));
        jPanel6.add(jLabel17, java.awt.BorderLayout.PAGE_START);

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.X_AXIS));

        janela_ula.setBackground(new java.awt.Color(255, 255, 255));
        janela_ula.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        janela_ula.setMaximumSize(new java.awt.Dimension(40, 32767));
        janela_ula.setMinimumSize(new java.awt.Dimension(40, 0));
        janela_ula.setPreferredSize(new java.awt.Dimension(40, 4));
        janela_ula.setLayout(new java.awt.GridBagLayout());
        jPanel10.add(janela_ula);
        jPanel10.add(filler1);

        ula.setBackground(new java.awt.Color(255, 255, 255));
        ula.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ula.setMinimumSize(new java.awt.Dimension(100, 0));
        ula.setLayout(new java.awt.BorderLayout());
        jPanel10.add(ula);

        jPanel6.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6, new java.awt.GridBagConstraints());

        jPanel7.setMaximumSize(new java.awt.Dimension(32767, 74));
        jPanel7.setMinimumSize(new java.awt.Dimension(240, 80));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel20.setBackground(new java.awt.Color(51, 51, 51));
        jLabel20.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("MULT/DIV");
        jLabel20.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel20.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel20.setPreferredSize(new java.awt.Dimension(19, 24));
        jPanel7.add(jLabel20, java.awt.BorderLayout.PAGE_START);

        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.X_AXIS));

        janela_mult.setBackground(new java.awt.Color(255, 255, 255));
        janela_mult.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        janela_mult.setMaximumSize(new java.awt.Dimension(40, 32767));
        janela_mult.setMinimumSize(new java.awt.Dimension(40, 0));
        janela_mult.setPreferredSize(new java.awt.Dimension(40, 4));
        janela_mult.setLayout(new java.awt.GridBagLayout());
        jPanel15.add(janela_mult);
        jPanel15.add(filler4);

        mult_div.setBackground(new java.awt.Color(255, 255, 255));
        mult_div.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mult_div.setMinimumSize(new java.awt.Dimension(100, 0));
        mult_div.setLayout(new java.awt.BorderLayout());
        jPanel15.add(mult_div);

        jPanel7.add(jPanel15, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel3.add(jPanel7, gridBagConstraints);

        jPanel8.setMaximumSize(new java.awt.Dimension(32767, 74));
        jPanel8.setMinimumSize(new java.awt.Dimension(240, 80));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel19.setBackground(new java.awt.Color(51, 51, 51));
        jLabel19.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("LW/SW");
        jLabel19.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel19.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel19.setPreferredSize(new java.awt.Dimension(19, 24));
        jPanel8.add(jLabel19, java.awt.BorderLayout.PAGE_START);

        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.LINE_AXIS));

        janela_lwsw.setBackground(new java.awt.Color(255, 255, 255));
        janela_lwsw.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        janela_lwsw.setMaximumSize(new java.awt.Dimension(40, 32767));
        janela_lwsw.setMinimumSize(new java.awt.Dimension(40, 100));
        janela_lwsw.setPreferredSize(new java.awt.Dimension(40, 100));
        janela_lwsw.setLayout(new java.awt.GridBagLayout());
        jPanel36.add(janela_lwsw);
        jPanel36.add(filler3);

        java.awt.GridBagLayout jPanel37Layout = new java.awt.GridBagLayout();
        jPanel37Layout.columnWidths = new int[] {90, 4, 90};
        jPanel37Layout.rowHeights = new int[] {0};
        jPanel37.setLayout(jPanel37Layout);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel12.setMaximumSize(new java.awt.Dimension(90, 32767));
        jPanel12.setMinimumSize(new java.awt.Dimension(90, 56));
        jPanel12.setPreferredSize(new java.awt.Dimension(90, 56));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel21.setBackground(new java.awt.Color(51, 51, 51));
        jLabel21.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("EX");
        jLabel21.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel21.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel21.setPreferredSize(new java.awt.Dimension(19, 24));
        jPanel12.add(jLabel21, java.awt.BorderLayout.PAGE_START);

        ex_swlw.setBackground(new java.awt.Color(204, 204, 204));
        ex_swlw.setLayout(new java.awt.BorderLayout());
        jPanel12.add(ex_swlw, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel37.add(jPanel12, gridBagConstraints);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.setPreferredSize(new java.awt.Dimension(90, 56));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel22.setBackground(new java.awt.Color(51, 51, 51));
        jLabel22.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("MEM");
        jLabel22.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel22.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel22.setPreferredSize(new java.awt.Dimension(19, 24));
        jPanel16.add(jLabel22, java.awt.BorderLayout.PAGE_START);

        mem_lwsw.setBackground(new java.awt.Color(204, 204, 204));
        mem_lwsw.setLayout(new java.awt.BorderLayout());
        jPanel16.add(mem_lwsw, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel37.add(jPanel16, gridBagConstraints);

        jPanel36.add(jPanel37);

        jPanel8.add(jPanel36, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel3.add(jPanel8, gridBagConstraints);

        jPanel9.setMaximumSize(new java.awt.Dimension(32767, 74));
        jPanel9.setMinimumSize(new java.awt.Dimension(240, 80));
        jPanel9.setPreferredSize(new java.awt.Dimension(240, 80));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel18.setBackground(new java.awt.Color(51, 51, 51));
        jLabel18.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BRANCH");
        jLabel18.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel18.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel18.setPreferredSize(new java.awt.Dimension(19, 24));
        jPanel9.add(jLabel18, java.awt.BorderLayout.PAGE_START);

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.X_AXIS));

        janela_branch.setBackground(new java.awt.Color(255, 255, 255));
        janela_branch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        janela_branch.setMaximumSize(new java.awt.Dimension(40, 32767));
        janela_branch.setMinimumSize(new java.awt.Dimension(40, 0));
        janela_branch.setPreferredSize(new java.awt.Dimension(40, 4));
        janela_branch.setLayout(new java.awt.GridBagLayout());
        jPanel14.add(janela_branch);
        jPanel14.add(filler2);

        branch.setBackground(new java.awt.Color(255, 255, 255));
        branch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        branch.setMinimumSize(new java.awt.Dimension(100, 0));
        branch.setLayout(new java.awt.BorderLayout());
        jPanel14.add(branch);

        jPanel9.add(jPanel14, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jPanel9, gridBagConstraints);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel27, gridBagConstraints);

        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel28.setMaximumSize(new java.awt.Dimension(136, 288));
        jPanel28.setMinimumSize(new java.awt.Dimension(124, 384));
        jPanel28.setPreferredSize(new java.awt.Dimension(122, 384));

        jLabel15.setBackground(new java.awt.Color(51, 51, 51));
        jLabel15.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BUFFER");
        jLabel15.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel15.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel15.setPreferredSize(new java.awt.Dimension(19, 24));

        buffer_panel.setBackground(new java.awt.Color(255, 255, 255));
        buffer_panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buffer_panel.setMinimumSize(new java.awt.Dimension(106, 320));
        buffer_panel.setPreferredSize(new java.awt.Dimension(108, 319));
        buffer_panel.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buffer_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buffer_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel28, gridBagConstraints);

        jPanel29.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel29.setMaximumSize(new java.awt.Dimension(136, 288));
        jPanel29.setMinimumSize(new java.awt.Dimension(124, 384));
        jPanel29.setPreferredSize(new java.awt.Dimension(122, 384));

        jLabel16.setBackground(new java.awt.Color(51, 51, 51));
        jLabel16.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("IF");
        jLabel16.setMaximumSize(new java.awt.Dimension(19, 24));
        jLabel16.setMinimumSize(new java.awt.Dimension(19, 24));
        jLabel16.setPreferredSize(new java.awt.Dimension(19, 24));

        if_panel.setBackground(new java.awt.Color(255, 255, 255));
        if_panel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        if_panel.setMinimumSize(new java.awt.Dimension(106, 320));
        if_panel.setPreferredSize(new java.awt.Dimension(106, 320));
        if_panel.setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(if_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(if_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel29, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel branch;
    private javax.swing.JPanel buffer_panel;
    private javax.swing.JPanel ex_swlw;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel id_panel;
    private javax.swing.JPanel if_panel;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel janela_branch;
    private javax.swing.JPanel janela_lwsw;
    private javax.swing.JPanel janela_mult;
    private javax.swing.JPanel janela_ula;
    private javax.swing.JPanel mem_lwsw;
    private javax.swing.JPanel mult_div;
    private javax.swing.JPanel ula;
    private javax.swing.JPanel wb_panel;
    // End of variables declaration//GEN-END:variables
}
