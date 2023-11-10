import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

  @Override
  public void start(Stage stage) throws Exception {

    ArrayList<Button> botones = new ArrayList<>();

    GridPane root = new GridPane();

    TextField results = new TextField();

    Button one = new Button("1");
    Button two = new Button("2");
    Button three = new Button("3");
    Button four = new Button("4");
    Button five = new Button("5");
    Button six = new Button("6");
    Button seven = new Button("7");
    Button eight = new Button("8");
    Button nine = new Button("9");
    Button plus = new Button("+");
    Button minus = new Button("-");
    Button multiply = new Button("x");
    Button divide = new Button("/");
    Button c = new Button("C");
    Button zero = new Button("0");
    Button equals = new Button("=");

    botones.add(seven);
    botones.add(eight);
    botones.add(nine);
    botones.add(plus);
    botones.add(four);
    botones.add(five);
    botones.add(six);
    botones.add(minus);
    botones.add(one);
    botones.add(two);
    botones.add(three);
    botones.add(multiply);
    botones.add(c);
    botones.add(zero);
    botones.add(equals);
    botones.add(divide);

    int row = 1;
    int col = 0;
    for (Button boton : botones) {

      boton.setMinWidth(50);
      GridPane.setMargin(boton, new Insets(1, 1, 1, 1));

      System.out.println(boton.getText());

      if (!boton.getText().equals("=") && !boton.getText().equals("C")) {
        boton.setOnAction(e -> {
          if (boton.getText().equals("+") || boton.getText().equals("-") || boton.getText().equals("x") || boton.getText().equals("/")) {
            results.setText(results.getText() + " " + boton.getText() + " ");
          } else {
            results.setText(results.getText() + boton.getText());
          }
        });
      }

      GridPane.setConstraints(boton, col, row);
      col++;
      if (col == 4) {
        col = 0;
        row++;
      }
    }

    equals.setOnAction(e -> {

      if (!results.getText().equals("")) {
        String[] values = results.getText().split(" ");

        if (values.length == 3) {

          int operacion = Integer.parseInt(values[0]);
          String operador = "";

          for (int i = 1; i < values.length; i++) {
            if (i % 2 == 0) {
              switch (operador) {
              case "+":
                operacion = operacion + Integer.parseInt(values[i]);
                break;
              case "-":
                operacion = operacion - Integer.parseInt(values[i]);
                break;
              case "x":
                operacion = operacion * Integer.parseInt(values[i]);
                break;
              case "/":
                operacion = operacion / Integer.parseInt(values[i]);
                break;
              default:
                break;
              }
            } else {
              operador = values[i];
            }
          }

          results.setText("" + operacion);

        } else {
          results.setText("");
        }

      }

    });

    c.setOnAction(e -> {
      results.setText("");
    });

    GridPane.setConstraints(results, 0, 0);
    GridPane.setColumnSpan(results, 4);
    GridPane.setMargin(results, new Insets(0, 0, 15, 0));
    results.setEditable(false);

    root.getChildren().addAll(botones);
    root.getChildren().addAll(results);
    root.setPadding(new Insets(15, 15, 15, 15));
    root.setAlignment(Pos.CENTER);

    Scene scene = new Scene(root, 230, 200);
    stage.setTitle("JavaFX Calculator");
    stage.setScene(scene);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
