package com.example.proyectodeclase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodeclase.data.DatabaseHelper;

import java.util.List;

public class VerContactos extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactoAdapter adapter;
    private DatabaseHelper dbHelper;
    private Button btnEliminarContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_contactos);

        // Inicializar DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los contactos de la base de datos
        List<Contacto> listaContactos = dbHelper.obtenerContactos();

        // Configurar el adaptador
        adapter = new ContactoAdapter(listaContactos, this);
        recyclerView.setAdapter(adapter);

        // Configurar el botón "Eliminar Contacto"
        btnEliminarContacto = findViewById(R.id.btnEliminarContacto);
        btnEliminarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el contacto seleccionado
                Contacto contactoSeleccionado = adapter.getContactoSeleccionado();

                if (contactoSeleccionado != null) {
                    // Mostrar un diálogo de confirmación
                    new AlertDialog.Builder(VerContactos.this)
                            .setTitle("Eliminar Contacto")
                            .setMessage("¿Estás seguro de que quieres eliminar este contacto?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Eliminar el contacto de la base de datos
                                    dbHelper.eliminarContacto(contactoSeleccionado.getId());

                                    // Actualizar la lista de contactos
                                    listaContactos.remove(contactoSeleccionado);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    // Mostrar un mensaje si no se ha seleccionado ningún contacto
                    new AlertDialog.Builder(VerContactos.this)
                            .setTitle("Advertencia")
                            .setMessage("Por favor, selecciona un contacto para eliminar.")
                            .setPositiveButton("Aceptar", null)
                            .show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();  // Cerrar la conexión a la base de datos
        super.onDestroy();
    }
}