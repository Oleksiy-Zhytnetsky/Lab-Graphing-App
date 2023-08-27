/*
 * Author: Oleksiy Zhytnetsky
 * File: graphics.Renderer.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package graphics;

import constants.Fonts;
import constants.GraphColours;
import constants.GraphConstants;
import utils.CoordinatePair;
import utils.Mapper;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class Renderer {
    /* Constructors */
    public Renderer(Mapper mapper, float planeWidth, float planeHeight) {
        this.mapper = mapper;
        this.planeWidth = planeWidth;
        this.planeHeight = planeHeight;

        planeCentreX = planeWidth / 2f;
        planeCentreY = planeHeight / 2f;

        normalDeltaX = planeWidth / (GraphConstants.DEFAULT_X_AXIS_LIMIT * 2.0f);
        normalDeltaY = planeHeight / (GraphConstants.DEFAULT_Y_AXIS_LIMIT * 2.0f);

        scaleGraph();
    }

    /* Public Methods */
    public void drawGraph(final Graphics graphics) {
        Graphics2D canvas = (Graphics2D) graphics;
        canvas.setStroke(new BasicStroke(2));
        canvas.setColor(GraphColours.GRAPHING_COLOUR);

        Path2D path = new Path2D.Float();
        Set<Map.Entry<Float, CoordinatePair>> entrySet = mapper.getAngleToPointMap().entrySet();
        Iterator<Map.Entry<Float, CoordinatePair>> it = entrySet.iterator();

        final CoordinatePair firstPoint = it.next().getValue();
        path.moveTo(
                planeCentreX + (firstPoint.x * normalDeltaX / graphScale),
                planeCentreY + (firstPoint.y * normalDeltaY / graphScale)
        );
        while (it.hasNext()) {
            final CoordinatePair point = it.next().getValue();
            path.lineTo(
                    planeCentreX + (point.x * normalDeltaX / graphScale),
                    planeCentreY + (point.y * normalDeltaY / graphScale)
            );
        }
        canvas.draw(path);
    }

    public void drawGraphPlane(final Graphics graphics) {
        drawGraphPlaneCrosshairs(graphics);
        indexGraphPlane(graphics);
    }

    /* Private Methods */
    private void indexGraphPlane(final Graphics graphics) {
        indexGraphPlanePositiveX(graphics);
        indexGraphPlaneNegativeX(graphics);
        indexGraphPlanePositiveY(graphics);
        indexGraphPlaneNegativeY(graphics);
    }

    private void indexGraphPlaneNegativeY(final Graphics graphics) {
        Graphics2D canvas = (Graphics2D)graphics;
        canvas.setColor(GraphColours.INDEXING_FONT_COLOUR);
        canvas.setFont(Fonts.GRAPH_INDEXING_FONT);

        Polygon polygon = new Polygon();
        int counter = 1;
        for (int i = -graphScale; i >= -GraphConstants.DEFAULT_Y_AXIS_LIMIT * graphScale; i -= graphScale) {
            polygon.addPoint((int)planeCentreX, (int)(planeCentreY + normalDeltaY * counter));
            polygon.addPoint((int)planeCentreX + 5, (int)(planeCentreY + normalDeltaY * counter));
            polygon.addPoint((int)planeCentreX - 5, (int)(planeCentreY + normalDeltaY * counter));
            polygon.addPoint((int)planeCentreX, (int)(planeCentreY + normalDeltaY * counter));
            canvas.drawString(
                    String.valueOf(i),
                    planeCentreX - 35, (planeCentreY + normalDeltaY * counter) + 4
            );
            ++counter;
        }
        canvas.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
    }

    private void indexGraphPlanePositiveY(final Graphics graphics) {
        Graphics2D canvas = (Graphics2D)graphics;
        canvas.setColor(GraphColours.INDEXING_FONT_COLOUR);
        canvas.setFont(Fonts.GRAPH_INDEXING_FONT);

        Polygon polygon = new Polygon();
        int counter = 1;
        for (int i = graphScale; i <= GraphConstants.DEFAULT_Y_AXIS_LIMIT * graphScale; i += graphScale) {
            polygon.addPoint((int)planeCentreX, (int)(planeCentreY - normalDeltaY * counter));
            polygon.addPoint((int)planeCentreX + 5, (int)(planeCentreY - normalDeltaY * counter));
            polygon.addPoint((int)planeCentreX - 5, (int)(planeCentreY - normalDeltaY * counter));
            polygon.addPoint((int)planeCentreX, (int)(planeCentreY - normalDeltaY * counter));
            canvas.drawString(
                    String.valueOf(i),
                    planeCentreX - 25, (planeCentreY - normalDeltaY * counter) + 4
            );
            ++counter;
        }
        canvas.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
    }

    private void indexGraphPlaneNegativeX(final Graphics graphics) {
        Graphics2D canvas = (Graphics2D)graphics;
        canvas.setColor(GraphColours.INDEXING_FONT_COLOUR);
        canvas.setFont(Fonts.GRAPH_INDEXING_FONT);

        Polygon polygon = new Polygon();
        int counter = 1;
        for (int i = -graphScale; i >= -GraphConstants.DEFAULT_X_AXIS_LIMIT * graphScale; i -= graphScale) {
            polygon.addPoint((int)(planeCentreX - normalDeltaX * counter), (int)planeCentreY);
            polygon.addPoint((int)(planeCentreX - normalDeltaX * counter), (int)(planeCentreY + 5));
            polygon.addPoint((int)(planeCentreX - normalDeltaX * counter), (int)(planeCentreY - 5));
            polygon.addPoint((int)(planeCentreX - normalDeltaX * counter), (int)planeCentreY);
            canvas.drawString(
                    String.valueOf(i),
                    (planeCentreX - normalDeltaX * counter) - 15, planeCentreY + 18
            );
            ++counter;
        }
        canvas.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
    }

    private void indexGraphPlanePositiveX(final Graphics graphics) {
        Graphics2D canvas = (Graphics2D)graphics;
        canvas.setColor(GraphColours.INDEXING_FONT_COLOUR);
        canvas.setFont(Fonts.GRAPH_INDEXING_FONT);

        Polygon polygon = new Polygon();
        int counter = 1;
        for (int i = graphScale; i <= GraphConstants.DEFAULT_X_AXIS_LIMIT * graphScale; i += graphScale) {
            polygon.addPoint((int)(planeCentreX + normalDeltaX * counter), (int)planeCentreY);
            polygon.addPoint((int)(planeCentreX + normalDeltaX * counter), (int)(planeCentreY + 5));
            polygon.addPoint((int)(planeCentreX + normalDeltaX * counter), (int)(planeCentreY - 5));
            polygon.addPoint((int)(planeCentreX + normalDeltaX * counter), (int)planeCentreY);
            canvas.drawString(
                    String.valueOf(i),
                    (planeCentreX + normalDeltaX * counter) - 5, planeCentreY + 18
            );
            ++counter;
        }
        canvas.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
    }

    private void drawGraphPlaneCrosshairs(final Graphics graphics) {
        Graphics2D canvas = (Graphics2D)graphics;
        canvas.setStroke(new BasicStroke(2));
        canvas.setFont(Fonts.GRAPH_INDEXING_FONT);
        canvas.setColor(GraphColours.INDEXING_FONT_COLOUR);

        // Draw crosshairs
        canvas.drawLine(0, (int)planeCentreY, (int)planeWidth, (int)planeCentreY);
        canvas.drawLine((int)planeCentreX, 0, (int)planeCentreX, (int)planeHeight);

        // Draw x-axis arrow
        canvas.drawLine((int)planeWidth, (int)planeCentreY, (int)planeWidth - 10, (int)planeCentreY - 7);
        canvas.drawLine((int)planeWidth, (int)planeCentreY, (int)planeWidth - 10, (int)planeCentreY + 7);
        canvas.drawString("X", planeWidth - 25, planeCentreY - 15);

        // Draw y-axis arrow
        canvas.drawLine((int)planeCentreX, 0, (int)planeCentreX - 7, 10);
        canvas.drawLine((int)planeCentreX, 0, (int)planeCentreX + 7, 10);
        canvas.drawString("Y", planeCentreX + 15, 25);

        // Index starting point
        canvas.drawString("0", planeCentreX - 20, planeCentreY + 20);
    }

    private void scaleGraph() {
        graphScale = 1;
        while (graphExtremeIsOutOfBounds()) graphScale *= 2;
    }

    private boolean graphExtremeIsOutOfBounds() {
        return (mapper.getExtremesX().max > GraphConstants.DEFAULT_X_AXIS_LIMIT * graphScale) ||
                (mapper.getExtremesX().min < -GraphConstants.DEFAULT_X_AXIS_LIMIT * graphScale) ||
                (mapper.getExtremesY().max > GraphConstants.DEFAULT_Y_AXIS_LIMIT * graphScale) ||
                (mapper.getExtremesY().min < -GraphConstants.DEFAULT_Y_AXIS_LIMIT * graphScale);
    }

    /* Fields */
    private final Mapper mapper;
    private final float planeWidth, planeHeight, planeCentreX, planeCentreY, normalDeltaX, normalDeltaY;
    private int graphScale;
}