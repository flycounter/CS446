package cs446_w2018_group3.supercardgame.model.network;

import org.java_websocket.WebSocket;

import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface INetworkConnector {
    void sendGameEvent(GameEvent e);
    void sendSyncData(GameRuntimeData gameRuntimeData);
    void sendSyncData(GameRuntimeData gameRuntimeData, WebSocket sender);
    void requestSyncData();
    void sendPlayerData(Player player);
    void sendFieldData(GameField gameField);
}
