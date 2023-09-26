import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class Client {

	static final int groupStocksAndFiis = 4444;
	static final int groupChat = 5555;
	static final int serverPort = 4321;
	static final String stockAndFiiPrefix = "[STOCKS/FIIs]";
	static final String chatPrefix = "[CHAT]";
	static boolean shouldRun = true;

	public static void main(String[] args) throws IOException {
		while (shouldRun) {
			int mode;
			String prefix;
			System.out.println("[CLIENT] 1- See Stocks and FIIs; 2- Chat with other users; 3- quit: ");
			mode = Integer.parseInt(new Scanner(System.in).nextLine());
			if (mode != 1 && mode != 2 && mode != 3) {
				System.out.println("Invalid mode");
				return;
			}

			if (mode == 3) {
				shouldRun = false;
			}

			prefix = mode == 1 ? stockAndFiiPrefix : chatPrefix;
			mode = mode == 1 ? groupStocksAndFiis : groupChat;

			String msg = " ";
			MulticastSocket socket = new MulticastSocket(mode);
			InetAddress ia = InetAddress.getByName("230.0.0.0");
			InetSocketAddress addrAndPort = new InetSocketAddress(ia, mode);
			NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
			socket.joinGroup(addrAndPort, ni);

			while (shouldRun && mode == groupStocksAndFiis) {
				byte[] buffer = new byte[1024];

				DatagramPacket serverMsgPacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(serverMsgPacket);
				System.out.println(prefix + " Message received: " + StringBufferUtils.trimNullSpaces(new String(buffer)));
			}

			if (shouldRun && mode == groupChat) {
				Random rnd = new Random();
				int num = 100000 + rnd.nextInt(900000);

				Thread reading = new Thread(new ClientTextReadThread(num));
				Thread writing = new Thread(new ClientTextInputThread(num));

				try {
					writing.start();
					reading.start();
					reading.join();
					writing.join();
				} catch (Exception e) {

				}
			}

			System.out.println("[CLIENT] Conexao Encerrada!");
			socket.leaveGroup(addrAndPort, ni);
			socket.close();
		}
	}
}