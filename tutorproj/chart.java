/*
 * Creates chart using jfree.chart for tutor view
 */
package tutorproj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class chart {
    
    private int count = 0;
      
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    private String token01, token02, token03, token04, token05, token06, token07, 
           token08, token09, token10, token11, token12, token13, token14,
           token15, token16, token17, token18, token19, token20, token21,
           token22, token23, token24, token25, token26, token27, token28, 
           token29, token30, token31, token32, token33, token34, token35,
           token36, token37, token38, token39, token40;
    
    public JPanel createPanel(String title) {
         
        JFreeChart chart = createChart(createDataset("skip", "skip"), title);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800,300));
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        
        return panel;
    }
     public JFreeChart createChart(CategoryDataset dataset, String title) {
         
        JFreeChart chart = ChartFactory.createBarChart(
            "Student Data: " + title,  // title
            "Problem set",             // x-axis label
            "Attention",   // y-axis label
            dataset,            // data
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        );

        chart.setBackgroundPaint(Color.BLACK);
        CategoryPlot plot = chart.getCategoryPlot();
        chart.getTitle().setPaint(Color.WHITE);
        
        plot.setBackgroundPaint(Color.GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
        plot.getDomainAxis().setLabelPaint(Color.WHITE);
        plot.getRangeAxis().setLabelPaint(Color.WHITE);
 
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0, 101);
        
        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.WHITE
        );
        GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.WHITE
        );

        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setItemMargin(0);

        return chart;

    }
     public CategoryDataset createDataset(String att, String corr) {

         if (att.compareTo("skip")==0) {

             dataset.addValue(0, "true", "1");
             dataset.addValue(0, "true", "2");
             dataset.addValue(0, "true", "3");
             dataset.addValue(0, "true", "4");
             dataset.addValue(0, "true", "5");
             dataset.addValue(0, "true", "6");
             dataset.addValue(0, "true", "7");
             dataset.addValue(0, "true", "8");
             dataset.addValue(0, "true", "9");
             dataset.addValue(0, "true", "10");
             dataset.addValue(0, "true", "11");
             dataset.addValue(0, "true", "12");
             dataset.addValue(0, "true", "13");
             dataset.addValue(0, "true", "14");
             dataset.addValue(0, "true", "15");
             dataset.addValue(0, "true", "16");
             dataset.addValue(0, "true", "17");
             dataset.addValue(0, "true", "18");
             dataset.addValue(0, "true", "19");
             dataset.addValue(0, "true", "20");
         
         } else {
            
            StringTokenizer test = new StringTokenizer(att);
            StringTokenizer test01 = new StringTokenizer(corr);
            String comma = ",";

            token01 = test.nextToken(comma);
            token02 = test01.nextToken(comma);
            token03 = test.nextToken(comma);
            token04 = test01.nextToken(comma);
            token05 = test.nextToken(comma);
            token06 = test01.nextToken(comma);
            token07 = test.nextToken(comma);
            token08 = test01.nextToken(comma);
            token09 = test.nextToken(comma);
            token10 = test01.nextToken(comma);
            
            dataset.addValue(Integer.parseInt(token01), token02, Integer.toString(count + 1));
            dataset.addValue(Integer.parseInt(token03), token04, Integer.toString(count + 2));
            dataset.addValue(Integer.parseInt(token05), token06, Integer.toString(count + 3));
            dataset.addValue(Integer.parseInt(token07), token08, Integer.toString(count + 4));
            dataset.addValue(Integer.parseInt(token09), token10, Integer.toString(count + 5));
            //dataset.addValue(50, "true", "1");

            count = count + 5;
         }

         return dataset;

     }
     public CategoryDataset createHistDataset(String[] data) {
         
         StringTokenizer test = new StringTokenizer(data[0]);
         StringTokenizer test01 = new StringTokenizer(data[1]);
         String comma = ",";

         token01 = test.nextToken(comma);
         token02 = test.nextToken(comma);
         token03 = test.nextToken(comma);
         token04 = test.nextToken(comma);
         token05 = test.nextToken(comma);
         token06 = test.nextToken(comma);
         token07 = test.nextToken(comma);
         token08 = test.nextToken(comma);
         token09 = test.nextToken(comma);
         token10 = test.nextToken(comma);
         token11 = test.nextToken(comma);
         token12 = test.nextToken(comma);
         token13 = test.nextToken(comma);
         token14 = test.nextToken(comma);
         token15 = test.nextToken(comma);
         token16 = test.nextToken(comma);
         token17 = test.nextToken(comma);
         token18 = test.nextToken(comma);
         token19 = test.nextToken(comma);
         token20 = test.nextToken(comma);
         
         token21 = test01.nextToken(comma);
         token22 = test01.nextToken(comma);
         token23 = test01.nextToken(comma);
         token24 = test01.nextToken(comma);
         token25 = test01.nextToken(comma);
         token26 = test01.nextToken(comma);
         token27 = test01.nextToken(comma);
         token28 = test01.nextToken(comma);
         token29 = test01.nextToken(comma);
         token30 = test01.nextToken(comma);
         token31 = test01.nextToken(comma);
         token32 = test01.nextToken(comma);
         token33 = test01.nextToken(comma);
         token34 = test01.nextToken(comma);
         token35 = test01.nextToken(comma);
         token36 = test01.nextToken(comma);
         token37 = test01.nextToken(comma);
         token38 = test01.nextToken(comma);
         token39 = test01.nextToken(comma);
         token40 = test01.nextToken(comma);
         
         dataset.addValue(Integer.parseInt(token01), token21, "1");
         dataset.addValue(Integer.parseInt(token02), token22, "2");
         dataset.addValue(Integer.parseInt(token03), token23, "3");
         dataset.addValue(Integer.parseInt(token04), token24, "4");
         dataset.addValue(Integer.parseInt(token05), token25, "5");
         dataset.addValue(Integer.parseInt(token06), token26, "6");
         dataset.addValue(Integer.parseInt(token07), token27, "7");
         dataset.addValue(Integer.parseInt(token08), token28, "8");
         dataset.addValue(Integer.parseInt(token09), token29, "9");
         dataset.addValue(Integer.parseInt(token10), token30, "10");
         dataset.addValue(Integer.parseInt(token11), token31, "11");
         dataset.addValue(Integer.parseInt(token12), token32, "12");
         dataset.addValue(Integer.parseInt(token13), token33, "13");
         dataset.addValue(Integer.parseInt(token14), token34, "14");
         dataset.addValue(Integer.parseInt(token15), token35, "15");
         dataset.addValue(Integer.parseInt(token16), token36, "16");
         dataset.addValue(Integer.parseInt(token17), token37, "17");
         dataset.addValue(Integer.parseInt(token18), token38, "18");
         dataset.addValue(Integer.parseInt(token19), token39, "19");
         dataset.addValue(Integer.parseInt(token20), token40, "20");
         
         return dataset;
     }
}
