/*
 * Author: Oleksiy Zhytnetsky
 * File: constants.WindowDimensions.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package constants;

public interface WindowDimensions {

    interface Components {
        float GRAPH_INFO_HEIGHT_COEFFICIENT = 0.1275f;
        float GRAPH_DATA_WIDTH_COEFFICIENT = 0.2275f;
    }

    int DEFAULT_OFFSET = 75;
    int DEFAULT_WIDTH = 1080;
    int DEFAULT_HEIGHT = 800;
    int MIN_WIDTH = 540;
    int MIN_HEIGHT = 400;
}
