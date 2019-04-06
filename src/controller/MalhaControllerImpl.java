package controller;

import java.util.*;
import java.util.concurrent.Semaphore;
import javax.swing.Icon;

import model.Chao;
import model.CoordenadasFim;
import model.Cruzamento;
import model.Estrada;
import model.InsereVeiculo;
import model.MovimentoSemafaro;
import model.Peca;
import model.SentidoEstrada;
import model.Veiculo;

/**
 *
 * @author 42519630833
 */
public class MalhaControllerImpl implements MalhaController {

    private int quantidadeMaxCarros;
    private int quantidadeMinima;
    private int quantidadeCarros;
    private List<Peca> listaInicios = new ArrayList<>();
    private List<CoordenadasFim> listaFins = new ArrayList<>();
    private int tipoExclusao;
    private List<Estrada> listaChao = new ArrayList<>();
    private List<Estrada> listaCruzamento = new ArrayList<>();
    private List<Observador> observadores = new ArrayList<>();
    private Peca[][] malhaTable;
    private List<Veiculo> listaVeiculos = new ArrayList<>();

    private static MalhaControllerImpl instance;//Padrão Singleton

    public synchronized static MalhaControllerImpl getInstance() {//Padrão Singleton
        if (instance == null) {
            instance = new MalhaControllerImpl();
        }
        return instance;
    }

    public List<Estrada> getListaChao() {
        return listaChao;
    }

    public List<Veiculo> getListaVeiculos() {
        return listaVeiculos;
    }

    public void setListaVeiculos(List<Veiculo> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public List<Peca> getListaInicios() {
        return listaInicios;
    }

    public List<Estrada> getListaCruzamento() {
        return listaCruzamento;
    }

    public List<CoordenadasFim> getListaFins() {
        return listaFins;
    }

    public Peca[][] getMalhaTable() {
        return malhaTable;
    }

    public int getQuantidadeCarros() {
        return quantidadeCarros;
    }

    public void setQuantidadeCarros(int quantidadeCarros) {
        this.quantidadeCarros = quantidadeCarros;
    }

    public int getQuantidadeMaxCarros() {
        return quantidadeMaxCarros;
    }

    @Override
    public void addObservador(Observador obs) {
        this.observadores.add(obs);
    }
    
    public void addCarro(Veiculo v){
        this.listaVeiculos.add(v);
    }

    @Override
    public void carrega(Peca[][] malhaMontada, Scanner scanX, int tipoExclusao) {
        this.tipoExclusao = tipoExclusao;

        int inix = scanX.nextInt();
        int iniy = scanX.nextInt();
        int fimx = scanX.nextInt();
        int fimy = scanX.nextInt();

        if (iniy == fimy) { // Verifica se vai percorrer em horizontal.
            if (inix < fimx) {// Faz percorrer para a direita.
                for (int i = inix; i <= fimx; i++) {
                    malhaMontada[fimy][i] = new Chao("chao-direita", SentidoEstrada.DIREITA, fimy, i, tipoExclusao);
                    this.listaChao.add((Chao) malhaMontada[fimy][i]);
                }

            } else {// Faz percorrer para a esquerda.
                for (int i = inix; i >= fimx; i--) {
                    malhaMontada[fimy][i] = new Chao("chao-esquerda", SentidoEstrada.ESQUERDA, fimy, i, tipoExclusao);
                    this.listaChao.add((Chao) malhaMontada[fimy][i]);
                }

            }
        } else { // Verifica se vai percorrer na vertical.
            if (iniy < fimy) {// Faz percorrer para baixo.

                for (int i = iniy; i <= fimy; i++) {
                    malhaMontada[i][fimx] = new Chao("chao-baixo", SentidoEstrada.BAIXO, i, fimx, tipoExclusao);
                    this.listaChao.add((Chao) malhaMontada[i][fimx]);
                }

            } else {// Faz percorrer para cima.
                for (int i = iniy; i >= fimy; i--) {
                    malhaMontada[i][fimx] = new Chao("chao-cima", SentidoEstrada.CIMA, i, fimx, tipoExclusao);
                    this.listaChao.add((Chao) malhaMontada[i][fimx]);
                }
            }
        }

        this.malhaTable = malhaMontada;
    }

    @Override
    public Icon getEstrada(int col, int row) {
        return (malhaTable[row][col] == null ? null : malhaTable[row][col].getImagem());
    }

    public void verificaCruzamentoVertical(int coluna, int linha, Peca[][] malhaMontada) {

        int tamanhoMalha = malhaMontada.length;
        int contadorEstradasVizinhas = 0;

        if ((linha - 1 > 0) && malhaMontada[linha - 1][coluna] != null) {// Verifica célula a cima.
            contadorEstradasVizinhas++;
        }

        if (linha + 1 < (tamanhoMalha - 1) && malhaMontada[linha + 1][coluna] != null) {// Verifica célula a baixo.
            contadorEstradasVizinhas++;
        }

        if (contadorEstradasVizinhas >= 1) {
            verificaCruzamentoHorizontal(coluna, linha, tamanhoMalha, malhaMontada, contadorEstradasVizinhas);
        }

    }

    public void verificaCruzamentoHorizontal(int coluna, int linha, int tamanhoMalha, Peca[][] malhaMontada, int contadorVertical) {

        int contadorEstradasVizinhas = contadorVertical;

        if ((coluna - 1 > 0) && malhaMontada[linha][coluna - 1] != null) {// Verifica célula a esquerda.
            contadorEstradasVizinhas++;

        }

        if ((coluna + 1 < malhaTable[0].length) && malhaMontada[linha][coluna + 1] != null) {// Verifica célula a direita.
            contadorEstradasVizinhas++;

        }

        if (contadorEstradasVizinhas > 2) {

            boolean cimaLivre = false;
            boolean baixoLivre = false;
            boolean esquerdaLivre = false;
            boolean direitaLivre = false;

            Peca estradaEmCima = malhaMontada[linha - 1][coluna];
            Peca estradaEmBaixo = malhaMontada[linha + 1][coluna];
            Peca estradaAEsquerda = malhaMontada[linha][coluna - 1];
            Peca estradaADireita = malhaMontada[linha][coluna + 1];

            if (estradaEmCima != null) {
                if (estradaEmCima.getSentidoEstrada() == SentidoEstrada.CIMA) {
                    cimaLivre = true;
                }
            }

            if (estradaEmBaixo != null) {
                if (estradaEmBaixo.getSentidoEstrada() == SentidoEstrada.BAIXO) {
                    baixoLivre = true;
                }
            }

            if (estradaADireita != null) {
                if (estradaADireita.getSentidoEstrada() == SentidoEstrada.DIREITA) {
                    direitaLivre = true;
                }
            }

            if (estradaAEsquerda != null) {
                if (estradaAEsquerda.getSentidoEstrada() == SentidoEstrada.ESQUERDA) {
                    esquerdaLivre = true;
                }
            }
            malhaMontada[linha][coluna] = new Cruzamento(cimaLivre, baixoLivre, esquerdaLivre, direitaLivre, linha, coluna, this.tipoExclusao);
            this.listaCruzamento.add((Estrada) malhaMontada[linha][coluna]);

        }
        this.malhaTable = malhaMontada;
    }

    @Override
    public Peca[][] identificaCruzamentos() {

        for (int i = 0; i < malhaTable[0].length; i++) { // Coluna
            for (int j = 0; j < malhaTable.length; j++) { // Linha

                // IDENTIFICA O INICIO DAS ESTRADAS
                if (malhaTable[j][i] != null && i == 0) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.DIREITA) {
                        listaInicios.add(malhaTable[j][i]);

                    }
                }

                if (malhaTable[j][i] != null && i == malhaTable[0].length - 1) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.ESQUERDA) {
                        listaInicios.add(malhaTable[j][i]);

                    }
                }

                if (malhaTable[j][i] != null && j == 0) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.BAIXO) {
                        listaInicios.add(malhaTable[j][i]);
                    }
                }

                if (malhaTable[j][i] != null && j == malhaTable.length - 1) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.CIMA) {
                        listaInicios.add(malhaTable[j][i]);
                    }
                }

                // IDENTIFICA O FIM DAS ESTRADAS
                if (malhaTable[j][i] != null && i == 0) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.ESQUERDA) {
                        listaFins.add(new CoordenadasFim(j, i));
                    }
                }

                if (malhaTable[j][i] != null && i == malhaTable[0].length - 1) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.DIREITA) {
                        listaFins.add(new CoordenadasFim(j, i));
                    }
                }

                if (malhaTable[j][i] != null && j == 0) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.CIMA) {
                        listaFins.add(new CoordenadasFim(j, i));
                    }
                }

                if (malhaTable[j][i] != null && j == malhaTable.length - 1) {
                    if (malhaTable[j][i].getSentidoEstrada() == SentidoEstrada.BAIXO) {
                        listaFins.add(new CoordenadasFim(j, i));
                    }
                }

                verificaCruzamentoVertical(i, j, malhaTable);
            }
        }

        return malhaTable;
    }

    private InsereVeiculo insereCarro;

    @Override
    public void iniciaCarros(int quantidade, int tempoInsercao) {

        this.quantidadeMaxCarros = quantidade;
        this.quantidadeMinima = quantidade;

        if (tempoInsercao == 0) {
            tempoInsercao = 100;
        }

        insereCarro = new InsereVeiculo(listaInicios, malhaTable, tempoInsercao);

        Thread insertVehicleThread = new Thread(insereCarro);
        insertVehicleThread.start();

    }

    @Override
    public void encerrarCriacao() {
        insereCarro.setIsExecutando(false);
    }

    public void retirarVeiculo() {
        Semaphore mutex = new Semaphore(1);
        try {
            mutex.acquire();
            this.quantidadeCarros--;
            for (Observador obs : observadores) {
                obs.notificaQuantidadeAtualCarro(quantidadeCarros);
            }

            atualizouMalha();
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void atualizouMalha() {
        for (Observador obs : observadores) {
            obs.notificaAtualizouMalha();
        }
    }

    @Override
    public void inseriuCarro() {

        this.quantidadeCarros++;
        for (Observador observadore : observadores) {
            observadore.notificaAtualizouMalha();
            observadore.notificaQuantidadeAtualCarro(quantidadeCarros);
        }

    }

    @Override
    public void encerrarAplicacao() {
        for (Veiculo listaVeiculo : listaVeiculos) {
            listaVeiculo.setParadaForcada(true);
        }
        for (Observador obs : observadores) {
            obs.notificaEncerrouAplicacao();
        }
    }

}
