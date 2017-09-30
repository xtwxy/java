package pc;

public class Product {
    private String name;
    private String serial;
    public Product(String name, String serial) {
        this.name = name;
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public String getSerial() {
        return serial;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", serial='" + serial + '\'' +
                '}';
    }
}
