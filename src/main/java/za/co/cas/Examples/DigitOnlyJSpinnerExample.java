package za.co.cas.Examples;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class DigitOnlyJSpinnerExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Digit Only JSpinner Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            // Create a JSpinner with a SpinnerNumberModel
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

            // Set a NumberEditor on the JSpinner
            JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
            spinner.setEditor(editor);

            // Get the JTextField from the editor
            JFormattedTextField textField = editor.getTextField();

            // Apply the DocumentFilter to the JTextField
            ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DigitDocumentFilter());

            frame.add(spinner);
            frame.setSize(300, 100);
            frame.setVisible(true);
        });
    }
}

class DigitDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isDigitOnly(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isDigitOnly(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    private boolean isDigitOnly(String text) {
        return text.chars().allMatch(Character::isDigit);
    }
}