package Game;

public abstract class Barrier {
    protected Position position;
    protected boolean isExist;
    protected boolean isDestroyable;
    protected boolean isThroughable;
    //protected Graphic graphic;
    //protected Item secretItem;

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setExist (boolean exist) { isExist = exist; }

    public void setDestroyable (boolean destroyable) {
        isDestroyable = destroyable;
    }

    public void setThroughable (boolean throughable) { isThroughable = throughable; }

}
