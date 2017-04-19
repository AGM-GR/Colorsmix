package ugr.pdm.rafalex.colorsmix;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
        dibujos.add(new Dibujo(R.drawable.cerdo, R.drawable.cerdo_colored));
        dibujos.add(new Dibujo(R.drawable.conejo, R.drawable.conejo_colored));
        dibujos.add(new Dibujo(R.drawable.leon, R.drawable.leon_colored));
        dibujos.add(new Dibujo(R.drawable.kiwi, R.drawable.kiwi_colored));

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
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

            //Dibujo dibujo = (Dibujo) av.getSelectedItem();
            Toast.makeText(getBaseContext(), av.getItemAtPosition(position).getClass().toString(), Toast.LENGTH_SHORT).show();

            // Inicia la siguiente acitividad.
            Intent i = new Intent(LevelsList.this, Paint.class);
            //i.putExtra("Dibujo", dibujo);
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
