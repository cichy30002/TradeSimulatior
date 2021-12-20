package app.valuables;

public class Share extends Valuable{
    private final String company;

    public Share(String name, Integer price, String company) {
        super(name, price);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }
}
