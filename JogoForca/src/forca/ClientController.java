package forca;

import Conexao.ReceberPacotes;
import Front.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public final class ClientController {

    static TelaJogador tela;
    int idJogador = 1;

    int porta = 6868;
    InetAddress ipGrupo = null;
    MulticastSocket s = null;
    String msg = "mensagem default";
    byte[] buf = new byte[1512];

    public ClientController() throws IOException, UnknownHostException, InterruptedException {
        entrarJogo();
        this.tela = new TelaJogador(this);
        tela.setVisible(true);
        entraGrupo();
        ReceberPacotes Pac = new ReceberPacotes(this, s);
        Thread threadPacotesRecebidos = new Thread(Pac);
        threadPacotesRecebidos.start();

    }
    //Entrar no grupo 

    void entraGrupo() throws UnknownHostException, IOException {
        try {
            ipGrupo = InetAddress.getByName("224.225.226.227");
            s = new MulticastSocket(porta);
            s.joinGroup(ipGrupo);
        } catch (SocketException e) {
        }
    }

    //Entrar no jogo
    void entrarJogo() throws UnknownHostException, IOException, InterruptedException {
        
        int portaE = 7475; //Porta de envio de pacotes
        int portaR = 4343; //Porta de recebimento de pacotes
        DatagramSocket clientSocket = new DatagramSocket(portaR);
        String servidor = "255.255.255.255";
        
        InetAddress IPAddress = InetAddress.getByName(servidor);
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        
        
        JOptionPane.showMessageDialog(null, "Aguadando conexão com servidor");
        
        while(true){
            
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            clientSocket.receive(receivePacket);
            String stringRecebida = new String(receivePacket.getData());
            System.out.println(stringRecebida);
            if(stringRecebida.contains("aaa")){
                System.out.println("saindo");
                break; 
            }
        }
        
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            clientSocket.receive(receivePacket);
            String stringRecebida = new String(receivePacket.getData());
            System.out.println("Aqui");
            if(stringRecebida.contains("Ok")) {
                Thread.sleep(5000);
                String sentence = "InicioJogo";
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portaE);
                System.out.println("Cliente iniciando o jogo");
                clientSocket.send(sendPacket);
                break;
            } 
        }
        clientSocket.close();   

    }
    //Analisa mensagens recebidas e trata elas:

    public void analiseMensagem(Mensagem msg) {

        if (msg.numeroJogador == 0) {
            switch (msg.tipoMensagem) {
                case 1: //Mensagem letra do servidor
                    tela.setLetrasDigitadas(msg.letra);
                    break;
                case 2: //Mensagem jogador da rodada
                case 3: //Mensagem ganhador
                case 4: //Mensagem de fim de jogo
                case 5: //Mensagem de dica
                    tela.setDica(msg.dados);
                    break;
            }
        }

    }

    //Jogo
    public void jogo() {

    }
    //Enviar mensagem para servidor:

    public void enviarLetra(String letra) {
        
        Mensagem msg = new Mensagem();
        msg.letra = letra;
        msg.numeroJogador = this.idJogador;
        msg.tipoMensagem = 1;

        ConverteMsgToBytes c1 = new ConverteMsgToBytes();
        DatagramPacket MensagemParaEnviar = new DatagramPacket(c1.convertObjectToByteArray(msg), c1.getLenght(), ipGrupo, porta);

        try {
            s.send(MensagemParaEnviar);
        } catch (IOException e) {
        }
    System.out.println("Mensagem enviada");
    }
    
    //Enviar palavra completa para servidor:
    
    public void enviarPalavra(String palavra) {
        
        Mensagem msg = new Mensagem();
        msg.letra = palavra;
        msg.numeroJogador = this.idJogador;
        msg.tipoMensagem = 3;

        ConverteMsgToBytes c1 = new ConverteMsgToBytes();
        DatagramPacket MensagemParaEnviar = new DatagramPacket(c1.convertObjectToByteArray(msg), c1.getLenght(), ipGrupo, porta);

        try {
            s.send(MensagemParaEnviar);
        } catch (IOException e) {
        }
    System.out.println("Mensagem enviada");
    }
    
    
}
