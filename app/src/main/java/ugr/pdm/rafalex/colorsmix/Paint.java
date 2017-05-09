package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;
import java.util.ArrayList;

public class Paint extends AppCompatActivity {

    private Dibujo dibujo_seleccionado = null;
    private int color_seleccionado = Color.rgb(0,0,0);
    private ArrayList<Integer> colores = new ArrayList<>();

    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    private ImageView imagen_coloreada;
    private ImageView imagen;
    private FrameLayout paint_zone;
    private ArrayList<ImageView> imagenes = new ArrayList<>();
    private ToggleButton botonAzul;
    private ToggleButton botonAmarillo;
    private ToggleButton botonRojo;
    private ToggleButton botonBlanco;
    private ToggleButton botonNegro;
    private Button primer_color;
    private Button segundo_color;
    private ToggleButton mixColor;
    private ToggleButton botonSeleccionado;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        //Enlaza los componentes con la vista
        paint_zone = (FrameLayout) findViewById(R.id.paint_zone);

        imagen = (ImageView) findViewById(R.id.image_painter);

        imagen_coloreada = (ImageView) findViewById(R.id.image_sample);

        botonRojo = (ToggleButton) findViewById(R.id.redColor);
        botonAmarillo = (ToggleButton) findViewById(R.id.yellowColor);
        botonAzul = (ToggleButton) findViewById(R.id.blueColor);
        botonNegro = (ToggleButton) findViewById(R.id.blackColor);
        botonBlanco = (ToggleButton) findViewById(R.id.whiteColor);

        primer_color = (Button) findViewById(R.id.primer_color);
        segundo_color = (Button) findViewById(R.id.segundo_color);
        mixColor = (ToggleButton) findViewById(R.id.mixColor);

        botonSeleccionado = botonNegro;

        //Establece el comportamiento de los botones de colores
        botonRojo.setOnCheckedChangeListener(ColorToggle);
        botonAmarillo.setOnCheckedChangeListener(ColorToggle);
        botonRojo.setOnCheckedChangeListener(ColorToggle);
        botonAzul.setOnCheckedChangeListener(ColorToggle);
        botonNegro.setOnCheckedChangeListener(ColorToggle);
        botonBlanco.setOnCheckedChangeListener(ColorToggle);

        primer_color.setOnClickListener(SeleccionColorMezcla);
        segundo_color.setOnClickListener(SeleccionColorMezcla);
        mixColor.setOnCheckedChangeListener(ColorToggle);

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
        ((TextView) dialogView.findViewById(R.id.helpText)).setText(R.string.appDescriptionPersonajes);
        ((ImageView) dialogView.findViewById(R.id.mezclaColores)).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.mezcla_colores, null));
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean yaColoreado = false;

        if (colores.size() > 0)
            yaColoreado = true;

        //Recupera el objeto dibujo del EXTRA
        dibujo_seleccionado = (Dibujo) getIntent().getExtras().getSerializable("Dibujo");

        //Establece el dibujo coloreado
        imagen_coloreada.setImageResource(dibujo_seleccionado.getDibujoColoreado());

        //Establece el contorno del dibujo
        imagen.setImageResource(dibujo_seleccionado.getTrozos().get(0));
        paint_zone.removeView(imagen);

        //Crea un ImageView para cada trozo del dibujo
        for (int image : dibujo_seleccionado.getTrozos()) {
            //Crea el imageview sobre los demás y establece la imagen
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            imageView.setDrawingCacheEnabled(true);
            imageView.setOnTouchListener(touchColorListener);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(image);

            //Añade el imageview al layaut correspondiente
            paint_zone.addView(imageView);

            //Añade el color actual
            if (!yaColoreado)
                colores.add(Color.WHITE);
            else
                imageView.setColorFilter(colores.get(dibujo_seleccionado.getTrozos().indexOf(image)));

            //Añade el ImageView al array
            imagenes.add(imageView);
        }

        //Vuelve a añadir el contorno para que se vea sobre los trozos
        paint_zone.addView(imagen);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ClearImagesViews();
    }

    @Override
    protected void onDestroy() {

        ClearImagesViews();

        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntegerArrayList("DIBUJO", colores);
        savedInstanceState.putInt("Primer color", (((ColorDrawable) primer_color.getBackground()).getColor()));
        savedInstanceState.putInt("Segundo color", (((ColorDrawable) segundo_color.getBackground()).getColor()));
        savedInstanceState.putInt("mix color", (((ColorDrawable) mixColor.getBackground()).getColor()));

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        colores = savedInstanceState.getIntegerArrayList("DIBUJO");
        primer_color.setBackgroundColor(savedInstanceState.getInt("Primer color"));
        segundo_color.setBackgroundColor(savedInstanceState.getInt("Segundo color"));
        mixColor.setBackgroundColor(savedInstanceState.getInt("mix color"));
    }

    //Limpia los ImageViews creados y el array de ImageViews
    private void ClearImagesViews () {

        for (ImageView imageView : imagenes) {
            if (imageView != imagen)
                paint_zone.removeView(imageView);
        }

        imagenes.clear();
    }

    private int MezclaColores(int c1, int c2) {
        if (c1 == Color.rgb(255, 0, 0) || c2 == Color.rgb(255, 0, 0)) {
            if (c1 == Color.rgb(255, 255, 0) || c2 == Color.rgb(255, 255, 0))
                return Color.rgb(255, 127, 0);
            else if (c1 == Color.rgb(0, 0, 255) || c2 == Color.rgb(0, 0, 255))
                return Color.rgb(255, 0, 255);
            else if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(127, 0, 0);
            else if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(255, 127, 127);
        }

        if (c1 == Color.rgb(255, 255, 0) || c2 == Color.rgb(255, 255, 0)) {
            if (c1 == Color.rgb(0, 0, 255) || c2 == Color.rgb(0, 0, 255))
                return Color.rgb(0, 255, 0);
            else if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(127, 127, 0);
            else if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(255, 255, 127);
        }

        if (c1 == Color.rgb(0, 0, 255) || c2 == Color.rgb(0, 0, 255)) {
            if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(0, 0, 127);
            else if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(127, 127, 255);
        }

        if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
            if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(126, 126, 126);

        if (c1 == Color.rgb(255, 127, 0) || c2 == Color.rgb(255, 127, 0)) {
            if (c1 == Color.rgb(0, 0, 255) || c2 == Color.rgb(0, 0, 255))
                return Color.rgb(150, 102, 61);
            else if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(255, 190, 126);
            else if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(158, 79, 0);
        }

        if (c1 == Color.rgb(255, 0, 255) || c2 == Color.rgb(255, 0, 255)) {
            if (c1 == Color.rgb(255, 255, 0) || c2 == Color.rgb(255, 255, 0))
                return Color.rgb(150, 102, 61);
            else if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(255, 127, 255);
            else if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(127, 0, 127);
        }

        if (c1 == Color.rgb(0, 255, 0) || c2 == Color.rgb(0, 255, 0)) {
            if (c1 == Color.rgb(255, 0, 0) || c2 == Color.rgb(255, 0, 0))
                return Color.rgb(150, 102, 61);
            else if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(127, 255, 127);
            else if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(0, 127, 0);
        }

        if (c1 == Color.rgb(150, 102, 61) || c2 == Color.rgb(150, 102, 61)) {
            if (c1 == Color.rgb(255, 255, 255) || c2 == Color.rgb(255, 255, 255))
                return Color.rgb(164, 122, 90);
            else if (c1 == Color.rgb(0, 0, 0) || c2 == Color.rgb(0, 0, 0))
                return Color.rgb(107, 66, 34);
        }

        return c1;
    }

    //Comportamiento de la imagen al ser seleccionada
    private View.OnTouchListener touchColorListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView imageView = new ImageView(getBaseContext());
            Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
            int color = bmp.getPixel((int) event.getX(), (int) event.getY());

            if (color == Color.TRANSPARENT)
                return false;
            else {

                ImageView currentImage = ((ImageView) v);
                currentImage.setColorFilter(color_seleccionado);

                colores.set(imagenes.indexOf(currentImage), color_seleccionado);

                //if(imageView.setColorFilter(colores.get(dibujo_seleccionado.getTrozos(). == Color.rgb(255, 127, 127)){
                //    mensaje();
                //}

                return true;
            }
        }
    };

    private void mensaje() {
        toast = Toast.makeText(this, "Vas bien, sigue asi", Toast.LENGTH_SHORT);
        toast.show();
    }

    //Comportamiento de los botones de colores
    private CompoundButton.OnCheckedChangeListener ColorToggle = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isCheked) {
            if (isCheked) {
                color_seleccionado = ((ColorDrawable) buttonView.getBackground()).getColor();

                botonSeleccionado = (ToggleButton) buttonView;

                if (buttonView != botonRojo)
                    botonRojo.setChecked(false);
                if (buttonView != botonAmarillo)
                    botonAmarillo.setChecked(false);
                if (buttonView != botonAzul)
                    botonAzul.setChecked(false);
                if (buttonView != botonNegro)
                    botonNegro.setChecked(false);
                if (buttonView != botonBlanco)
                    botonBlanco.setChecked(false);
                if (buttonView != mixColor)
                    mixColor.setChecked(false);
            }
            else {
                if ( buttonView == botonSeleccionado) {
                    botonSeleccionado.setChecked(true);
                }
            }
        }
    };

    private View.OnClickListener SeleccionColorMezcla = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setBackgroundColor(color_seleccionado);

            mixColor.setBackgroundColor(MezclaColores(((ColorDrawable) primer_color.getBackground()).getColor(),
                    ((ColorDrawable) segundo_color.getBackground()).getColor()));

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