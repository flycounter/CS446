package cs446_w2018_group3.supercardgame.util.events;

import cs446_w2018_group3.supercardgame.info.CardInfo;
import cs446_w2018_group3.supercardgame.info.PlayerInfo;

/**
 * Created by Yakumo on 2/26/2018.
 */

public class PlayerCombineElementEvent extends PlayerEvent {
    public CardInfo card1Info, card2Info;
    public PlayerCombineElementEvent (PlayerInfo info, CardInfo card1Info, CardInfo card2Info ) {
        super(info);
        this.card1Info = card1Info;
        this.card2Info = card2Info;
    }
}
