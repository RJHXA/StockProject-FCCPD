import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerSpeakingThread implements Runnable {
    private ArrayList<Item> stocks;
    private ArrayList<Item> fiis;
    int groupStocksAndFiis;
    int groupChat;
    int serverPort;
    public ServerSpeakingThread(ArrayList<Item> stockList, ArrayList<Item> fiiList, int stockGroupPort, int chatGroupPort, int servPort) {
        stocks = stockList;
        fiis = fiiList;
        groupStocksAndFiis = stockGroupPort;
        groupChat = chatGroupPort;
        serverPort = servPort;
    }

    public void run() {
        try {
            boolean shouldRun = true;
            String mensagem = " ";
            byte[] envioGeneral;
            byte[] envioLower;
            byte[] envioUpper;
            Scanner sc = new Scanner(System.in);

            MulticastSocket socket = new MulticastSocket(serverPort);
            InetAddress addr = InetAddress.getByName("230.0.0.0");
            InetSocketAddress serverFullAddr = new InetSocketAddress(addr, serverPort);
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            socket.joinGroup(serverFullAddr, ni);

            while (shouldRun) {
                // String stockString = stocks.toString();
                // String fiiString = fiis.toString();
                String stockString = "";
                for (Item stock : stocks) {
                    stockString = stockString + stock.toString() + "\n";
                }
                String fiiString = "";
                for (Item fii : fiis) {
                    fiiString = fiiString + fii.toString() + "\n";
                }
                System.out.println("[SERVER] Multicasting stocks and FIIs...");
                String messageToClient = "\nStocks:\n" + stockString + "\nFIIs:\n" + fiiString;
                byte[] msgBytes = messageToClient.getBytes();
                DatagramPacket packetStocks = new DatagramPacket(msgBytes, msgBytes.length, addr, groupStocksAndFiis);
                socket.send(packetStocks);
                Thread.sleep(5000);
            }

            System.out.println("[SERVER] Multicast Closed");
            socket.close();
        } catch (Exception e) {

        }
    }
}