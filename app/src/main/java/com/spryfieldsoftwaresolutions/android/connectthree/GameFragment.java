package com.spryfieldsoftwaresolutions.android.connectthree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Adam Baxter on 09/03/18.
 * <p>
 * Fragment that will host the game.
 */

public class GameFragment extends Fragment {

    private GameState mGameState;
    private PlayerList mPlayers;
    private FragmentManager mManager;
    private TextView p1, p1Score, p2, p2Score;
    private ImageView p1TurnIndicator, p2TurnIndicator;
    private final String KEY_GAMESTATE = "gamestate";

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mGameState = savedInstanceState.getParcelable(KEY_GAMESTATE);
        }

        mPlayers = PlayerList.get();
        mManager = getFragmentManager();
        PlaceChip placeChip = new PlaceChip(mPlayers, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        GridLayout board = view.findViewById(R.id.game_gridLayout);
        p1 = view.findViewById(R.id.scoreboard_player1_name);
        p1Score = view.findViewById(R.id.scoreboard_player1_score);
        p1TurnIndicator = view.findViewById(R.id.scoreboard_player1_indicator);
        p2 = view.findViewById(R.id.scoreboard_player2_name);
        p2Score = view.findViewById(R.id.scoreboard_player2_score);
        p2TurnIndicator = view.findViewById(R.id.scoreboard_player2_indicator);

        if (mGameState != null) {
            PlaceChip.setGameState(mGameState, board, getContext());
            updateScore();
            updateNames();
            setInitialTurnIndicator();

        }

        int childCount = board.getChildCount();
        if (mPlayers.getSize() > 0) {
            PlaceChip.determineTurn(getContext());
        }

        for (int i = 0; i < childCount; i++) {
            ImageView child = (ImageView) board.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView view = (ImageView) v;
                    PlaceChip.placeChip(view, mManager, getContext());
                }
            });

        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPlayers.getSize() == 0) {
            PlaceChip.setPlayers(mManager);
            setInitialTurnIndicator();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (mPlayers.getSize() != 0) {
            savedInstanceState.putParcelable(KEY_GAMESTATE, PlaceChip.getGameState());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    public void updateScore() {
        if (mPlayers.getSize() > 0) {

            p1Score.setText(Integer.toString(mPlayers.getPlayer(0).getScore()));
            p2Score.setText(Integer.toString(mPlayers.getPlayer(1).getScore()));


        }
    }

    public void updateNames() {
        if (mPlayers.getSize() > 0) {
            p1.setText(mPlayers.getPlayer(0).getName());
            p2.setText(mPlayers.getPlayer(1).getName());
            p1.postInvalidate();
            p1.postInvalidate();
        }


    }

    public void setInitialTurnIndicator() {
        if (PlaceChip.isRedsTurn()) {
            p1TurnIndicator.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            p2TurnIndicator.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }
}
