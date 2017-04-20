package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Paint extends AppCompatActivity {

    private Dibujo dibujo_seleccionado = null;
    private int color_seleccionado = Color.rgb(0,0,0);

    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    private ImageView imagen_coloreada;
    private ImageView imagen;
    private FrameLayout paint_zone;
    private ArrayList<ImageView> imagenes = new ArrayList<ImageView>();
    private ToggleButton botonAzul;
    private ToggleButton botonAmarillo;
    private ToggleButton botonRojo;
    private ToggleButton botonBlanco;
    private ToggleButton botonNegro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        paint_zone = (FrameLayout) findViewById(R.id.paint_zone);

        imagen = (ImageView) findViewById(R.id.image_painter);
        imagenes.add(imagen);

        imagen_coloreada = (ImageView) findViewById(R.id.image_sample);

        botonRojo = (ToggleButton) findViewById(R.id.redColor);
        botonAmarillo = (ToggleButton) findViewById(R.id.yellowColor);
        botonAzul = (ToggleButton) findViewById(R.id.blueColor);
        botonNegro = (ToggleButton) findViewById(R.id.blackColor);
        botonBlanco = (ToggleButton) findViewById(R.id.whiteColor);

        botonRojo.setOnCheckedChangeListener(ColorToggle);
        botonAmarillo.setOnCheckedChangeListener(ColorToggle);
        botonRojo.setOnCheckedChangeListener(ColorToggle);
        botonAzul.setOnCheckedChangeListener(ColorToggle);
        botonNegro.setOnCheckedChangeListener(ColorToggle);
        botonBlanco.setOnCheckedChangeListener(ColorToggle);

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dibujo_seleccionado = (Dibujo) getIntent().getExtras().getSerializable("Dibujo");

        imagen_coloreada.setImageResource(dibujo_seleccionado.getDibujoColoreado());

        imagen.setImageResource(dibujo_seleccionado.getTrozos().get(0));
        paint_zone.removeView(imagen);

        for (int image : dibujo_seleccionado.getTrozos()) {
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            imageView.setDrawingCacheEnabled(true);
            imageView.setOnTouchListener(touchColorListener);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(image);

            paint_zone.addView(imageView);

            imagenes.add(imageView);
        }

        paint_zone.addView(imagen);
    }

    //Comportamiento de la imagen al ser seleccionada
    private View.OnTouchListener touchColorListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
            int color = bmp.getPixel((int) event.getX(), (int) event.getY());

            if (color == Color.TRANSPARENT)
                return false;
            else {

                ((ImageView) v).setColorFilter(color_seleccionado);

                return true;
            }
        }
    };

    //Comportamiento de los botones de colores
    private CompoundButton.OnCheckedChangeListener ColorToggle = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isCheked) {
            if (isCheked) {
                color_seleccionado = ((ColorDrawable) buttonView.getBackground()).getColor();
            }
        }
    };

    //Función onCreateOptionMenu, para añadir el estilo de nuestro action_bar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu); // set your file name
        return super.onCreateOptionsMenu(menu);
    }

    //Función onOptionItemSelected, para definir el funcionamiento de las opciones del action_bar
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.help) {

            menuDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
