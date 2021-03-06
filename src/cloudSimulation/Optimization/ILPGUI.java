/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ovatman
 */
public class ILPGUI extends javax.swing.JFrame {

    private DefaultCategoryDataset resultA;
    private DefaultCategoryDataset resultB;
    private DefaultCategoryDataset resultC;
    private JFreeChart chartA;
    private JFreeChart chartB;
    private JFreeChart chartC;
    private final SimEngine engin;

    /**
     * Creates new form ILPGUI
     */
    public ILPGUI(SimEngine engin) {
        this.engin = engin;
        initComponents();
        createCharts();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Virtual Machines");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setMaximumSize(new java.awt.Dimension(334, 144));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 238, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("StepSim");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clicked(evt);
            }
        });

        jButton2.setText("ResetSim");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetClicked(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jButton1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clicked

        engin.step();
        String s=engin.printLogTo();
        jTextArea1.setText(jTextArea1.getText()+s);
        ArrayList<VMach> vms=engin.getVMs();
        
        VMach temp;
        temp=vms.get(0);
        
        for(App a:temp.getApps()){
            resultA.addValue(a.getCpuCon(), a.getName(), "CPU");
            resultA.addValue(a.getRamCon(), a.getName(), "RAM");
            resultA.addValue(a.getBwCon(), a.getName(), "BW");
            resultA.addValue(a.getStorageCon(), a.getName(), "STO");
        } 
        
        chartA = ChartFactory.createStackedBarChart(
                null, null, null, resultA,
                PlotOrientation.VERTICAL, false, false, false);

        NumberAxis rangeAxisA = (NumberAxis) ((CategoryPlot) chartA.getPlot()).getRangeAxis();
        rangeAxisA.setRange(0, 100);      
        
        ChartPanel cpA = new ChartPanel(chartA);
        cpA.setPreferredSize(new Dimension(350, 200));
        cpA.setVisible(true);
        
        jPanel3.removeAll();
        jPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel3.add(cpA);
        jPanel3.revalidate();
        
        temp=vms.get(1);
        
        for(App a:temp.getApps()){
            resultB.addValue(a.getCpuCon(), a.getName(), "CPU");
            resultB.addValue(a.getRamCon(), a.getName(), "RAM");
            resultB.addValue(a.getBwCon(), a.getName(), "BW");
            resultB.addValue(a.getStorageCon(), a.getName(), "STO");
        } 
        
        chartB = ChartFactory.createStackedBarChart(
                null, null, null, resultB,
                PlotOrientation.VERTICAL, false, false, false);

        NumberAxis rangeAxisB = (NumberAxis) ((CategoryPlot) chartB.getPlot()).getRangeAxis();
        rangeAxisB.setRange(0, 100);      
        
        ChartPanel cpB = new ChartPanel(chartB);
        cpB.setPreferredSize(new Dimension(350, 200));
        cpB.setVisible(true);
        
        jPanel2.removeAll();
        jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel2.add(cpB);
        jPanel2.revalidate();
        
        temp=vms.get(2);
        
        for(App a:temp.getApps()){
            resultC.addValue(a.getCpuCon(), a.getName(), "CPU");
            resultC.addValue(a.getRamCon(), a.getName(), "RAM");
            resultC.addValue(a.getBwCon(), a.getName(), "BW");
            resultC.addValue(a.getStorageCon(), a.getName(), "STO");
        } 
        
        chartC = ChartFactory.createStackedBarChart(
                null, null, null, resultC,
                PlotOrientation.VERTICAL, false, false, false);

        NumberAxis rangeAxisC = (NumberAxis) ((CategoryPlot) chartC.getPlot()).getRangeAxis();
        rangeAxisC.setRange(0, 100);      
        
        ChartPanel cpC = new ChartPanel(chartC);
        cpC.setPreferredSize(new Dimension(350, 200));
        cpC.setVisible(true);
        
        jPanel4.removeAll();
        jPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel4.add(cpC);
        jPanel4.revalidate();
    }//GEN-LAST:event_clicked

    private void resetClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetClicked
        engin.reset();
        createCharts();
        jTextArea1.setText("");
    }//GEN-LAST:event_resetClicked

    /**
     * @param args the command line arguments
     */
    public static void go(final SimEngine engin) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ILPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ILPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ILPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ILPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */





        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ILPGUI(engin).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    private void createCharts() {
        resultA = new DefaultCategoryDataset();
        resultB = new DefaultCategoryDataset();
        resultC = new DefaultCategoryDataset();

        chartA = ChartFactory.createStackedBarChart(
                null, null, null, resultA,
                PlotOrientation.VERTICAL, false, false, false);

        NumberAxis rangeAxisA = (NumberAxis) ((CategoryPlot) chartA.getPlot()).getRangeAxis();
        rangeAxisA.setRange(0, 100);

        chartB = ChartFactory.createStackedBarChart(
                null, null, null, resultB,
                PlotOrientation.VERTICAL, false, false, false);

        NumberAxis rangeAxisB = (NumberAxis) ((CategoryPlot) chartB.getPlot()).getRangeAxis();
        rangeAxisB.setRange(0, 100);

        chartC = ChartFactory.createStackedBarChart(
                null, null, null, resultC,
                PlotOrientation.VERTICAL, false, false, false);

        NumberAxis rangeAxisC = (NumberAxis) ((CategoryPlot) chartC.getPlot()).getRangeAxis();
        rangeAxisC.setRange(0, 100);

//        chart.setBackgroundPaint(Color.yellow);
//        chart.getTitle().setPaint(Color.blue);
//        CategoryPlot p = chart.getCategoryPlot();
//        p.setRangeGridlinePaint(Color.red);


        //jPanel3.setSize(300, 100);
        ChartPanel cpA = new ChartPanel(chartA);
        cpA.setPreferredSize(new Dimension(350, 200));
        cpA.setVisible(true);

        ChartPanel cpB = new ChartPanel(chartB);
        cpB.setPreferredSize(new Dimension(350, 200));
        cpB.setVisible(true);

        ChartPanel cpC = new ChartPanel(chartC);
        cpC.setPreferredSize(new Dimension(350, 200));
        cpC.setVisible(true);


        jPanel3.removeAll();
        jPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel3.add(cpA);
        jPanel3.revalidate();

        jPanel2.removeAll();
        jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel2.add(cpB);
        jPanel2.revalidate();

        jPanel4.removeAll();
        jPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel4.add(cpC);
        jPanel4.revalidate();
    }
}
