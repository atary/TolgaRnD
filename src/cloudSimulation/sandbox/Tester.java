package cloudSimulation.sandbox;

import cloudSimulation.Optimization.SimEngine;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jfree.ui.RefineryUtilities;
import org.xml.sax.SAXException;

import cloudSimulation.CloudUtils.CloudExample;
import cloudSimulation.GML2BRITE.GML2BRITE;
import cloudSimulation.GraphUtils.GraphML;
import cloudSimulation.MathUtils.Charter;
import javax.swing.JFrame;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryLpSolve;

public class Tester {

    /**
     * @param args
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        //graphTester();
        //cloudTester();
        //gmlBRTTester();
        //chartTester();
        //guiTester();
        optimizer();
    }

    private static void chartTester() {
        Charter charter = new Charter("Demo");
        charter.fetchWindow();
        RefineryUtilities.centerFrameOnScreen(charter);
        charter.setVisible(true);
    }

    private static void gmlBRTTester() throws IOException, SAXException {
        GML2BRITE gbr = new GML2BRITE();
        gbr.readIn("io/topology.graphml");
        System.out.println(gbr);

    }

    private static void cloudTester() {
        (new CloudExample()).go();

    }

    public static void graphTester() throws ParserConfigurationException, SAXException, IOException {
        String filename = "io/sample.graphml";
        GraphML graph = new GraphML();
        graph.readIn(filename);
        graph.outConsole();

        final JFrame frame = new JFrame();
        frame.setTitle("GraphMLReader for Trees - Reading in Attributes"); // Set the
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Give a close
        graph.drawGraph(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);

    }

    private static void guiTester() throws ParserConfigurationException, SAXException, IOException {
        String filename = "io/sample.graphml";
        SimFacade.getInstance().go();
    }

    private static void optimizer() throws InterruptedException {

//        SimEngine engin = new SimEngine();
//        engin.createVMs();
//       
//        engin.go(1500,50);

        SolverFactory factory = new SolverFactoryLpSolve(); // use lp_solve
        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, 100); // set timeout to 100 seconds

        /**
         * Constructing a Problem: 
         * Maximize: 143x+60y 
         * Subject to: 
         * 120x+210y <= 15000 
         * 110x+30y <= 4000 
         * x+y <= 75
         *           
         * With x,y being integers
         *         
         */
        
        Problem problem = new Problem();

        Linear linear = new Linear();
        
        //Set objective: Server A-B-C cpu wlds summed should be max
        for(char ind='A';ind<='C';ind++){
            linear.add(1, "cpu"+ind);      
//          linear.add(1, "ram"+ind);
//          linear.add(1, "sto"+ind);
//          linear.add(1, "bwi"+ind);
        }

        problem.setObjective(linear, OptType.MAX);


        int numOfTasks=9;
        
        
        //Each task should be assigned to at most one server
        for(int i=0;i<=numOfTasks;i++){
            linear = new Linear();

            for(char ind='A';ind<='C';ind++)
                linear.add(1, ""+ind+i);
            
            problem.add(linear, "<=", 1);                    
        }
                

        int[] taskCpuWLs = {40,10,20,30,40,50,60,30,20,10};
        
        //for each server
        for(char ind='A';ind<='C';ind++){
            
            //Sum up all the assigned tasks... should be equal to cpu wld of the server
            linear = new Linear();
            for(int i=0;i<=numOfTasks;i++)    
                linear.add(taskCpuWLs[i], ""+ind+i);  
            linear.add(-1, "cpu"+ind);
            problem.add(linear, "=", 0);
        }

        for(char ind='A';ind<='C';ind++){
            problem.setVarType("cpu"+ind, Integer.class);
//          problem.setVarType("ramA", Integer.class);
//          problem.setVarType("stoA", Integer.class);
//          problem.setVarType("bwiA", Integer.class);
        }

        for(char ind='A';ind<='C';ind++)
            for(int i=0;i<=numOfTasks;i++)    
                problem.setVarType(""+ind+i, Integer.class);
        
        for(char ind='A';ind<='C';ind++){
            problem.setVarUpperBound("cpu"+ind, 100);
//          problem.setVarUpperBound("ramA", 100);
//          problem.setVarUpperBound("stoA", 100);
//          problem.setVarUpperBound("bwiA", 100);
        } 
        
        Solver solver = factory.get();
        Result result = solver.solve(problem);

        System.out.println(result);
    }
}
