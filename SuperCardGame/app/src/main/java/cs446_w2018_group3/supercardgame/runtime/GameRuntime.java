package cs446_w2018_group3.supercardgame.runtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exception.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerActionNotAllowed;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.ActionEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerEvent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class GameRuntime implements GameStateControl {
    private static final String TAG = GameRuntime.class.getName();

    // game objects
    private final GameFSM fsm = new GameFSM();
    private final Game gameModel = new Game();
    private Player currPlayer;
    private final MutableLiveData<Player> localPlayer = new MutableLiveData<>();
    private final MutableLiveData<Player> otherPlayer = new MutableLiveData<>();
    private final MutableLiveData<GameField> mGameField = new MutableLiveData<>();


    public GameRuntime() {
        gameModel.bind(this);
    }

    public void setGameStateChangeListener(GameFSM.GameStateChangeListener gameStateChangeListener) {
        fsm.setGameStateChangeListener(gameStateChangeListener);
    }

    // ================ getters & setters


    public LiveData<Player> getLocalPlayer() {
        return localPlayer;
    }

    public LiveData<Player> getOtherPlayer() {
        return otherPlayer;
    }

    synchronized void replaceGameData(GameRuntimeData gameRuntimeData) {
        updateLocalPlayer(gameRuntimeData.getLocalPlayer());
        updateOtherPlayer(gameRuntimeData.getOtherPlayer());
        updateCurrPlayer(gameRuntimeData.getCurrPlayer());
        updateGameField(gameRuntimeData.getGameField());
        fsm.setState(gameRuntimeData.getGameState());
        Log.i(TAG, "game runtime data refreshed");
    }

    void updateLocalPlayer(Player player) {
        if (Looper.myLooper() == Looper.getMainLooper())
            localPlayer.setValue(player);
        else localPlayer.postValue(player);
    }

    void updateOtherPlayer(Player player) {
        if (Looper.myLooper() == Looper.getMainLooper())
            otherPlayer.setValue(player);
        else otherPlayer.postValue(player);
    }

    private void updateCurrPlayer(Player player) {
        // NOTE: not doing validation
        currPlayer = player;
    }

    // methods for viewmodel
    public LiveData<Player> getPlayer(int playerId) {
        if (localPlayer.getValue() != null && localPlayer.getValue().getId() == playerId) { return localPlayer; }
        if (otherPlayer.getValue() != null && otherPlayer.getValue().getId() == playerId) { return otherPlayer; }
        return null;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public List<LiveData<Player>> getPlayers() {
        return Arrays.asList(localPlayer, otherPlayer);
    }

    public void setNextPlayer() {
        Log.i(TAG, String.format("curr player: %s; local player: %s; remote player: %s", currPlayer, localPlayer.getValue(), otherPlayer.getValue()));
        if (currPlayer == null) {
            updateCurrPlayer(localPlayer.getValue());
        }
        else if (currPlayer == localPlayer.getValue()) {
            updateCurrPlayer(otherPlayer.getValue());
        }
        else {
            updateCurrPlayer(localPlayer.getValue());
        }

        Log.i(TAG, "next player: " + currPlayer.getName());
    }

    private void setNextPlayer(Player player) throws PlayerNotFoundException {
        if (player != null && (player == localPlayer.getValue() || player == otherPlayer.getValue())) {
            updateCurrPlayer(player);
            return;
        }

        // player not found
        throw new PlayerNotFoundException();
    }

    public void updatePlayer(Player player) throws PlayerNotFoundException {
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        if (player == localPlayer.getValue()) {
            updateLocalPlayer(player);
        }
        else if (player == otherPlayer.getValue()) {
            updateOtherPlayer(player);
        }
        else {
            // player not found
            throw new PlayerNotFoundException();
        }
    }

    public LiveData<GameField> getGameField() {
        return mGameField;
    }

    public void updateGameField(GameField gameField) {
        if (Looper.myLooper() == Looper.getMainLooper())
            mGameField.setValue(gameField);
        else mGameField.postValue(gameField);
    }

    Game getGameModel() {
        return gameModel;
    }

    public GameRuntimeData getSyncData() {
        return new GameRuntimeData(localPlayer.getValue(), otherPlayer.getValue(), currPlayer, mGameField.getValue(), fsm.getState());
    }

    @Override
    public GameFSM.State getState() {
        return fsm.getState();
    }

    // ================ game control
    @Override
    public void start() {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.IDLE) {
                // TODO: start game during a game?
                Log.w(TAG, "game already started; state: " + fsm.getState());
                return;
            }

            if (localPlayer.getValue() == null || otherPlayer.getValue() == null) {
                Log.w(TAG, "cannot start game with one of the player being null");
                return;
            }

            try {
                // assume the first player joined takes the first turn
                setNextPlayer(localPlayer.getValue());
                gameModel.init();
                setNextPlayer(localPlayer.getValue());

                // state update
                fsm.nextState(); // goes to TURN_START
                Log.i(TAG, "game started");
            } catch (PlayerNotFoundException err) {
                Log.w(TAG, err);
            }
        } catch (InvalidStateException err) {
            // TODO: logs
            Log.w(TAG, err);
        }
    }

    @Override
    public void turnStart() throws PlayerCanNotEnterTurnException {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.TURN_START) {
                throw new InvalidStateException(fsm.getState());
            }

            gameModel.playerTurnStart(getCurrPlayer());

            // state update
            fsm.nextState();
        } catch (PlayerNotFoundException | InvalidStateException err) {
            Log.w(TAG, err);
    }
    }

    @Override
    public void turnEnd() { // called by the player side (e.g. human players or a bot)
        try {
            fsm.nextState(); // goes to TURN_END
            gameModel.playerTurnEnd(getCurrPlayer());
            fsm.nextState(); // goes to TURN_START
        } catch (PlayerNotFoundException | InvalidStateException err) {
            // TODO: logs
            Log.w(TAG, err);
        }
    }

    void checkPlayerEventState(PlayerEvent e) throws PlayerActionNotAllowed, InvalidStateException {
        if (e instanceof ActionEvent) {
            if (getCurrPlayer() == null) {
                throw new PlayerActionNotAllowed();
            }
            if (!getCurrPlayer().equals(getPlayer(e.getSubjectId()).getValue())) {
                throw new PlayerActionNotAllowed();
            }

            switch (e.getEventCode()) {
                case PLAYER_USE_CARD:
                case PLAYER_COMBINE_ELEMENT: {
                    // state check
                    if (fsm.getState() != GameFSM.State.PLAYER_TURN) {
                        throw new InvalidStateException(fsm.getState());
                    }
                }
                break;
                case PLAYER_END_TURN: {
                    // state check
                    if (fsm.getState() != GameFSM.State.PLAYER_TURN) {
                        throw new InvalidStateException(fsm.getState());
                    }
                }
                break;
                default:
                    // unsupported player event
            }
        } else if (e instanceof PlayerAddEvent) {
            // doesn't allow adding players once the game starts
            if (fsm.getState() != GameFSM.State.IDLE) {
                throw new InvalidStateException(fsm.getState());
            }
        } else {
            // unsupported player event
            Log.w(TAG, "unsupported player event: " + e);
        }
    }
}
