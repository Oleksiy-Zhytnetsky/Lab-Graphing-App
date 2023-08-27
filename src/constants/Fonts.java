/*
 * Author: Oleksiy Zhytnetsky
 * File: constants.Fonts.java
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

public interface Fonts {
    Font NORMAL_FONT = new Font("Verdana", Font.PLAIN, 20);
    Font BUTTON_FONT = new Font("Verdana", Font.BOLD, 18);
    Font DIALOG_CAPTION_FONT = new Font("Verdana", Font.BOLD, 24);
    Font DIALOG_NOTES_FONT = new Font("Verdana", Font.ITALIC, 20);
    Font GRAPH_INDEXING_FONT = new Font("Consolas", Font.PLAIN, 18);
}
