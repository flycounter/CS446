package cs446_w2018_group3.supercardgame.model.cards.element;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by JarvieK on 2018/3/27.
 */

public class ElementCardDeserializer implements JsonDeserializer<ElementCard> {
    @Override
    public ElementCard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Translate.CardType cardType = Translate.stringToCard(jsonObject.get("cardType").getAsString());
        int id = jsonObject.get("id").getAsInt();
        int level = jsonObject.get("level").getAsInt(); // TODO: set card level?
        int damage = jsonObject.get("damage").getAsInt(); // TODO: set card damage?

        switch (cardType) {
            case Air:
                return new AirCard(id);
            case Aqua:
                return new AquaCard(id);
            case Blast:
                return new BlastCard(id);
            case Dirt:
                return new DirtCard(id);
            case Fire:
                return new FireCard(id);
            case Flame:
                return new FlameCard(id);
            case Gale:
                return new GaleCard(id);
            case Ice:
                return new IceCard(id);
            case Lava:
                return new LavaCard(id);
            case Mud:
                return new MudCard(id);
            case Rock:
                return new RockCard(id);
            case Sand:
                return new SandCard(id);
            case Steam:
                return new SteamCard(id);
            case Water:
                return new WaterCard(id);
            default:
                throw new JsonParseException("unsupported CardType: " + cardType);
                // nothing
        }
    }
}
