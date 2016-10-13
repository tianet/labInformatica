package com.prova.jocprova;



import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.security.spec.ECField;

public class MainActivity extends JocProvaActivity {
    private PantallaJoc joc;

 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joc);
    }

    @Override
    public void onStart() {
    	super.onStart();
        LinearLayout pantalla = (LinearLayout) findViewById(R.id.layoutJoc);
        super.init();
        // i ara cream per programa la zona de dibuix
        try {

            Col colors []  =new Col[2];
            colors[0] = new Col(Color.BLACK,Form.CIRCLE);
            colors[1] = new Col(Color.GREEN,Form.SQUARE);

            Grid grid = new Grid(7,7,colors);
            TopLeftIndicators tli = new TopLeftIndicators(grid);
            joc = new PantallaJoc(this, grid ,  tli);

            // afegeix la pantalla de dibuix al Layout
            pantalla.addView(joc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
