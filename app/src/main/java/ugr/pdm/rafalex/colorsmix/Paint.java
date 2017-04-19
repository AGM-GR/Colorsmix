package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Paint extends AppCompatActivity {

    private Dibujo dibujo_seleccionado = null;
    private int color_seleccionado = Color.rgb(0,0,0);

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

    //Comportamiento de los botones de colores
    private CompoundButton.OnCheckedChangeListener ColorToggle = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isCheked) {
            if (isCheked) {
                color_seleccionado = ((ColorDrawable) buttonView.getBackground()).getColor();
                Toast.makeText(getBaseContext(),"Color: " + color_seleccionado, Toast.LENGTH_SHORT).show();
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
