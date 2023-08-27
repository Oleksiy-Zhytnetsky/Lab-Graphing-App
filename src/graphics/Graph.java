/*
 * Author: Oleksiy Zhytnetsky
 * File: graphics.Graph.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package graphics;

import constants.GraphConstants;
import utils.Mapper;

import javax.swing.*;
import java.awt.*;

public final class Graph extends JComponent {
    /* Constructors */
    public Graph(Mapper mapper, float planeWidth, float planeHeight) {
        this.mapper = mapper;
        this.planeWidth = planeWidth;
        this.planeHeight = planeHeight;
    }

    /* Public Methods */
    @Override
    public void paintComponent(Graphics graphics) {
        Renderer renderer = new Renderer(
                mapper,
                planeWidth - GraphConstants.DEFAULT_X_AXIS_BUFFER,
                planeHeight - GraphConstants.DEFAULT_Y_AXIS_BUFFER
        );

        renderer.drawGraphPlane(graphics);
        renderer.drawGraph(graphics);
    }

    /* Getters */
    public Mapper getMapper() { return mapper; }
    public float getPlaneWidth() { return planeWidth; }
    public float getPlaneHeight() { return planeHeight; }

    /* Fields */
    private final Mapper mapper;
    private final float planeWidth, planeHeight;
}