package com.example.myapplication2;

public class Preguntas {

    //atributos
    private int operandoA;
    private int operandoB;
    private String operador;
    private String[] operadores = {"+" , "-" , "*" , "/"};

    //contructor
    public Preguntas() {

     this.operandoA = (int) (Math.random() * 11);
     this.operandoB = (int) (Math.random() * 11 + 1);

     int posicion = (int) (Math.random() * 4);
     this.operador = operadores[posicion];

    }


    //Metodos
    public String getPreguntas(){

        return operandoA + " " + operador + " " + operandoB;

    }

    public int getRespuesta(){

        int respuesta = 0;
        switch (operador){

            case "+":
                respuesta = this.operandoA + this.operandoB;
                break;

            case "-":
                respuesta = this.operandoA - this.operandoB;
                break;

            case "*":
                respuesta = this.operandoA * this.operandoB;
                break;

            case "/":
                respuesta = this.operandoA / this.operandoB;
                break;
        }

        return respuesta;

    }

}
