package cs446_w2018_group3.supercardgame.util.events;

import cs446_w2018_group3.supercardgame.info.PlayerInfo;

import java.util.List;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class PlayerEvent extends GameEvent {
    public PlayerInfo playerInfo;
    public PlayerEvent ( PlayerInfo info ) {
        playerInfo = info;
    }
}
