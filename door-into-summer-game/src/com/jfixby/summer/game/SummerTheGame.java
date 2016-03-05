package com.jfixby.summer.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.r3.api.game.GameLogicComponent;
import com.jfixby.r3.api.game.LoadTask;
import com.jfixby.r3.api.ui.GameUI;
import com.jfixby.r3.api.ui.UILoaderListener;

public class SummerTheGame implements GameLogicComponent {

	private long fade_time = 1500;

	public static final boolean TEST_TEXT = !true;
	public static final boolean TEST_SHADER = !true;

	public static final AssetID game_ui_unit_id = Names.newAssetID("com.jfixby.tinto.ui.game.GameMainUI");
	public static final AssetID loader_ui_unit_id = Names.newAssetID("com.jfixby.tinto.ui.game.GameMainUI");

	@Override
	public void startGame() {

		if (TEST_TEXT) {
			GameUI.showTextTestScene();
			return;
		}

		if (TEST_SHADER) {
			GameUI.showShaderTestScene();
			return;
		}

		GameUI.showLoadingScreen(loader_ui_unit_id);
		LoadTask task = GameUI.prepareLoadGameUITask();
		UILoaderListener ui_loader_listener = new UILoaderListener() {

			@Override
			public void onUILoaderDone() {
				GameUI.pushFadeOut(fade_time);

				GameUI.switchToGameUI(game_ui_unit_id);
				GameUI.pushFadeIn(fade_time);
				GameUI.allowUserInput();
			}

		};
		GameUI.pushTaskToLoader(task, ui_loader_listener);

	}

}
