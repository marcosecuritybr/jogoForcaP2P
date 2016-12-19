package forca;

import Conexao.ReceberPacotes;
import Front.TelaCoordenador;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public final class ServerController {

    int porta = 6868;
    InetAddress ipGrupo = null;
    MulticastSocket s = null;
    TelaCoordenador tela;
    Game jogoAtual;
    String msg = "mensagem conexao";

    public ServerController() throws IOException, UnknownHostException, InterruptedException {
        entrarJogo();
        entraGrupo();
        this.tela = new TelaCoordenador(this);
        tela.setVisible(true);
        this.jogoAtual = new Game(tela);
        ReceberPacotes Pac = new ReceberPacotes(this, s);
        Thread threadPacotesRecebidos = new Thread(Pac);
        threadPacotesRecebidos.start();
    }

    //Entra no grupo
    void entraGrupo() throws UnknownHostException, IOException {
        try {
            ipGrupo = InetAddress.getByName("224.225.226.227");
            s = new MulticastSocket(porta);
            s.joinGroup(ipGrupo);
        } catch (SocketException e) {
        }
    }

    //Entra no jogo
    public void entrarJogo() throws UnknownHostException, IOException, InterruptedException {

        //Aguardar clientes conectarem:
        int portaR = 7475;
        int portaE = 4343;
        DatagramSocket clientSocket = new DatagramSocket(portaR);
        String servidorC = "255.255.255.255";

        InetAddress IPAddress = InetAddress.getByName(servidorC);
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        //Envia 5 mesagens em Broadcast para clientes:
        for (int i = 0; i < 5; i++) {
            String sentence = "aaa";
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portaE);
            System.out.println("Enviando pacote UDP em Broadcast");
            clientSocket.send(sendPacket);
            Thread.sleep(5000);
        }
        //Envia mensagem ok para clientes:

        String sentence = "Ok";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portaE);
        System.out.println("Enviando mensagem ok Broadcast");
        clientSocket.send(sendPacket);
        //Thread.sleep(1000);

        //Aguarda confirmação de conexão com os clintes
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        System.out.println("Cliente 1 conectado");
        /*clientSocket.receive(receivePacket);
        System.out.println("Cliente 2 conectado");
        clientSocket.close();
        */
    }
    
    //Analise de mensagens recebidas
    public void analiseMensagem(Mensagem msg) {

        if (msg.numeroJogador == 1) {
            switch (msg.tipoMensagem) {
                case 1: //Mensagem letra do cliente
                    jogoAtual.politicaDeEscalonamento(msg, tela,this);
                    break;
                case 2: //Mesagem finalisar partida

                case 3: //Mensagem chutar palavra
            }
        }
    }
    
    //Envia dica para clientes
    public void darDica(String dica){
        Mensagem msg = new Mensagem();
        msg.numeroJogador = 0;
        msg.tipoMensagem = 5;
        msg.dados = dica;
        ConverteMsgToBytes c1 = new ConverteMsgToBytes();
        DatagramPacket MensagemParaEnviar = new DatagramPacket(c1.convertObjectToByteArray(msg), c1.getLenght(), ipGrupo, porta);

        try {
            s.send(MensagemParaEnviar);
        } catch (IOException e) {
        }
        System.out.println("Dica enviada");
    
    }
    
    //Envia letra pra clientes
    public void enviarLetra(String letra){
        Mensagem msg = new Mensagem();
        msg.numeroJogador = 0;
        msg.tipoMensagem = 1;
        msg.letra = letra;
        ConverteMsgToBytes c1 = new ConverteMsgToBytes();
        DatagramPacket MensagemParaEnviar = new DatagramPacket(c1.convertObjectToByteArray(msg), c1.getLenght(), ipGrupo, porta);

        try {
            s.send(MensagemParaEnviar);
        } catch (IOException e) {
        }
        System.out.println("Letra enviada do servidor para clientes");
    
    }
}
