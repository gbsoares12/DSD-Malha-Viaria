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

/**
 *
 * @author 42519630833
 */
public class InsereVeiculo implements Runnable {

    private Peca[][] malhaDoControle;
    private Peca pecaInicio;
    private List<Peca> listaInicios = new ArrayList<>();
    private MalhaControllerImpl controle;
    private int intervaloMilisegundos;
    
    private boolean isExecutando = true;

    public InsereVeiculo(List<Peca> lista, Peca[][] malha, int milisegundosAguardo) {

        this.listaInicios = lista;
        this.malhaDoControle = malha;
        this.intervaloMilisegundos = milisegundosAguardo;
        controle = MalhaControllerImpl.getInstance();

    }

    public void setIsExecutando(boolean isExecutando) {
        this.isExecutando = isExecutando;
    }

    @Override
    public void run() {
        
        while (isExecutando) {
            boolean stop = false;
            try {
                Thread.sleep(intervaloMilisegundos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (controle.getQuantidadeCarros() < controle.getQuantidadeMaxCarros()) {
                Random random = new Random();
                Veiculo carro = new Veiculo("" + random.nextInt(4), null, 0, 0);

                while (!stop) {
                    boolean inseriu = false;
                    int randomPosition = random.nextInt(listaInicios.size());
                    if (malhaDoControle[listaInicios.get(randomPosition).getLinha()][listaInicios.get(randomPosition).getColuna()].getClass() == Chao.class) {

                        pecaInicio = malhaDoControle[listaInicios.get(randomPosition).getLinha()][listaInicios.get(randomPosition).getColuna()];
                        carro.addChao(pecaInicio);

                        malhaDoControle[listaInicios.get(randomPosition).getLinha()][listaInicios.get(randomPosition).getColuna()] = carro;

                        carro.casaAtual(listaInicios.get(randomPosition).getLinha(), listaInicios.get(randomPosition).getColuna());//CONFIGURA A CELULA QUE O VEICULO SE ENCONTRA

                        carro.setSentidoEstrada(pecaInicio.getSentidoEstrada());

                        inseriu = true;
                    }
                    if (inseriu && malhaDoControle[listaInicios.get(randomPosition).getLinha()][listaInicios.get(randomPosition).getColuna()].getClass() == Veiculo.class) {
                        stop = true;
                        controle.inseriuCarro();
                    }
                }

                Thread veiculoThread = new Thread(carro);
                veiculoThread.start();
                
                try {
                    Thread.sleep(intervaloMilisegundos + 400);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            
            if(controle.getQuantidadeMinima() < controle.getQuantidadeCarros()){
                this.run();
            }

        }
    }

}
