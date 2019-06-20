package com.example.camaba1.camaba1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.camaba1.camaba1.R;
import com.example.camaba1.camaba1.entidades.Notificaciones;

import java.util.List;

public class Notificaciones_adapter extends RecyclerView.Adapter<Notificaciones_adapter.UsuariosHolder> implements View.OnClickListener{

    List<Notificaciones> listaitems;
    public View.OnClickListener listener;

    public Notificaciones_adapter(List<Notificaciones> listaitems, Context context) {
        this.listaitems = listaitems;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificaciones_vista_adaptada,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        vista.setOnClickListener(this);

        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {

        holder.idMensaje.setText(listaitems.get(position).getIdMensajeNotif());
        holder.Mens_id_usu_envia.setText(listaitems.get(position).getMens_id_usu_envia());




    }

    @Override
    public int getItemCount() {
        return listaitems.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;

    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick( view );

        }

    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView idMensaje,Mens_id_usu_envia;

        public UsuariosHolder(View itemView) {
            super(itemView);
            idMensaje= (TextView) itemView.findViewById(R.id.idMensajeNotif);
            Mens_id_usu_envia = (TextView)itemView.findViewById(R.id.Mens_id_usu_envia);



        }
    }
}
