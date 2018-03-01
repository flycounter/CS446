package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class BuffInfo {
    static int id = 0;
    int buffId;
    Translate.BuffType buffType;

    public BuffInfo(Translate.BuffType buffType) {
        this.buffId = id;
        this.buffType = buffType;
        id++;
    }

    public int getBuffId() {
        return buffId;
    }

    public Translate.BuffType getLabel() {
        return buffType;
    }
 }
