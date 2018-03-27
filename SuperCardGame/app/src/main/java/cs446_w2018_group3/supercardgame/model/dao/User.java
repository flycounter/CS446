package cs446_w2018_group3.supercardgame.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JarvieK on 2018/3/27.
 */

@Entity
public class User {
    @org.greenrobot.greendao.annotation.Id
    private long Id;

    private String playerData;

    @Generated(hash = 1315291914)
    public User(long Id, String playerData) {
        this.Id = Id;
        this.playerData = playerData;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public static void replaceUser(UserDao dao, User user) {
        dao.deleteAll();
        dao.insert(user);
    }

    public static User getLocalUser(UserDao dao) {
        return dao.queryBuilder().list().get(0);
    }

    public long getId() {
        return this.Id;
    }

    public String getPlayerData() {
        return this.playerData;
    }

    public void setPlayerData(String playerData) {
        this.playerData = playerData;
    }

    public void setId(long Id) {
        this.Id = Id;
    }
}
