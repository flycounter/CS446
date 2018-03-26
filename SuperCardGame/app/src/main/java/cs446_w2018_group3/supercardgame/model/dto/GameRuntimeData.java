package cs446_w2018_group3.supercardgame.model.dto;

import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.GameFSM;

/**
 * Created by JarvieK on 2018/3/23.
 */

// used by network connector to sync game
public class GameRuntimeData {
    private final Player localPlayer;
    private final Player otherPlayer;
    private final Player currPlayer;
    private final GameField gameField;
    private final GameFSM.State gameState;

    public GameRuntimeData(Player localPlayer, Player otherPlayer, Player currPlayer, GameField gameField, GameFSM.State gameState) {
        this.localPlayer = localPlayer;
        this.otherPlayer = otherPlayer;
        this.currPlayer = currPlayer;
        this.gameField = gameField;
        this.gameState = gameState;
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public GameField getGameField() {
        return gameField;
    }

    public GameFSM.State getGameState() {
        return gameState;
    }
}
