package cs446_w2018_group3.supercardgame.model.bot;

import android.util.Log;

import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.AbsStateEventAdapter;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.IGameEventHandler;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/3/3.
 */

public class Bot implements IBot {
    private Player botPlayer;
    private IGameEventHandler mGameEventHandler;
    private StateEventListener mStateEventListener;

    public Bot(Player player) {
        botPlayer = player;
        setStateEventListener(new StateEventAdapter());
    }

    public void bind(IGameEventHandler gameEventHandler) {
        mGameEventHandler = gameEventHandler;
        mGameEventHandler.addStateEventListener(mStateEventListener);
    }

    @Override
    public void setStateEventListener(StateEventListener stateEventListener) {
        mStateEventListener = stateEventListener;
    }

    private class StateEventAdapter extends AbsStateEventAdapter {
        @Override
        public void onTurnStart(TurnStartEvent e) {
            Log.i("main",
                    String.format("TurnStartEvent, playerId: %s, receiver: %s", e.getSubjectId(), botPlayer.getId()));
            if (e.getSubjectId() == botPlayer.getId()) {
                // TODO: bot's turn
                // for now just end the turn

//                // wait for 2 sec
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException err) {}

                mGameEventHandler.handlePlayerEndTurnEvent(new PlayerEndTurnEvent(botPlayer.getId()));
            } else {
                // player's turn
            }
        }

        @Override
        public void onGameEnd(GameEndEvent e) {
            // nothing
        }
    }
}
