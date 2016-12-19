
import forca.ConverteMsgToBytes;
import forca.Mensagem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


    public class Observador {
            public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
                int porta=6868;
                InetAddress ipGrupo=null;
                MulticastSocket s=null;
                String msg="mensagem default";
                byte[] buf = new byte[1512];
                
                // junta-se a um grupo de Multicast
                try {
                    ipGrupo = InetAddress.getByName("224.225.226.227");
                    s = new MulticastSocket(porta);
                    s.joinGroup(ipGrupo);
                } catch (SocketException e) { }
               
                Mensagem c1 = new Mensagem();
                ConverteMsgToBytes conversor = new ConverteMsgToBytes();
                
                DatagramPacket recebido = new DatagramPacket(buf, buf.length);
                DatagramPacket dtgrm = new DatagramPacket(msg.getBytes(),msg.length(), ipGrupo, porta);
                
                // Aguarda mensagem do servidor:
                try {
                    System.out.println("Aguardando Mensagem");
                    s.setSoTimeout(50000);
                    s.receive(recebido);
                    System.out.println("Recebido"); 
                } catch (SocketTimeoutException e) {} 
                
                c1 = (Mensagem) conversor.convertByteArrayToObject(recebido.getData());

                c1.ExibirMensagem();

                //Envia msg para servidor dizendo que ele recebeu a msg:
                try {
                    s.send(dtgrm);
                }catch (IOException e) { }
                
          }
    }
