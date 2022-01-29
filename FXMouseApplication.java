package com.sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.shape.Rectangle;

import static com.sample.Drawable.defaultDrawableFillColor;
import static com.sample.Drawable.defaultDrawableStrokeColor;
import static com.sample.Drawable.highlightColor;

public class FXMouseApplication extends Application {


    int canvasWidth = 800;
    int canvasHeight = 400;


    //constants
    private String APPLICATION_WINDOW_TITLE = "Word Scramble";
    private String APPLICATION_VERSION = "Ver 1.0";
    private String APPLICATION_AUTHOR = "\u00A9 Gurkirat";


    //greeting text and instruction for each puzzle
    private String greetingText = "Welcome To Gurkirat's Word Puzzle Game!";
    private String instructionsTextPuzzle1 = "Instructions\nConstruct the Song Lyrics";
    private String instructionsTextPuzzle2 = "Instructions\nConstruct the poetry line";
    private String instructionsTextPuzzle3 = "Instructions\nConstruct the famous Quotation";


    //private instance variables
    private ArrayList<DraggableWord> draggableWords; //collection of words that can be dragged
    private ArrayList<DraggableWord> draggedWordsInLine = new ArrayList<>(); //collection of words that are dragged in lines
    private DraggableWord wordBeingDragged; //the word currently being dragged

    private ArrayList<Line> pageLines;
    private ArrayList<Drawable> itemsToDraw; //collection of things to draw on canvas
    private ArrayList<Movable> itemsToMove; //collection of items to move with timer

    VBox root = new VBox(); //root node of scene graph


    private AnimationTimer timer; //for animating frame based motion
    boolean animationIsRunning = false; //to test if animation is running

    //GUI elements
    Canvas canvas; //drawing canvas

    Label wordLabel = new Label("  word:");
    TextField wordTextField = new TextField();
    Button insertButton = new Button("Insert");
    Button removeButton = new Button("Remove");
    Button findButton = new Button("Find");
    Button refreshButton = new Button("Refresh");
    TextArea consoleArea = new TextArea();

    private double mousePressedLocationX;
    private double mousePressedLocationY;
    private double previousMouseLocationX;
    private double previousMouseLocationY;
    private double mouseReleasedLocationX;
    private double mouseReleasedLocationY;

    //GUI menus
    MenuBar menubar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu runMenu = new Menu("Run");
    Menu puzzleMenu = new Menu("Puzzles");
    ContextMenu contextMenu = new ContextMenu();

    Rectangle dragBox = new Rectangle(0, 0, 0, 0);


    private void buildMenus(Stage theStage) {
        //build the menus for the menu bar

        //Build Run menu items
        MenuItem startMenuItem = new MenuItem("Start Timer");
        runMenu.getItems().addAll(startMenuItem);
        startMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                startAnimation();
                repaintCanvas(canvas);
            }
        });
        MenuItem stopMenuItem = new MenuItem("Stop Timer");
        runMenu.getItems().addAll(stopMenuItem);
        stopMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stopAnimation();
                repaintCanvas(canvas);
            }
        });

        //Build File menu items
        MenuItem aboutMenuItem = new MenuItem("About This App");
        fileMenu.getItems().addAll(aboutMenuItem);
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(APPLICATION_VERSION + " " + APPLICATION_AUTHOR);
                alert.showAndWait();
            }
        });

        //Build puzzle menu items
        MenuItem puzzle1 = new MenuItem("Puzzle 1");
        puzzleMenu.getItems().addAll(puzzle1);
        puzzle1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                //initializing the arraylist to store the dragged words in line
                draggedWordsInLine = new ArrayList<>();

                //removing the previous dragged words from the list
                itemsToDraw.removeAll(draggableWords);

                Random random = new Random(canvasWidth);
                Random randomY = new Random(canvasHeight / 2);

                itemsToDraw.removeAll(draggableWords);
                draggableWords = new ArrayList<DraggableWord>();
                //loop to randomly add the words of puzzle1
                for (String sampleWord : Puzzle.puzzle1().getPuzzleWords()) {
                    draggableWords.add(new DraggableWord(sampleWord, random.nextDouble() * (canvasWidth - 50), random.nextDouble() * canvasHeight / 2 + 20));
                }


                //re-setting the console area text with new greeting and instructions
                consoleArea.setText(greetingText + "\n" + instructionsTextPuzzle1);
                //lastly adding the words to itemsToDraw list
                itemsToDraw.addAll(draggableWords);
                repaintCanvas(canvas);
            }
        });

        MenuItem puzzle2 = new MenuItem("Puzzle 2");
        puzzleMenu.getItems().addAll(puzzle2);
        puzzle2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {


                //removing the previous dragged words from the list
                itemsToDraw.removeAll(draggableWords);

                //initializing the arraylist to store the dragged words in line
                draggedWordsInLine = new ArrayList<>();

                Random random = new Random(canvasWidth);
                Random randomY = new Random(canvasHeight / 2);

                itemsToDraw.removeAll(draggableWords);
                draggableWords = new ArrayList<DraggableWord>();
                //loop to randomly add the words of puzzle2
                for (String sampleWord : Puzzle.puzzle2().getPuzzleWords()) {
                    draggableWords.add(new DraggableWord(sampleWord, random.nextDouble() * (canvasWidth - 50), random.nextDouble() * canvasHeight / 2 + 20));
                }


                //re-setting the console area text with new greeting and instructions
                consoleArea.setText(greetingText + "\n" + instructionsTextPuzzle2);
                //lastly adding the words to itemsToDraw list
                itemsToDraw.addAll(draggableWords);
                repaintCanvas(canvas);
            }
        });


        MenuItem puzzle3 = new MenuItem("Puzzle 3");
        puzzleMenu.getItems().addAll(puzzle3);
        puzzle3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                //removing the previous dragged words from the list
                itemsToDraw.removeAll(draggableWords);

                //initializing the arraylist to store the dragged words in line
                draggedWordsInLine = new ArrayList<>();

                Random random = new Random(canvasWidth);
                Random randomY = new Random(canvasHeight / 2);

                itemsToDraw.removeAll(draggableWords);
                draggableWords = new ArrayList<DraggableWord>();
                //loop to randomly add the words of puzzle3
                for (String sampleWord : Puzzle.puzzle3().getPuzzleWords()) {
                    draggableWords.add(new DraggableWord(sampleWord, random.nextDouble() * (canvasWidth - 50), random.nextDouble() * canvasHeight / 2 + 20));
                }


                //re-setting the console area text with new greeting and instructions
                consoleArea.setText(greetingText + "\n" + instructionsTextPuzzle3);
                //lastly adding the words to itemsToDraw list
                itemsToDraw.addAll(draggableWords);
                repaintCanvas(canvas);
            }
        });


        //Build Popup context menu items
        MenuItem pauseContextMenuItem = new MenuItem("Pause Animation");
        contextMenu.getItems().addAll(pauseContextMenuItem);
        pauseContextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stopAnimation();
                repaintCanvas(canvas);
            }
        });

        MenuItem resumeContextMenuItem = new MenuItem("Resume Animation");
        contextMenu.getItems().addAll(resumeContextMenuItem);
        resumeContextMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                startAnimation();
                repaintCanvas(canvas);
            }
        });

    }

    //required by any Application subclass
    @Override
    public void start(Stage mainStage) {

        //Here we do most of the initialization for the application
        //This method is called automatically as a result of
        // launching the application
        mainStage.setTitle(APPLICATION_WINDOW_TITLE); //window title

        Scene theScene = new Scene(root); //our GUI scene
        mainStage.setResizable(false); //don't allow window to resize
        mainStage.setScene(theScene); //add scene to our app's stage



        //build application menus
        //add menus to menu bar object
        menubar.getMenus().add(fileMenu);
        menubar.getMenus().add(runMenu);
        menubar.getMenus().add(puzzleMenu);
        //add menu bar object to application scene root
        root.getChildren().add(menubar); //add menubar to GUI
        buildMenus(mainStage); //add menu items to menus

        canvas = new Canvas(canvasWidth, canvasHeight); //GUI element we will draw on
        root.getChildren().add(canvas);
        root.getChildren().add(dragBox);

        //add mouse event handler (for popup menu)
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                    handleMousePressedEvent(e);

                }
        );
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent e) -> {
                    handleMouseReleasedEvent(e);
                }
        );
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent e) -> {
                    handleMouseDraggedEvent(e);

                }
        );

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                //listen for arrow keys using key press event
                //arrow keys don't show up in KeyTyped events
                String text = "";
                double moveIncrement = 5;
                if (ke.getCode() == KeyCode.RIGHT) {
                    text += "RIGHT";
                    //do nothing
                } else if (ke.getCode() == KeyCode.LEFT) {
                    //do nothing
                } else if (ke.getCode() == KeyCode.UP) {
                    text += "UP";
                    //do nothing
                } else if (ke.getCode() == KeyCode.DOWN) {
                    text += "DOWN";
                    //do nothing
                }
                System.out.println("key press: " + text);
                ke.consume(); //don't let keyboard event propogate
            }
        });

        HBox wordEntryBox = new HBox();
        wordEntryBox.setSpacing(20); //space between elements
        wordEntryBox.setAlignment(Pos.TOP_LEFT);
        wordTextField.setPrefWidth(240); //preferred width for text field
        wordEntryBox.getChildren().addAll(wordLabel, wordTextField, insertButton, removeButton, findButton, refreshButton);
        root.getChildren().addAll(wordEntryBox);

        HBox consoleAreaBox = new HBox();
        consoleAreaBox.setSpacing(20); //space between elements
        consoleAreaBox.setAlignment(Pos.TOP_LEFT);
        consoleArea.setPrefWidth(canvasWidth); //preferred width for text area
        consoleArea.setText(greetingText + "\n" + instructionsTextPuzzle1);
        consoleAreaBox.getChildren().addAll(consoleArea);
        root.getChildren().addAll(consoleAreaBox);

        wordTextField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //handle ENTER key for text field
                Random random = new Random();
                DraggableWord newWord = new DraggableWord(wordTextField.getText(), random.nextDouble() * (canvasWidth - 50), (random.nextDouble() * canvasHeight / 2) + 20);
                draggableWords.add(newWord);
                itemsToDraw.add(newWord);
                wordTextField.setText(""); //erase word in text field
                repaintCanvas(canvas);
            }
        });

        insertButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //handle "insert" button press
                Random random = new Random();
                DraggableWord newWord = new DraggableWord(wordTextField.getText(), random.nextDouble() * (canvasWidth - 50), (random.nextDouble() * canvasHeight / 2) + 20);
                draggableWords.add(newWord);
                itemsToDraw.add(newWord);
                wordTextField.setText(""); //erase word in text field
                repaintCanvas(canvas);
            }
        });

        findButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //handle "find" button press
                //find the word and select it

                for (DraggableWord w : draggableWords) {
                    w.setSelected(false);
                    if (w.getText().equals(wordTextField.getText().trim())) {
                        w.setSelected(true);
                    }
                }

                repaintCanvas(canvas);
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //handle "refresh" button press
                //add the sentence formed in console area


                //adding the words to console area that are being dragged in the line
                consoleArea.setText("");
                for (int i = 0; i < draggedWordsInLine.size(); i++) {
                    consoleArea.setText(consoleArea.getText() + draggedWordsInLine.get(i).getText() + "\n");
                }


                //checking if all the words are dragged correctly
                if (checkIfWins()) {

                    consoleArea.clear();
                    consoleArea.setText("Congratulation you formed the puzzle correctly");
                }
                repaintCanvas(canvas);
            }
        });

        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //handle "remove" button press

                //find the word
                DraggableWord foundWord = null;
                for (DraggableWord w : draggableWords) {
                    if (w.getText().equals(wordTextField.getText())) {
                        foundWord = w;
                    }
                }
                //remove the found word
                if (foundWord != null) {
                    draggableWords.remove(foundWord);
                    itemsToDraw.remove(foundWord);
                }

                wordTextField.setText(""); //erase word in text field
                repaintCanvas(canvas);
            }
        });

        timer = new AnimationTimer() { //e.g. of anonymous inner subclass
            @Override
            public void handle(long nowInNanoSeconds) {
                //this method will be called about 60 times per second
                //which is default behaviour of the AnimationTimer class

                //move all movable items within the area provided
                for (Movable m : itemsToMove) {
                    m.advanceInArea(0, 0, canvas.getWidth(), canvas.getHeight());
                }

                repaintCanvas(canvas); //refesh our canvas rendering
            }
        };

        //initialize array lists and model objects
        itemsToDraw = new ArrayList<Drawable>();
        itemsToMove = new ArrayList<Movable>();

        int NUMBER_OF_PAGE_LINES = 6;
        int LINE_THICKNESS = 2;
        double LINE_SPACING = canvas.getHeight() / 16;
        pageLines = new ArrayList<Line>();

        for (int i = 0; i < NUMBER_OF_PAGE_LINES; i++) {
            Line line = new Line(0,
                    canvas.getHeight() - LINE_SPACING * NUMBER_OF_PAGE_LINES + i * LINE_SPACING,
                    canvas.getWidth(), LINE_THICKNESS, Color.LIGHTBLUE);
            pageLines.add(line);

            itemsToDraw.add(line);
        }


        Random random = new Random(canvasWidth);
        Random randomY = new Random(canvasHeight / 2);

        draggableWords = new ArrayList<DraggableWord>();
        for (String sampleWord : Puzzle.puzzle1().getPuzzleWords()) {
            draggableWords.add(new DraggableWord(sampleWord, random.nextDouble() * (canvasWidth - 50), random.nextDouble() * canvasHeight / 2 + 20));
        }


        itemsToDraw.addAll(draggableWords);

        startAnimation(); //start the animation timer

        mainStage.show(); //show the application window
        repaintCanvas(canvas); //do initial repaint

    }

    private void startAnimation() {
        timer.start();
        animationIsRunning = true;
    }

    private void stopAnimation() {
        timer.stop();
        animationIsRunning = false;
    }

    private void handleMousePressedEvent(MouseEvent e) {
        //mouse handler for canvas
        canvas.requestFocus(); //set canvas to receive keyboard events

        mousePressedLocationX = e.getX();
        mousePressedLocationY = e.getY();
        previousMouseLocationX = mousePressedLocationX;
        previousMouseLocationY = mousePressedLocationY;


        //Windows uses mouse release popup trigger
        //Mac uses mouse press popup trigger
        if (e.isPopupTrigger()) {
            contextMenu.show(canvas, e.getScreenX(), e.getScreenY());
        } else {
            contextMenu.hide(); //in case it was left open

            //print out mouse locations for inspection and debugging
            System.out.println("mouse scene: " + e.getSceneX() + "," + e.getSceneY()); //window co-ordinates
            System.out.println("mouse screen: " + e.getScreenX() + "," + e.getScreenY()); //screen co-ordinates
            System.out.println("mouse get: " + e.getX() + "," + e.getY()); //canvas co-ordinates


            wordBeingDragged = null;
            //see if any words were targeted and if not clear all the selections
            for (DraggableWord dw : draggableWords) {
                if (dw.containsPoint(e.getX(), e.getY())) {
                    wordBeingDragged = dw;
                    dw.setSelected(true);
                }
            }

            if (wordBeingDragged == null) {
                //clear all selections
                for (DraggableWord dw : draggableWords) {
                    dw.setSelected(false);
                }
            }
        }


        repaintCanvas(canvas); //update the GUI canvas
    }

    private void handleMouseReleasedEvent(MouseEvent e) {
        //Windows uses mouse release popup trigger
        //Mac uses mouse press popup trigger
        mouseReleasedLocationX = e.getX();
        mouseReleasedLocationY = e.getY();
        //if(wordBeingDragged != null) wordBeingDragged.setSelected(false);
        wordBeingDragged = null;

        if (e.isPopupTrigger()) {
            contextMenu.show(canvas, e.getScreenX(), e.getScreenY());
        }

        //If words have actually been dragged a substantial distance then clear the selections.
        double DRAG_TOLLERANCE = 5;
        if ((Math.abs(mouseReleasedLocationX - mousePressedLocationX) > DRAG_TOLLERANCE) || (Math.abs(mouseReleasedLocationY - mousePressedLocationY) > DRAG_TOLLERANCE)) {


            //checking if the word that is being dragged is in lines or not
            //if it is in lines area then add to draggedwordline list
            for (DraggableWord dw : draggableWords) {
                if (checkWordIsDragged(dw)) {
                    if (!draggedWordsInLine.contains(dw))
                        draggedWordsInLine.add(dw);
                }
                if (!checkWordIsDragged(dw)) {
                    if (draggedWordsInLine.contains(dw))
                        draggedWordsInLine.remove(dw);
                }

                dw.setSelected(false);
            }
        }

        repaintCanvas(canvas);
    }

    private void handleMouseDraggedEvent(MouseEvent e) {
        //PROBLEM 1 answer code
        if (wordBeingDragged != null) {
            for (DraggableWord dw : draggableWords) {
                if (dw.getSelected() == true) {
                    dw.moveBy(e.getX() - previousMouseLocationX, e.getY() - previousMouseLocationY);
                }
            }
        }


        previousMouseLocationX = e.getX();
        previousMouseLocationY = e.getY();
        repaintCanvas(canvas);

    }

    private void repaintCanvas(Canvas aCanvas) {
        //repaint the contents of our GUI canvas

        //obtain the graphics context for drawing on the canvas
        GraphicsContext thePen = aCanvas.getGraphicsContext2D();

        //clear the canvas
        thePen.clearRect(0, 0, aCanvas.getWidth(), aCanvas.getHeight());

        //Draw all the drawable items on the canvas
        for (Drawable item : itemsToDraw) {
            item.drawWith(thePen);
        }

    }

    public static void main(String[] args) {
        //entry point for javaFX application
        System.out.println("starting main application");
        launch(args); //will cause application's to start and
        // run it's start() method
        System.out.println("main application is finished");
    }


    //main logic to check the word is in line area or not
    //if yes then return true otherwise false
    public boolean checkWordIsDragged(DraggableWord word) {
        for (Line line : pageLines) {
            if (word.getLocationX() >= line.getTopLeftX() && word.getLocationX() <= line.getWidth()) {

                if (word.getLocationY() >= 250 && word.getLocationY() <= 375) {
                    return true;
                }

            }

        }
        return false;
    }



    //main logic to check if the words are dragged correctly
    //if yes then return true otherwise false
    public boolean checkIfWins() {


        String constructString = "";
        for (DraggableWord word : draggedWordsInLine) {
            constructString += word.getText() + " ";
        }


        int myhashcode = new Puzzle(constructString).hashCode();

        if ((myhashcode == Puzzle.puzzle1().hashCode()) ||
                (myhashcode == Puzzle.puzzle2().hashCode()) ||
                (myhashcode == Puzzle.puzzle3().hashCode())) {
            return true;
        }
        return false;
    }

}
