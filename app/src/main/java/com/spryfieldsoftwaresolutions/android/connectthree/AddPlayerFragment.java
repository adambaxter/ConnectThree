package com.spryfieldsoftwaresolutions.android.connectthree;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Adam Baxter on 13/03/18.
 * <p>
 * Fragment that will be launched to get user names before the game has started
 */

public class AddPlayerFragment extends DialogFragment {
    // private static final String ARG_PLAYER_COUNT = "playerCount";
    String player1Name, player2Name;
    PlayerList mPlayers;
    TextView p1, p1Score, p2, p2Score;

    public static AddPlayerFragment newInstance() {
        //Bundle args = new Bundle();

       // AddPlayerFragment fragment = new AddPlayerFragment();
        // fragment.setArguments(args);

        return new AddPlayerFragment();
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        mPlayers = PlaceChip.getPlayerList();

        View dialogView = inflater.inflate(R.layout.fragment_add_player, null);
        final EditText userInput1 = dialogView.findViewById(R.id.add_user_edittext_player1);
        final EditText userInput2 = dialogView.findViewById(R.id.add_user_edittext_player2);

        p1 = getActivity().findViewById(R.id.scoreboard_player1_name);
        p1Score = getActivity().findViewById(R.id.scoreboard_player1_score);
        p2 = getActivity().findViewById(R.id.scoreboard_player2_name);
        p2Score = getActivity().findViewById(R.id.scoreboard_player2_score);

        userInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1Name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        userInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2Name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.add_user_dialog_title)
                .setMessage(R.string.add_user_dialog_message)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPlayers.addPlayer(new Player(player1Name));
                        mPlayers.addPlayer(new Player(player2Name));
                        updateNames();
                        setScore();

                    }
                })
                .create();


    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    public void updateNames() {
        Log.e("UPDATENAMES", "UPDATE NAMES");
        if(mPlayers.getSize() > 0) {
            Log.e("UPDATENAMES", "UPDATE NAMES SIZE > 2");
            p1.setText(mPlayers.getPlayer(0).getName());
            p2.setText(mPlayers.getPlayer(1).getName());
        }


    }

    public void setScore(){
        if(mPlayers.getSize() > 0) {
            p1Score.setText(Integer.toString(mPlayers.getPlayer(0).getScore()));
            p2Score.setText(Integer.toString(mPlayers.getPlayer(1).getScore()));
        }
    }

}
