package com.spryfieldsoftwaresolutions.android.connectthree;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by Adam Baxter on 12/03/18
 * <p>
 * Dialog that will alert users that the game is over and will ask to start a new game
 */

public class GameOverFragment extends DialogFragment {
    public static final String ARG_WINNER = "winner";


    public static GameOverFragment newInstance(Player winner) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_WINNER, winner);


        GameOverFragment fragment = new GameOverFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public boolean wasWinner() {
        if (getArguments().getParcelable(ARG_WINNER) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message;
        if (wasWinner()) {
            Player winner = getArguments().getParcelable(ARG_WINNER);
            winner.addWin();
            String winnerMsg = String.format(getString(R.string.game_over_winner_message), winner.getName());
            message = String.format(getString(R.string.game_over_dialog_message), winnerMsg);
        } else {
            message = String.format(getString(R.string.game_over_dialog_message),
                    getString(R.string.game_over_no_more_moves));
        }


        return new AlertDialog.Builder(getActivity()).setTitle(R.string.game_over_dialog_title)
                .setMessage(message)
                .setPositiveButton(R.string.game_over_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GridLayout board = getActivity().findViewById(R.id.game_gridLayout);
                        if(wasWinner()) {
                            updateScore((Player) getArguments().getParcelable(ARG_WINNER));
                        }
                        PlaceChip.newGame(board, getContext());
                    }
                })
                .setNegativeButton(R.string.game_over_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finishAndRemoveTask();
                    }
                })
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    public void updateScore(Player player) {
        int position = PlaceChip.getPlayerList().getPositionByName(player.getName());
        TextView score;
        switch (position) {
            case 0:
                score = getActivity().findViewById(R.id.scoreboard_player1_score);
                score.setText(Integer.toString(player.getScore()));
                break;
            case 1:
                score = getActivity().findViewById(R.id.scoreboard_player2_score);
                score.setText(Integer.toString(player.getScore()));
                break;
            default:
                break;

        }

    }
}
