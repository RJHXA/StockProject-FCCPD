public class Item {
    private final String code;
    private final String name;
    private final double price;
    private int quant;

    public Item(String code_num, String prod_name, double priceNum, int qtt) {
        code = code_num;
        name = prod_name;
        price = priceNum;
        quant = qtt;
    }

    public String getCode() {
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

    public String toString() {
        return getCode() + " está a R$" + getPrice();
    }
}