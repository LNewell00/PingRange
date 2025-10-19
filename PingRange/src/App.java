import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Logan Newell
 * @Date 10/18/2025
 */
public class App extends javax.swing.JFrame {

    public App() {
        initComponents();
        ERRORTEXT.setVisible(false);
        model = (DefaultTableModel) resultsTable.getModel();
    }

    DefaultTableModel model;
    boolean stopState;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopAddressField = new javax.swing.JTextField();
        startAddressField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        ERRORTEXT = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        startButton1 = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setMinimumSize(new java.awt.Dimension(600, 500));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        startButton.setText("START");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        jPanel2.add(startButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, -1, -1));
        jPanel2.add(stopAddressField, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 180, -1));
        jPanel2.add(startAddressField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 180, -1));

        jLabel1.setText("Stop Address");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        jLabel2.setText("Start Address");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        resultsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        resultsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IP Address", "Reachable"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(resultsTable);
        if (resultsTable.getColumnModel().getColumnCount() > 0) {
            resultsTable.getColumnModel().getColumn(0).setResizable(false);
            resultsTable.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, 280));

        ERRORTEXT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ERRORTEXT.setText("INVALID ADDRESS");
        jPanel2.add(ERRORTEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 120, -1));

        jCheckBox1.setText("Only Check 0 and 1");
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 140, -1));

        startButton1.setText("START");
        jPanel2.add(startButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, -1, -1));

        stopButton.setText("STOP");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        jPanel2.add(stopButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        ERRORTEXT.setVisible(false);
        stopState = false;
        
        String input1 = startAddressField.getText();
        String input2 = stopAddressField.getText();
        
        String[] s1 = input1.split("\\.");
        String[] s2 = input2.split("\\.");
        
        if(checkInputs(input1, input2) && s1.length == 4 && s2.length == 4) {
            
            int[] start = new int[4];
            int[] end = new int[4];
            
            start[0] = Integer.parseInt(s1[0]);
            start[1] = Integer.parseInt(s1[1]);
            start[2] = Integer.parseInt(s1[2]);
            start[3] = Integer.parseInt(s1[3]);
            
            end[0] = Integer.parseInt(s2[0]);
            end[1] = Integer.parseInt(s2[1]);
            end[2] = Integer.parseInt(s2[2]);
            end[3] = Integer.parseInt(s2[3]);
            
            new Thread(() -> {
                try{
                    walkIpRange(start, end);
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } else {
            ERRORTEXT.setVisible(true);
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        stopState = true;
    }//GEN-LAST:event_stopButtonActionPerformed
    
    public void walkIpRange(int[] startOctet, int[] endOctet) throws IOException {
        
        model.setRowCount(0);

        for(int i = startOctet[0]; i <= 255; i++) {

            if(stopState) {
                break;
            }
            
            if(i == endOctet[0] + 1) {
                break;
            }

            for(int j = startOctet[1]; j <= 255; j++) {
                
                if(stopState) {
                    break;
                }

                if(j == endOctet[1] + 1 && i == endOctet[0]) {
                    break;
                }

                for(int k = startOctet[2]; k <= 255; k++) {
                    
                    if(stopState) {
                        break;
                    }

                    if(k == endOctet[2] + 1 && j == endOctet[1]) {
                        break;
                    }

                    for(int l = startOctet[3]; l <= 255; l++) {
                        
                        if(stopState) {
                            break;
                        }

                        if(l == endOctet[3] + 1 && k == endOctet[2]) {
                            break;
                        }

                        String address = String.format("%d.%d.%d.%d", i, j, k, l);
                        InetAddress inetAddress = InetAddress.getByName(address);
                        
                        
                        boolean reachable = inetAddress.isReachable(5);
                        model.addRow(new Object[]{address, reachable ? "Yes" : "No"});

                        if(l == 1 && !inetAddress.isReachable(5) && jCheckBox1.isSelected()) {
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public boolean checkInputs(String input1, String input2) {
        return isValidIP(input1) && isValidIP(input2);
    }

    private boolean isValidIP(String ip) {
        if (ip == null || ip.isEmpty()) return false;

        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;

        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) return false;
                // Prevent leading zeros like "01"
                if (!String.valueOf(value).equals(part)) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ERRORTEXT;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable resultsTable;
    private javax.swing.JTextField startAddressField;
    private javax.swing.JButton startButton;
    private javax.swing.JButton startButton1;
    private javax.swing.JTextField stopAddressField;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
