/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Veiculo;

/**
 *
 * @author 42519630833
 */
public interface Observador {
    
    public void notificaAtualizouMalha();
    public void notificaQuantidadeAtualCarro(int carros);
}
