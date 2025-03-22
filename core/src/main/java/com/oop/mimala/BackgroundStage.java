package com.oop.mimala;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class BackgroundStage implements Disposable {
    private Texture background;
    private Texture layer1, layer2, layer3;

    private Texture spriteSheet;
    private TextureRegion groundTile, edgeTile;


    public BackgroundStage() {
        background = new Texture("assets/background-tiles/bgcolor.png");
        layer1 = new Texture("assets/background-tiles/Tlayer1.png");
        layer2 = new Texture("assets/background-tiles/Tlayer2.png");
        layer3 = new Texture("assets/background-tiles/Tlayer3.png");

        spriteSheet = new Texture("assets/background-tiles/Textures&trees.png");


        groundTile = new TextureRegion(spriteSheet, 203, 101, 52, 180); // Ensure proper Y-offset
        edgeTile = new TextureRegion(spriteSheet, 154, 96, 39,180);

    }

    public void render(SpriteBatch batch) {
        // Remove batch.begin() and batch.end()

        batch.draw(background, 0, -130, 1920, 1080);
        batch.draw(layer1, 0, -130, 1920, 1080);
        batch.draw(layer2, 0, -130, 1920, 1080);
        batch.draw(layer3, 0, -130, 1920, 1080);

        // Tile ground across the width of the screen
        int tileWidth = 81; // Width of the ground tile
        int screenWidth = 1296; // Total screen width

        batch.draw(edgeTile,1296 ,-20, 39, 180);

        for (int x = 0; x < screenWidth; x += tileWidth) {
            batch.draw(groundTile, x, -20, tileWidth, 180);
        }
    }


    @Override
    public void dispose() {
        background.dispose();
        layer1.dispose();
        layer2.dispose();
        layer3.dispose();
        spriteSheet.dispose();
    }

}
