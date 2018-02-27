package cs446_w2018_group3.supercardgame.util.events;

import cs446_w2018_group3.supercardgame.info.CardInfo;
import cs446_w2018_group3.supercardgame.info.PlayerInfo;

/**
 * Created by Yakumo on 2/26/2018.
 */

public class PlayerUseCardEvent extends PlayerEvent {
    public CardInfo cardInfo;
    public PlayerInfo objectInfo;
    public PlayerUseCardEvent (PlayerInfo playerInfo, PlayerInfo objectInfo, CardInfo cardInfo ) {
        super(playerInfo);
        this.objectInfo = objectInfo;
        this.cardInfo = cardInfo;
    }
}
