package ui;

import javafx.scene.paint.Color;

public interface Lockable {

    public void lock();
    public void unlock();
    public void lockAll();
    public void unlockAll();
    public boolean isLocked();
    public void lockEnemyTiles(Color x);
    public void unlockFriendlyTiles(Color x);
    
}
