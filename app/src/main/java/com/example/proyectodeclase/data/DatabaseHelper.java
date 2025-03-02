package com.example.proyectodeclase.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyectodeclase.Contacto;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "Lucasbd.db";

    // Versión de la base de datos (debes incrementarla si cambias el esquema)
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    private static final String TABLE_CONTACTOS = "Contactos";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NOMBRE = "NOMBRE";
    private static final String COLUMN_NUMERO = "NUMERO";

    // Sentencia SQL para crear la tabla
    private static final String CREATE_TABLE_CONTACTOS =
            "CREATE TABLE " + TABLE_CONTACTOS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOMBRE + " TEXT,"
                    + COLUMN_NUMERO + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla Contactos si no existe
        db.execSQL(CREATE_TABLE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla si existe y crearla de nuevo
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        onCreate(db);
    }

    // Método para verificar la conexión a la base de datos
    public boolean isDatabaseConnected() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db != null && db.isOpen();
    }



    public long insertContacto(String nombre, String numero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_NUMERO, numero);
        long id = db.insert(TABLE_CONTACTOS, null, values);
        db.close();
        return id;
    }

    // Método para obtener todos los contactos como un Cursor
    public Cursor getAllContactos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CONTACTOS, null, null, null, null, null, null);
    }

    // Método para obtener todos los contactos como una lista de objetos Contacto
    public List<Contacto> obtenerContactos() {
        List<Contacto> listaContactos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTOS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMERO));
                listaContactos.add(new Contacto(id, nombre, numero));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return listaContactos;
    }


    // Método para eliminar un contacto por ID
    public int eliminarContacto(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONTACTOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }




}