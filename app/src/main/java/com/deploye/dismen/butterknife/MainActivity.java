package com.deploye.dismen.butterknife;

import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //primera pantalla
    @BindView(R.id.llPrimera)
    public LinearLayout llPrimera;

    @BindView(R.id.tvPuntuacionFinal)
    public TextView tvPuntuacionFinal;

    @BindView(R.id.btGo)
    public Button btGo;

    //segunda pantalla
    @BindView(R.id.llSegunda)
    public LinearLayout llSegunda;

    @BindView(R.id.tvOperacion)
    public TextView tvOperacion;

    @BindView(R.id.tvConteo)
    public TextView tvConteo;

    @BindView(R.id.tvSegundos)
    public TextView tvSegundos;

    @BindView(R.id.tvUno)
    public TextView tvUno;

    @BindView(R.id.tvDos)
    public TextView tvDos;

    @BindView(R.id.tvtres)
    public TextView tvTres;

    @BindView(R.id.tvCuatro)
    public TextView tvCuatro;

    @BindView(R.id.tvCorrectoIncorecto)
    public TextView tvCorrecto;

    // variables sin dependencia
    public int resultado = 0, tiempo = 30, intentos = 0, intentosCorrectos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //solo colocar en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    void mostrarPrimeraPantalla(){
        llPrimera.setVisibility(View.VISIBLE);
        llSegunda.setVisibility(View.INVISIBLE);

        tvPuntuacionFinal.setText("Tu puntuación " + tvConteo.getText().toString());

        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                intentosCorrectos = intentos = 0;
                btGo.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    void mostrarSegundaPantalla(){
        llPrimera.setVisibility(View.INVISIBLE);
        llSegunda.setVisibility(View.VISIBLE);
    }

    void generarPregunta(){

        int posicion = (int)(Math.random() * 4) + 1,
                entero1 =(int)(Math.random() * 99) + 1,
                entero2 =(int)(Math.random() * 99) + 1;

        tvOperacion.setText(entero1 + " + " + entero2);

        //guardar respuesta
        resultado = entero1 + entero2;

        for (int i = 0;i < 5;i++){
            int valor = (posicion == i ? resultado : i == 1 ? resultado - 3 : i == 2 ? resultado + 2 : i == 3 ? resultado - 7 : resultado +5 );

            if (i == 1){
                tvUno.setText(valor + "");
            }else if(i == 2){
                tvDos.setText(valor + "");
            }else if(i == 3){
                tvTres.setText(valor + "");
            }else{
                tvCuatro.setText(valor + "");
            }
        }

    }

    void comprobarRespuesta(int respuestaSeleccionada){

        if(respuestaSeleccionada == resultado) intentosCorrectos++;

        tvCorrecto.setText(respuestaSeleccionada == resultado ? "Correcto" : "Incorrecto");
        tvCorrecto.setVisibility(View.VISIBLE);

        intentos++;

        tvConteo.setText(intentosCorrectos + "/" + intentos);

        generarPregunta();

    }

    @OnClick(R.id.btGo)
    public void go(View view){
        generarPregunta();
        btGo.setVisibility(View.INVISIBLE);
        tvPuntuacionFinal.setText("");
        tvConteo.setText("0/0");
        mostrarSegundaPantalla();
        tvCorrecto.setVisibility(View.INVISIBLE);
        tiempo = 30;

        new CountDownTimer(tiempo * 1000,1000){

            @Override
            public void onTick(long l) {
                tiempo--;
                tvSegundos.setText(tiempo + "s");
            }

            @Override
            public void onFinish() {
                mostrarPrimeraPantalla();
            }
        }.start();

    }

    //region Posibles Respuesta
    @OnClick(R.id.tvUno)
    public void opcionUno(View view){
        comprobarRespuesta(Integer.parseInt(tvUno.getText().toString()));
    }

    @OnClick(R.id.tvDos)
    public void opcionDos(View view){
        comprobarRespuesta(Integer.parseInt(tvDos.getText().toString()));
    }

    @OnClick(R.id.tvtres)
    public void opcionTres(View view){
        comprobarRespuesta(Integer.parseInt(tvTres.getText().toString()));
    }

    @OnClick(R.id.tvCuatro)
    public void opcionCuatro(View view){
        comprobarRespuesta(Integer.parseInt(tvCuatro.getText().toString()));
    }

    //endregion
}
