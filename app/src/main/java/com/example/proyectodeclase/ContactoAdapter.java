package com.example.proyectodeclase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private List<Contacto> listaContactos;
    private Context context;
    private int selectedPosition = -1;  // Posición del ítem seleccionado

    public ContactoAdapter(List<Contacto> listaContactos, Context context) {
        this.listaContactos = listaContactos;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);
        holder.tvId.setText(String.valueOf(contacto.getId()));
        holder.tvNombre.setText(contacto.getNombre());
        holder.tvNumero.setText(contacto.getNumero());

        // Cambiar el color de fondo si el ítem está seleccionado
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }

        // Manejar el clic en el ítem
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();  // Actualizar la vista para reflejar el cambio de color
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    // Método para obtener el contacto seleccionado
    public Contacto getContactoSeleccionado() {
        if (selectedPosition != -1) {
            return listaContactos.get(selectedPosition);
        }
        return null;
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvNombre, tvNumero;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvNumero = itemView.findViewById(R.id.tvNumero);
        }
    }
}