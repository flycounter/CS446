package cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class PlayerAddEvent extends PlayerEvent {
    private final Player player;
    public PlayerAddEvent(Player player) {
        super(EventCode.PLAYER_ADD, player.getId());
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
