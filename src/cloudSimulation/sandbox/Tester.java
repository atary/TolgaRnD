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
import cloudSimulation.Optimization.ILPGUI;
import cloudSimulation.Optimization.LPSolver;
import java.util.Scanner;
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

        SimEngine engin = new SimEngine();
        engin.createVMs();
       
        ILPGUI.go(engin);
        
        //engin.go(1000,100);
        
 
            
        //LPSolver lps=new LPSolver();
        //lps.solve();
            
            

    }
}
