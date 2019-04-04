/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MalhaControllerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author 42519630833
 */
public class Veiculo extends Peca implements Runnable {

    private List<Peca> chaoPercorrido = new ArrayList<>();

    public void addChao(Peca chao) {
        chaoPercorrido.add(chao);
    }

    public Peca inicioChaoPercorrido() {
        return chaoPercorrido.get(0);
    }

    public Peca penultimoChaoPercorrido() {
        return chaoPercorrido.get(chaoPercorrido.size() - 2);
    }
    
    public Peca ultimoChaoPercorrido(){
        return chaoPercorrido.get(chaoPercorrido.size() - 1);
    }

    public List<Peca> getChaoPercorrido() {
        return chaoPercorrido;
    }

    public Veiculo(String numeroCarro, SentidoEstrada se, int linha, int coluna) {

        super(new ImageIcon("imagem/carro-" + numeroCarro + ".png"), se, linha, coluna);
    }

    @Override
    public void run() {
        VeiculoController vc = new VeiculoController(this);
        boolean stop = false;
        
        while (!stop) {
            
            stop = vc.mover();
            
        }
    }
    
    @Override
    public String toString() {
        return "Veiculo: chaoPercorrido: " + chaoPercorrido.get(this.chaoPercorrido.size() - 2) + '}';
    }
}
