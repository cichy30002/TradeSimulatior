package app.valuables;

public abstract class Valuable {
    private final String name;
    private Integer price;


    protected Valuable(String name, Integer price) {
        this.name = name;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
