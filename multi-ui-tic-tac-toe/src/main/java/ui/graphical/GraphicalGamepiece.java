package ui.graphical;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class GraphicalGamepiece {
    float defaultX;
    float defaultY;
    float currentX;
    float currentY;
    TextureRegion picture;
    int playerNo;
    boolean visible;

    public GraphicalGamepiece(float defaultX, float defaultY, TextureRegion picture, int playerNo) {
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.picture = picture;
        this.playerNo = playerNo;
        resetLocation();
        setVisible(true);
    }

    public void resetLocation() {
        currentX = defaultX;
        currentY = defaultY;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void draw(SpriteBatch batch) {
        if (visible) {
            batch.draw(picture, currentX, currentY);
        }
    }

    public void setLocation(float newX, float newY) {
        currentX = newX;
        currentY = newY;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public boolean isHit(float x, float y) {
        return  (currentX < x) &&
                (currentX+picture.getRegionWidth() > x) &&
                (currentY < y) &&
                (currentY+picture.getRegionHeight() > y) &&
                visible;
    }
}
