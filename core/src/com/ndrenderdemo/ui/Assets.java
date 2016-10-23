package com.ndrenderdemo.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static AssetManager manager;
	
	public static Skin transparentSkin;
	public static Skin defaultSkin;
	
	public static void initialize() {
		manager = new AssetManager();
		manager.load("GUI/Transparent Skin/uiskin.json",Skin.class);
		manager.load("GUI/Default Skin/uiskin.json",Skin.class);
	}
	
	public static void finish() {
		transparentSkin = manager.get("GUI/Transparent Skin/uiskin.json",Skin.class);
		defaultSkin = manager.get("GUI/Default Skin/uiskin.json",Skin.class);
	}
}
