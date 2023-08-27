/*
 * Author: Oleksiy Zhytnetsky
 * File: constants.WindowColours.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package constants;

import java.awt.*;

public interface WindowColours {
    interface Components {
        Color GRAPH_INFO_BACKGROUND_COLOUR = new Color(185, 195, 196);
        Color GRAPH_DATA_BACKGROUND_COLOUR = new Color(185, 195, 196);
        Color GRAPH_VIEW_BACKGROUND_COLOUR = new Color(210, 198, 177);
    }
}