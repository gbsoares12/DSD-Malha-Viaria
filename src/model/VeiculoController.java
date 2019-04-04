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
public class VeiculoController {

    private MalhaControllerImpl controle;
    private boolean celulaDeSaida = false;
    private Peca[][] malhaViaria;
    private Veiculo veiculo;
    private List<Peca> chaoPercorrido = new ArrayList<>();

    public VeiculoController(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.controle = MalhaControllerImpl.getInstance();
    }

    public void addChao(Peca chao) {
        chaoPercorrido.add(chao);
    }

    public Peca inicioChaoPercorrido() {
        return chaoPercorrido.get(0);
    }

    public Peca penultimoChaoPercorrido() {
        return chaoPercorrido.get(chaoPercorrido.size() - 2);
    }

    public List<Peca> getChaoPercorrido() {
        return chaoPercorrido;
    }

    public boolean mover() {

        malhaViaria = controle.getMalhaTable();
        Random random = new Random();
        List<CoordenadasFim> listaFim = controle.getListaFins();

        boolean podeContinuarCruzamento = false;

        SentidoEstrada novoSentido = null;
        Cruzamento decisaoCruzamento = null;

        if (this.veiculo.getSentidoEstrada() != null) {

            Estrada proxEstrada = null;

            switch (this.veiculo.getSentidoEstrada()) { //Identifica qual a proxima casa
                case DIREITA:
                    if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getClass() == Chao.class) {

                        proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1];

                        if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada() != SentidoEstrada.DIREITA) {
                            novoSentido = malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada();
                            System.out.println("Mudou a direção para " + this.veiculo.getSentidoEstrada());
                        }

                        podeContinuarCruzamento = true; // SEMPRE TRUE POIS NÃO É CRUZAMENTO.

                    } else if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getClass() == Cruzamento.class) {

                        proxEstrada = (Cruzamento) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1];
                        decisaoCruzamento = (Cruzamento) proxEstrada;

                        switch (defineSaidaCruzamento(decisaoCruzamento)) {
                            case 0:
                                //VERIFICA SE O PROXIMA ESTRADA SELECIONADA DEPOIS DO CRUZAMENTO ESTÁ LIVRE, PARA EVITAR O BLOQUEIO DO MESMO
                                if ((malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                    novoSentido = SentidoEstrada.BAIXO;
                                    podeContinuarCruzamento = true;
                                }

                                break;
                            case 1:
                                if ((malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                    novoSentido = SentidoEstrada.CIMA;
                                    podeContinuarCruzamento = true;
                                }
                                break;
                            case 2:
                                if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1].getClass() == Chao.class) {
                                    novoSentido = SentidoEstrada.ESQUERDA;
                                    podeContinuarCruzamento = true;
                                }

                                break;
                            case 3:
                                if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1].getClass() == Chao.class) {
                                    novoSentido = SentidoEstrada.DIREITA;
                                    podeContinuarCruzamento = true;
                                }

                                break;
                        }
                    }
                    break;

                case ESQUERDA:
                    if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1] == null) {
                        if (malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()] != null//verifica para baixo
                                && (malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getClass() == Chao.class && malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getSentidoEstrada() != SentidoEstrada.CIMA)) {

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()];
                            this.veiculo.setSentidoEstrada(malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getSentidoEstrada());
                            podeContinuarCruzamento = true;

                        } else if ((malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()] != null) && malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()].getClass() == Chao.class) {

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()];
                            this.veiculo.setSentidoEstrada(malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()].getSentidoEstrada());
                            podeContinuarCruzamento = true;

                        }
                    } else {

                        if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getClass() == Chao.class) {
                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1];

                            if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getSentidoEstrada() != SentidoEstrada.ESQUERDA) {
                                novoSentido = malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getSentidoEstrada();
                                System.out.println("Mudou a direção para " + this.veiculo.getSentidoEstrada());
                            }

                            podeContinuarCruzamento = true; // SEMPRE TRUE POIS NÃO É CRUZAMENTO.

                        } else if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getClass() == Cruzamento.class) {
                            proxEstrada = (Cruzamento) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1];
                            decisaoCruzamento = (Cruzamento) proxEstrada;

                            switch (defineSaidaCruzamento(decisaoCruzamento)) {
                                case 0:
                                    //VERIFICA SE O PROXIMA ESTRADA SELECIONADA DEPOIS DO CRUZAMENTO ESTÁ LIVRE, PARA EVITAR O BLOQUEIO DO MESMO
                                    if ((malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.BAIXO;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                                case 1:
                                    if ((malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.CIMA;
                                        podeContinuarCruzamento = true;
                                    }
                                    break;
                                case 2:
                                    if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.ESQUERDA;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                                case 3:
                                    if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.DIREITA;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                            }

                        }
                    }
                    break;
                case BAIXO:
                    if (malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()] == null) {
                        if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1] != null) {//direita
                            if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getClass() == Chao.class && malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada() != SentidoEstrada.DIREITA) {

                                proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1];
                                this.veiculo.setSentidoEstrada(malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada());
                                novoSentido = malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada();
                                podeContinuarCruzamento = true;

                            }
                        } else if ((malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1] != null) && malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getClass() == Chao.class) {// esquerda

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1];
                            this.veiculo.setSentidoEstrada(malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getSentidoEstrada());
                            novoSentido = malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getSentidoEstrada();
                            podeContinuarCruzamento = true;

                        }
                    } else {

                        if (malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getClass() == Chao.class) {

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()];

                            if (malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getSentidoEstrada() != SentidoEstrada.BAIXO) {
                                novoSentido = malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getSentidoEstrada();
                                System.out.println("Mudou a direção para " + this.veiculo.getSentidoEstrada());
                            }
                            podeContinuarCruzamento = true; // SEMPRE TRUE POIS NÃO É CRUZAMENTO.

                        } else if (malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()].getClass() == Cruzamento.class) {
                            proxEstrada = (Cruzamento) malhaViaria[this.veiculo.getLinha() + 1][this.veiculo.getColuna()];
                            decisaoCruzamento = (Cruzamento) proxEstrada;

                            switch (defineSaidaCruzamento(decisaoCruzamento)) {
                                //VERIFICA SE O PROXIMA ESTRADA SELECIONADA DEPOIS DO CRUZAMENTO ESTÁ LIVRE, PARA EVITAR O BLOQUEIO DO MESMO
                                case 0:
                                    if ((malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.BAIXO;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                                case 1:
                                    if ((malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.CIMA;
                                        podeContinuarCruzamento = true;
                                    }
                                    break;
                                case 2:
                                    if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.ESQUERDA;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                                case 3:
                                    if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.DIREITA;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                            }

                        }
                    }
                    break;
                case CIMA:
                    if (malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()] == null) {
                        if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1] != null
                                && malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getClass() == Chao.class && malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada() != SentidoEstrada.DIREITA) {//direita

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1];
                            this.veiculo.setSentidoEstrada(malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada());
                            novoSentido = malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() + 1].getSentidoEstrada();
                            podeContinuarCruzamento = true;

                        } else if (malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1] != null
                                && malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getClass() == Chao.class) {

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1];
                            this.veiculo.setSentidoEstrada(malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getSentidoEstrada());
                            novoSentido = malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna() - 1].getSentidoEstrada();
                            podeContinuarCruzamento = true;

                        }

                    } else {

                        if (malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()].getClass() == Chao.class) {

                            proxEstrada = (Chao) malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()];

                            if (malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()].getSentidoEstrada() != SentidoEstrada.CIMA) {
                                novoSentido = malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()].getSentidoEstrada();
                                System.out.println("Mudou a direção para " + this.veiculo.getSentidoEstrada());
                            }

                            podeContinuarCruzamento = true; // SEMPRE TRUE POIS NÃO É CRUZAMENTO.

                        } else if (malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()].getClass() == Cruzamento.class) {
                            proxEstrada = (Cruzamento) malhaViaria[this.veiculo.getLinha() - 1][this.veiculo.getColuna()];

                            decisaoCruzamento = (Cruzamento) proxEstrada;

                            switch (defineSaidaCruzamento(decisaoCruzamento)) {
                                //VERIFICA SE O PROXIMA ESTRADA SELECIONADA DEPOIS DO CRUZAMENTO ESTÁ LIVRE, PARA EVITAR O BLOQUEIO DO MESMO
                                case 0:
                                    if ((malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() + 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.BAIXO;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                                case 1:
                                    if ((malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()] != null) && malhaViaria[proxEstrada.getLinha() - 1][proxEstrada.getColuna()].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.CIMA;
                                        podeContinuarCruzamento = true;
                                    }
                                    break;
                                case 2:
                                    if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() - 1].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.ESQUERDA;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                                case 3:
                                    if ((malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1] != null) && malhaViaria[proxEstrada.getLinha()][proxEstrada.getColuna() + 1].getClass() == Chao.class) {
                                        novoSentido = SentidoEstrada.DIREITA;
                                        podeContinuarCruzamento = true;
                                    }

                                    break;
                            }

                        }

                    }
                    break;
            }

            if (proxEstrada != null && podeContinuarCruzamento) {

                for (CoordenadasFim coordenadasFim : listaFim) {
                    if (coordenadasFim.getLinha() == proxEstrada.getLinha() && coordenadasFim.getColuna() == proxEstrada.getColuna()) {
                        celulaDeSaida = true;
                        System.out.println("Chegou no fim");
                    }
                }

                proxEstrada.getTipoMovimento().movimentarCarro(this.veiculo, novoSentido);

                if (celulaDeSaida) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    malhaViaria[this.veiculo.getLinha()][this.veiculo.getColuna()] = this.veiculo.ultimoChaoPercorrido();
                    controle.retirarVeiculo();
                    return true;
                    
                }
            }
        }

        try {
            Thread.sleep(random.nextInt(200) + 500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        return false;
    }

    public int defineSaidaCruzamento(Cruzamento cruzamento) {
        boolean saidasPossivelCruzamento[] = {false, false, false, false};
        /*
        [0] - baixo.
        [1] - cima.
        [2] - esquerda.
        [3] - direita.
         */

        if (cruzamento.isSaidaBaixo()) {
            saidasPossivelCruzamento[0] = cruzamento.isSaidaBaixo();
        }
        if (cruzamento.isSaidaCima()) {
            saidasPossivelCruzamento[1] = cruzamento.isSaidaCima();
        }
        if (cruzamento.isSaidaEsquerda()) {
            saidasPossivelCruzamento[2] = cruzamento.isSaidaEsquerda();
        }
        if (cruzamento.isSaidaDireita()) {
            saidasPossivelCruzamento[3] = cruzamento.isSaidaDireita();
        }
        boolean stop = false;
        int saida = -1;

        while (!stop) {
            Random random = new Random();
            int n = random.nextInt(4);

            if (saidasPossivelCruzamento[n]) {
                stop = true;
                saida = n;
            }
        }

        return saida;
    }

}
