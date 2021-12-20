package app.valuables;

import app.world.Company;

import java.util.List;

public class Index extends Valuable{
    private List<Company> listOfCompanies;

    public Index(String name, Integer price, List<Company> listOfCompanies) {
        super(name, price);
        this.listOfCompanies = listOfCompanies;
    }

    public void update(){}
}
