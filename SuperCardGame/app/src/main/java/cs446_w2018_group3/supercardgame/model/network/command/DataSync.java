package cs446_w2018_group3.supercardgame.model.network.command;

import cs446_w2018_group3.supercardgame.runtime.GameRuntime;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class DataSync implements ICommand {
    private final GameRuntime gameRuntime;

    public DataSync(GameRuntime gameRuntime) {
        this.gameRuntime = gameRuntime;
    }

    @Override
    public void execute() {

    }
}
