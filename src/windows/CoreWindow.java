/*
 * Author: Oleksiy Zhytnetsky
 * File: windows.CoreWindow.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package windows;

import constants.*;
import enums.ValidationModes;
import graphics.Graph;
import utils.FormFieldDataVector;
import utils.Mapper;
import utils.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class CoreWindow extends JFrame {
    /* Constructors */
    public CoreWindow(Dimension initialWindowDimension) {
        // Fundamental window initialisation
        initWindowState(initialWindowDimension);

        // Window components initialisation
        initGraphInfoPanel(initialWindowDimension);
        initGraphDataPanel(initialWindowDimension);
        updateGraphViewPanel(getGraphMapper());

        // Button action listeners initialisation
        initButtonActionListeners();
    }

    /* Private Methods */
    private void initButtonActionListeners() {
        plotButton.addActionListener(e -> {
            if (graphDataIsValid()) updateGraphViewPanel(getGraphMapper());
            else {
                resetInputFields();
                ErrorDialog errorDialog = new ErrorDialog(
                        this, true, "Error", Fonts.DIALOG_CAPTION_FONT.getSize(),
                        true, "Input data is not valid", false
                );
                errorDialog.setVisible(true);
            }
        });

        saveToFileButton.addActionListener(e -> {
            saveGraphToFile();
            SuccessDialog successDialog = new SuccessDialog(
                    this, true, "Successfully saved graph to file",
                    Fonts.DIALOG_CAPTION_FONT.getSize(), false, "", false
            );
            successDialog.setVisible(true);
        });
    }

    private void saveGraphToFile() throws RuntimeException {
        final FormFieldDataVector graphData = graph.getMapper().getDataVector();
        final BufferedImage image = new BufferedImage(
                (int)graph.getPlaneWidth() - GraphConstants.DEFAULT_X_AXIS_BUFFER,
                (int)graph.getPlaneHeight() - GraphConstants.DEFAULT_Y_AXIS_BUFFER,
                BufferedImage.TYPE_INT_ARGB
        );
        final Graphics2D imageCanvas = image.createGraphics();
        imageCanvas.setFont(Fonts.GRAPH_INDEXING_FONT);
        imageCanvas.setColor(GraphColours.SAVE_FILE_BACKGROUND_COLOUR);
        imageCanvas.fillRect(0, 0, image.getWidth(), image.getHeight());

        imageCanvas.setColor(GraphColours.INDEXING_FONT_COLOUR);
        imageCanvas.drawString("Graph: rho = b + (a * cos(phi))", 20, 25);
        imageCanvas.drawString(
                "\"phi\" range: [" + graphData.angleRangeBegin + ";" + graphData.angleRangeEnd + "]",
                20, 50
        );
        imageCanvas.drawString("\"a\" parameter value: " + graphData.coefficientA, 20, 75);
        imageCanvas.drawString("\"b\" parameter value: " + graphData.coefficientB, 20, 100);

        graph.paint(imageCanvas);
        imageCanvas.dispose();

        try { ImageIO.write(image, "png", new File(GraphConstants.SAVE_FILE_PATH)); }
        catch (IOException exception) { throw new RuntimeException(exception); }
    }

    private Mapper getGraphMapper() {
        Vector2<Integer, Integer> rangeData = getAngleRangeData();
        return new Mapper(new FormFieldDataVector(
                rangeData.first,
                rangeData.second,
                Float.parseFloat(textFieldForCoefficientA.getText()),
                Float.parseFloat(textFieldForCoefficientB.getText())
        ));
    }

    private Vector2<Integer, Integer> getAngleRangeData() {
        String data = textFieldForAngleRangePhi.getText();
        return new Vector2<>(
                Integer.parseInt(data.substring(1, data.indexOf(';'))),
                Integer.parseInt(data.substring(data.indexOf(';') + 1, data.length() - 1))
        );
    }

    private void resetInputFields() {
        textFieldForCoefficientA.setText("");
        textFieldForCoefficientB.setText("");
        textFieldForAngleRangePhi.setText("");
    }

    private boolean graphDataIsValid() {
        return fieldDataIsValid(textFieldForCoefficientA, ValidationModes.REAL_NUMBER) &&
                fieldDataIsValid(textFieldForCoefficientB, ValidationModes.REAL_NUMBER) &&
                fieldDataIsValid(textFieldForAngleRangePhi, ValidationModes.RANGE);
    }

    private boolean fieldDataIsValid(final JTextField dataField, final ValidationModes validationMode) {
        try {
            switch (validationMode) {
                case REAL_NUMBER: validateNumber(dataField); break;
                case RANGE: validateRange(dataField);
            }
            return true;
        }
        catch (IllegalArgumentException exception) { return false; }
    }

    private void validateNumber(final JTextField dataField) throws IllegalArgumentException {
        try { Double.parseDouble(dataField.getText()); }
        catch (IllegalArgumentException exception) { throw new IllegalArgumentException(); }
    }

    private void validateRange(final JTextField dataField) throws IllegalArgumentException {
        if (dataField.getText().matches("\\[[+-]?\\d+;[+-]?\\d+]")) {
            int semicolonIndex = dataField.getText().indexOf(';');
            double beginRangeValue = Integer.parseInt(dataField.getText().substring(1, semicolonIndex));
            double endRangeValue = Integer.parseInt(
                    dataField.getText().substring(semicolonIndex + 1, dataField.getText().length() - 1)
            );
            if (endRangeValue < beginRangeValue) throw new IllegalArgumentException();
        }
        else throw new IllegalArgumentException();
    }

    private void updateGraphViewPanel(final Mapper mapper) {
        this.setVisible(false);
        this.remove(graphViewPanel);
        if (graph != null) this.remove(graph);

        graphViewPanel = new JPanel(new FlowLayout());
        graphViewPanel.setBackground(WindowColours.Components.GRAPH_VIEW_BACKGROUND_COLOUR);

        Vector2<Float, Float> graphPlaneDimensions = getGraphPlaneDimensions(this.getPreferredSize());
        graph = new Graph(mapper, graphPlaneDimensions.first, graphPlaneDimensions.second);
        graph.setBounds(0, 0, Math.round(graphPlaneDimensions.first), Math.round(graphPlaneDimensions.second));

        this.getContentPane().add(graph);
        this.getContentPane().add(graphViewPanel);
        this.setVisible(true);
    }

    private Vector2<Float, Float> getGraphPlaneDimensions(final Dimension coreWindowSize) {
        return new Vector2<>(
                (float)(coreWindowSize.getWidth() * (1 - WindowDimensions.Components.GRAPH_DATA_WIDTH_COEFFICIENT)),
                (float)(coreWindowSize.getHeight() * (1 - WindowDimensions.Components.GRAPH_INFO_HEIGHT_COEFFICIENT))
        );
    }

    private void initGraphDataPanel(final Dimension windowDimension) {
        JPanel graphDataPanel = new JPanel(new GridLayout(4, 1));
        setComponentSize(graphDataPanel, new Dimension(
                (int)(windowDimension.width * WindowDimensions.Components.GRAPH_DATA_WIDTH_COEFFICIENT),
                windowDimension.height
        ));
        graphDataPanel.setBackground(WindowColours.Components.GRAPH_DATA_BACKGROUND_COLOUR);

        graphDataPanel.add(getBoundLabelInputBlock("Enter \"a\" param value",
                textFieldForCoefficientA, "Enter any real number"));
        graphDataPanel.add(getBoundLabelInputBlock("Enter \"b\" param value",
                textFieldForCoefficientB, "Enter any real number"));
        graphDataPanel.add(getBoundLabelInputBlock("Enter \"phi\" param value",
                textFieldForAngleRangePhi, "Enter the angle range. " +
                        "Format: [beginAngleValue;endAngleValue], in deg."));
        graphDataPanel.add(getButtonsBlock());
        initGraphDataUsingDefaults();

        this.getContentPane().add(graphDataPanel, BorderLayout.EAST);
    }

    private void initGraphDataUsingDefaults() {
        textFieldForCoefficientA.setText("2");
        textFieldForCoefficientB.setText("1");
        textFieldForAngleRangePhi.setText("[0;360]");
    }

    private JPanel getButtonsBlock() {
        JPanel buttonsWrapperPanel = new JPanel(new GridLayout(2, 1));
        buttonsWrapperPanel.setBackground(WindowColours.Components.GRAPH_DATA_BACKGROUND_COLOUR);

        buttonsWrapperPanel.add(getButtonPanel(plotButton, "Plot graph"));
        buttonsWrapperPanel.add(getButtonPanel(saveToFileButton, "Save graph to file"));
        return buttonsWrapperPanel;
    }

    private JPanel getButtonPanel(final JButton button, final String buttonLabelContent) {
        JPanel wrapperPanel = new JPanel(new FlowLayout());
        wrapperPanel.setBackground(WindowColours.Components.GRAPH_DATA_BACKGROUND_COLOUR);

        button.setText(buttonLabelContent);
        button.setFont(Fonts.BUTTON_FONT);
        button.setHorizontalAlignment(SwingConstants.CENTER);

        wrapperPanel.add(button);
        return wrapperPanel;
    }

    private JPanel getBoundLabelInputBlock(final String labelContent, final JTextField boundInputField,
                                           final String toolTipText) {
        JPanel wrapperPanel = new JPanel(new GridLayout(2, 1));
        wrapperPanel.setBackground(WindowColours.Components.GRAPH_DATA_BACKGROUND_COLOUR);

        wrapperPanel.add(getBoundLabel(labelContent));
        wrapperPanel.add(getInputFieldBlock(boundInputField, toolTipText));
        return wrapperPanel;
    }

    private JPanel getInputFieldBlock(final JTextField inputField, final String toolTipText) {
        JPanel inputFieldWrapperPanel = new JPanel(new FlowLayout());
        inputFieldWrapperPanel.setBackground(WindowColours.Components.GRAPH_DATA_BACKGROUND_COLOUR);

        inputField.setColumns(12);
        if (toolTipText != null) inputField.setToolTipText(toolTipText);
        inputField.setFont(Fonts.NORMAL_FONT);
        inputField.setHorizontalAlignment(SwingConstants.CENTER);

        inputFieldWrapperPanel.add(inputField);
        return inputFieldWrapperPanel;
    }

    private JLabel getBoundLabel(final String labelContent) {
        JLabel boundLabel = new JLabel(labelContent);

        boundLabel.setFont(Fonts.NORMAL_FONT);
        boundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boundLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        return boundLabel;
    }

    private void initGraphInfoPanel(final Dimension windowDimension) {
        JPanel graphInfoPanel = new JPanel(new GridLayout(1, 1));
        setComponentSize(graphInfoPanel, new Dimension(
                windowDimension.width,
                (int)(windowDimension.height * WindowDimensions.Components.GRAPH_INFO_HEIGHT_COEFFICIENT)
        ));
        graphInfoPanel.setBackground(WindowColours.Components.GRAPH_INFO_BACKGROUND_COLOUR);

        JLabel label = new JLabel("Graph: rho = b + (a * cos(phi)) | " +
                "Polar: x = rho * cos(phi), y = rho * sin(phi)");
        label.setFont(Fonts.NORMAL_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        graphInfoPanel.add(label);
        this.getContentPane().add(graphInfoPanel, BorderLayout.SOUTH);
    }

    private void setComponentSize(final JComponent component, final Dimension componentDimension) {
        component.setSize(componentDimension);
        component.setPreferredSize(componentDimension);
    }

    private void initWindowState(final Dimension windowDimension) {
        this.setTitle("Graphing Application");
        initWindowDimensions(windowDimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }

    private void initWindowDimensions(final Dimension windowDimension) {
        this.setSize(windowDimension.width, windowDimension.height);
        this.setPreferredSize(windowDimension);
        this.setMinimumSize(new Dimension(WindowDimensions.MIN_WIDTH, WindowDimensions.MIN_HEIGHT));
        this.setBounds(
                WindowDimensions.DEFAULT_OFFSET, WindowDimensions.DEFAULT_OFFSET,
                windowDimension.width, windowDimension.height
        );
    }

    /* Fields */
    private Graph graph;
    private JPanel graphViewPanel = new JPanel(new FlowLayout());
    private final JButton plotButton = new JButton();
    private final JButton saveToFileButton = new JButton();
    private final JTextField textFieldForCoefficientA = new JTextField();
    private final JTextField textFieldForCoefficientB = new JTextField();
    private final JTextField textFieldForAngleRangePhi = new JTextField();
}