package cs446_w2018_group3.supercardgame.runtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

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

    private static GameRuntime gameRuntime;

    public GameRuntime() {
        player = new MutableLiveData<>();
        opponent = new MutableLiveData<>();
        gameField = new MutableLiveData<>();
    }

    private void init(Game game) {
        gameRuntime = new GameRuntime();
        Player playerData = new Player(1, "you");
        for (int i = 0; i < 3; i++) {
            playerData.addCardToHand(new WaterCard());
        }

        Player opponentData = new Player(2, "opponent");
        for (int i = 0; i < 3; i++) {
            playerData.addCardToHand(new FireCard());
        }

        player.setValue(playerData);
        opponent.setValue(opponentData);

        gameField.setValue(new GameField());

        // NOTE: for demo purpose the game always starts with player goes first
        setNextPlayer(this.getPlayer().getValue());

        game.bindRuntime(this);
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
