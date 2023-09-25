public class Stock {
    private final int code;
    private String name;
    private final double price;
    private int quant;

    public Stock(int codeGiven, String nameGiven, double priceGiven, int quantGiven) {
        code = codeGiven;
        name = nameGiven;
        price = priceGiven;
        quant = quantGiven;
    }

    public int getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public int getQuant() {
        return quant;
    }

    public String getName() {
        return name;
    }
}