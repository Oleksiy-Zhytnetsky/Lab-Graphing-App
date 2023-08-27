/*
 * Author: Oleksiy Zhytnetsky
 * File: constants.GraphConstants.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package constants;

public interface GraphConstants {
    float PHI_ANGLE_STEP = 0.05f;
    int DEFAULT_X_AXIS_LIMIT = 8;
    int DEFAULT_Y_AXIS_LIMIT = 8;
    int DEFAULT_X_AXIS_BUFFER = 21;
    int DEFAULT_Y_AXIS_BUFFER = 51;
    String SAVE_FILE_PATH = "./Dev/Application/Graphing-Application/graph.png";
}
