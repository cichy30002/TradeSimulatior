package app.world;

import app.controls.ControlPanel;
import app.valuables.Valuable;

import java.util.ArrayList;
import java.util.List;

public class Investor extends MarketClient{


    public Investor(String name) {
        super(name);
        ControlPanel.getInstance().addInvestor(this);
    }

    public void bonusFounds(){}
}
