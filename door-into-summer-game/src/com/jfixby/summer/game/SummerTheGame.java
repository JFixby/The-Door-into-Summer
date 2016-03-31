package com.jfixby.summer.game;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.sys.settings.SystemSettings;
import com.jfixby.r3.api.RedTriplaneParams;
import com.jfixby.r3.api.game.GameLogicComponent;
import com.jfixby.r3.api.game.LoadTask;
import com.jfixby.r3.api.ui.GameUI;
import com.jfixby.r3.api.ui.UILoaderListener;

public class SummerTheGame implements GameLogicComponent {

    // private long fade_time = 1500;

    public static final AssetID game_ui_unit_id = Names.newAssetID("com.jfixby.tinto.ui.game.GameMainUI");
    public static final AssetID loader_ui_unit_id = Names.newAssetID("com.jfixby.tinto.ui.game.GameMainUI");

    @Override
    public void startGame() {

	final long logoFadeTime = SystemSettings.getLongParameter(RedTriplaneParams.DEFAULT_LOGO_FADE_TIME);

	GameUI.showLoadingScreen(loader_ui_unit_id, false);
	LoadTask task = GameUI.prepareLoadGameUITask(game_ui_unit_id);
	UILoaderListener ui_loader_listener = new UILoaderListener() {

	    @Override
	    public void onUILoaderDone() {
		GameUI.pushFadeOut(logoFadeTime);

		GameUI.switchToGameUI(game_ui_unit_id);
		GameUI.pushFadeIn(logoFadeTime);
		GameUI.allowUserInput();
	    }

	};
	GameUI.pushTaskToLoader(task, ui_loader_listener);

    }

}
