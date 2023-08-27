/*
 * Author: Oleksiy Zhytnetsky
 * File: windows.SuccessDialog.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package windows;

import constants.DialogColours;

import java.awt.*;

public final class SuccessDialog extends MessageDialog {
    public SuccessDialog(Frame parent, boolean modal, String errorMessage, int fontSize, boolean useTwoLabels,
                       String errorMessage2, boolean killParentFrame) {
        super(parent, modal, fontSize, "Success", errorMessage, DialogColours.SUCCESS_BACKGROUND_COLOUR,
                useTwoLabels, errorMessage2, killParentFrame);
    }
}
