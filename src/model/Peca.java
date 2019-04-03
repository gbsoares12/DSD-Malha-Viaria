/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.Icon;

/**
 *
 * @author 42519630833
 */
public abstract class Peca {

    private Icon imagem;
    private SentidoEstrada sentidoEstrada;
    private int linha;
    private int coluna;
    
    public Peca(Icon imagem, SentidoEstrada se, int linha, int coluna) {
        this.imagem = imagem;
        this.sentidoEstrada = se;
        this.linha = linha;
        this.coluna = coluna;
    }

    
    public void casaAtual(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public Icon getImagem() {
        return imagem;
    }

    public void setImagem(Icon imagem) {
        this.imagem = imagem;
    }

    public SentidoEstrada getSentidoEstrada() {
        return sentidoEstrada;
    }

    public void setSentidoEstrada(SentidoEstrada sentidoEstrada) {
        this.sentidoEstrada = sentidoEstrada;
    }
    

}
