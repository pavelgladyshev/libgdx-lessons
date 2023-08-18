package ui.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import model.Board;
import ui.UI;

import java.io.PrintStream;

public class TicTacToeGame extends Game {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int NUMBER_OF_GAMEPIECES = 5;

    Thread gameControlThread;
    PrintStream uiStream;

    OrthographicCamera camera;
    Stage stage;
    Skin skin;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer renderer;
    Texture splashscreenTexture;
    SpriteBatch batch;
    PlayScreenInputProcessor playScreenInputProcessor;

    StartScreen startScreen;
    PlayScreen playScreen;

    public TicTacToeGame(Thread gameControlThread, UI ui) {
        GUI gui = (GUI)ui;
        this.gameControlThread = gameControlThread;

        // establishing communication between GameControl thread and HelloGame (libGDX thread)
        // control -> game
        gui.setGame(this); // for posting runnables into libGDX game loop
        // game -> control
        this.uiStream = new PrintStream(gui.getPipedOutputStream()) ;  // for sending text to control
    }

    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("tic-tac-toe.json"));

        splashscreenTexture = new Texture(Gdx.files.internal("splashscreen.png"));

        tiledMap = new TmxMapLoader().load("playscreen.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        renderer.setView(camera);

        startScreen = new StartScreen(this);
        playScreen = new PlayScreen(this);
        playScreenInputProcessor = new PlayScreenInputProcessor(this);
        activateStartScreen();
    }

    public void activateStartScreen() {
        setScreen(startScreen);
    }

    public void activatePlayScreen() {
        setScreen(playScreen);
    }

    public void postRunnable(Runnable r) {
        Gdx.app.postRunnable(r);
    }

    void showMessage(String text) {
        playScreen.showMessage(text);
    }

    public void updateBoard(Board board) {
        playScreen.graphicalBoard.updateBoard(board);
    }

    public void setActivePlayerNo(int playerNo) {
        playScreenInputProcessor.setActivePlayerNo(playerNo);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (startScreen != null) startScreen.dispose();
        if (playScreen != null) playScreen.dispose();
        if (skin != null) skin.dispose();
        if (stage != null) stage.dispose();
        if (tiledMap != null) tiledMap.dispose();
        if (batch != null) batch.dispose();
        if (splashscreenTexture != null) splashscreenTexture.dispose();
        if (gameControlThread != null) gameControlThread.stop();
    }


}
