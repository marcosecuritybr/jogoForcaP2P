import forca.ConverteMsgToBytes;
import forca.Mensagem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Envia {
    public static void main(String args[]) throws IOException, InterruptedException {
        
        int porta=6868;
        InetAddress ipGrupo=null;
        MulticastSocket s=null;
        String msg="mensagem default";
       

        // junta-se a um grupo de Multicast
        try {
            ipGrupo = InetAddress.getByName("224.225.226.227");
            s = new MulticastSocket(porta);
            s.joinGroup(ipGrupo);
        } catch (SocketException e) { }

        // Aguarda 5 participantes entrarem na partida:
        DatagramPacket dtgrm = new DatagramPacket(msg.getBytes(),msg.length(), ipGrupo, porta);
        
        int descobertas = 0;
        int jogadores = 0;
        
        while(jogadores < 1 || descobertas < 4){
            //Envia...
            byte[] buf = new byte[1512];
            DatagramPacket recebido = new DatagramPacket(buf, buf.length);
            try {
		s.send(dtgrm);
                s.setSoTimeout(2000);
                s.receive(recebido);
            }catch (IOException e) { }
           
            
            //Espera 5segundos para receber algo:
            try {
                s.setSoTimeout(2000);
                s.receive(recebido);
                jogadores++;
                System.out.println("Recebido jogador" + jogadores);
            } catch (SocketTimeoutException e) {}
            descobertas++;
        }
        
        System.out.println("Concluido");
        
        Mensagem m1 = new Mensagem(10,"Olhasooooo");
        ConverteMsgToBytes c1 = new ConverteMsgToBytes();
        DatagramPacket MensagemParaEnviar = new DatagramPacket(c1.convertObjectToByteArray(m1),c1.getLenght(), ipGrupo, porta);
        try {
            s.send(MensagemParaEnviar);  
        }catch (IOException e) { }
           
    }
    
    
    
}
