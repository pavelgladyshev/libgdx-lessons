package ui.graphical;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import model.Board;

public class PlayScreen extends ScreenAdapter {

    TicTacToeGame ticTacToeGame;
    Stage stage;
    Skin skin;
    OrthogonalTiledMapRenderer renderer;
    SpriteBatch batch;
    Array<GraphicalGamepiece> pieces;
    GraphicalBoard graphicalBoard;

    String message;
    BitmapFont messageFont;
    float messageX;
    float messageY;
    float messageWidth;

    public PlayScreen(TicTacToeGame ticTacToeGame) {
        this.ticTacToeGame = ticTacToeGame;
        this.stage = ticTacToeGame.stage;
        this.skin = ticTacToeGame.skin;
        this.batch = ticTacToeGame.batch;
        this.renderer = ticTacToeGame.renderer;

        MapLayer layer = ticTacToeGame.tiledMap.getLayers().get("Object Layer");
        MapObjects objects = layer.getObjects();

        pieces = new Array<GraphicalGamepiece>();
        TextureRegion xPicture = skin.getRegion("X");
        TextureRegion oPicture = skin.getRegion("O");
        addPlayerGamepieces(pieces,objects,xPicture,Board.X);
        addPlayerGamepieces(pieces,objects,oPicture,Board.O);

        MapObject boardLocation = objects.get("Board");
        float boardX = (float) boardLocation.getProperties().get("x");
        float boardY = (float) boardLocation.getProperties().get("y");
        float boardHeight = (float) boardLocation.getProperties().get("height");
        float boardWidth = (float) boardLocation.getProperties().get("width");
        graphicalBoard = new GraphicalBoard(boardX,boardY,boardWidth,boardHeight,xPicture,oPicture,new Board());

        MapObject messageLocation = objects.get("Message");
        messageX = (float) messageLocation.getProperties().get("x");
        messageY = (float) messageLocation.getProperties().get("y");
        messageWidth = (float) messageLocation.getProperties().get("width");
        messageFont = skin.getFont("sub-title");

    }

    private void addPlayerGamepieces(
            Array<GraphicalGamepiece> pieces,
            MapObjects objects,
            TextureRegion picture,
            int playerNo
    ) {
        for (int i=0; i<TicTacToeGame.NUMBER_OF_GAMEPIECES; i++) {
            String pieceName = String.valueOf(playerNo)+String.valueOf(i);
            MapObject object = objects.get(pieceName);
            float x = (float) object.getProperties().get("x");
            float y = (float) object.getProperties().get("y");
            pieces.add(new GraphicalGamepiece(x,y,picture,playerNo));
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ticTacToeGame.playScreenInputProcessor);
    }

    public void showMessage(String text) {
        message = text;
    }

    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        renderer.render();
        batch.begin();
        for (GraphicalGamepiece p : pieces) {
            p.draw(batch);
        }
        graphicalBoard.draw(batch);
        messageFont.draw(batch,message,messageX,messageY,messageWidth, Align.center,true);
        batch.end();
    }

}
