import java.io.IOException;
import java.util.ArrayList;

public class Server {

	static private ArrayList<Item> stocks = new ArrayList<Item>();
	static private ArrayList<Item> fiis = new ArrayList<Item>();
	static final int groupStocksAndFiis = 4444;
	static final int groupChat = 5555;
	static final int serverPort = 4321;

	public static void fillStocksAndFIIs() {
		Item itemITSA4 = new Item("ITSA4", "Itausa", 9.99, 10);
		Item itemMGLU3 = new Item("MGLU3", "Magalu", 2.10, 10);
		Item itemCASH3 = new Item("CASH3", "Meliuz", 6.00, 10);
		stocks.add(itemITSA4);
		stocks.add(itemMGLU3);
		stocks.add(itemCASH3);

		Item itemHTMX11 = new Item("HTMX11", "FII Hotel Maxinvest", 146.80, 10);
		Item itemOUJP11 = new Item("OUJP11", "Ourinvest JPP FI Imobiliario", 100.14, 10);
		Item itemRZAK11 = new Item("RZAK11", "Riza Akin FII", 92.47, 10);
		fiis.add(itemHTMX11);
		fiis.add(itemOUJP11);
		fiis.add(itemRZAK11);
	}

	public static void main(String[] args) {
		fillStocksAndFIIs();

		Thread speaking = new Thread(new ServerSpeakingThread(stocks, fiis, groupStocksAndFiis, groupChat, serverPort));
		Thread chatManager = new Thread(new ChatThread(stocks, fiis, groupStocksAndFiis, groupChat, serverPort));
		speaking.start();
		chatManager.start();
	}
}
