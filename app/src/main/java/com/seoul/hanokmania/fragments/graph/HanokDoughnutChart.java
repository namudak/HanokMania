package com.seoul.hanokmania.fragments.graph;

/**
 * Created by student on 2015-10-23.
 */
/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.renderer.DefaultRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Budget demo pie chart.
 */
public class HanokDoughnutChart extends AbstractChart {
    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Budget chart for several years";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The budget per project for several years (doughnut chart)";
    }

    /**
     * Executes the chart demo.
     *
     * @param context the context
     * @return the built intent
     */
    public GraphicalView getGraphView(Context context, List list) {

        List<String[]> titles = new ArrayList<String[]>();
        List<double[]> values = new ArrayList<double[]>();
        String[] str= new String[list.size()- 1];
        String[] strTitle= new String[list.size()- 1];
        double[] val= new double[list.size()- 1];
        for(int i= 0; i< list.size()- 1; i++){
            str= list.get(i).toString().split(",");
            strTitle[i]= str[0];
            val[i]= Float.parseFloat(str[1]);
        }
        titles.add(strTitle);
        values.add(val);
        //titles.add(new String[] { "P1", "P2", "P3", "P4", "P5" });
        //titles.add(new String[] { "Project1", "Project2", "Project3", "Project4", "Project5" });
        //values.add(new double[] { 12, 14, 11, 10, 19 });
        //values.add(new double[] { 10, 9, 14, 20, 11 });
        int[] colors = new int[] { Color.MAGENTA, Color.YELLOW, Color.CYAN };
        //int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };

        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.rgb(222, 222, 200));
        renderer.setLabelsColor(Color.GRAY);

        return ChartFactory.getDoughnutChartView(
                context,
                buildMultipleCategoryDataset("Project budget", titles, values),
                renderer
        );

//        return ChartFactory.getDoughnutChartIntent(
//                context,
//                buildMultipleCategoryDataset("Project budget", titles, values),
//                renderer,
//                "Doughnut chart demo");
    }

}
