/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MalhaController;
import controller.MalhaControllerImpl;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author 42519630833
 */
public class MalhaViaria extends AbstractTableModel {

    MalhaController controle = MalhaControllerImpl.getInstance();
    
    private static final long serialVersionUID = 1L;
    
    private final int coluna;
    private final int linha;
    
    public MalhaViaria(int coluna, int linha) {
        this.coluna = coluna;
        this.linha = linha;
    }

    @Override
    public int getColumnCount() {
        
        return coluna;
    }

    @Override
    public int getRowCount() {
        return linha;
    }

    @Override
    public Object getValueAt(int coluna, int linha) {
        try {
            return controle.getEstrada(linha, coluna);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return null;
        }
    }

}
