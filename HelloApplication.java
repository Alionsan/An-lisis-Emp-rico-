package com.clase.analisisempirico;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private LineChart<String, Number> lineChart;

    @Override
    public void start(Stage stage) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("n");
        yAxis.setLabel("log10(tiempo en ns)");   // logaritmo para que se vean todos los métodos

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Comparación de métodos");
        lineChart.setAnimated(false);

        Button boton = new Button("Ejecutar de nuevo");
        boton.setOnAction(evento -> ejecutar());

        BorderPane raiz = new BorderPane();
        raiz.setCenter(lineChart);
        raiz.setBottom(boton);
        BorderPane.setAlignment(boton, Pos.CENTER);
        BorderPane.setMargin(boton, new Insets(10));

        ejecutar();   // primera ejecución al abrir la ventana

        stage.setTitle("Comparación de algoritmos");
        stage.setScene(new Scene(raiz, 800, 600));
        stage.show();
    }

    // Mide los tiempos y vuelve a dibujar la gráfica
    private void ejecutar() {
        lineChart.getData().clear();

        long[][] tiempos = Sumatoria.medirTiempos();
        String[] nombres = {"Gauss", "Iterativa", "Recursiva"};

        for (int m = 0; m < 3; m++) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(nombres[m]);
            for (int k = 0; k < Sumatoria.VALORES_N.length; k++) {
                if (tiempos[m][k] > 0) {   // la recursiva no tiene punto si falló
                    serie.getData().add(new XYChart.Data<>(
                            String.valueOf(Sumatoria.VALORES_N[k]),
                            Math.log10(tiempos[m][k])));
                }
            }
            lineChart.getData().add(serie);
        }
    }
}
