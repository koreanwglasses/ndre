package com.ndrenderdemo.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ndrender.polytope.PolytopeRenderer;
import com.ndrenderdemo.ui.Assets;
import com.ndrenderdemo.ui.ErrorDialog;

public class NTessUI implements Screen {

	public static final int DEFAULT_DIM = 4;

	public static final int GREEN_MAGENTA_3D = 0;
	public static final int RED_CYAN_3D = 1;
	public static final int WHITE_2D = 2;

	int displayMode = 0;

	public NTess base;

	Stage stage;
	ScreenViewport viewport;

	boolean isExpanded = true;

	@Override
	public void show() {
		base = new NTess(DEFAULT_DIM);

		Assets.initialize();
		while (!Assets.manager.update())
			;
		Assets.finish();

		base.show();

		// HUD
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		viewport = new ScreenViewport();
		stage.setViewport(viewport);

		// Declarations
		Table screenTable = new Table();
		screenTable.setFillParent(true);
		stage.addActor(screenTable);

		final Table uiControls = new Table();
		uiControls.setVisible(isExpanded);

		final TextButton btnExpand = new TextButton(">>",
				Assets.transparentSkin);
		btnExpand.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				isExpanded = !isExpanded;
				btnExpand.setText(isExpanded ? ">>" : "<<");
				uiControls.setVisible(isExpanded);
			}
		});

		Label lblDim = new Label("Dimensions", Assets.transparentSkin);
		final Label lblDimSlider = new Label(String.valueOf(DEFAULT_DIM),
				Assets.transparentSkin);
		final Slider hsbDim = new Slider(4F, 15F, 1F, false,
				Assets.transparentSkin);

		hsbDim.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				updateSlider();
			}

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				updateSlider();
			}

			void updateSlider() {
				lblDimSlider.setText(String.valueOf((int) hsbDim.getValue()));
			}
		});

		Label lblLineWidth = new Label("Line Width", Assets.transparentSkin);
		final Label lblLineWidthSlider = new Label(
				String.valueOf(Main.lineWidth), Assets.transparentSkin);
		final Slider hsbLineWidth = new Slider(1F, 5F, 1F, false,
				Assets.transparentSkin);

		hsbLineWidth.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				updateSlider();
			}

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				updateSlider();
			}

			void updateSlider() {
				lblLineWidthSlider.setText(String.valueOf((int) hsbLineWidth
						.getValue()));
				base.lineWidth = (int) hsbLineWidth.getValue();
			}
		});

		Label lblBackgroundColor = new Label("Background Color",
				Assets.transparentSkin);
		final Label lblBackgroundColorSlider = new Label(String.format("%.2f",
				Main.backgroundColor.r), Assets.transparentSkin);
		final Slider hsbBackgroundColor = new Slider(0F, 1F, 0.01F, false,
				Assets.transparentSkin);
		hsbBackgroundColor.setValue(Main.backgroundColor.r);

		hsbBackgroundColor.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				updateSlider();
			}

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				updateSlider();
			}

			void updateSlider() {
				float value = hsbBackgroundColor.getValue();
				lblBackgroundColorSlider.setText(String.format("%.2f", value));
				base.backgroundColor = new Color(value, value, value, 0);
			}
		});

		final TextButton btnDisplayMode = new TextButton("G-M 3D",
				Assets.transparentSkin);
		btnDisplayMode.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (displayMode < 2)
					displayMode++;
				else
					displayMode = 0;

				switch (displayMode) {
				case 0:
					btnDisplayMode.setText("G-M 3D");
					break;
				case 1:
					btnDisplayMode.setText("R-C 3D");
					break;
				case 2:
					btnDisplayMode.setText("2D");
					break;
				}
			}
		});

		final TextButton btnOk = new TextButton("Go!", Assets.transparentSkin);
		btnOk.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {

				float value = hsbBackgroundColor.getValue();
				loadNew((int) hsbDim.getValue(), (int) hsbLineWidth.getValue(),
						new Color(value, value, value, 0));
			}
		});

		// Placement
		screenTable.add(btnExpand).expandX().top().right().pad(2);
		screenTable.row();
		screenTable.add(uiControls).expand().width(200).top().right().pad(2);
		uiControls.add(lblDim).colspan(2);
		uiControls.row();
		uiControls.add(lblDimSlider).padRight(10);
		uiControls.add(hsbDim).fillX();
		uiControls.row();
		uiControls.add(lblLineWidth).colspan(2);
		uiControls.row();
		uiControls.add(lblLineWidthSlider).padRight(10);
		uiControls.add(hsbLineWidth).fillX();
		uiControls.row();
		uiControls.add(lblBackgroundColor).colspan(2);
		uiControls.row();
		uiControls.add(lblBackgroundColorSlider).padRight(10);
		uiControls.add(hsbBackgroundColor).fillX();
		uiControls.row();
		uiControls.add(btnDisplayMode).colspan(2).width(100);
		uiControls.row();
		uiControls.add(btnOk).colspan(2).width(100);
	}

	public void loadNew(int dimensions, int lineWidth, Color backgroundColor) {
		if (dimensions > 9) {
			new Dialog("Warning!", Assets.defaultSkin) {
				{
					text("Setting the number of dimensions to > 9 \nwill run slowly and may cause problems. \nContinue?");
					button("Continue", true);
					button("Cancel", false);
				}

				@Override
				protected void result(Object object) {
					if (object == (Object) true) {
						reload(dimensions, lineWidth, backgroundColor);
					}
				}
			}.show(stage);
		} else {
			reload(dimensions, lineWidth, backgroundColor);
		}
	}
	
	public void reload(int dimensions, int lineWidth, Color backgroundColor) {
		base = new NTess(dimensions);
		base.lineWidth = lineWidth;
		base.backgroundColor = backgroundColor;
		
		switch(displayMode) {
		case 0:
			base.renderMode = PolytopeRenderer.ANAGLYPH_BIT;
			base.leftColor = Main.leftColor;
			base.rightColor = Main.rightColor;
			break;
		case 1:
			base.renderMode = PolytopeRenderer.ANAGLYPH_BIT;
			base.leftColor = Color.RED;
			base.rightColor = Color.CYAN;
			break;
		case 2:
			base.renderMode = 0;
			break;
		}
		
		base.show();
		base.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		base.render(delta);

		stage.act(Math.min(delta, 0.03F));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		base.resize(width, height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		base.pause();
	}

	@Override
	public void resume() {
		base.resume();
	}

	@Override
	public void hide() {
		base.hide();
	}

	@Override
	public void dispose() {
		base.dispose();
	}

}
