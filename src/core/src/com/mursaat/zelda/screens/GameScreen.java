package com.mursaat.zelda.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mursaat.zelda.Zelda;
import com.mursaat.zelda.map.Chunk;
import com.mursaat.zelda.map.Map;
import com.mursaat.zelda.sound.Musics;
import com.mursaat.zelda.tiles.Tile;
import com.mursaat.zelda.world.World;

/**
 * @author Mursaat
 *         L'écran de jeu. Il peut gérer les inputs qui lui sont propres à condition de le definir en tant
 *         qu'InputProcessor de Gdx.input
 */
public class GameScreen implements Screen
{
    private Zelda game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private float time;

    public static float TIME_STEP = 1/60f;
    public static int VELOCITY_ITERATIONS = 6;
    public static int POSITION_ITERATIONS = 2;

    public GameScreen(Zelda game)
    {
        this.game = game;
        this.batch = new SpriteBatch();
        this.time = 0;

        float w = 864;
        float h = 480;
        this.camera = new OrthographicCamera(w, h);
        float tw = Tile.TILE_SIZE * Chunk.CHUNK_TILE_SIZE * Map.MAP_CHUNK_SIZE;

        camera.position.set(w / 2f, h / 2f, 0);
        camera.zoom = 0.5f;

        camera.translate((tw - w) / 2, (tw - h) / 2);
        camera.update();
    }

    @Override
    public void show()
    {
        Musics.exterieur.setLooping(true);
        Musics.exterieur.play();
    }

    @Override
    public void render(float delta)
    {
        // Déplacer le héros
        World.getHero().update();
        // Positionner la caméra sur le héros
        camera.position.set(World.getHero().x * Tile.TILE_SIZE, World.getHero().y * Tile.TILE_SIZE, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        time += Gdx.graphics.getDeltaTime();

        // Dessiner
        batch.begin();
        World.getCurrentMap().draw(batch, time);
        World.getHero().draw(batch, time);
        batch.end();
    }

    private float accumulator = 0;

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

}
