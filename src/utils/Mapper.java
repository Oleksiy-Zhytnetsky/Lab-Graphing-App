/*
 * Author: Oleksiy Zhytnetsky
 * File: utils.Mapper.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package utils;

import constants.GraphConstants;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class Mapper {
    /* Constructors */
    public Mapper(FormFieldDataVector dataVector) {
        initAngleToPointMap(dataVector);
        initExtremes(dataVector);
        this.dataVector = dataVector;
    }

    /* Private Methods */
    private void initExtremes(final FormFieldDataVector dataVector) {
        Set<Map.Entry<Float, CoordinatePair>> entrySet = angleToPointMap.entrySet();
        Iterator<Map.Entry<Float, CoordinatePair>> it = entrySet.iterator();

        float xMin = angleToPointMap.get((float)dataVector.angleRangeBegin).x;
        float xMax = angleToPointMap.get((float)dataVector.angleRangeBegin).x;
        float yMin = angleToPointMap.get((float)dataVector.angleRangeBegin).y;
        float yMax = angleToPointMap.get((float)dataVector.angleRangeBegin).y;

        while (it.hasNext()) {
            final CoordinatePair point = it.next().getValue();

            if (point.x > xMax) xMax = point.x;
            else if (point.x < xMin) xMin = point.x;

            if (point.y > yMax) yMax = point.y;
            else if (point.y < yMin) yMin = point.y;
        }

        extremesX = new ExtremePair(xMin, xMax);
        extremesY = new ExtremePair(yMin, yMax);
    }

    private void initAngleToPointMap(final FormFieldDataVector dataVector) {
        angleToPointMap = new LinkedHashMap<>();
        for (float currentAngle = dataVector.angleRangeBegin; currentAngle <= dataVector.angleRangeEnd;
             currentAngle += GraphConstants.PHI_ANGLE_STEP) {
            final float rho = dataVector.coefficientB +
                    (float)(dataVector.coefficientA * Math.cos(inRadians(currentAngle)));
            angleToPointMap.put(currentAngle, new CoordinatePair(
                    (float)(rho * Math.cos(inRadians(currentAngle))),
                    (float)(rho * Math.sin(inRadians(currentAngle)))
            ));
        }
    }

    private double inRadians(final float angleInDegrees) {
        return (angleInDegrees * Math.PI) / 180;
    }

    /* Getters */
    public FormFieldDataVector getDataVector() { return dataVector; }
    public ExtremePair getExtremesX() { return extremesX; }
    public ExtremePair getExtremesY() { return extremesY; }
    public LinkedHashMap<Float, CoordinatePair> getAngleToPointMap() { return angleToPointMap; }

    /* Fields */
    private final FormFieldDataVector dataVector;
    private ExtremePair extremesX, extremesY;
    private LinkedHashMap<Float, CoordinatePair> angleToPointMap;
}