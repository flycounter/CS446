package cs446_w2018_group3.supercardgame.model;

/**
 * Created by JarvieK on 2018/2/26.
 */

public abstract class BaseModel {
    protected int id;
    protected String label;

    public BaseModel() {}

    public BaseModel(int id) {
        // initialize model with data retrieved by id
        // e.g. assign label retrieved from locald by id
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
}
