package cs446_w2018_group3.supercardgame.util.events.payload;

import java.util.ArrayList;


/**
 * Created by yandong on 2018-02-25.
 */

public class PlayerInfo {
    public String name;
    public int playerId;
    public int HP;
    public int AP;
    public ArrayList<CardInfo> hands;
    public ArrayList<BuffPayload> buffs;

    public PlayerInfo (String name, int playerId, int HP, int AP, ArrayList<CardInfo> hands, ArrayList<BuffPayload> buffs){
        this.name = name;
        this.playerId = playerId;
        this.HP = HP;
        this.AP = AP;
        this.hands = hands;
        this.buffs = buffs;
    }
}
