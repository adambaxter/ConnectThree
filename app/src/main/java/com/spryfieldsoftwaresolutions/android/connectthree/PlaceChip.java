package com.spryfieldsoftwaresolutions.android.connectthree;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Adam Baxter on 10/03/18.
 * <p>
 * Class to handle placing a chip.
 */

class PlaceChip {
    private static boolean mIsRedsTurn;
    private static PlayerList mPlayers;
    private static int mMoveCount, mPlayerCount;
    private static int[] mGamestate;
    private static int[][] mWinningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private static final String DIALOG_GAME_OVER = "GameOverDialog";
    private static final String DIALOG_ADD_PLAYER = "AddPlayerDialog";

    PlaceChip(PlayerList players, Context context) {
        mPlayers = players;
        mIsRedsTurn = setTurn(context);
        mGamestate = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        mMoveCount = 0;
        mPlayerCount = 0;
    }

    static void setPlayers(FragmentManager manager) {
        AddPlayerFragment fragment = AddPlayerFragment.newInstance();
        fragment.setCancelable(false);
        fragment.show(manager, DIALOG_ADD_PLAYER);

    }

    private static boolean setTurn(Context context) {
        final int random = new Random().nextInt(100);
        int turn = random % 2;
        Log.e("turn", "turn: " + turn + "random: " + random);
        if (turn == 0) {
            return true;
        } else {
            return false;
        }
    }

    static void setTurnIndicator(Context context) {
        ImageView turnIndicator1, turnIndicator2;
        if (mIsRedsTurn) {
            turnIndicator1 = ((Activity) context).findViewById(R.id.scoreboard_player1_indicator);
            turnIndicator2 = ((Activity) context).findViewById(R.id.scoreboard_player2_indicator);

            if (turnIndicator1 != null && turnIndicator2 != null) {
                turnIndicator1.setBackgroundColor((context).getResources().getColor(R.color.red));
                turnIndicator2.setBackgroundColor(0);
            }
        } else {
            turnIndicator1 = ((Activity) context).findViewById(R.id.scoreboard_player1_indicator);
            turnIndicator2 = ((Activity) context).findViewById(R.id.scoreboard_player2_indicator);

            if (turnIndicator1 != null && turnIndicator2 != null) {
                turnIndicator1.setBackgroundColor(0);
                turnIndicator2.setBackgroundColor((context).getResources().getColor(R.color.yellow));
            }

        }
    }

    static void determineTurn(Context context) {
        if (mPlayers.getSize() > 0) {
            setTurnIndicator(context);
        }

    }

    static void placeChip(ImageView view, FragmentManager manager, Context context) {

        ConstraintLayout parent = (ConstraintLayout) view.getParent().getParent();
        LinearLayout disableView = parent.findViewById(R.id.board_disabled_view);
        if (mMoveCount <= 8) {
            if (view.getDrawable() != null) {
                Toast.makeText(context, "Choose a different position", Toast.LENGTH_SHORT).show();
            } else {
                if (mIsRedsTurn) {
                    view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.red));
                    view.setTranslationY(-1000f);
                    view.animate().translationYBy(1000f).setDuration(300);
                    mGamestate[Integer.parseInt(view.getTag().toString())] = 0;
                } else {
                    view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.yellow));
                    view.setTranslationY(-1000f);
                    view.animate().translationYBy(1000f).setDuration(300);
                    mGamestate[Integer.parseInt(view.getTag().toString())] = 1;
                }
                if (checkForWinner()) {
                    //disableView.setBackgroundColor();
                    disableView.setVisibility(View.VISIBLE);
                    disableView.bringToFront();
                    ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    GameOverFragment fragment = GameOverFragment.newInstance(getCurrentPlayer());
                    fragment.setCancelable(false);
                    fragment.show(manager, DIALOG_GAME_OVER);

                }
                mIsRedsTurn = !mIsRedsTurn;
                mMoveCount++;
                determineTurn(context);
            }
        } else {
            checkMaxMoves(manager);
        }
    }

    private static boolean checkForWinner() {
        boolean isWinner = false;

        for (int[] positions : mWinningPositions) {
            if (mGamestate[positions[0]] == mGamestate[positions[1]] &&
                    mGamestate[positions[0]] == mGamestate[positions[2]] && mGamestate[positions[0]] != 2) {
                isWinner = true;
                break;
            }
        }
        return isWinner;
    }

    private static void checkMaxMoves(FragmentManager manager) {
        if (mMoveCount == 9) {
            GameOverFragment fragment = GameOverFragment.newInstance(null);
            fragment.setCancelable(false);
            fragment.show(manager, DIALOG_GAME_OVER);
        }
    }


    static void newGame(GridLayout board, Context context) {
        mIsRedsTurn = setTurn(context);
        mGamestate = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        mMoveCount = 0;

        ConstraintLayout parent = (ConstraintLayout) board.getParent();
        LinearLayout disableView = parent.findViewById(R.id.board_disabled_view);
        ((Activity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        disableView.setVisibility(View.GONE);
        int childCount = board.getChildCount();

        PlaceChip.determineTurn(context);

        for (int i = 0; i < childCount; i++) {
            ImageView child = (ImageView) board.getChildAt(i);
            child.setImageDrawable(null);
        }
    }

    static Player getCurrentPlayer() {
        if (mIsRedsTurn) {
            return mPlayers.getPlayer(0);
        } else {
            return mPlayers.getPlayer(1);
        }
    }

    static Boolean isRedsTurn() {
        return mIsRedsTurn;
    }

    static int getMoveCount() {
        return mMoveCount;
    }

    static PlayerList getPlayerList() {
        return mPlayers;
    }

    static GameState getGameState() {
        return new GameState(mPlayers.getPlayer(0),
                mPlayers.getPlayer(1),
                mGamestate,
                mIsRedsTurn,
                mMoveCount,
                mPlayerCount);
    }

    static void setGameState(GameState state, GridLayout board, Context context) {
        mPlayers.addPlayer(state.getPlayerOne());
        mPlayers.addPlayer(state.getPlayerTwo());
        mGamestate = state.getGameState();
        mIsRedsTurn = state.getIsRedsTurn();
        mMoveCount = state.getMoveCount();
        mPlayerCount = state.getPlayerCount();

        int childCount = board.getChildCount();

        for (int i = 0; i < childCount; i++) {
            ImageView child = (ImageView) board.getChildAt(i);
            switch (mGamestate[i]) {
                case 0:
                    child.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.red));
                    break;
                case 1:
                    child.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.yellow));
                    break;
                default:
                    break;
            }


        }
    }


}
