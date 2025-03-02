package com.example.proyectodeclase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectodeclase.data.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

public class Menu extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextInputEditText etNombre, etNumero;
    private Button btnAgregarContacto, btnVerContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);


        etNombre = findViewById(R.id.etNombre);
        etNumero = findViewById(R.id.etNumero);
        btnAgregarContacto = findViewById(R.id.btnAgregarContacto);
        btnVerContactos = findViewById(R.id.btnVerContactos);


        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = etNombre.getText().toString().trim();
                String numero = etNumero.getText().toString().trim();


                if (nombre.isEmpty() || numero.isEmpty()) {
                    Toast.makeText(Menu.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Insertar el contacto en la base de datos
                    long id = dbHelper.insertContacto(nombre, numero);
                    if (id != -1) {
                        Toast.makeText(Menu.this, "Contacto agregado con éxito", Toast.LENGTH_SHORT).show();
                        etNombre.setText("");  // Limpiar el campo de nombre
                        etNumero.setText("");  // Limpiar el campo de número
                        verificarContactos();  // Verificar si hay contactos para habilitar el botón "Ver Contactos"
                    } else {
                        Toast.makeText(Menu.this, "Error al agregar el contacto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Configurar el botón "Ver Contactos"
        btnVerContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad VerContactos
                Intent intent = new Intent(Menu.this, VerContactos.class);
                startActivity(intent);
            }
        });

        // Verificar si hay contactos al iniciar la actividad
        verificarContactos();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para verificar si hay contactos en la base de datos
    private void verificarContactos() {
        Cursor cursor = dbHelper.getAllContactos();
        if (cursor != null && cursor.getCount() > 0) {
            btnVerContactos.setEnabled(true);  // Habilitar el botón "Ver Contactos"
        } else {
            btnVerContactos.setEnabled(false);  // Deshabilitar el botón "Ver Contactos"
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();  // Cerrar la conexión a la base de datos
        super.onDestroy();
    }
}