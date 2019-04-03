package view;

import controller.MalhaController;
import controller.MalhaControllerImpl;
import controller.Observador;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import model.Peca;
import model.MalhaViaria;
import model.Veiculo;

/**
 *
 * @author Denilson Laucsen da Rosa
 */
public class NewJFrame extends javax.swing.JFrame {

    private static MalhaController controle;

    /**
     * Creates new form NewJFrame
     */
    static class MalhaRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);
            return this;
        }

    }

    private int quantidadeMaxCarros;
    private int tempoIntervalo;

    public NewJFrame() {
        super();
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setTitle("Simulador Malha Viária");

        this.controle = MalhaControllerImpl.getInstance();

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        MenuBar = new javax.swing.JMenuBar();
        Menu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        fileChooser.setDialogTitle("");
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Menu1.setText("File");

        jMenuItem1.setText("Adcionar Arquivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                jMenuItem1ActionPerformed(evt);

            }
        });
        Menu1.add(jMenuItem1);

        MenuBar.add(Menu1);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 144, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 105, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        TelaMalha tm = new TelaMalha();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // What to do with the file, e.g. display it in a TextArea
                Scanner scanA = new Scanner(file);
                int linA = scanA.nextInt();
                int colA = scanA.nextInt();
                System.out.println("lin: " + linA + " col: " + colA);

                Peca[][] matrizMalha = new Peca[linA][colA]; // aqui vai chamar um deafultTable e passar o linA no get linha e colA no get Cols

                int tipoGerenciamento = -1;
               
                tipoGerenciamento = Integer.parseInt(JOptionPane.showInputDialog("Qual tipo de gerenciamento? (1) Semáfaro | (2) Monitor"));

                while (scanA.hasNext()) {
                    controle.carrega(matrizMalha, scanA, tipoGerenciamento);

                }

                controle.identificaCruzamentos();
                quantidadeMaxCarros = Integer.parseInt(JOptionPane.showInputDialog("Quantos carros? "));

                switch (Integer.parseInt(JOptionPane.showInputDialog("Deseja especificar o intervalo entre as inserções? (1) Sim | (2) Não"))) {
                    case 1:
                        tempoIntervalo = Integer.parseInt(JOptionPane.showInputDialog("Quantos milisegundos o sistema irá aguardar?"));

                        break;
                    default:
                        tempoIntervalo = 0;
                        break;
                }

                controle.iniciaCarros(quantidadeMaxCarros, tempoIntervalo);

                String colunmNames[] = new String[colA];
                for (int i = 0; i < colunmNames.length; i++) {
                    colunmNames[i] = String.valueOf(i);

                }

                tm.runTable(colunmNames, linA, colA);

                scanA.close();
            } catch (IOException ex) {
                System.out.println("problem accessing file" + file.getAbsolutePath());
            }
        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event_fileChooserActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        fileChooserActionPerformed(evt);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Menu1;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
