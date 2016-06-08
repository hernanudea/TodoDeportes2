package com.tododeportes.tododeportesapp.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tododeportes.tododeportesapp.R;
import com.tododeportes.tododeportesapp.pojo.Cancha;

import java.util.ArrayList;

/**
 * Created by Giovani Cardona on 31/05/16.
 */
public class ListarCanchasAdapter extends AnimatedAdapter<ListarCanchasAdapter.ViewHolder, Cancha>  {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFieldName;
        public TextView txtSport;
        public TextView txtFloorType;
        public ImageView imgSportIcon;

        public ViewHolder(LinearLayout layFieldInfo) {
            super(layFieldInfo);
        }
    }

    public ListarCanchasAdapter(ArrayList<Cancha> listCanchas) {
        this.array = new ArrayList<>(listCanchas);
    }

    @Override
    public ListarCanchasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter_cancha, parent, false);

        ViewHolder vh = new ViewHolder((LinearLayout) v);
        vh.imgSportIcon = (ImageView) v.findViewById(R.id.imgSportIcon);
        vh.txtFieldName = (TextView) v.findViewById(R.id.txtFieldName);
        vh.txtSport = (TextView) v.findViewById(R.id.txtSport);
        vh.txtFloorType = (TextView) v.findViewById(R.id.txtFloorType);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cancha cancha = array.get(position);
        holder.txtFieldName.setText(cancha.getDescripcion());
        holder.txtSport.setText(cancha.getTipoDeporte().getDescripcion());
        holder.txtFloorType.setText(cancha.getTipoEscenario().getDescripcion());

        switch (cancha.getTipoDeporte().getId()) {
            case 1:
            case 2:
                holder.imgSportIcon.setImageResource(R.drawable.soccer_ball);
                break;
            case 3:
                holder.imgSportIcon.setImageResource(R.drawable.basketball_ball);
                break;
            case 4:
                holder.imgSportIcon.setImageResource(R.drawable.bicycling_helmet);
                break;
            case 5:
                holder.imgSportIcon.setImageResource(R.drawable.volleyball_ball);
                break;
            case 6:
                holder.imgSportIcon.setImageResource(R.drawable.tejo);
        }
    }
}

