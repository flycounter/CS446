package cs446_w2018_group3.supercardgame.model.bot;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exception.BotException.BotNotEnoughBaseCardInHandException;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.model.cards.SandCard;
import cs446_w2018_group3.supercardgame.model.cards.SteamCard;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerUseCardEvent;
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
    private Player botPlayer,localPlayer;
    private IGameEventHandler mGameEventHandler;
    private StateEventListener mStateEventListener;
    /* Bot decision variables */
    private int hybridCardsCount;
    private enum TurnStrategy {
        COMBINE, OFFENCE, DEFENCE, IDLE }


    public Bot(Player player) {
        botPlayer = player;
        setStateEventListener(new StateEventAdapter());

        List<ElementCard> deck = new ArrayList<>();

        for( int i = 0; i < 6; i++ ) {
            deck.add( ElementCard.createNewCard( Translate.CardType.Water ) );
            deck.add( ElementCard.createNewCard( Translate.CardType.Water ) );
            deck.add( ElementCard.createNewCard( Translate.CardType.Water ) );
            deck.add( ElementCard.createNewCard( Translate.CardType.Fire ) );
            deck.add( ElementCard.createNewCard( Translate.CardType.Air ) );
            deck.add( ElementCard.createNewCard( Translate.CardType.Dirt ) );
        }

        player.setDeck(deck);
    }

    public void bind(IGameEventHandler gameEventHandler) {
        mGameEventHandler = gameEventHandler;
        mGameEventHandler.addStateEventListener(mStateEventListener);
    }

    public void bindLocalPlayer(Player player) {
        localPlayer = player;
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
                delay(500);
                botAction();
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
    private void delay(int millis) {
        // wait for millis ms
        try {
            Thread.sleep(millis);
        } catch (InterruptedException err) {
            Log.i(Bot.class.getName(),
                    "Interrupted during psudo waiting");
        }
    }
    /* Bot decision static methods */
    private void botAction() {
        processHand(botPlayer.getHand());
        TurnStrategy strategy = makeStrategyDecision();
        Log.i(Bot.class.getName(),
                "Chosen Strategy is: " + strategy);
        makeMoves(strategy);
    }
    private void makeMoves (TurnStrategy strategy) {
        switch (strategy) {
            case COMBINE:
                // Given no hybrid card in hand
                for (int i = 0; botPlayer.getHand().size() - hybridCardsCount >= 2
                        && botPlayer.getAP() > 2
                        && i < 2; i += 1) {
                    try {
                        Log.i(Bot.class.getName(),
                                "Combining");
                        combineBaseCardInHand();
                    } catch (BotNotEnoughBaseCardInHandException e) {
                        break;
                    }
                }
            case DEFENCE:
                useDefenciveCards();
            case OFFENCE:
                useHybridCards();
                useAnyCards();
            case IDLE:
                break;
        }
        mGameEventHandler.handlePlayerEndTurnEvent(new PlayerEndTurnEvent(botPlayer.getId()));
    }
    private void useDefenciveCards() {
        int AP = botPlayer.getAP();
        for (int i = 0; i < AP; i += 1) {
            for ( ElementCard c:botPlayer.getHand() ) {
                if (c.getClass() == SandCard.class
                        ||  c.getClass() == SteamCard.class) {
                    Log.i(Bot.class.getName(),
                            "Using Defencive Card " + c.getLabel());
                    mGameEventHandler.handlePlayerUseCardEvent(new PlayerUseCardEvent(botPlayer.getId(),localPlayer.getId(), c.getCardId()));
                    break;
                }
            }
        }
    }
    private void useHybridCards() {
        int AP = botPlayer.getAP();
        for (int i = 0; i < AP; i += 1) {
            for ( ElementCard c:botPlayer.getHand() ) {
                if (c.getLevel() >= 2) {
                    Log.i(Bot.class.getName(),
                            "Using Hybrid Card " + c.getLabel());
                    mGameEventHandler.handlePlayerUseCardEvent(new PlayerUseCardEvent(botPlayer.getId(),localPlayer.getId(), c.getCardId()));
                    break;
                }
            }
        }
    }
    private void useAnyCards() {
        int AP = botPlayer.getAP();
        for (int i = 0; i < AP; i += 1) {
            if(!botPlayer.getHand().isEmpty()) {
                Log.i(Bot.class.getName(),
                        "Using Any Card " + botPlayer.getHand().get(0).getLabel());
                mGameEventHandler.handlePlayerUseCardEvent(new PlayerUseCardEvent(botPlayer.getId(),localPlayer.getId(), botPlayer.getHand().get(0).getCardId()));
            }
        }
    }
    private void combineBaseCardInHand() throws BotNotEnoughBaseCardInHandException {
        List<Integer> cardIds = new ArrayList<>();
        for (ElementCard c:botPlayer.getHand()){
            if (c.getLevel() == 1) {
                cardIds.add(c.getCardId());
                if (cardIds.size() == 2) {
                    break;
                }
            }
        }
        if (cardIds.size()!=2) throw new BotNotEnoughBaseCardInHandException();
        mGameEventHandler.handlePlayerCombineElementEvent(new PlayerCombineElementEvent(botPlayer.getId(), cardIds));
    }
    /* Bot always tries to use hybrid cards */
    private TurnStrategy makeStrategyDecision() {
        Log.i(Bot.class.getName(),
                "hybridCardsCount: " + hybridCardsCount + " AP: " + botPlayer.getAP());
        if (botPlayer.getAP() <= 0 ) {
            return TurnStrategy.IDLE;
        } else if (hybridCardsCount <= botPlayer.getAP()) {
            return TurnStrategy.COMBINE;
        } else if ( botPlayer.getHP() > 5 ) {
            return TurnStrategy.OFFENCE;
        } else {
            return TurnStrategy.DEFENCE;
        }
    }
    private void processHand(List<ElementCard> hand) {
        hybridCardsCount = 0;
        for (ElementCard c:hand) {
            if (c.getLevel() > 1) {
                hybridCardsCount += 1;
            }
        }
    }

}
