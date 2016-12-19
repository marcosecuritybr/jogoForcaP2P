package forca;

import Front.TelaCoordenador;
import java.util.Random;

/**
 *
 * @author Marcos
 */
public class Game {

    String palavra;
    String dicaInicial;
    String letrasSorteadas;

    int contadorErros;
    int Acertos = 0;
    int jogadorDaVez = 1; //Seleciona jogador da rodada, começa com jogador 1

    //Pontuação dos jogadores:
    int pontosjogador1 = 0;
    int pontosjogador2 = 0;
    int pontosjogador3 = 0;

    public Game(TelaCoordenador tela) {
        sortearPalavra();
        tela.setarPalaavra(palavra);
        
    }

    //Sorteia palavra para jogo
    void sortearPalavra() {
        String palavra1 = "lapis";
        String palavra2 = "mouse";
        String palavra3 = "avestruz";
        String palavra4 = "espinafre";

        String dica1 = "Tem na sala de aula";
        String dica2 = "Computador";
        String dica3 = "animal";
        String dica4 = "vegetal";

        Random random = new Random();
        int numeroSorteado = random.nextInt(4) + 1;

        switch (numeroSorteado) {
            case 1:
                palavra = palavra1;
                dicaInicial = dica1;
                break;
            case 2:
                palavra = palavra2;
                dicaInicial = dica2;
                break;
            case 3:
                palavra = palavra3;
                dicaInicial = dica3;
                break;
            case 4:
                palavra = palavra4;
                dicaInicial = dica4;
                break;
            default : 
                palavra = palavra4;
                dicaInicial = dica4;
                break;
        }

    }

    //Analisa letras enviadas pelos jgadores
    void adicionaEVerificaLetra(String letra, int jogador, TelaCoordenador tela, ServerController server) {
        letrasSorteadas = letrasSorteadas + letra;
        System.out.println("okkkk");
        tela.setarLetras(letrasSorteadas);

        //Letra já foi escolhida?
        if (!this.letrasSorteadas.contains(letra)) {
            //Letra está certa?
            if (this.palavra.contains(letra)) {

                letrasSorteadas = letrasSorteadas + letra;
                System.out.println("okkkk");
                tela.setarLetras(letrasSorteadas);
                server.enviarLetra(letra);
                Acertos++;
                switch (jogador) {
                    case 1:
                        this.pontosjogador1++;
                        break;
                    case 2:
                        this.pontosjogador2++;
                        break;

                }
                //Letra errada
            } else {
                contadorErros--;
                //------------IMPLEMENTRAR CORPO FORCA ----------
            }
        }

        if (contadorErros == 0) {
            System.out.println("Jogo terminado! Ninguem acertou!");
            //----------------ENVIAR MENSAGEM DE JOGO TERMINADO----------
        }
        //Proximo jogador da rodada
        switch (jogadorDaVez) {
            case 1:
                jogadorDaVez = 2;
                break;
            case 2:
                jogadorDaVez = 1;
                break;
        }

        System.out.println("Proximo jogador:" + jogadorDaVez);

    }

    //Polilita de escalonamento, recebe mensagem, se mensagem for do jogador adiciona e verifica letra enviada
    void politicaDeEscalonamento(Mensagem msg, TelaCoordenador tela, ServerController server) {

        adicionaEVerificaLetra(msg.letra, msg.numeroJogador, tela, server);
        /*if(msg.numeroJogador == jogadorDaVez){
            adicionaEVerificaLetra(msg.letra,msg.numeroJogador,tela,server);     
        }
         */
    }

    void darDIca() {

    }

}
