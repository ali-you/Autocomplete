import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CreditsPane {
    Button button = new Button("Back");
    public BorderPane creditPane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.web("#6EDBA1"), null, null)));
        button.setBackground(new Background(new BackgroundFill(Color.web("#6EDBA1"), null, null)));
        button.setFont(new Font(20));
        AnchorPane anchorPane = new AnchorPane();
        Text text = new Text("Design And Programing :\nAli Yousefi");
        text.setFont(new Font("Segoe UI Black", 30));
        text.setFill(Color.web("#171B2A"));
        text.setTextAlignment(TextAlignment.CENTER);
        anchorPane.getChildren().add(button);
        AnchorPane.setTopAnchor(button, 20.0);
        AnchorPane.setLeftAnchor(button, 20.0);
        borderPane.setTop(anchorPane);
        borderPane.setCenter(text);
        return borderPane;
    }
}
