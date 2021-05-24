package com.ake.game.core;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen, InputProcessor
{
    protected Stage mainStage;
    protected Stage uiStage;
    protected Table hudTable;
    protected Table uiTable;

    private ShapeRenderer shapeRenderer;
    private float alpha;
    private boolean alphaDirection;
    
    public BaseScreen()
    {
        mainStage = new Stage();
        uiStage = new Stage();
        uiTable = new Table();
        hudTable = new Table();
        hudTable.setFillParent(true);
        uiTable.setFillParent(true);
        uiStage.addActor(hudTable);
        uiStage.addActor(uiTable);
        shapeRenderer = new ShapeRenderer();
        alpha = 1f;
        alphaDirection = false;
        initialize();
    }

    public abstract void initialize();

    public abstract void update(float dt);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    public void render(float dt) 
    {
        // act methods
        uiStage.act(dt);
        mainStage.act(dt);
        
        // defined by user
        update(dt);
        
        // clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // draw the graphics
        mainStage.draw();
        uiStage.draw();
        
        // fade in/out effect
        // effect is not linear due to variable framerate
        if(alpha >= 0 || alphaDirection){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0f, 0f, 0f, alpha);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            if(alphaDirection){
                if(alpha >= 1){
                    alphaDirection = false;
                    return;
                }
                if (dt > 200)
                    alpha += 0.001f;
                else if (dt <= 200 && dt > 100)
                    alpha += 0.005f;
                else if (dt <= 100)
                    alpha += 0.01f;
            }else{
                if(dt > 200)
                    alpha -= 0.001f;
                else if(dt <= 200 && dt > 100)
                    alpha -= 0.005f;
                else if(dt <= 100)
                    alpha -= 0.01f;
            }
        }
    }

    public void triggerFadeOut(){
        alphaDirection = true;
    }

    // methods required by Screen interface
    public void resize(int width, int height) {  }

    public void pause()   {  }

    public void resume()  {  }

    public void dispose() {  }

    public void show(){
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);
    }
   
    public void hide(){
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
