package com.mygdx.game;

import com.mygdx.game.core.*;
import com.mygdx.game.screens.*;

public class AKEGame extends BaseGame
{
    @Override
    public void create() 
    {
        super.create();
        setCursor("arrow_16.png");
        setFont("fonts/joystix monospace.ttf");
        setActiveScreen( new MenuScreen() );
    }
}
