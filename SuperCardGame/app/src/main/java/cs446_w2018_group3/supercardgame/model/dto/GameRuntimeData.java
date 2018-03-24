package cs446_w2018_group3.supercardgame.model.dto;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/3/23.
 */

// used by network connector to sync game
public class GameRuntimeData {
    private final List<Player> players;
    private final GameField gameField;

    public GameRuntimeData(List<Player> players, GameField gameField) {
        this.players = players;
        this.gameField = gameField;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameField getGameField() {
        return gameField;
    }
}
