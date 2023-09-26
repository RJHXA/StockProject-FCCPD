import javax.xml.crypto.Data;
import java.net.*;

public class ClientTextReadThread implements Runnable {
    static final int groupChat = 5555;
    static final int serverPort = 4321;
    static final String chatPrefix = "[CHAT]";
    static int identifier;
    boolean shouldRun = true;

    public ClientTextReadThread(int id) {
        identifier = id;
    }

    private String trimIdentifier(String msg) {
        return msg.substring(6);
    }

    private int getIdentifier(String msg) {
        return Integer.parseInt(msg.substring(0, 6));
    }

    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(groupChat);
            InetAddress ia = InetAddress.getByName("230.0.0.0");
            InetSocketAddress addrAndPort = new InetSocketAddress(ia , groupChat);
            NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
            socket.joinGroup(addrAndPort, ni);

            System.out.println("[WARNING] Client identifier " + identifier);

            while (shouldRun) {
                byte[] buffer = new byte[1024];
                DatagramPacket packetRead = new DatagramPacket(buffer, buffer.length);
                socket.receive(packetRead);

                String msg = new String(buffer);

                if (msg.contains("[SERVER] " + identifier + " left the chat\n")) {
                    shouldRun = false;
                }

                if (!(getIdentifier(msg) == identifier)) {
                    System.out.println(chatPrefix + getIdentifier(msg) + ": " + StringBufferUtils.trimNullSpaces(trimIdentifier(msg)));
                }
            }
        }
        catch (Exception e) {

        }
    }
}
