/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Scanner;
import javax.swing.Icon;
import javax.swing.JTable;
import model.Peca;

/**
 *
 * @author 42519630833
 */
public interface MalhaController extends Observado{
    
    void carrega(Peca[][] X, Scanner scanX, int tipoExclusao);
    Icon getEstrada(int linha, int coluna);
    Peca[][] identificaCruzamentos();
    void iniciaCarros(int quantidade, int tempoInsercao);
    void inseriuCarro();
    void encerrarCriacao();
    void encerrarAplicacao();
    
}
