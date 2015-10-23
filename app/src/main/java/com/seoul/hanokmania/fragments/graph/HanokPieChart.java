package com.seoul.hanokmania.fragments.graph;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
public class HanokPieChart extends AbstractChart {
    /**
     * pie chart.
     */

    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Sales stacked pie chart";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The monthly sales for the last 2 years (stacked pie chart)";
    }

    /**
     * Executes the chart.
     *
     * @param context the context
     * @return the built graphicalview
     */
    public GraphicalView getGraphView(Context context, List list) {

        int[] COLORS = new int[]{Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN};

        double[] VALUES = new double[]{10, 11, 12, 13};

        String[] NAME_LIST = new String[]{"A", "B", "C", "D"};

        CategorySeries mSeries = new CategorySeries("");

        DefaultRenderer mRenderer = new DefaultRenderer();

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        for (int i = 0; i < VALUES.length; i++) {
            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        return ChartFactory.getPieChartView(
                context,
                mSeries,
                mRenderer);


    }
}