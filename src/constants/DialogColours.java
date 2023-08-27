/*
 * Author: Oleksiy Zhytnetsky
 * File: constants.DialogColours.java
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

public interface DialogColours {
    Color ERROR_BACKGROUND_COLOUR = new Color(216, 167, 167);
    Color SUCCESS_BACKGROUND_COLOUR = new Color(173, 212,139);
}
