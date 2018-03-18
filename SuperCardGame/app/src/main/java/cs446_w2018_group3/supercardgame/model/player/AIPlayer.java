package cs446_w2018_group3.supercardgame.model.player;

import cs446_w2018_group3.supercardgame.runtime.GameEventHandler;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class AIPlayer extends Player {
    private GameEventHandler gameEventHandler;

    public AIPlayer(int id, String name) {
        super(id, name);
    }

    public void bind(GameEventHandler gameEventHandler) {
        this.gameEventHandler = gameEventHandler;
    }

    public void onTurnStart() {
        // TODO:
        // NOTE: for now do nothing
        gameEventHandler.handlePlayerEndTurnEvent(new PlayerEndTurnEvent(getId()));
    }
}
