package com.oop.mimala;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.oop.mimala.characters.LolaCharacter;
import com.oop.mimala.characters.MiloCharacter;
import com.oop.mimala.characters.SantoCharacter;

public class CharacterSelectionMenu {
    private Stage stage;
    private Skin skin;
    private Mimala game;

    public CharacterSelectionMenu(Skin skin, int width, int height, Mimala game) {
        this.skin = skin;
        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("Choose Your Character", skin);

        TextButton miloButton = new TextButton("Milo", skin);
        TextButton char2Button = new TextButton("Lola", skin);
        TextButton char3Button = new TextButton("Santo", skin);

        miloButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.startGame(new MiloCharacter(100, 50));
            }
        });

        char2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame(new LolaCharacter(100, 50)); // ✅ Replace with actual class
            }
        });

        char3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame(new SantoCharacter(100, 50)); // ✅ Replace with actual class
            }
        });

        table.add(titleLabel).colspan(3).padBottom(20);
        table.row();
        table.add(miloButton).pad(10);
        table.add(char2Button).pad(10);
        table.add(char3Button).pad(10);
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
