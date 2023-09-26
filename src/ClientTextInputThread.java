import javax.swing.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class ClientTextInputThread implements Runnable {
    static final int groupChat = 5555;
    static final int serverPort = 4321;
    static final String chatPrefix = "[CHAT]";
    static int identifier;
    boolean shouldRun = true;

    public ClientTextInputThread(int id) {
        identifier = id;
    }

    private String askForInput() {
        return JOptionPane.showInputDialog(null, "Enter your message:", JOptionPane.OK_OPTION);
    }

    public void run() {

        try {
            MulticastSocket socket = new MulticastSocket();
            InetAddress addr = InetAddress.getByName("230.0.0.0");

            while (shouldRun) {
                String input = JOptionPane.showInputDialog(null, "Enter your message:", JOptionPane.OK_OPTION);
                input = identifier + input;

                byte[] msg = input.getBytes();
                DatagramPacket packetToSend = new DatagramPacket(msg, msg.length, addr, serverPort);
                socket.send(packetToSend);

                if (input.contains(identifier + "quit")) {
                    shouldRun = false;
                }
            }
        }
        catch (Exception e) {

        }
    }
}
