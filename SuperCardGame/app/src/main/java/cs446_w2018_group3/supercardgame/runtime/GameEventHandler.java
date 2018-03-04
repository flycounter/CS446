package cs446_w2018_group3.supercardgame.runtime;


import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerActionException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.PlayerStateException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.util.events.StateEventAdapter;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class GameEventHandler implements IGameEventHandler {
    private Game game;
    private GameRuntime gameRuntime;

    // event listener
    private List<StateEventAdapter> stateEventAdapters;

    public GameEventHandler() {
        stateEventAdapters = new ArrayList<>();
    }

    @Override
    public void handlePlayerUseCardEvent(PlayerUseCardEvent e) {
        try {
            gameRuntime.checkPlayerEventState(e);

            Player subject = gameRuntime.getPlayer(e.getSubjectId()).getValue();
            Player target = gameRuntime.getPlayer(e.getTargetId()).getValue();
            if (subject == null || target == null) {
                throw new PlayerNotFoundException();
            }

            ElementCard card = (ElementCard) Game.getCardInHand(subject, e.getCardId());

            game.useCard(subject, target, card);
        }
        catch (PlayerStateException | PlayerActionException err) {
            Log.w("main", err);
            // TODO: send err to UI
        }
    }

    @Override
    public void handlePlayerCombineElementEvent(PlayerCombineElementEvent e) {
        try {
            gameRuntime.checkPlayerEventState(e);

            // get cards to be combined
            // NOTE: maybe replace with lambda map?
            Player player = gameRuntime.getPlayer(e.getSubjectId()).getValue();
            if (player == null) {
                throw new PlayerNotFoundException();
            }

            List<ElementCard> cards = new ArrayList<>();
            for (int cardId: e.getCardIds()) {
                cards.add((ElementCard) Game.getCardInHand(player, cardId));
            }

            game.playerCombineElementsEventHandler(player, cards);
        }
        catch (PlayerStateException | PlayerActionException err) {
            Log.w("main", err);
            // TODO: send err to UI
        }
    }

    @Override
    public void handlePlayerEndTurnEvent(PlayerEndTurnEvent _e) {
        try {
            gameRuntime.checkPlayerEventState(_e);
            gameRuntime.turnEnd();
            gameRuntime.turnStart(); // starts next player's turn
            TurnStartEvent e = new TurnStartEvent(gameRuntime.getCurrPlayer().getValue().getId());
            Log.i("main", "notifying turn start event");
            for (StateEventAdapter adapter: stateEventAdapters) {
                adapter.onTurnStart(e);
            }
        }
        catch (InvalidStateException | PlayerActionException err) {
            if (err instanceof PlayerCanNotEnterTurnException) {
                // TODO: add method gameRuntime.getWinner()
                Player winner = null;
                for (LiveData<Player> playerHolder: gameRuntime.getPlayers()) {
                    if (playerHolder.getValue().getHP() > 0) {
                        winner = playerHolder.getValue();
                    }
                }
                for (StateEventAdapter adapter: stateEventAdapters) {
                    adapter.onGameEnd(winner);
                }
                // game end
                return;
            }
            Log.w("main", err);
            // TODO: send err to UI
        }
    }

    void bind(GameRuntime gameRuntime) {
        this.gameRuntime = gameRuntime;
        this.game = gameRuntime.getGameModel();
    }

    @Override
    public void addStateEventListener(StateEventAdapter adapter) {
        stateEventAdapters.add(adapter);
        Log.i("main", "state event listener added");
    }
}
