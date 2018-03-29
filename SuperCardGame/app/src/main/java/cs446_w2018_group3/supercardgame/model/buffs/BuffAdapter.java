package cs446_w2018_group3.supercardgame.model.buffs;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by JarvieK on 2018/3/27.
 */

public class BuffAdapter extends TypeAdapter<Buff> {
    private final static String DODGE_STRING = getTypeString(Translate.BuffType.Dodge);
    private final static String BURN_STRING = getTypeString(Translate.BuffType.Burn);
    private final static String WET_STRING = getTypeString(Translate.BuffType.Wet);
    private final static String FREEZE_STRING = getTypeString(Translate.BuffType.Freeze);

    @Override
    public void write(JsonWriter out, Buff value) throws IOException {
        out.beginObject();
        out.name("id").value(value.getId());
        out.name("buffType").value(getTypeString(value.getBuffType()));
        out.name("remainingTurns").value(value.getRemainingTurns());
        out.endObject();
    }

    @Override
    public Buff read(JsonReader in) throws IOException {
        int id = -1;
        String buffTypeString = null;
        int remainingTurns = -1;

        Translate.BuffType type;

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "id":
                    id = in.nextInt();
                    break;
                case "buffType":
                    buffTypeString = in.nextString();
                    break;
                case "remainingTurns":
                    remainingTurns = in.nextInt();
                    break;
                default:
                    throw new IOException("cannot parse input");
            }
        }

        in.endObject();

        if (buffTypeString == null) {
            return null;
        }

        type = getBuffType(buffTypeString);

        if (buffTypeString.equals(DODGE_STRING)) {
            return new DodgeBuff(id, type, remainingTurns);
        } else if (buffTypeString.equals(BURN_STRING)) {
            return new BurningBuff(id, type, remainingTurns);
        } else if (buffTypeString.equals(WET_STRING)) {
            return new WetBuff(id, type, remainingTurns);
        } else if (buffTypeString.equals(FREEZE_STRING)) {
            return new FreezeBuff(id, type, remainingTurns);
        }

        return null;
    }

    private static String getTypeString(Translate.BuffType buffType) {
        if (buffType == Translate.BuffType.Dodge) {
            return "Dodge";
        } else if (buffType == Translate.BuffType.Burn) {
            return "Burn";
        } else if (buffType == Translate.BuffType.Wet) {
            return "Wet";
        } else if (buffType == Translate.BuffType.Freeze) {
            return "Freeze";
        } else {
            return null;
        }
    }

    private static Translate.BuffType getBuffType(String typeString) throws IOException {
        if (typeString.equals(DODGE_STRING)) {
            return Translate.BuffType.Dodge;
        } else if (typeString.equals(BURN_STRING)) {
            return Translate.BuffType.Burn;
        } else if (typeString.equals(WET_STRING)) {
            return Translate.BuffType.Wet;
        } else if (typeString.equals(FREEZE_STRING)) {
            return Translate.BuffType.Freeze;
        } else {
            throw new IOException("invalid typeString: " + typeString);
        }
    }
}
