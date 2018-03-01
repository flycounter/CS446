package cs446_w2018_group3.supercardgame.runtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerActionNotAllowed;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEvent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class GameController implements GameStateControl {
    private final GameFSM fsm;
    private final Game gameModel;
    private final GameEventHandler gameEventHandler;

    // game objects
    private List<MutableLiveData<Player>> players;
    private MutableLiveData<GameField> gameField;

    // references
    private int currPlayerIdx;

    public GameController(GameEventHandler gameEventHandler) {
        fsm = new GameFSM();
        gameModel = new Game();
        this.gameEventHandler = gameEventHandler;

        this.players = new ArrayList<>();
        currPlayerIdx = 0;

        gameEventHandler.bind(this);
        gameModel.bind(this);
    }

    public void addPlayer(Player player) {
        // TODO: check whether player is already added
        players.add(new MutableLiveData<>());
    }

    @Override
    public void start() {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.IDLE) {
                // TODO: start game during a game?
                return;
            }
            if (players.size() < 2) {
                // need more than 2 players to start?
                // ...
                return;
            }

            try {
                // assume the first player joined takes the first turn
                gameModel.init(players.get(0).getValue());
            }
            catch (PlayerNotFoundException err) {
                // TODO: logs
            }

            // state update
            fsm.nextState();
        }
        catch (InvalidStateException err) {
            // TODO: logs
        }
    }

    @Override
    public void beforeTurnStart() throws PlayerCanNotEnterTurnException {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.BEFORE_TURN_START) {
                throw new InvalidStateException(fsm.getState());
            }

            try {
                gameModel.beforePlayerTurnStart(getCurrPlayer().getValue());
            }
            catch (PlayerNotFoundException | PlayerCanNotEnterTurnException err) {
                if (err instanceof PlayerNotFoundException) {
                    // TODO: game end
                }

                if (err instanceof PlayerCanNotEnterTurnException) {
                    throw (PlayerCanNotEnterTurnException) err;
                }
            }
        }
        catch (InvalidStateException err) {
            // TODO: logs
        }
    }

    @Override
    public void turnStart() {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.PLAYER_TURN) {
                throw new InvalidStateException(fsm.getState());
            }

            try {
                gameModel.playerTurnStart(getCurrPlayer().getValue());
            }
            catch (PlayerNotFoundException err) {
                // TODO: logs
            }
        }
        catch (InvalidStateException err) {
            // TODO: logs
        }
    }

    @Override
    public void afterTurnStart() {
        // state update
        try {
            fsm.nextState();
        }
        catch (InvalidStateException err) {
            // TODO: logs
        }

    }

    @Override
    public void beforeTurnEnd() {
        // method not implemented in model
    }

    @Override
    public void turnEnd() {
        try {
            gameModel.playerTurnEnd(getCurrPlayer().getValue());
        }
        catch (PlayerNotFoundException err) {
            // TODO: logs
        }
    }

    @Override
    public void afterTurnEnd() {
        try {
            gameModel.afterPlayerTurnEnd(getCurrPlayer().getValue());

            // state update
            fsm.nextState();
        }
        catch (InvalidStateException err) {
            // TODO: logs
        }
    }

    private MutableLiveData<Player> getMutableCurrPlayer() {
        return players.get(currPlayerIdx);
    }

    private MutableLiveData<Player> getMutablePlayer(int playerId) {
        for (MutableLiveData<Player> player: players) {
            if (player.getValue().getId() == playerId) {
                return player;
            }
        }

        return null;
    }

    private List<MutableLiveData<Player>> getMutablePlayers() {
        return players;
    }

    private MutableLiveData<GameField> getMutableGameField() {
        return gameField;
    }

    // methods for viewmodel
    public LiveData<Player> getPlayer(int playerId) {
        return getMutablePlayer(playerId);
    }

    public LiveData<Player> getCurrPlayer() {
        return getMutableCurrPlayer();
    }

    public List<LiveData<Player>> getPlayers() {
        List<LiveData<Player>> list = new ArrayList<>();
        list.addAll(players);
        return list;
    }

    public LiveData<GameField> getGameField() {
        return getMutableGameField();
    }

    public void updateGameField(GameField gameField) {
        getMutableGameField().setValue(gameField);
    }

    public void setNextPlayer() {
        currPlayerIdx = (currPlayerIdx + 1) % players.size();
    }

    public void setNextPlayer(Player player) throws PlayerNotFoundException {
        // TODO: force set next player
    }

    public void updatePlayer(Player player) throws PlayerNotFoundException {
        for (MutableLiveData<Player> mPlayer: players) {
            if (player.equals(mPlayer.getValue())) {
                mPlayer.setValue(player);
                return;
            }
        }

        // player not found
        throw new PlayerNotFoundException();
    }

    Game getGameModel() {
        return gameModel;
    }

    void checkPlayerEventState(PlayerEvent e) throws PlayerActionNotAllowed, InvalidStateException {
        if (!getCurrPlayer()
                .getValue()
                .equals(
                        getPlayer(e.getSubjectId()).getValue())) {
            throw new PlayerActionNotAllowed();
        }

        switch (e.getEventCode()) {
            case PLAYER_USE_CARD:
            case PLAYER_COMBINE_ELEMENT:
            {
                // state check
                if (fsm.getState() != GameFSM.State.PLAYER_TURN) {
                    throw new InvalidStateException(fsm.getState());
                }
            }
            break;
            case PLAYER_END_TURN:
            {
                // state check
                if (fsm.getState() != GameFSM.State.PLAYER_TURN) {
                    throw new InvalidStateException(fsm.getState());
                }
            }
            break;
            default:
                // unsupported player event
        }
    }
}
