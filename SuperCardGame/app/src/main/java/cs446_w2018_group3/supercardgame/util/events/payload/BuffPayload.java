package cs446_w2018_group3.supercardgame.util.events.payload;

import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class BuffPayload {
    public int buffId;
    public Translate.BuffType buffType;

    public BuffPayload(int buffId, Translate.BuffType buffType) {
        this.buffId = buffId;
        this.buffType = buffType;
    }
 }
