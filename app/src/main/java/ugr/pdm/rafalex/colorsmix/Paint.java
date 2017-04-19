package ugr.pdm.rafalex.colorsmix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Paint extends AppCompatActivity {

    private Dibujo dibujo_seleccionado = null;
    private ImageView imagen_coloreada;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        imagen = (ImageView) findViewById(R.id.image_painter);
        imagen_coloreada = (ImageView) findViewById(R.id.image_sample);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dibujo_seleccionado = (Dibujo) getIntent().getExtras().getSerializable("Dibujo");

        imagen.setImageResource(dibujo_seleccionado.getDibujo());
        imagen_coloreada.setImageResource(dibujo_seleccionado.getDibujoColoreado());
    }
}
