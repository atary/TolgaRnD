/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudSimulation.Optimization;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ovatman
 */
public class SimEngine {

    private ArrayList<VMach> VMs;

    public SimEngine() {
        VMs = new ArrayList<VMach>();

    }

    public void createVMs() {
        VMs.add(new VMach("ONE", 100, 100, 100, 100));

        VMs.add(new VMach("TWO", 100, 100, 100, 100));

        VMs.add(new VMach("THREE", 100, 100, 100, 100));
    }

    public void submitApp(int i, App app) {
        if (!VMs.get(i).add(app)) {
            int j = i;
            do {
                j = (j + 1) % VMs.size();
                if (VMs.get(j).add(app)) {
                    System.out.println("App added to VM " + (j + 1) + " instead of VM " + (i + 1));
                    return;
                }
            } while (j != i);
        } else {
            return;
        }
        System.out.println("App refused");

    }

    public void printVMs() {

        for (VMach v : VMs) {
            System.out.println(v);
        }

    }

    public void go(int freq, int maxit) throws InterruptedException {
        int i = 0;
        Random r = new Random();

        while (i < maxit) {
            if (r.nextInt(5) > 0) {
                submitApp(r.nextInt(3), new App("App" + i, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5, r.nextInt(10) + 5));
            }

            printVMs();
            System.out.println("Iteration: " + i);
            System.out.println("-----------------------------------------------------------------------------");
            i++;
            Thread.sleep(freq);
        }
    }
}
