/*
 * Creates user charts for attention data
 */
package studproj;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class chart {
    
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    brain attentionResult = new brain();
    
    public chart() {
        
    }
     public JPanel createPanel(String title) {
        
        JFreeChart chart = createChart( title);
        ChartPanel panel = new ChartPanel(chart);
        
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(false);
        
        return panel;
    }
     private JFreeChart createChart(String title) {
         
        JFreeChart chart = ChartFactory.createBarChart(
            "",  // title
            "",             // x-axis label
            "",   // y-axis label
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false  // data
        );

        chart.setBackgroundPaint(Color.BLACK);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.black);

        BarRenderer r = (BarRenderer)plot.getRenderer();
        r.setSeriesPaint(0,Color.green);
        
        NumberAxis xAxis = (NumberAxis) plot.getRangeAxis();
        xAxis.setRange(1, 100);
        xAxis.setTickUnit(new NumberTickUnit(10));
         
        return chart;

    }
     public void createDataset(int y) {
        
        String series1 = "Attention";
        String category = "Attention";
        
        dataset.addValue(y, series1, category);

     }

}
