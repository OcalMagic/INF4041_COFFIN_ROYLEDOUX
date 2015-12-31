package org.esiea.coffin_royledoux.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by oliviercoffin on 31/12/2015.
 */
public class InformationFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        //Warning : null on the View root because we don't care on which View we put it in (because it's on top of all)
        @SuppressWarnings("all")
        View informationsView = dialogInflater.inflate(R.layout.fragment_information, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(informationsView)
                .setTitle(SecondActivity.nom)
                .setNeutralButton(android.R.string.ok, null);

        TextView desc = (TextView) informationsView.findViewById(R.id.description);
        desc.setText(SecondActivity.description);

        return dialogBuilder.create();
    }
}
