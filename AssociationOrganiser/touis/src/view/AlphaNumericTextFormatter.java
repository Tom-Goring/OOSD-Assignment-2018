package view;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class AlphaNumericTextFormatter extends TextFormatter<String> {

    private static final String DIGITS_ONLY = "^[0-9]*$";

    public AlphaNumericTextFormatter() {
        super(applyFilter(null));
    }

    public AlphaNumericTextFormatter(int maxLength) {
        super(applyFilter(new MaxLengthTextFormatter(maxLength).getFilter()));
    }

    private static UnaryOperator<Change> applyFilter(UnaryOperator<Change> filter) {
        return change -> {
            change.getControlNewText();
            if (change.getControlNewText().matches(DIGITS_ONLY)) {
                if (filter != null) {
                    filter.apply(change);
                }
                return change;
            }
            return null;
        };
    }

}
