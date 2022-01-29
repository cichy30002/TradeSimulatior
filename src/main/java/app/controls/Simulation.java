package app.controls;

import app.world.Company;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation extends Task{
    private ExecutorService executorService;
    public void startSimulation() {
        ArrayList<Task<Integer>> tasks = new ArrayList<>(ControlPanel.getInstance().getAllInvestors());
        tasks.addAll(ControlPanel.getInstance().getAllCompanies());
        tasks.addAll(ControlPanel.getInstance().getAllInvestmentFunds());
        executorService = Executors.newCachedThreadPool();
        ControlPanel.getInstance().setSimulationState(true);
        for (Task<Integer> task : tasks) {
            executorService.execute(task);
        }
        executorService.execute(this);

    }

    public void simulateNewTask(Task task)
    {
        executorService.execute(task);
    }
    @Override
    protected Object call() throws Exception {
        while(ControlPanel.getInstance().getSimulationState())
        {
            for(String valuableName : ControlPanel.getInstance().getAllValuables())
            {
                ControlPanel.getInstance().getValuable(valuableName).updatePrice();
            }
            for(Company company : ControlPanel.getInstance().getAllCompanies())
            {
                company.setCapital(company.getCapital() + company.getRevenue());
                company.setRevenue(ThreadLocalRandom.current().nextFloat(-10000, 10000));
                company.setProfit(ThreadLocalRandom.current().nextFloat(100000));
            }
            Thread.sleep(1000);
        }
        return 0;
    }
}
