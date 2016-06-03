package com.tododeportes.tododeportesapp.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.pojo.Persona;

import java.util.ArrayList;

/**
 * Created by hernan on 2/06/16.
 */
public class ListarPersonasAdapter extends RecyclerView.Adapter<ListarPersonasAdapter.ViewHolder> {
    private ArrayList<Persona> listPersonas;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombre;
        public TextView txtApellido;
        public TextView txtNumeroId;
        public TextView txtDireccion;
        public TextView txtCelular;

        public ViewHolder(LinearLayout layFieldInfo) {
            super(layFieldInfo);
        }
    }

    public ListarPersonasAdapter(ArrayList<Persona> listPersonas) {
        this.listPersonas = listPersonas;
    }

    @Override
    public ListarPersonasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter_persona, parent, false);

        ViewHolder vh = new ViewHolder((LinearLayout) v);
        vh.txtDireccion = (TextView) v.findViewById(R.id.txtDireccion);
        vh.txtNombre = (TextView) v.findViewById(R.id.txtNombre);
        vh.txtApellido = (TextView) v.findViewById(R.id.txtApellido);
        vh.txtNumeroId = (TextView) v.findViewById(R.id.txtNumeroId);
        vh.txtCelular = (TextView) v.findViewById(R.id.txtCelular);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Persona persona = listPersonas.get(position);
        holder.txtNombre.setText(persona.getNombres());
        holder.txtApellido.setText(persona.getApellidos());
        holder.txtNumeroId.setText(persona.getNumeroDocumento());
        holder.txtDireccion.setText(persona.getDireccion());
        holder.txtCelular.setText(persona.getTelefonoCelular());
    }

    @Override
    public int getItemCount() {
        return listPersonas.size();
    }
}