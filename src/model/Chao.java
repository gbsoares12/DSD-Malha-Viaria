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
public class Chao extends Estrada {    
    
    
    public Chao(String imagem, SentidoEstrada se, int linha, int coluna, int tipoExclusao) {
        
        super(new ImageIcon("imagem/"+imagem+".png"), se, linha, coluna, tipoExclusao);
        
        
    }

    @Override
    public String toString() {
        return "Chao {"+this.getLinha() + "}" + " {"+this.getColuna()+"}";
    }
    
    
    
}
