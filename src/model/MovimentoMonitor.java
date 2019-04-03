/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MalhaControllerImpl;

/**
 *
 * @author Gabriel Soares
 */
public class MovimentoMonitor implements MovimentoController {

    private MalhaControllerImpl controle;
    private Peca[][] malhaViaria;
    private final Estrada chaoAtual;

    public MovimentoMonitor(Estrada estradaAtual) {
        this.chaoAtual = estradaAtual;
    }

    @Override
    public synchronized void movimentarCarro(Veiculo veiculo, SentidoEstrada novoSentido) {
        
        controle = MalhaControllerImpl.getInstance();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            this.malhaViaria = controle.getMalhaTable();
            
            if (malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna()].getClass() != Veiculo.class) {
                
                Estrada ultimaEstrada;

                veiculo.addChao(chaoAtual);

                if (veiculo.getChaoPercorrido().size() == 2) {
                    ultimaEstrada = (Estrada) veiculo.inicioChaoPercorrido();
                } else {
                    ultimaEstrada = (Estrada) veiculo.penultimoChaoPercorrido();
                }

                if (null != veiculo.getSentidoEstrada()) {
                    switch (veiculo.getSentidoEstrada()) {
                        case ESQUERDA:

                            veiculo.casaAtual(chaoAtual.getLinha(), chaoAtual.getColuna());
                            malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna()] = veiculo;

                            malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna() + 1] = ultimaEstrada;
                            break;
                        case DIREITA:

                            veiculo.casaAtual(chaoAtual.getLinha(), chaoAtual.getColuna());
                            malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna()] = veiculo;

                            malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna() - 1] = ultimaEstrada;
                            break;
                        case CIMA:

                            veiculo.casaAtual(chaoAtual.getLinha(), chaoAtual.getColuna());
                            malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna()] = veiculo;

                            malhaViaria[chaoAtual.getLinha() + 1][chaoAtual.getColuna()] = ultimaEstrada;
                            break;
                        case BAIXO:

                            veiculo.casaAtual(chaoAtual.getLinha(), chaoAtual.getColuna());
                            malhaViaria[chaoAtual.getLinha()][chaoAtual.getColuna()] = veiculo;

                            malhaViaria[chaoAtual.getLinha() - 1][chaoAtual.getColuna()] = ultimaEstrada;
                            break;
                    }
                }

                System.out.println("Passei pelo caminho: [" + ultimaEstrada.getLinha() + "] [" + ultimaEstrada.getColuna() + "]" + " carro: " + veiculo.toString());
                
                if(novoSentido != null){
                    veiculo.setSentidoEstrada(novoSentido);
                }
                
                controle.atualizouMalha();

            }
    }

}
