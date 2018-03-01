package cs446_w2018_group3.supercardgame.runtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.cards.FireCard;
import cs446_w2018_group3.supercardgame.model.cards.WaterCard;
import cs446_w2018_group3.supercardgame.util.events.payload.GameInfo;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class GameRuntime implements GameScheduler {
    private final MutableLiveData<Player> player;
    private final MutableLiveData<Player> opponent;
    private final MutableLiveData<GameField> gameField;

    private Player nextPlayer;
    private Game game;

    private GameRuntime gameRuntime;

    public GameRuntime() {
        player = new MutableLiveData<>();
        opponent = new MutableLiveData<>();
        gameField = new MutableLiveData<>();
    }

    public void init(Game game) {
        gameRuntime = new GameRuntime();
        // NOTE: for demo purpose the game always starts with player goes first

        this.game = game;
        Log.i("GameRuntime", "binding runtime to game");
        this.game.bindRuntime(this);
    }

    public LiveData<Player> getPlayer() {
        return player;
    }

    public MutableLiveData<Player> getPlayer(int playerId) {
        // TODO: refactor player and opponent into List of Players
        if (playerId == player.getValue().getId()) {
            return player;
        }
        else if (playerId == opponent.getValue().getId()) {
            return opponent;
        }
        else {
            return null;
        }
    }

    public LiveData<Player> getOpponent() {
        return opponent;
    }

    public LiveData<GameField> getGameField() {
        return gameField;
    }

    public MutableLiveData<Player> getMutablePlayer() {
        return player;
    }

    public MutableLiveData<Player> getMutablePlayer(int playerId) {
        // TODO: refactor player and opponent into List of Players
        if (playerId == player.getValue().getId()) {
            return player;
        }
        else if (playerId == opponent.getValue().getId()) {
            return opponent;
        }
        else {
            return null;
        }
    }

    public MutableLiveData<Player> getMutableOpponent() {
        return opponent;
    }

    public MutableLiveData<GameField> getMutableField() {
        return gameField;
    }

    @Override
    public void handlePlayerUseCardEvent(PlayerUseCardEvent e) {
        game.playerUseCardEventHandler(e);
    }

    @Override
    public void handlePlayerCombineElementEvent(PlayerCombineElementEvent e) {
        game.playerCombineElementsEventHandler(e);
    }

    @Override
    public void handlePlayerEndTurnEvent(PlayerEndTurnEvent e) {
        game.playerEndTurnEventHandler(e);
    }

    public void handlePlayerStartTurnEvent() {
        try {
            game.beforePlayerTurnStart(this.getOpponent().getValue());
            game.beforePlayerTurnStart(this.getPlayer().getValue());
            game.playerTurnStart(this.getPlayer().getValue());
        } catch ( PlayerCanNotEnterTurnException e ) {
        }
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void changePlayer() {
        // NOTE: right now there are two players, so just switch between them
        gameRuntime.setNextPlayer(
                this.getNextPlayer().getId() == gameRuntime.getPlayer().getValue().getId()
                        ? this.getOpponent().getValue()
                        : this.getPlayer().getValue()
        );
    }
}
