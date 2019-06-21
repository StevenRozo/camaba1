package com.example.camaba1.camaba1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fracment_responder_notificacion extends Fragment {

   // public static final String Token="Token";

    private String Token;

    TextView id_responder;

    public fracment_responder_notificacion() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=  inflater.inflate( R.layout.fragment_fracment_responder_notificacion, container, false );

        id_responder =(TextView) view.findViewById( R.id.id_responder );

        //id_responder.setText( Token );

        if(getArguments() !=null){

            Token = getArguments().getString("Token" );



        }

        return view;

    }

}
