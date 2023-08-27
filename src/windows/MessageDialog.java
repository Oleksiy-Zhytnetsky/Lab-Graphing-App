/*
 * Author: Oleksiy Zhytnetsky
 * File: windows.MessageDialog.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package windows;

import javax.swing.*;
import java.awt.*;

import constants.DialogDimensions;
import constants.Fonts;

public abstract class MessageDialog extends JDialog {
    /* Constructors */

    /**
     * A constructor for MessageDialog (derived) objects
     *
     * @param parent          The parent frame for this window
     * @param modal           The modality of the window
     * @param fontSize        The font size for all the text displayed in this window
     * @param winTitle        The title of the window
     * @param messageText     The text to be displayed by the JLabel of this window
     * @param bgColour        The background colour of the window
     * @param useTwoLabels    Determines whether one or two labels should be used
     * @param messageText2    The text for the second label
     * @param killParentFrame Determines whether the parent frame should be
     *                        disposed of on exit
     */
    public MessageDialog(Frame parent, boolean modal, int fontSize, String winTitle,
                         String messageText, Color bgColour, boolean useTwoLabels,
                         String messageText2, boolean killParentFrame) {
        super(parent, modal);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle(winTitle);
        this.getContentPane().setBackground(bgColour);

        initAcceptButton(parent, this, fontSize, bgColour, killParentFrame);
        initMessageLabel(messageText, fontSize, useTwoLabels, messageText2);

        this.pack();
        this.setSize(
                (int)(this.getWidth() * DialogDimensions.DEFAULT_X_AXIS_SCALE_MULTIPLIER),
                (int)(this.getHeight() + DialogDimensions.DEFAULT_HEIGHT_OVERFLOW_BUFFER)
        );
        this.setLocation(
                (int)(parent.getWidth() * DialogDimensions.MAINFRAME_X_OFFSET_COEFFICIENT),
                parent.getY() + (int)(parent.getHeight() * DialogDimensions.MAINFRAME_Y_OFFSET_COEFFICIENT)
        );
    }

    /* Private Methods */

    /**
     * Creates a JButton titled 'acceptButton' and adds its panel (wrapper) to the frame
     *
     * @param parent          The parent frame
     * @param frame           The frame to add the wrapper JPanel to
     * @param fontSize        The font size of the text inside the button
     * @param bgColour        The background colour of the wrapper panel
     * @param killParentFrame Determines whether the parent frame should be
     *                        disposed of on exit
     */
    private void initAcceptButton(final Frame parent, final MessageDialog frame,
                                  final int fontSize, final Color bgColour,
                                  final boolean killParentFrame) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton acceptButton = new JButton("OK");

        acceptButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
        acceptButton.addActionListener(e -> {
            frame.dispose();
            if (killParentFrame) parent.dispose();
        });
        buttonPanel.setBackground(bgColour);
        buttonPanel.add(acceptButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a JLabel titled 'messagelabel' and adds it to the frame
     *
     * @param messageText  The text to be displayed in the label
     * @param fontSize     The font size of the text
     * @param useTwoLabels Determines whether one or two labels should be used
     * @param messageText2 The text for the second label
     */
    private void initMessageLabel(final String messageText, final int fontSize,
                                  final boolean useTwoLabels, final String messageText2) {
        if (useTwoLabels) {
            JPanel messagePanel = new JPanel(new GridLayout(2, 1));
            messagePanel.setBackground(this.getContentPane().getBackground());

            JLabel topLabel = new JLabel(messageText, SwingConstants.CENTER);
            topLabel.setVerticalAlignment(SwingConstants.CENTER);
            topLabel.setFont(new Font("Verdana", Font.BOLD, fontSize));

            JLabel bottomLabel = new JLabel(messageText2, SwingConstants.CENTER);
            bottomLabel.setVerticalAlignment(SwingConstants.TOP);
            bottomLabel.setFont(Fonts.DIALOG_NOTES_FONT);

            messagePanel.add(topLabel);
            messagePanel.add(bottomLabel);
            this.getContentPane().add(messagePanel);
        }
        else {
            JLabel messageLabel = new JLabel(messageText, SwingConstants.CENTER);
            messageLabel.setFont(new Font("Verdana", Font.BOLD, fontSize));
            this.getContentPane().add(messageLabel);
        }
    }
}