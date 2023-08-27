/*
 * Author: Oleksiy Zhytnetsky
 * File: constants.DialogDimensions.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package constants;

public interface DialogDimensions {
    float MAINFRAME_X_OFFSET_COEFFICIENT = 0.215f;
    float MAINFRAME_Y_OFFSET_COEFFICIENT = 0.1666667f;
    float DEFAULT_X_AXIS_SCALE_MULTIPLIER = 1.2f;
    float DEFAULT_HEIGHT_OVERFLOW_BUFFER = 40f;
}
