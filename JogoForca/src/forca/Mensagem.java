package forca;
import java.io.*;

/**
 *
 * @author Marcos
 */
public class Mensagem implements Serializable{
    int tipoMensagem;
    int numeroJogador;
    String dados;
    String letra;

    public int getTipoMensagem() {
        return tipoMensagem;
    }

    public void setTipoMensagem(int tipoMensagem) {
        this.tipoMensagem = tipoMensagem;
    }

    public int getNumeroJogador() {
        return numeroJogador;
    }

    public void setNumeroJogador(int numeroJogador) {
        this.numeroJogador = numeroJogador;
    }

    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
    
    public Mensagem(int tipoMensagem, String dados){
        this.tipoMensagem = tipoMensagem;
        this.dados = dados;
    }
    
    public Mensagem(){}
    
    public void ExibirMensagem(){
     System.out.println(dados);
    }
        
    
}
