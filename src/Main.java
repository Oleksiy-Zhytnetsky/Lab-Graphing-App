/*
 * Author: Oleksiy Zhytnetsky
 * File: Main.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

import constants.WindowDimensions;
import windows.CoreWindow;

import java.awt.*;

public final class Main {

    public static void main(String[] args) {
        CoreWindow appWindow = new CoreWindow(new Dimension(
                WindowDimensions.DEFAULT_WIDTH,
                WindowDimensions.DEFAULT_HEIGHT
        ));
        appWindow.setVisible(true);
    }

}