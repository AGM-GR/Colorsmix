package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelsList extends AppCompatActivity {

    //Datos para dialogos
    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    //Dibujos
    private ArrayList<Dibujo> dibujos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_list);

        //Inicializamos el array de dibujos
        dibujos.add(new Dibujo(R.drawable.cerdo, R.drawable.cerdo_colored,
                new ArrayList<Integer>(Arrays.asList(R.drawable.cerdo_00, R.drawable.cerdo_01, R.drawable.cerdo_02,
                    R.drawable.cerdo_03, R.drawable.cerdo_04, R.drawable.cerdo_05)),
                new ArrayList<Integer>(Arrays.asList(Color.BLACK,Color.rgb(255, 127, 127), Color.rgb(255, 127, 127),
                        Color.rgb(255, 0, 0), Color.rgb(255, 0, 0), Color.rgb(255, 0, 0)))));
                    dibujos.add(new Dibujo(R.drawable.conejo, R.drawable.conejo_colored,
                new ArrayList<Integer>(Arrays.asList(R.drawable.conejo_00, R.drawable.conejo_01, R.drawable.conejo_02,
                        R.drawable.conejo_03, R.drawable.conejo_04, R.drawable.conejo_05, R.drawable.conejo_06)),
                new ArrayList<Integer>(Arrays.asList(Color.BLACK, Color.rgb(150, 102, 61), Color.rgb(255, 127, 127),
                        Color.rgb(255, 127, 127), Color.rgb(126, 126, 126), Color.rgb(255, 0, 0), Color.WHITE))));
                    dibujos.add(new Dibujo(R.drawable.leon, R.drawable.leon_colored,
                new ArrayList<Integer>(Arrays.asList(R.drawable.leon_00, R.drawable.leon_01, R.drawable.leon_02, R.drawable.leon_03)),
                new ArrayList<Integer>(Arrays.asList(Color.BLACK, Color.rgb(255, 255, 0), Color.rgb(150, 102, 61), Color.rgb(150, 102, 61)))));
                    dibujos.add(new Dibujo(R.drawable.kiwi, R.drawable.kiwi_colored,
                new ArrayList<Integer>(Arrays.asList(R.drawable.kiwi_00, R.drawable.kiwi_01, R.drawable.kiwi_02, R.drawable.kiwi_03,
                        R.drawable.kiwi_04)),
                new ArrayList<Integer>(Arrays.asList(Color.BLACK, Color.rgb(0, 255, 0), Color.rgb(255, 255, 0), Color.rgb(150, 102, 61),
                        Color.rgb(150, 102, 61)))));

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
        ((TextView) dialogView.findViewById(R.id.helpText)).setText(R.string.appDescriptionLevel);
    }

    //Función Resume de la actividad
    @Override
    public void onResume() {

        super.onResume();

        // Establece la vista, el adaptador y la funcion al hacer click de los elementos de la lista
        ListView pairedListView = (ListView) findViewById(R.id.level_selector);
        pairedListView.setAdapter(new DibujoAdapter(dibujos));
        pairedListView.setOnItemClickListener(DibujoClickListener);

    }


    // OnItemClickListener para las imágenes de la lista
    private AdapterView.OnItemClickListener DibujoClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int position, long id) {

            // Inicia la siguiente acitividad.
            Intent i = new Intent(LevelsList.this, Paint.class);
            i.putExtra("Dibujo", (Dibujo) av.getItemAtPosition(position));
            startActivity(i);
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
