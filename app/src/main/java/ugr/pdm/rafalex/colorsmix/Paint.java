package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.LinkedList;
import java.util.Queue;

public class Paint extends AppCompatActivity {

    private Dibujo dibujo_seleccionado = null;
    private int color_seleccionado = Color.rgb(0,0,0);
    private QueueLinearFloodFiller floodFiller = null;

    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    private ImageView imagen_coloreada;
    private ImageView imagen;
    private ToggleButton botonAzul;
    private ToggleButton botonAmarillo;
    private ToggleButton botonRojo;
    private ToggleButton botonBlanco;
    private ToggleButton botonNegro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        imagen = (ImageView) findViewById(R.id.image_painter);
        imagen.setDrawingCacheEnabled(true);
        imagen.setOnTouchListener(touchColorListener);

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

        imagen.setImageResource(dibujo_seleccionado.getDibujo());
        imagen_coloreada.setImageResource(dibujo_seleccionado.getDibujoColoreado());
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

                floodFiller = new QueueLinearFloodFiller(bmp, color, color_seleccionado);
                //floodFiller.setTargetColor(color);
                //floodFiller.setFillColor(color_seleccionado);
                floodFiller.setTolerance(10);
                floodFiller.floodFill((int) event.getX(), (int) event.getY());
                imagen.setImageBitmap(floodFiller.getImage());

                return true;
            }
        }
    };

    //Algortimo para colorear un área seleccionada de un dibujo
    // http://stackoverflow.com/questions/8801047/how-to-fill-color-in-image-in-particular-area
    /*private void FloodFill(Bitmap bmp, Point pt, int replacementColor){
        Queue<Point> q = new LinkedList<Point>();
        q.add(pt);
        int targetColor = bmp.getPixel(pt.x,pt.y);

        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1)
                    && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }
    }*/

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
