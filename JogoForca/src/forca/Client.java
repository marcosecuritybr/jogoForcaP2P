/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forca;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
    public void conexao() 
        throws UnknownHostException, IOException {
        Socket cliente = new Socket("localhost", 12345);
        System.out.println("O cliente se conectou ao servidor!");
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        Scanner teclado = new Scanner(System.in);
        while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }
   } 
}
