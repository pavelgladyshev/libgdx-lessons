package ui.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen extends ScreenAdapter {

    TicTacToeGame ticTacToeGame;

    Stage stage;
    Skin skin;
    Table table;
    Texture woodenGameTexture;
    SpriteBatch batch;

    public StartScreen(TicTacToeGame ticTacToeGame) {
        this.ticTacToeGame = ticTacToeGame;
        this.stage = ticTacToeGame.stage;
        this.skin = ticTacToeGame.skin;
        this.woodenGameTexture = ticTacToeGame.splashscreenTexture;
        this.batch = ticTacToeGame.batch;

        table = new Table();
        table.setFillParent(true);

        Color blueColor = skin.getColor("color-pressed");

        Label labelX = new Label("Player 'X' name:",skin,"sub-title", blueColor );
        table.add(labelX).pad(10);
        table.row();

        TextField textFieldX = new TextField(null,skin);
        textFieldX.setMessageText("John");
        table.add(textFieldX).pad(10);
        table.row();

        Label labelO = new Label("Player 'O' name:",skin,"sub-title", blueColor );
        table.add(labelO).pad(10);
        table.row();

        TextField textFieldO = new TextField(null,skin);
        textFieldO.setMessageText("Mary");
        table.add(textFieldO).pad(10);
        table.row();

        TextButton textButton = new TextButton("OK",skin);
        table.add(textButton).pad(10);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String nameX = textFieldX.getText();
                if (nameX.isEmpty()) {
                    nameX = textFieldX.getMessageText();
                }
                String nameO = textFieldO.getText();
                if (nameO.isEmpty()) {
                    nameO = textFieldO.getMessageText();
                }

                // send names to game control thread
                ticTacToeGame.uiStream.println(nameX);
                ticTacToeGame.uiStream.println(nameO);
            };
        });
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        stage.addActor(table);
    }

    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        batch.begin();
        batch.draw(woodenGameTexture,0,0);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    public void hide() {
        stage.clear();
    }

}
