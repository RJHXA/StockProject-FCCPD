import javax.xml.crypto.Data;
import java.net.*;
import java.util.ArrayList;

public class ChatThread implements Runnable {
    private ArrayList<Item> stocks;
    private ArrayList<Item> fiis;
    int groupStocksAndFiis;
    int groupChat;
    int serverPort;

    public ChatThread(ArrayList<Item> stockList, ArrayList<Item> fiiList, int stockGroupPort, int chatGroupPort, int servPort) {
        stocks = stockList;
        fiis = fiiList;
        groupStocksAndFiis = stockGroupPort;
        groupChat = chatGroupPort;
        serverPort = servPort;
    }

    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(serverPort);
            InetAddress addr = InetAddress.getByName("230.0.0.0");
            InetSocketAddress serverFullAddr = new InetSocketAddress(addr, serverPort);
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            socket.joinGroup(serverFullAddr, ni);

            while (true) {
                System.out.println("[SERVER] Waiting for messages to multicast...");
                byte[] msgBuffer = new byte[1024];

                DatagramPacket packet = new DatagramPacket(msgBuffer, msgBuffer.length);
                socket.receive(packet);

                // System.out.println("[SERVER] Message received: " + new String(msgBuffer));

                DatagramPacket packetToMulticast = new DatagramPacket(msgBuffer, msgBuffer.length, addr, groupChat);
                socket.send(packetToMulticast);
            }
        }
        catch (Exception e) {

        }
    }
}
