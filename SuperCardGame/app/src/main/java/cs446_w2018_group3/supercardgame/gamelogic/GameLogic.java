package cs446_w2018_group3.supercardgame.gamelogic;

import android.arch.lifecycle.MutableLiveData;

import cs446_w2018_group3.supercardgame.util.events.payload.GameInfo;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.BasePlayerEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class GameLogic {
    private Game game;
    private static GameLogic gameLogic;

    static {
        gameLogic = new GameLogic();
    }

    private final MutableLiveData<GameInfo> gameInfoMutableLiveData;

    private GameLogic() {
        gameInfoMutableLiveData = new MutableLiveData<GameInfo>();
    }

    public GameLogic getInstance() {
        return gameLogic;
    }

    public void playerStartTurnEvent( BasePlayerEvent basePlayerEvent) {
        game.startPlayerTurn( basePlayerEvent.playerInfo );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public void playerUseCardEvent( PlayerUseCardEvent playerUseCardEvent ) {
        game.playerUseCardEventHandler( playerUseCardEvent.playerInfo, playerUseCardEvent.objectInfo, playerUseCardEvent.cardInfo );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public void playerCombineElementEvent( PlayerCombineElementEvent playerCombineElementEvent ) {
        game.playerCombineElementsEventHandler( playerCombineElementEvent.playerInfo, playerCombineElementEvent.card1Info, playerCombineElementEvent.card2Info );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public void playerEndTurnEvent( BasePlayerEvent basePlayerEvent) {
        game.PlayerEndTurnEventHandler( basePlayerEvent.playerInfo );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public MutableLiveData<GameInfo> startNewGame() {
        game = new Game();
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
        return gameInfoMutableLiveData;
    }
}
