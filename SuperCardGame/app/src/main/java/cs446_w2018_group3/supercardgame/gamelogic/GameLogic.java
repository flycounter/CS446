package cs446_w2018_group3.supercardgame.gamelogic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import cs446_w2018_group3.supercardgame.info.GameInfo;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.util.events.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerUseCardEvent;

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

    public void playerStartTurnEvent( PlayerEvent playerEvent ) {
        game.startPlayerTurn( playerEvent.playerInfo );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public void playerUseCardEvent( PlayerUseCardEvent playerUseCardEvent ) {
        game.playerUseCard( playerUseCardEvent.playerInfo, playerUseCardEvent.objectInfo, playerUseCardEvent.cardInfo );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public void playerCombineElementEvent( PlayerCombineElementEvent playerCombineElementEvent ) {
        game.combineElement( playerCombineElementEvent.playerInfo, playerCombineElementEvent.card1Info, playerCombineElementEvent.card2Info );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public void playerEndTurnEvent( PlayerEvent playerEvent ) {
        game.endTurn( playerEvent.playerInfo );
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
    }

    public MutableLiveData<GameInfo> startNewGame() {
        game = new Game();
        gameInfoMutableLiveData.setValue( game.getGameInfo() );
        return gameInfoMutableLiveData;
    }
}
