/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forca;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
     public void novoJogo() throws IOException {
 
     ServerSocket servidor = new ServerSocket(12345);
     System.out.println("Porta 12345 aberta!");
       // a continuação do servidor deve ser escrita aqui
       
     Socket cliente1 = servidor.accept();
     //Socket cliente2 = servidor.accept();
     //Socket cliente3 = servidor.accept();
       
     System.out.println("Começando o jogo.........");
       
     Game partida = new Game();
     
     partida.novaPartida(cliente1, cliente1, cliente1);
     
     Scanner scanner = new Scanner(cliente1.getInputStream());
     //Espera respostas dos clientes:
     while (true) {
        String letraDigitada;
        letraDigitada = scanner.nextLine();
    }
     
 
   }
    
    
    
}
