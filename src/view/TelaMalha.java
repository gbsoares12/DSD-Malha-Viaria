/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MalhaController;
import controller.MalhaControllerImpl;
import controller.Observado;
import controller.Observador;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.MalhaViaria;

/**
 *
 * @author 42519630833
 */
public class TelaMalha implements Observador {

    private JTable malhaTable;
    private MalhaController controle;
    private int quantidadeAtualCarros = 0;
    private JLabel jlQuantidadeCarroAtual = new JLabel(""+this.quantidadeAtualCarros);

    public TelaMalha() {

        this.controle = MalhaControllerImpl.getInstance();

    }

    void runTable(String[] colunmNames, int coluna, int linha) {

        controle.addObservador(this);
        JFrame f = new JFrame("Malha Viaria");
        Container content = f.getContentPane();

        malhaTable = new JTable();

        malhaTable.setModel(new MalhaViaria(linha, coluna));
        malhaTable.setDefaultRenderer(Object.class, new Inicio.MalhaRenderer());

        JPanel jpControle = new JPanel();
        JScrollPane scrollPane = new JScrollPane(malhaTable);
        JButton jbStop = new JButton("Parar Aplicação");

        JLabel jlInfQuantidade = new JLabel("Quantidade atual de carros:");
        jpControle.add(jbStop, BorderLayout.CENTER);
        jpControle.add(jlInfQuantidade, BorderLayout.WEST);
        jpControle.add(jlQuantidadeCarroAtual, BorderLayout.EAST);
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(jpControle, BorderLayout.SOUTH);

        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setSize(900, 500);

        f.setVisible(true);

        //Configurações botão voltar rodada
        jbStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    controle.encerrarCriacao();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
    }

    @Override
    public void notificaAtualizouMalha() {
        malhaTable.repaint();
    }

    @Override
    public void notificaQuantidadeAtualCarro(int carros) {
        this.jlQuantidadeCarroAtual.setText(carros + "");
    }

    }
