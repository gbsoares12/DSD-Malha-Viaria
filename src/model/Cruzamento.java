/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author 42519630833
 */
public class Cruzamento extends Estrada{
    
    private boolean saidaCima = false;
    private boolean saidaBaixo = false;
    private boolean saidaDireita = false;
    private boolean saidaEsquerda = false;
    
    public Cruzamento(boolean cima, boolean baixo, boolean esquerda, boolean direita, int linha, int coluna, int tipoExclusao) {
        
        super(new ImageIcon("imagem/cruzamento.png"), null, linha, coluna, tipoExclusao);
    
        this.saidaCima = cima;
        this.saidaBaixo = baixo;
        this.saidaDireita = direita;
        this.saidaEsquerda = esquerda;
    
    }
    
    public boolean isSaidaCima() {
        return saidaCima;
    }

    public boolean isSaidaBaixo() {
        return saidaBaixo;
    }

    public boolean isSaidaDireita() {
        return saidaDireita;
    }

    public boolean isSaidaEsquerda() {
        return saidaEsquerda;
    }

    @Override
    public String toString() {
        return "Cruzamento {"+this.getLinha()+"}{"+this.getColuna()+"} | Saidas: esquerda: " + this.saidaEsquerda + " | direita: "+ this.saidaDireita + " | cima: " + this.saidaCima + " | baixo: " + this.saidaBaixo;
    }
    
    

}
