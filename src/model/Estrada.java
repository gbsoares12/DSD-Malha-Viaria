/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MovimentoController;
import javax.swing.Icon;

/**
 *
 * @author 42519630833
 */
public abstract class Estrada extends Peca{
    
    private MovimentoController tipoMovimento;
    
    public Estrada(Icon imagem, SentidoEstrada se, int linha, int coluna, int tipoExclusao) {
        super(imagem, se, linha, coluna);
        
        switch(tipoExclusao){
            case 1:
                this.tipoMovimento = new MovimentoSemafaro(this);
                break;
            case 2:
                this.tipoMovimento = new MovimentoMonitor(this);
                break;
        }
    }

    public MovimentoController getTipoMovimento() {
        return tipoMovimento;
    }
    
}
