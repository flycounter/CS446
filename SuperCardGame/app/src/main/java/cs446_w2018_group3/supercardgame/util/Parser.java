package cs446_w2018_group3.supercardgame.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.BuffAdapter;
import cs446_w2018_group3.supercardgame.model.cards.element.ElementCard;
import cs446_w2018_group3.supercardgame.model.cards.element.ElementCardDeserializer;

/**
 * Created by JarvieK on 2018/3/27.
 */

public class Parser {
    private static Parser instance;

    private final Gson gson;

    private Parser() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Buff.class, new BuffAdapter())
                .registerTypeAdapter(ElementCard.class, new ElementCardDeserializer())
                .create();
    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }

        return instance;
    }

    public Gson getParser() {
        return gson;
    }
}
