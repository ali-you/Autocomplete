import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main extends Application {
    Scene scene1, scene2, credits;
    SpellChecker spellChecker = new SpellChecker();
    Stack<Integer> mileStoneStack = new Stack<>();
    String[] sugText;


    public static void main(String[] args) throws IOException {

//        Scanner input = new Scanner(System.in);
//        String word = input.next();
//        SpellChecker spellChecker = new SpellChecker();
//        System.out.println(spellChecker.suggestWord(word));

//        System.out.println(spellChecker.res2);
//        System.out.println(spellChecker.res3);
////        spellChecker.filePath.writeFile(word);
//        spellChecker.filePath.getTrie().add(word);
//        System.out.println(spellChecker.suggestWord(word));

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
//        primaryStage.initStyle(StageStyle.UNDECORATED);


/**
 * Scene 1
 */
        BorderPane borderPane = new BorderPane();
        FlowPane welcome = new FlowPane(Orientation.VERTICAL, 20, 20);
        FadeTransition ft = new FadeTransition(Duration.seconds(3), welcome);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();


        welcome.setAlignment(Pos.CENTER);
        welcome.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        Text text = new Text("SPELL CHECKER");
        text.setFont(new Font("Segoe UI Black", 55));
        Text text1 = new Text("Algorithm Design\nSpell Checking Project");
        text1.setFont(new Font("Segoe UI Black", 15));
        welcome.getChildren().addAll(text, text1);
        scene1 = new Scene(welcome, width, height);


/**
Transition animation change scene to Scene2
*/
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished( event ->{
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene2);
        });
        delay.play();

/**
 *  Scene 2
 */

        FlowPane flowPane = new FlowPane(Orientation.VERTICAL, 100, 100);
        flowPane.setAlignment(Pos.CENTER);
        TextArea textArea = new TextArea();
        textArea.setFont(new Font("Titillium", 18));
        textArea.setPrefSize(900, 400);
        Text checkText = new Text();
        checkText.setFont(Font.font("Titillium", FontWeight.BOLD, 18));
        flowPane.getChildren().addAll(textArea, checkText);
/**
 * Event handler for specific key pressing "Space" "Enter" "backspace"
 */
        mileStoneStack.push(0);
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER){
                    String textInput = textArea.getText(mileStoneStack.peek(), textArea.getCaretPosition()).replaceAll("[\n\\s]", "");
                    System.out.println(textInput);
                    sugText = spellChecker.suggestWord(textInput);
/**
 * calculate number of menu item for any word
 */


                    ContextMenu contextMenu = new ContextMenu();
                    SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
/**
 * menu implementation
 */
                    if (sugText != null) {
                        textArea.setTooltip(new Tooltip("Incorrect Word"));
                        checkText.setText("Incorrect Word");

                        int menuLen =0;
                        for (int i = 0; i < sugText.length; i++) {
                            if (sugText[i] != null)
                                menuLen++;
                        }

                        for (int i = 0; i < menuLen; i++) {
                            MenuItem menuItem1 = new MenuItem(sugText[i]);
                            menuItem1.setStyle("menu-item");
                            menuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+"+i));
                            menuItem1.setOnAction(event1 -> {
                                mileStoneStack.pop();
                                textArea.setText(textArea.getText(0, mileStoneStack.peek()) + menuItem1.getText());
                                mileStoneStack.push(mileStoneStack.peek() + menuItem1.getText().length());
                            });

                            contextMenu.getItems().addAll(menuItem1);
                        }

                    }
                    else {
                        textArea.setTooltip(new Tooltip("Correct Word"));
                        checkText.setText("Correct Word");
                    }

                    if (event.getCode() == KeyCode.SPACE)
                        mileStoneStack.push(textArea.getCaretPosition()+1);
                    else if (event.getCode() == KeyCode.ENTER)
                        mileStoneStack.push(textArea.getCaretPosition());


                    MenuItem ignoreItem = new MenuItem("Ignore All");
                    ignoreItem.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
                    ignoreItem.setOnAction(event1 -> {
                        spellChecker.filePath.getTrie().add(textInput);});


                    MenuItem addDictionaryItem = new MenuItem("Add to Dictionary");
                    addDictionaryItem.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
                    addDictionaryItem.setOnAction(event1 -> {
                        spellChecker.filePath.writeFile(textInput);});

                    contextMenu.getItems().addAll(separatorMenuItem, ignoreItem, addDictionaryItem);

                    textArea.setContextMenu(contextMenu);
                }
                if (event.getCode() == KeyCode.BACK_SPACE && textArea.getCaretPosition() < mileStoneStack.peek())
                    mileStoneStack.pop();
            }
        });
        borderPane.getStylesheets().add("style.css");

/**
 * toolbar implementation
 */
        ToolBar toolBar = new ToolBar();
        toolBar.setBackground(new Background(new BackgroundFill(Color.web("#6EDBA1"), null, null)));

        Button menuCredits = new Button("Credits");
        menuCredits.setBackground(new Background(new BackgroundFill(Color.web("#6EDBA1"), null, null)));
        menuCredits.setFont(new Font(15));
        CreditsPane creditsPane = new CreditsPane();
        credits = new Scene(creditsPane.creditPane(), width, height);
        menuCredits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(credits);
            }
        });

        creditsPane.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene2);
            }
        });


        javafx.scene.control.Button menuExit = new Button("Exit");
        menuExit.setBackground(new Background(new BackgroundFill(Color.web("#6EDBA1"), null, null)));
        menuExit.setFont(new Font(15));
        menuExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Exit Massage");
                alert.setHeaderText("");
                alert.setContentText("Are You Sure ?");
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES)
                    Platform.exit();
            }
        });

        toolBar.getItems().addAll(menuCredits, menuExit );


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(toolBar);
        AnchorPane.setTopAnchor(toolBar, 10.0);
        AnchorPane.setLeftAnchor(toolBar, 10.0);

        borderPane.setBackground(new Background(new BackgroundFill(Color.web("#6EDBA1"), null, null)));
        borderPane.setCenter(flowPane);
        borderPane.setTop(anchorPane);
        scene2 = new Scene(borderPane, width, height);


        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Spell Checker");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
