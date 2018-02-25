package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.gamelogic.Effect;
import cs446_w2018_group3.supercardgame.model.IModel;
import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by JarvieK on 2018/2/23.
 */
//  Card Id Reference:
//  0: Water
//  1: Fire
//  2: Air
//  3: Dirt
//  4: Aqua
//  5: Steam
//  6: Ice
//  7: Mud
//  8: Flame
//  9: Blast
//  10: Lava
//  11: Gale
//  12: Sand
//  13: Rock

public abstract class Card implements IModel {
    int id;
    String label;

    public abstract void apply();
    public int getId() {
        return this.id;
    }
    public String getLabel() {
        return this.label;
    }
}


