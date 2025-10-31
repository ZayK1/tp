package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A UI component that displays result messages with GitHub-style colored formatting.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    // GitHub-style color scheme
    private static final Color SUCCESS_BG = Color.web("#DFF6DD");
    private static final Color SUCCESS_BORDER = Color.web("#34C759");
    private static final Color SUCCESS_TEXT = Color.web("#0F4A1E");

    private static final Color ERROR_BG = Color.web("#FFE4E1");
    private static final Color ERROR_BORDER = Color.web("#FF453A");
    private static final Color ERROR_TEXT = Color.web("#8B0000");

    private static final Color INFO_BG = Color.web("#D0E7FF");
    private static final Color INFO_BORDER = Color.web("#0A84FF");
    private static final Color INFO_TEXT = Color.web("#003D7A");

    private static final Color ACCENT_COLOR = Color.web("#0A84FF");
    private static final Color MODULE_COLOR = Color.web("#BF5AF2");
    private static final Color HEADER_TEXT_COLOR = Color.WHITE;

    private boolean isDarkTheme = true;

    @FXML
    private VBox resultDisplay;

    /**
     * Creates a ResultDisplay with GitHub-style formatted messages.
     */
    public ResultDisplay() {
        super(FXML);
        showWelcomeMessage();
    }

    /**
     * Shows the welcome message on startup.
     */
    private void showWelcomeMessage() {
        String welcomeMessage = "Welcome to TeachMate!\n\n"
                + "TeachMate helps NUS TAs manage students efficiently.\n"
                + "This display shows feedback for all your commands.\n\n"
                + "Available commands:\n"
                + "• add - Add a new student (with optional consultations using c/)\n"
                + "• edit - Edit student details (including consultations)\n"
                + "• delete - Delete a student\n"
                + "• list - List all students\n"
                + "• view - View detailed student information\n"
                + "• find - Find students by name\n"
                + "• filter - Filter students by tags\n"
                + "• attendance - Mark student attendance\n"
                + "• grade - Add or update grades\n"
                + "• deletegrade - Delete a specific grade\n"
                + "• remark - Add notes about a student\n"
                + "• tag - Add tags to categorize students\n"
                + "• untag - Remove tags from students\n"
                + "• clear - Clear all student data\n"
                + "• help - Show detailed help window\n"
                + "• exit - Exit the application\n\n"
                + "Type 'help' for detailed command formats and examples.";
        setFeedbackToUser(welcomeMessage, MessageType.INFO);
    }

    /**
     * Sets the feedback message to display to the user.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        MessageType type = detectMessageType(feedbackToUser);
        setFeedbackToUser(feedbackToUser, type);
    }

    /**
     * Sets the feedback message with explicit type.
     */
    private void setFeedbackToUser(String feedbackToUser, MessageType type) {
        requireNonNull(feedbackToUser);
        resultDisplay.getChildren().clear();

        VBox messageBox = createGitHubStyleBox(feedbackToUser, type);
        resultDisplay.getChildren().add(messageBox);
    }

    /**
     * Creates a GitHub-style alert box with colored header and content.
     */
    private VBox createGitHubStyleBox(String message, MessageType type) {
        VBox box = new VBox(0);

        // Set colors based on message type
        Color bgColor;
        Color borderColor;
        Color textColor;
        Color headerBgColor;
        String icon;
        String headerText;

        switch (type) {
        case SUCCESS:
            if (isDarkTheme) {
                bgColor = Color.web("#0D3A1F", 0.3);
                borderColor = SUCCESS_BORDER;
                textColor = Color.web("#7EE787");
                headerBgColor = Color.web("#34C759", 0.2);
            } else {
                bgColor = SUCCESS_BG;
                borderColor = SUCCESS_BORDER;
                textColor = SUCCESS_TEXT;
                headerBgColor = Color.web("#34C759", 0.15);
            }
            icon = "✓";
            headerText = "Success";
            message = message.replace("✓ ", "");
            break;

        case ERROR:
            if (isDarkTheme) {
                bgColor = Color.web("#3D0A0A", 0.3);
                borderColor = ERROR_BORDER;
                textColor = Color.web("#FF7B72");
                headerBgColor = Color.web("#FF453A", 0.2);
            } else {
                bgColor = ERROR_BG;
                borderColor = ERROR_BORDER;
                textColor = ERROR_TEXT;
                headerBgColor = Color.web("#FF453A", 0.15);
            }
            icon = "✗";
            headerText = "Error";
            break;

        default: // INFO
            if (isDarkTheme) {
                bgColor = Color.web("#0D2E4A", 0.3);
                borderColor = INFO_BORDER;
                textColor = Color.web("#79C0FF");
                headerBgColor = Color.web("#0A84FF", 0.2);
            } else {
                bgColor = INFO_BG;
                borderColor = INFO_BORDER;
                textColor = INFO_TEXT;
                headerBgColor = Color.web("#0A84FF", 0.15);
            }
            icon = "ℹ";
            headerText = "TeachMate";
        }

        // Create header
        VBox header = new VBox();
        header.setPadding(new Insets(10, 14, 10, 14));
        header.setBackground(new Background(new BackgroundFill(
            headerBgColor, new CornerRadii(8, 8, 0, 0, false), Insets.EMPTY)));

        TextFlow headerFlow = new TextFlow();
        Text iconText = new Text(icon + " ");
        iconText.setFill(isDarkTheme ? HEADER_TEXT_COLOR : textColor);
        iconText.setFont(Font.font("System", FontWeight.BOLD, 15));

        Text titleText = new Text(headerText);
        titleText.setFill(isDarkTheme ? HEADER_TEXT_COLOR : textColor);
        titleText.setFont(Font.font("System", FontWeight.BOLD, 15));

        headerFlow.getChildren().addAll(iconText, titleText);
        header.getChildren().add(headerFlow);

        // Create content with syntax highlighting
        TextFlow content = createFormattedContent(message, textColor, type);
        content.setPadding(new Insets(12, 14, 12, 14));
        content.setBackground(new Background(new BackgroundFill(
            bgColor, new CornerRadii(0, 0, 8, 8, false), Insets.EMPTY)));

        // Assemble box
        box.getChildren().addAll(header, content);
        box.setBorder(new Border(new BorderStroke(
            borderColor, BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(1, 1, 1, 3))));
        box.setBackground(new Background(new BackgroundFill(
            bgColor, new CornerRadii(8), Insets.EMPTY)));

        return box;
    }

    /**
     * Creates formatted content with syntax highlighting for IDs and module codes.
     */
    private TextFlow createFormattedContent(String message, Color baseColor, MessageType type) {
        TextFlow flow = new TextFlow();
        flow.setLineSpacing(4);

        // Add command list for unknown command errors
        if (type == MessageType.ERROR && message.toLowerCase().contains("unknown command")) {
            addColoredText(flow, message + "\n\n", baseColor, false, false);
            addColoredText(flow, "Available Commands:\n", baseColor, true, false);

            String[] commands = {
                "• add - Add a new student (with optional consultations using c/)",
                "• edit - Edit student details (including consultations)",
                "• delete - Delete a student",
                "• list - List all students",
                "• view - View detailed student information",
                "• find - Find students by name",
                "• filter - Filter students by tags",
                "• attendance - Mark student attendance",
                "• grade - Add or update grades",
                "• deletegrade - Delete a specific grade",
                "• remark - Add notes about a student",
                "• tag - Add tags to categorize students",
                "• untag - Remove tags from students",
                "• clear - Clear all student data",
                "• help - Show detailed help window",
                "• exit - Exit the application"
            };

            for (String cmd : commands) {
                addColoredText(flow, cmd + "\n", baseColor, false, true);
            }
            return flow;
        }

        // Parse regular messages
        String[] lines = message.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                flow.getChildren().add(new Text("\n"));
                continue;
            }

            boolean isHeader = line.startsWith("===") || line.matches("^[A-Z ]+:$");
            boolean isBullet = line.trim().startsWith("•") || line.trim().startsWith("-");

            // Highlight student IDs and module codes
            highlightSyntax(flow, line + "\n", baseColor, isHeader, isBullet);
        }

        return flow;
    }

    /**
     * Adds colored text to the flow.
     */
    private void addColoredText(TextFlow flow, String text, Color color, boolean isBold, boolean isSmall) {
        Text textNode = new Text(text);
        textNode.setFill(color);
        if (isBold) {
            textNode.setFont(Font.font("System", FontWeight.BOLD, 14));
        } else if (isSmall) {
            textNode.setFont(Font.font("System", 12.5));
        } else {
            textNode.setFont(Font.font("System", 13.5));
        }
        flow.getChildren().add(textNode);
    }

    /**
     * Highlights student IDs and module codes with accent colors.
     */
    private void highlightSyntax(TextFlow flow, String text, Color baseColor, boolean isHeader, boolean isBullet) {
        if (isHeader) {
            Text headerText = new Text(text);
            headerText.setFill(baseColor);
            headerText.setFont(Font.font("System", FontWeight.BOLD, 14));
            flow.getChildren().add(headerText);
            return;
        }

        // Split by student IDs (A0123456X) and module codes (CS2103T)
        String regex = "(A\\d{7}[A-Z]|[A-Z]{2,3}\\d{4}[A-Z]?)";
        String[] parts = text.split(regex);
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        int partIndex = 0;
        while (matcher.find()) {
            // Add text before match
            if (partIndex < parts.length && !parts[partIndex].isEmpty()) {
                Text textNode = new Text(parts[partIndex]);
                textNode.setFill(baseColor);
                textNode.setFont(Font.font("System", isBullet ? 12.5 : 13.5));
                flow.getChildren().add(textNode);
            }

            // Add highlighted match
            String match = matcher.group();
            Text highlightedText = new Text(match);
            if (match.matches("A\\d{7}[A-Z]")) {
                // Student ID
                highlightedText.setFill(isDarkTheme ? ACCENT_COLOR : Color.web("#005CC5"));
            } else {
                // Module code
                highlightedText.setFill(isDarkTheme ? MODULE_COLOR : Color.web("#8B008B"));
            }
            highlightedText.setFont(Font.font("SF Mono", FontWeight.SEMI_BOLD, 13));
            flow.getChildren().add(highlightedText);

            partIndex++;
        }

        // Add remaining text
        if (partIndex < parts.length && !parts[partIndex].isEmpty()) {
            Text textNode = new Text(parts[partIndex]);
            textNode.setFill(baseColor);
            textNode.setFont(Font.font("System", isBullet ? 12.5 : 13.5));
            flow.getChildren().add(textNode);
        }
    }

    /**
     * Detects the message type based on content.
     */
    private MessageType detectMessageType(String message) {
        if (message.contains("✓")) {
            return MessageType.SUCCESS;
        }
        String lower = message.toLowerCase();
        if (lower.contains("error") || lower.contains("invalid")
                || lower.contains("unknown") || lower.contains("not found")
                || lower.contains("cannot") || lower.contains("failed")) {
            return MessageType.ERROR;
        }
        return MessageType.INFO;
    }

    /**
     * Sets the theme for the result display.
     */
    public void setTheme(boolean isDark) {
        this.isDarkTheme = isDark;
        // Theme will be applied on next message
    }

    /**
     * Enum representing different message types.
     */
    private enum MessageType {
        SUCCESS, ERROR, INFO
    }
}
