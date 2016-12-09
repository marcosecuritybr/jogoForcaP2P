/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forca;

import java.net.Socket;

/**
 *
 * @author Marcos
 */
public class Game {
    String palavra = "Maria";
    String dica = "Nome proprio";
    String letrasSorteadas;
    int contadorErros = 6;
    int Acertos = 0;
    
    Socket jogador1;
    Socket jogador2;
    Socket jogador3;
    
    void adicionaEVerificaLetra(char letra){
        if(this.palavra.indexOf(letra) != -1){ //Acertou letras
            if(this.letrasSorteadas.indexOf(letra)!= -1) //Letra não foi escolhida ainda
                letrasSorteadas = letrasSorteadas + letra;
            Acertos++;    
        }else{ //Letra errada
            contadorErros--;
        }
        if(contadorErros == 0){
            System.out.println("Jogo terminado! Ninguem acetou!");
        }
    }
    
    void novaPartida(Socket jogador1, Socket jogador2, Socket jogador3){
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogador3 = jogador3;
    }
    
    void darDIca(){
        
    }
    
    
}
