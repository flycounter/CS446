package cs446_w2018_group3.supercardgame.util.events;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/3/4.
 */

public class GameEndEvent extends GameEvent {
    private Player winner;

    public GameEndEvent(Player winner) {
        super(EventCode.GAME_END);
        this.winner = winner;
    }

    public Player getWinner() { return winner; }
}
