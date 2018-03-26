package cs446_w2018_group3.supercardgame.runtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exception.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerActionNotAllowed;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.bot.Bot;
import cs446_w2018_group3.supercardgame.model.player.AIPlayer;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.util.events.stateevent.StateEventAdapter;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEvent;
import cs446_w2018_group3.supercardgame.util.events.stateevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class GameRuntime implements GameStateControl {
    private final GameFSM fsm;
    private final Game gameModel;
    private final GameEventHandler gameEventHandler;

    // game objects
    private List<MutableLiveData<Player>> players;
    private MutableLiveData<Player> currPlayer; // NOTE: currPlayer is updated when turn changes
    private MutableLiveData<GameField> gameField;

    // references
    private int currPlayerIdx;

    public GameRuntime(GameEventHandler gameEventHandler) {
        fsm = new GameFSM();
        gameModel = new Game();
        this.gameEventHandler = gameEventHandler;

        players = new ArrayList<>();
        currPlayer = new MutableLiveData<>();
        currPlayerIdx = 0;

        gameField = new MutableLiveData<>();

        gameEventHandler.bind(this);
        gameModel.bind(this);
    }

    public void addPlayer(Player player) {
        // TODO: check whether player is already added
        MutableLiveData<Player> mPlayer = new MutableLiveData<>();
        mPlayer.setValue(player);
        players.add(mPlayer);
        Log.i("GameRuntime", String.format("player added: %s", player.getName()));
    }

    public void addBot() {
        // add bot to game
        Player player = new AIPlayer(2, "Bot");
        final Bot bot = new Bot(player);
        bot.bind(gameEventHandler);
        gameEventHandler.addStateEventListener(new StateEventAdapter() {
            @Override
            public void onTurnStart(TurnStartEvent e) {
                bot.onTurnStart(e);
            }

            @Override
            public void onGameEnd(Player winner) {
                // nothing to do for bot
            }
        });
        addPlayer(player);
        Log.i("GameRuntime", String.format("bot player added: %s", player.getName()));
    }

    @Override
    public void start() {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.IDLE) {
                // TODO: start game during a game?
                Log.w("main", "attempting to start game during a game; state: " + fsm.getState());
                return;
            }

            if (players.size() < 2) {
                // need more than 2 players to start?
                // ...
                return;
            }

            try {
                // assume the first player joined takes the first turn
                setNextPlayer();
                gameModel.init();
                setNextPlayer(players.get(0).getValue());


                // state update
                fsm.nextState(); // goes to TURN_START
                Log.i("GameRuntime", "game started");
            }
            catch (PlayerNotFoundException err) {
                // TODO: logs
                Log.w("main", err);
            }


        }
        catch (InvalidStateException err) {
            // TODO: logs
            Log.w("main", err);
        }
    }

    @Override
    public void turnStart() throws PlayerCanNotEnterTurnException {
        try {
            // state check
            if (fsm.getState() != GameFSM.State.TURN_START) {
                throw new InvalidStateException(fsm.getState());
            }

            gameModel.playerTurnStart(getCurrPlayer().getValue());

            // state update
            fsm.nextState();
        }
        catch (PlayerNotFoundException | InvalidStateException err) {
            Log.w("main", err);
        }
    }

    @Override
    public void turnEnd() { // called by the player side (e.g. human players or a bot)
        try {
            fsm.nextState(); // goes to TURN_END
            gameModel.playerTurnEnd(getCurrPlayer().getValue());
            fsm.nextState(); // goes to TURN_START
        }
        catch (PlayerNotFoundException | InvalidStateException err) {
            // TODO: logs
            Log.w("main", err);
        }
    }

    private MutableLiveData<Player> getMutableCurrPlayer() {
        return currPlayer;
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
        currPlayer.setValue(players.get(currPlayerIdx).getValue());
        Log.i("main", "next player: " + currPlayer.getValue().getName());
    }

    private void setNextPlayer(Player player) throws PlayerNotFoundException {
        int playerIdx = 0;

        for (; playerIdx < players.size(); playerIdx++) {
            if (players.get(playerIdx).getValue().getId() == player.getId()) { break; }
        }

        if (playerIdx == players.size()) {
            // player not found
            throw new PlayerNotFoundException();
        }

        currPlayerIdx = playerIdx;
        currPlayer.setValue(player);
        Log.i("main", "next player: " + currPlayer.getValue().getName());
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

    // NOTE: getFSM() is only accessible within package (for GameEventHandler to get state)
    GameFSM getFSM() { return fsm; }

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