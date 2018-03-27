package cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/3/4.
 */

public class GameEndEvent extends StateEvent {
    private final Player winner;
    public GameEndEvent(Player winner) {
        super(EventCode.GAME_END, winner.getId());
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
