package Conexao;
import forca.ClientController;
import forca.ConverteMsgToBytes;
import forca.Mensagem;
import forca.ServerController;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcos
 */
public class ReceberPacotes implements Runnable{
    
    MulticastSocket s = null;
    ServerController server;
    ClientController client;
    int cs; //Zero se for cliente, 1 se for servidor
    
    public ReceberPacotes(ServerController tela,MulticastSocket s){
        this.s = s;
        this.server = tela;
        this.cs = 1;
    }
    
    
    public ReceberPacotes(ClientController tela,MulticastSocket s){
        this.s = s;
        this.client = tela;
        this.cs = 0;
    }
    
    @Override
    public void run() {
        while (true) {
            byte[] buf = new byte[1512];

            Mensagem msg = new Mensagem();
            ConverteMsgToBytes conversor = new ConverteMsgToBytes();
            DatagramPacket recebido = new DatagramPacket(buf, buf.length);

            // Aguarda mensagem do cleinte:
            try {
                System.out.println("Aguardando Mensagem");
                s.setSoTimeout(50000);
                s.receive(recebido);
                System.out.println("Recebido");
            } catch (SocketTimeoutException e) {
            } catch (SocketException ex) {
                Logger.getLogger(ReceberPacotes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReceberPacotes.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
            //Transforma array de bytes recebidos em objeto mensagem
            msg = (Mensagem) conversor.convertByteArrayToObject(recebido.getData());

            //Tratamento de mensagens recebidas
            if(cs == 1) server.analiseMensagem(msg);
            else client.analiseMensagem(msg);
            
        }
    }
    
   

    
    
}
