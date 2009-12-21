/**
 * Copyright (C) 2009 SC 4ViewSoft SRL
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
package org.achartengine.view.chart;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;


/**
 * The line chart rendering class.
 */
public class LineChart extends XYChart {
	/** The legend shape width. */
	private static final int SHAPE_WIDTH = 30;

	/**
	 * Builds a new line chart instance.
	 * @param dataset the multiple series dataset
	 * @param renderer the multiple series renderer
	 */
	public LineChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
		super(dataset, renderer);
	}

	/**
	 * The graphical representation of a series.
	 * @param canvas the canvas to paint to
	 * @param paint the paint to be used for drawing
	 * @param points the array of points to be used for drawing the series
	 * @param seriesRenderer the series renderer
	 * @param yAxisValue the minimum value of the y axis
	 * @param seriesIndex the index of the series currently being drawn
	 */
	public void drawSeries(Canvas canvas, Paint paint, float[] points,
			SimpleSeriesRenderer seriesRenderer, float yAxisValue, int seriesIndex) {
		int length = points.length;
		XYSeriesRenderer renderer = (XYSeriesRenderer) seriesRenderer;
		if (renderer.isFillBelowLine()) {
			paint.setColor(renderer.getFillBelowLineColor());
			int pLength = points.length;
			float[] fillPoints = new float[pLength + 4];
			System.arraycopy(points, 0, fillPoints, 0, length);
			fillPoints[0] = points[0] + 1;
			fillPoints[length] = fillPoints[length - 2];
			fillPoints[length + 1] = yAxisValue;
			fillPoints[length + 2] = fillPoints[0];
			fillPoints[length + 3] = fillPoints[length + 1];
			paint.setStyle(Style.FILL);
			drawPath(canvas, fillPoints, paint, true);
		}
		paint.setColor(seriesRenderer.getColor());
		paint.setStyle(Style.STROKE);
		drawPath(canvas, points, paint, false);
	}

	/**
	 * Returns if the chart should display the points as a certain shape.
	 * @param renderer the series renderer
	 */
	public boolean isRenderPoints(SimpleSeriesRenderer renderer) {
		return ((XYSeriesRenderer) renderer).getPointStyle() != PointStyle.POINT;
	}

}