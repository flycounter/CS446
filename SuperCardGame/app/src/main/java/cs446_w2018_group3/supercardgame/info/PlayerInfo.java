package cs446_w2018_group3.supercardgame.info;

/**
 * Created by yandong on 2018-02-25.
 */

public class PlayerInfo {
    public int playerId;
    public int HP;
    public int AP;
    public ArrayList<CardInfo> hand;
    public ArrayList<BuffInfo> buffs;

    public PlayerInfo (int playerId, int HP, int AP){}
}
