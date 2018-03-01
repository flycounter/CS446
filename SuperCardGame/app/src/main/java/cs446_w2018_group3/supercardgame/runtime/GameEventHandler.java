package cs446_w2018_group3.supercardgame.runtime;


import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerActionException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.PlayerStateException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class GameEventHandler implements IGameEventHandler {
//    private final MutableLiveData<Player> player;
//    private final MutableLiveData<Player> opponent;
//    private final MutableLiveData<GameField> gameField;
//
//    private Player nextPlayer;
    private Game game;
    private GameController gameController;

    public GameEventHandler() { }

//    public LiveData<Player> getPlayer() {
//        return player;
//    }
//
//    public MutableLiveData<Player> getPlayer(int playerId) {
//        // TODO: refactor player and opponent into List of Players
//        if (playerId == player.getValue().getId()) {
//            return player;
//        }
//        else if (playerId == opponent.getValue().getId()) {
//            return opponent;
//        }
//        else {
//            return null;
//        }
//    }
//
//    public LiveData<Player> getOpponent() {
//        return opponent;
//    }
//
//    public LiveData<GameField> getGameField() {
//        return gameField;
//    }
//
//    public MutableLiveData<Player> getMutablePlayer() {
//        return player;
//    }
//
//    public MutableLiveData<Player> getMutablePlayer(int playerId) {
//        // TODO: refactor player and opponent into List of Players
//        if (playerId == player.getValue().getId()) {
//            return player;
//        }
//        else if (playerId == opponent.getValue().getId()) {
//            return opponent;
//        }
//        else {
//            return null;
//        }
//    }
//
//    public MutableLiveData<Player> getMutableOpponent() {
//        return opponent;
//    }
//
//    public MutableLiveData<GameField> getMutableField() {
//        return gameField;
//    }

    @Override
    public void handlePlayerUseCardEvent(PlayerUseCardEvent e) {
        try {
            gameController.checkPlayerEventState(e);

            Player subject = gameController.getPlayer(e.getSubjectId()).getValue();
            Player target = gameController.getPlayer(e.getTargetId()).getValue();
            if (subject == null || target == null) {
                throw new PlayerNotFoundException();
            }

            ElementCard card = (ElementCard) Game.getCardInHand(subject, e.getCardId());

            game.useCard(subject, target, card);
        }
        catch (PlayerStateException | PlayerActionException err) {
            // TODO: send err to UI
        }
    }

    @Override
    public void handlePlayerCombineElementEvent(PlayerCombineElementEvent e) {
        try {
            gameController.checkPlayerEventState(e);

            // get cards to be combined
            // NOTE: maybe replace with lambda map?
            Player player = gameController.getPlayer(e.getSubjectId()).getValue();
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
            // TODO: send err to UI
        }
    }

    @Override
    public void handlePlayerEndTurnEvent(PlayerEndTurnEvent e) {
        try {
            gameController.checkPlayerEventState(e);

            gameController.beforeTurnEnd();
            gameController.turnEnd();
            gameController.afterTurnEnd();
        }
        catch (InvalidStateException | PlayerActionException err) {
            // ...
        }
    }

//    public Player getNextPlayer() {
//        return nextPlayer;
//    }
//
//    public void setNextPlayer(Player nextPlayer) {
//        this.nextPlayer = nextPlayer;
//    }

//    public void changePlayer() {
//        // NOTE: right now there are two players, so just switch between them
//        setNextPlayer(
//                getNextPlayer().getId() == getPlayer().getValue().getId()
//                        ? this.getOpponent().getValue()
//                        : this.getPlayer().getValue()
//        );
//    }

    void bind(GameController gameController) {
        this.gameController = gameController;
        this.game = gameController.getGameModel();
    }
}
