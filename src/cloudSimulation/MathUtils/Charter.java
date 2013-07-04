package cloudSimulation.MathUtils;

import cloudSimulation.CloudUtils.UtilizationModels.DistributionType;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.fitting.CurveFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;


public class Charter extends ApplicationFrame implements ActionListener {

    private XYSeries series;
    private Distributor dist;
    private int lastValue = 0;
    private XYDataset dataset;
    private ChartPanel chartPanel;
    private CurveFitter fitter;

    public Charter(String title) {
        super(title);
        dataset = new XYSeriesCollection();
        series = new XYSeries("Random Data");
        fitter = new CurveFitter(new LevenbergMarquardtOptimizer());
        chartPanel = new ChartPanel(createChart(dataset));
        dist=new Distributor();
    }

    public void fetchWindow() {
        chartPanel = new ChartPanel(createChart(dataset));
        final JButton button = new JButton("Add New Data Item");
        button.setActionCommand("ADD_DATA");
        button.addActionListener(this);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        content.add(button, BorderLayout.SOUTH);
        //chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);

        pack();

    }

    public void drawChart(Container content) {
        addAllDataPoints();

        chartPanel = new ChartPanel(createChart(dataset));
        
   
        content.removeAll();
        content.setLayout(new BorderLayout());
        content.add(chartPanel);
        content.setPreferredSize(new Dimension(194, 174));
        content.revalidate();
    }

    public void getConfig(DistributionType dt,JPanel panel,JPanel mPanel){
       
        switch(dt){
            case NORMALDISTRIBUTION:
                mPanel.removeAll();
                mPanel.setLayout(new GridLayout(5,2));
                for(Component c:normalDistroComponents(panel))
                    mPanel.add(c);
                mPanel.revalidate();
            break;
            case POISSONDISTRIBUTION:                
                mPanel.removeAll();
                mPanel.setLayout(new GridLayout(5,2));
                for(Component c:poissonDistroComponents(panel))
                    mPanel.add(c);
                mPanel.revalidate();
            break;
            case ZIPFDISTRIBUTION:           
                mPanel.removeAll();
                mPanel.setLayout(new GridLayout(5,2));
                for(Component c:zipfDistroComponents(panel))
                    mPanel.add(c);
                mPanel.revalidate();
            break;
            case RANDOMDISTRIBUTION:
                randomDistroComponents(panel);
            break;
            default:
            break;
        }
        
    }
    
    
    private JFreeChart createChart(XYDataset data) {
        JFreeChart chart = ChartFactory.createScatterPlot(null, "X", "Y", data,
                PlotOrientation.VERTICAL, true, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();


        XYItemRenderer scatterRenderer = plot.getRenderer();
        plot.setDataset(1, data);

/*
        StandardXYItemRenderer regressionRenderer = new StandardXYItemRenderer();
        regressionRenderer.setBaseSeriesVisibleInLegend(false);

        plot.setDataset(2, regress(data));
        plot.setRenderer(2, regressionRenderer);

        DrawingSupplier ds = plot.getDrawingSupplier();
        for (int i = 0; i < data.getSeriesCount(); i++) {
            Paint paint = ds.getNextPaint();
            scatterRenderer.setSeriesPaint(i, paint);
            regressionRenderer.setSeriesPaint(i, paint);
        }
*/

        plot.getRangeAxis().setVisible(false);
        plot.getDomainAxis().setVisible(false);
        chart.removeLegend();
        return chart;
    }

    private XYDataset regress(XYDataset data) {
        double xMin = Double.MAX_VALUE, xMax = 0;
        for (int i = 0; i < data.getSeriesCount(); i++) {
            for (int j = 0; j < data.getItemCount(i); j++) {
                double x = data.getXValue(i, j);
                if (x < xMin) {
                    xMin = x;
                }
                if (x > xMax) {
                    xMax = x;
                }
            }
        }

        fitter.clearObservations();

        XYSeriesCollection coll = new XYSeriesCollection();
        for (int i = 0; i < data.getSeriesCount(); i++) {

            int n = data.getItemCount(i);

            for (int j = 0; j < n; j++) 
                fitter.addObservedPoint(data.getXValue(i, j), data.getYValue(i, j));
            

            final double[] init = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // 12.9 - 3.4 x + 2.1 x^2

            final double[] best = fitter.fit(new PolynomialFunction.Parametric(), init);

            final PolynomialFunction fitted = new PolynomialFunction(best);

            XYSeries regr = new XYSeries(data.getSeriesKey(i));
            for (double d = xMin; d < xMax; d += ((xMax - xMin) / 10)) {
                regr.add(d, fitted.value(d));
            }

            coll.addSeries(regr);
        }

        return coll;
    }
    

    
    public void addAllDataPoints() {
        lastValue=0;
        series.clear();
        
        for(int i=0;i<100;i++)
            series.add(lastValue, dist.getNextDistValue(lastValue++));
        
        ((XYSeriesCollection) dataset).removeAllSeries();
        ((XYSeriesCollection) dataset).addSeries(series);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ADD_DATA")) {
            series.add(lastValue, dist.getNextDistValue(lastValue));
            //series.add(lastValue++,Math.random());
            ((XYSeriesCollection) dataset).removeAllSeries();
            ((XYSeriesCollection) dataset).addSeries(series);
            fetchWindow();
        }

    }

    public void setDistribution(DistributionType distributionType) {
        dist.setDistribution(distributionType);
    }

    private ArrayList<Component> normalDistroComponents(JPanel panel) {
        ArrayList<Component> cList=new ArrayList<Component>();
        final JTextField syserr,randomerr,mean,var;
        final JPanel fpanel=panel;
        
        cList.add(new JLabel("SysErr"));
        syserr=new JTextField();
        syserr.setColumns(5);
        syserr.setText(""+dist.getParam("SysErr"));
        cList.add(syserr);

        cList.add(new JLabel("RandomErr"));
        randomerr=new JTextField();
        randomerr.setColumns(5);
        randomerr.setText(""+dist.getParam("RandomErr"));
        cList.add(randomerr);

        cList.add(new JLabel("Mean"));
        mean=new JTextField();
        mean.setColumns(5);
        mean.setText(""+dist.getParam("NormalMean"));
        cList.add(mean);
        
        cList.add(new JLabel("Variance"));
        var=new JTextField();
        var.setColumns(5);
        var.setText(""+dist.getParam("NormalVar"));
        cList.add(var);

        JButton but=new JButton("Replot");
        but.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                dist.setParam("SysErr", Double.parseDouble(syserr.getText().trim()));
                dist.setParam("RandomErr", Double.parseDouble(randomerr.getText().trim()));
                dist.setParam("NormalMean", Double.parseDouble(mean.getText().trim()));
                dist.setParam("NormalVar", Double.parseDouble(var.getText().trim()));
                dist.reDist();
               drawChart(fpanel);
            }
        }
        );
        cList.add(but);

        return cList;
    }

    private ArrayList<Component> poissonDistroComponents(JPanel panel) {
        ArrayList<Component> cList=new ArrayList<Component>();
        final JTextField syserr,randomerr,mean,epsilon;
        final JPanel fpanel=panel;
        
        cList.add(new JLabel("SysErr"));
        syserr=new JTextField();
        syserr.setColumns(5);
        syserr.setText(""+dist.getParam("SysErr"));
        cList.add(syserr);

        cList.add(new JLabel("RandomErr"));
        randomerr=new JTextField();
        randomerr.setColumns(5);
        randomerr.setText(""+dist.getParam("RandomErr"));
        cList.add(randomerr);

        cList.add(new JLabel("Mean"));
        mean=new JTextField();
        mean.setColumns(5);
        mean.setText(""+dist.getParam("PoiMean"));
        cList.add(mean);
        
        cList.add(new JLabel("Epsilon"));
        epsilon=new JTextField();
        epsilon.setColumns(5);
        epsilon.setText(""+dist.getParam("PoiEps"));
        cList.add(epsilon);

        JButton but=new JButton("Replot");
        but.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                dist.setParam("SysErr", Double.parseDouble(syserr.getText().trim()));
                dist.setParam("RandomErr", Double.parseDouble(randomerr.getText().trim()));
                dist.setParam("PoiMean", Double.parseDouble(mean.getText().trim()));
                dist.setParam("PoiEps", Double.parseDouble(epsilon.getText().trim()));
                dist.reDist();
               drawChart(fpanel);
            }
        }
        );
        cList.add(but);

        return cList;
    }

    private ArrayList<Component> zipfDistroComponents(JPanel panel) {
        ArrayList<Component> cList=new ArrayList<Component>();
        final JTextField syserr,randomerr,zipfnoe,zipfexpo;
        final JPanel fpanel=panel;
        
        cList.add(new JLabel("SysErr"));
        syserr=new JTextField();
        syserr.setColumns(5);
        syserr.setText(""+dist.getParam("SysErr"));
        cList.add(syserr);

        cList.add(new JLabel("RandomErr"));
        randomerr=new JTextField();
        randomerr.setColumns(5);
        randomerr.setText(""+dist.getParam("RandomErr"));
        cList.add(randomerr);

        cList.add(new JLabel("Number of Elements"));
        zipfnoe=new JTextField();
        zipfnoe.setColumns(5);
        zipfnoe.setText(""+dist.getParam("ZipfNoE"));
        cList.add(zipfnoe);
        
        cList.add(new JLabel("Exponent"));
        zipfexpo=new JTextField();
        zipfexpo.setColumns(5);
        zipfexpo.setText(""+dist.getParam("ZipfExpo"));
        cList.add(zipfexpo);

        JButton but=new JButton("Replot");
        but.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                dist.setParam("SysErr", Double.parseDouble(syserr.getText().trim()));
                dist.setParam("RandomErr", Double.parseDouble(randomerr.getText().trim()));
                dist.setParam("ZipfNoE", Double.parseDouble(zipfnoe.getText().trim()));
                dist.setParam("ZipfExpo", Double.parseDouble(zipfexpo.getText().trim()));
                 dist.reDist();   
                drawChart(fpanel);
            }
        }
        );
        
        cList.add(but);

        return cList;
    }

    private ArrayList<Component> randomDistroComponents(JPanel panel) {
        ArrayList<Component> cList=new ArrayList<Component>();
        JTextField temp;
        final JPanel fpanel=panel;
        
        cList.add(new JLabel("SysErr"));
        temp=new JTextField();
        temp.setColumns(5);
        cList.add(temp);

        cList.add(new JLabel("RandomErr"));
        temp=new JTextField();
        temp.setColumns(5);
        cList.add(temp);

        cList.add(new JLabel("Mean"));
        temp=new JTextField();
        temp.setColumns(5);
        cList.add(temp);
        
        cList.add(new JLabel("Variance"));
        temp=new JTextField();
        temp.setColumns(5);
        cList.add(temp);

        JButton but=new JButton("Replot");
        temp.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               drawChart(fpanel);
            }
        }
        );
        cList.add(but);

        return cList;
    }
}