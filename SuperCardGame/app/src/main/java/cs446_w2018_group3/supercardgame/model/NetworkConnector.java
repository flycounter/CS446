package cs446_w2018_group3.supercardgame.model;

import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.WebSocket;

import cs446_w2018_group3.supercardgame.Exception.NetworkException.UnknownMessageException;
import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.network.INetworkConnector;
import cs446_w2018_group3.supercardgame.model.network.PayloadInfo;
import cs446_w2018_group3.supercardgame.model.network.RemoteGameEventListener;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.network.Connection.IClient;
import cs446_w2018_group3.supercardgame.network.Connection.IHost;
import cs446_w2018_group3.supercardgame.network.Connection.IMessageConnector;
import cs446_w2018_group3.supercardgame.network.Connection.Sendable;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.LocalGameEventListener;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.ActionEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerUseCardEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.viewmodel.MultiGameViewModel;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class NetworkConnector implements INetworkConnector, IMessageConnector, LocalGameEventListener {
    private static final String TAG = NetworkConnector.class.getName();
    private final IClient mClient;
    private final IHost mHost;
    private final Sendable sendable;
    private final MultiGameViewModel mViewModel;
    private final RemoteGameEventListener mRemoteGameEventListener;

    private final Gson gson = new Gson();


    public NetworkConnector(IClient client,
                            RemoteGameEventListener remoteGameEventListener,
                            MultiGameViewModel viewModel) {
        mClient = client;
        mHost = null;
        sendable = mClient;
        mRemoteGameEventListener = remoteGameEventListener;
        mViewModel = viewModel;
        client.setMessageConnector(this);
    }

    public NetworkConnector(IHost host,
                            RemoteGameEventListener remoteGameEventListener,
                            MultiGameViewModel viewModel) {
        mHost = host;
        mClient = null;
        sendable = mHost;
        mRemoteGameEventListener = remoteGameEventListener;
        mViewModel = viewModel;
        mHost.setMessageConnector(this);
    }

    @Override
    public void onGameEvent(GameEvent e) {
        if (!mViewModel.isHost() && e instanceof ActionEvent) {
            // action event from client
            sendGameEvent(e);
        }
        else {
            // host
            if (e instanceof StateEvent) {
                sendGameEvent(e);
            }

            sendSyncData(mViewModel.getGameRuntime().getSyncData());
        }
    }

    @Override
    public void sendGameEvent(GameEvent e) {
        try {
            String payload;
            switch (e.getEventCode()) {
                // TODO: if GameEventHandler can handle GameEvent instead of the concrete events,
                // this code can be simpler and less error prone
                case PLAYER_COMBINE_ELEMENT:
                    payload = gson.toJson(e, PlayerCombineElementEvent.class);
                    break;
                case PLAYER_USE_CARD:
                    payload = gson.toJson(e, PlayerUseCardEvent.class);
                    break;
                case PLAYER_START_TURN:
                    if (!mViewModel.isHost()) {
                        return;
                    }
                    payload = gson.toJson(e, TurnStartEvent.class);
                    break;
                case PLAYER_END_TURN:
                    payload = gson.toJson(e, PlayerEndTurnEvent.class);
                    break;
                case PLAYER_ADD:
                    payload = gson.toJson(e, PlayerAddEvent.class);
                    break;
                case GAME_END:
                    if (!mViewModel.isHost()) {
                        return;
                    }
                    payload = gson.toJson(e, GameEndEvent.class);
                    break;
                default:
                    // unknown event
                    throw new UnknownMessageException();
            }
            sendable.sendMessage(gson.toJson(new PayloadInfo(PayloadInfo.Type.GAME_EVENT, e.getEventCode(), payload)));

        } catch (Exception err) {
            Log.i(TAG, "failed to handle message: ", err);
        }
    }

    @Override
    public void requestSyncData() {
        Log.i(TAG, "sending sync request to remote");
        sendable.sendMessage(gson.toJson(new PayloadInfo(PayloadInfo.Type.SYNC_DATA, null)));
        // TODO
    }

    @Override
    public void sendSyncData(GameRuntimeData gameRuntimeData) {
        Log.i(TAG, "sending sync data to remote");
        sendable.sendMessage(gson.toJson(new PayloadInfo(PayloadInfo.Type.SYNC_DATA, gson.toJson(gameRuntimeData))));
    }

    @Override
    public void sendSyncData(GameRuntimeData gameRuntimeData, WebSocket sender) {
        Log.i(TAG, "sending sync data to remote: " + sender.getRemoteSocketAddress().getAddress().getHostAddress());
        sendable.sendMessage(gson.toJson(new PayloadInfo(PayloadInfo.Type.SYNC_DATA, gson.toJson(gameRuntimeData))));
    }

    @Override
    public void sendPlayerData(Player player) {
        sendable.sendMessage(gson.toJson(new PayloadInfo(PayloadInfo.Type.PLAYER_DATA, gson.toJson(player))));
    }

    @Override
    public void sendFieldData(GameField gameField) {
        sendable.sendMessage(gson.toJson(new PayloadInfo(PayloadInfo.Type.FIELD_DATA, gson.toJson(gameField))));
    }

    private void sendACK(PayloadInfo.Type type) {
        sendable.sendMessage(gson.toJson(new PayloadInfo(type, null)));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        PayloadInfo payload = gson.fromJson(message, PayloadInfo.class);
        if (payload == null) return;
        PayloadInfo.Type payloadType = payload.getType();
        Log.i(TAG, String.format("valid message received: type: %s", payloadType));

        try {
            switch (payloadType) {
                case GAME_EVENT: {
                    GameEvent e;
                    switch (payload.getEventCode()) {
                        // TODO: if GameEventHandler can handle GameEvent instead of the concrete events,
                        // this code can be simpler and less error prone
                        case PLAYER_COMBINE_ELEMENT:
                            e = gson.fromJson(payload.getPayload(), PlayerCombineElementEvent.class);
                            break;
                        case PLAYER_USE_CARD:
                            e = gson.fromJson(payload.getPayload(), PlayerUseCardEvent.class);
                            break;
                        case PLAYER_START_TURN:
                            e = gson.fromJson(payload.getPayload(), TurnStartEvent.class);
                            break;
                        case PLAYER_END_TURN:
                            e = gson.fromJson(payload.getPayload(), PlayerEndTurnEvent.class);
                            break;
                        case PLAYER_ADD:
                            e = gson.fromJson(payload.getPayload(), PlayerAddEvent.class);
                            break;
                        case GAME_END:
                            e = gson.fromJson(payload.getPayload(), GameEndEvent.class);
                            break;
                        default:
                            // unknown event
                            throw new UnknownMessageException();
                    }
                    mRemoteGameEventListener.onGameEventReceived(e);
                }
                break;
                case SYNC_DATA:
                    if (mViewModel.isHost()) {
                        // message: sync data request
                        sendSyncData(mViewModel.getGameRuntime().getSyncData());
                    } else {
                        // message: sync data response
                        // NOTE: need to swap local/remote in the sync data
                        GameRuntimeData original = gson.fromJson(payload.getPayload(), GameRuntimeData.class);
                        Log.i(TAG, "" + original);
                        GameRuntimeData actual = new GameRuntimeData(
                                original.getOtherPlayer(),
                                original.getLocalPlayer(),
                                original.getCurrPlayer(),
                                original.getGameField(),
                                original.getGameState());
                        mRemoteGameEventListener.onSyncDataReceived(actual);
                        sendACK(PayloadInfo.Type.ACK_GAME_SYNC);
                    }
                    break;
                case FIELD_DATA:
                    mRemoteGameEventListener.onFieldDataReceived(gson.fromJson(payload.getPayload(), GameField.class));
                    break;
                case PLAYER_DATA:
                    mRemoteGameEventListener.onPlayerDataReceived(gson.fromJson(payload.getPayload(), Player.class));
                    sendACK(PayloadInfo.Type.ACK_ADD_PLAYER);
                    // NOTE: host should start the game and send sync message after game is inited (as state goes to TURN_START)
                    break;
                case ACK_ADD_PLAYER:
                    // ACK_ADD_PLAYER received from host, request data sync
//                    requestSyncData();
                    break;
                case ACK_GAME_SYNC:
                    // client confirms game sync
                    break;
                default:
                    // unknown event type
                    Log.w(TAG, "unknown message type: " + payloadType);
                    throw new UnknownMessageException();
            }
        } catch (Exception err) {
            Log.i(TAG, "failed to handle message: ", err);
        }
    }
}
