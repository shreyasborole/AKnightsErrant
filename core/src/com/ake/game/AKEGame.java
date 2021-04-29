package com.ake.game;

import com.ake.game.core.*;
import com.ake.game.screens.*;

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
