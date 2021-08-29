package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView preguntas;
    private TextView tiempo;
    private TextView puntaje;
    private EditText respuestas;
    private Button responder;
    private Button intento;
    private Preguntas preguntaActual;
    private int tiempoRestante;
    private int puntajeAct;
    private int tiempoPresionado;
    private boolean isPressed = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preguntas = findViewById(R.id.preguntas);
        tiempo = findViewById(R.id.tiempo);
        puntaje = findViewById(R.id.puntaje);
        respuestas = findViewById(R.id.respuesta);
        responder = findViewById(R.id.responderUser);
        intento = findViewById(R.id.intento);

        puntajeAct = 0;
        puntaje.setText("Puntaje : " + puntajeAct);

        tiempoRestante = 30;
        //tiempo.setText(" " + tiempoRestante);

        //preguntas
        generarNewPregunta();

        //tiempo
        timer();

        preguntas.setText(preguntaActual.getPreguntas());
        tiempo.setText(" " + tiempoRestante);

        //intento nuevo
        intento.setVisibility(View.GONE);
        tryAgain();

        //boton try again
        intento.setOnClickListener(
                (view) -> {

                    intento.setVisibility(View.GONE);
                    tiempoRestante = 30;
                    tiempo.setText("" + tiempoRestante);
                    timer();

                }
        );

        //verificacion
        responder.setOnClickListener(
                (view) -> {
                    verificarRespuesta();
                }
        );

        //skip
        tiempoPresionado = 0;

        preguntas.setOnTouchListener(
                (view,event) -> {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            isPressed = true;

                            new Thread(()->{
                                tiempoPresionado = 0;
                                while(tiempoPresionado <1500) {
                                    try {
                                        Thread.sleep(150);
                                        tiempoPresionado+=150;
                                        if (!isPressed) {
                                            return;
                                        }
                                    } catch (InterruptedException e) {

                                    }
                                }
                                runOnUiThread(()->{
                                    Toast.makeText(this,"siguiente pregunta",Toast.LENGTH_SHORT).show();
                                    generarNewPregunta();
                                    tiempoRestante = 30;
                                    tiempo.setText(""+tiempoRestante);
                                });


                            }).start();
                            break;

                        case MotionEvent.ACTION_UP:
                            isPressed = false;
                            break;
                    }
                    return true;
                });

    }

    public void timer() {

        new Thread(
                () -> {
                    while (tiempoRestante > 0) {
                        try {
                            tiempoRestante--;
                            runOnUiThread(
                                    () -> {
                                        tiempo.setText("" + tiempoRestante);

                                        if (tiempoRestante == 0) {
                                            intento.setVisibility(View.VISIBLE);

                                        }
                                    }
                            );

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.e("ERROR", e.toString());
                        }
                    }
                }

        ).start();

    }

    public void verificarRespuesta() {

        String respUsuario = respuestas.getText().toString();
        int respuestaInt = (int) Integer.parseInt(respUsuario);

        if (respuestaInt == preguntaActual.getRespuesta()){
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();
            puntajeAct += 5;
            puntaje.setText("Puntaje : " + puntajeAct);
        } else{
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();
        }

        generarNewPregunta();

    }

    public void generarNewPregunta(){

        preguntaActual = new Preguntas();
        preguntas.setText(preguntaActual.getPreguntas());
        respuestas.getText().clear();

    }

    public void tryAgain(){
        new Thread(
                ()->{
                    while(tiempoRestante >0){
                        try{
                            tiempoRestante--;
                            runOnUiThread(
                                    ()-> {
                                        tiempo.setText(" "+tiempoRestante);
                                        if(tiempoRestante == 0 ){
                                            intento.setVisibility(View.VISIBLE);

                                        }
                                    }
                            );
                            Thread.sleep(1000);
                        } catch (Exception e){

                        }
                    }
                }
        ).start();
    }

}