import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.*;

// Primeira mensegem a ser mandado para o cliente é o menu
// Aqui implementa a divulgação dos preços das ações a serem divulgados pelo multicast pros clientes
// Também aqui recebe respostas dos clientes dependendo do item que selecionarem do menu
public class Server implements Runnable{

    private MulticastSocket socket;
    private InetAddress grup;
    private ArrayList<Stock> stocks;
    private ArrayList<Stock> realEstates;

    public Server(MulticastSocket socketGiven, InetAddress grupGiven, ArrayList<Stock> stocksGiven, ArrayList<Stock> realEstatesGiven) {
        socket = socketGiven;
        grup = grupGiven;
        stocks = stocksGiven;
        realEstates = realEstatesGiven;
    }

    public void run(){
        try{
            boolean exit = true;
            String menu = "[1] - Visualizar as Ações\n[2] - Comprar Ações\n[3] - Encerrar";
            byte[] envioMenu = menu.getBytes();
            DatagramPacket pacoteMenu = new DatagramPacket(envioMenu, envioMenu.length, grup, 4321);

            while(exit) {
                socket.send(pacoteMenu);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, grup, 4321);
                socket.receive(receivePacket);
                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println(request);

                if(request == "1") {
                    sendStocks();
                    sendRealEstates();
                } else if (request == "2") {

                } else if (request == "3") {
                    exit = false;
                }
                else {
                    System.out.println("ENTROU AQUI MESMO!");
                }
            }

        } catch(Exception e){
            System.out.print("Erro");
            return;
        }

        System.out.print("[Servidor] Multicast Encerrado");
        socket.close();
    }

    public void sendStocks() {
        try{
            String mensagemStock = transformJSON(stocks);
            byte[] envioStock = new byte[1024];
            envioStock = mensagemStock.getBytes();
            DatagramPacket pacoteStock = new DatagramPacket(envioStock, envioStock.length, grup, 4321);
            socket.send(pacoteStock);

        } catch (Exception e) {
            System.out.print("Erro");
        }
    }

    public void sendRealEstates() {
        try{
            String mensagemRealEstate = transformJSON(realEstates);
            byte[] envioRealEstate = new byte[1024];
            envioRealEstate = mensagemRealEstate.getBytes();
            DatagramPacket pacoteRealEstate = new DatagramPacket(envioRealEstate, envioRealEstate.length,grup, 8080);
            socket.send(pacoteRealEstate);

        } catch (Exception e) {
            System.out.print("Erro");
        }
    }

    public static void addStocksItens(ArrayList<Stock> list) {
        Stock t1 = new Stock(123, "IPT4", 4.5, 500);
        list.add(t1);
        Stock t2 = new Stock(124, "CMIG", 7.5, 400);
        list.add(t2);
        Stock t3 = new Stock(125, "AZUL4", 2.5, 1000);
        list.add(t3);
    }

    public static void addRealEstateItens(ArrayList<Stock> list) {
        Stock t1 = new Stock(533, "HGLG11", 5.5, 300);
        list.add(t1);
        Stock t2 = new Stock(534, "KNIP11", 1.5, 500);
        list.add(t2);
        Stock t3 = new Stock(535, "LPLP11", 9.5, 800);
        list.add(t3);
    }

    public static String transformJSON(ArrayList<Stock> list) {
        String message = "";
        for(int i = 0; i < 3; i++) {
            String temp = "Code: " + list.get(i).getCode() + "; Name: " + list.get(i).getName() + "; Price: " + list.get(i).getPrice() + "; Qauntity: " + list.get(i).getQuant() + "\n";
            message = message + temp;
        }

        return message;
    }

    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket();
        InetAddress grupo = InetAddress.getByName("230.0.0.0");

        // Listas de ações e fundos imobiliários
        ArrayList<Stock> Stocks= new ArrayList<>();
        ArrayList<Stock> RealEstates= new ArrayList<>();

        // Adição de Itens nas duas listas
        addStocksItens(Stocks);
        addRealEstateItens(RealEstates);

        Server server = new Server(socket, grupo, Stocks, RealEstates);
        Thread threadServer = new Thread(server);

        try {
            threadServer.start();
            threadServer.join();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}