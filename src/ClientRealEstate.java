import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class ClientRealEstate implements Runnable{

    public void run(){
        try{
            for(;;){

            }
        }catch(Exception e){
            System.out.print("Erro");
            return;
        }
    }

    public static void PrintMenu() {
        System.out.println("[1] - Rever os Fundos Imobiliários Disponíveis\n[2] - Comprar um Fundo Imobiliário");
    }

    public static void main(String[] args) throws IOException {

        String msg = " ";
        MulticastSocket socket=new MulticastSocket(8080);

        InetAddress ia =InetAddress.getByName("230.0.0.0");
        InetSocketAddress grupo = new InetSocketAddress(ia , 8080);
        NetworkInterface ni = NetworkInterface.getByInetAddress(ia);

        socket.joinGroup(grupo,ni);

        while(!msg.contains("Servidor Encerrado!")){
            System.out.println("[Cliente] Esperando por mensagem Multicast...");
            byte[] buffer = new byte[1024];

            DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);

            msg=new String(packet.getData());
            System.out.println(msg);

        }

        System.out.println("[Cliente] Conexao Encerrada!");
        socket.leaveGroup(grupo, ni);
        socket.close();

    }

}
