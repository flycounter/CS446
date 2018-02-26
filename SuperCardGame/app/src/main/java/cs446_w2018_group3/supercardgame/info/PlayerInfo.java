package cs446_w2018_group3.supercardgame.info;

import java.util.ArrayList;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;


/**
 * Created by yandong on 2018-02-25.
 */

public class PlayerInfo {
    public int playerId;
    public int HP;
    public int AP;
    public ArrayList<CardInfo> hands;
    public ArrayList<BuffInfo> buffs;

    public PlayerInfo (int playerId, int HP, int AP, ArrayList<CardInfo> hands, ArrayList<BuffInfo> buffs){
        this.playerId = playerId;
        this.HP = HP;
        this.AP = AP;
        this.hands = hands;
        this.buffs = buffs;
    }
}
