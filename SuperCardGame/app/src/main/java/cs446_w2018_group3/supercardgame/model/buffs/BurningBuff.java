package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class BurningBuff extends Buff {
    private static final Translate.BuffType TYPE = Translate.BuffType.Burn;
    private static final int DMG = 2;

    public BurningBuff(int turns) {
        super(TYPE, turns);
    }

    public BurningBuff(int id, Translate.BuffType type, int remainingTurns) {
        super(id, type, remainingTurns);
    }

    @Override
    public void applyBuff(Player player) {
        super.applyBuff(player);
        Effect.dealDamageEffect(player, player, DMG);
    }
}
