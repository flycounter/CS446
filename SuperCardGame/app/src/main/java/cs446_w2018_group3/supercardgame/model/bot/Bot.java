package cs446_w2018_group3.supercardgame.model.bot;

import android.util.Log;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.GameEventHandler;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.stateevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/3/3.
 */

public class Bot implements IBot {
    private Player botPlayer;
    private GameEventHandler gameEventHandler;

    public Bot(Player player) {
        botPlayer = player;
    }

    public void bind(GameEventHandler gameEventHandler) {
        this.gameEventHandler = gameEventHandler;
    }

    @Override
    public void onTurnStart(TurnStartEvent e) {
        Log.i("main",
                String.format("TurnStartEvent, playerId: %s, receiver: %s", e.getSubjectId(), botPlayer.getId()));
        if (e.getSubjectId() == botPlayer.getId()) {
            // TODO: bot's turn
            // for now just end the turn

//            // wait for 2 sec
//            try {
//                Thread.sleep(2000);
//            }
//            catch (InterruptedException err) {}

            gameEventHandler.handlePlayerEndTurnEvent(
                    new PlayerEndTurnEvent(botPlayer.getId()));
        }
        else {
            // player's turn
        }
    }
}
