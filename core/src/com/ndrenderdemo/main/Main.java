package com.ndrenderdemo.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.ndrender.polytope.PolytopeRenderer;

public class Main extends Game {

    //public static Color backgroundColor = new Color(.6F, .6F, .6F, 0F);
    public static Color backgroundColor = new Color(0F, 0F, 0F, 0F);
    public static Color leftColor = new Color(0, 1F, 0F, 1F);
    public static Color rightColor = new Color(1, 0, 1, 1F);

    public static int renderMode = PolytopeRenderer.ANAGLYPH_BIT;

    public static int lineWidth = 2;

    public Main (String[] arg) {
        if(arg.length == 2) {
//            leftColor = new Color(Integer.parseUnsignedInt(arg[0], 16));
//            rightColor = new Color(Integer.parseUnsignedInt(arg[1], 16));
        }
    }

    @Override
    public void create() {
     //   // setScreen(new FourTess());
      //  setScreen(new NTess(4));
        // setScreen(new FourTorus());
        setScreen(new Equation());
        //setScreen(new HypercubeDemo());

        // setScreen(new NTessUI());
        // setScreen(new InteractiveHypercubeDemo());
      //  setScreen(new FourDimRotations());
    }
}
