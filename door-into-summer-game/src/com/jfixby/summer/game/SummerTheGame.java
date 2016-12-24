
package com.jfixby.summer.game;

import com.jfixby.r3.api.EngineParams.Assets;
import com.jfixby.r3.api.logic.BusinessLogicComponent;
import com.jfixby.r3.api.logic.LoadTask;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UILoaderListener;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class SummerTheGame implements BusinessLogicComponent {

	// private long fade_time = 1500;

	public static final ID game_ui_unit_id = Names.newID("com.jfixby.tinto.ui.game.GameMainUI");
	public static final ID loader_ui_unit_id = Names.newID("com.jfixby.tinto.ui.game.GameMainUI");

	@Override
	public void start () {

		final long logoFadeTime = SystemSettings.getLongParameter(Assets.DEFAULT_LOGO_FADE_TIME);

		UI.showLoadingScreen(loader_ui_unit_id, false);
		final LoadTask task = UI.prepareLoadUITask(null, game_ui_unit_id);
		final UILoaderListener ui_loader_listener = new UILoaderListener() {

			@Override
			public void onUILoaderDone () {
				UI.pushFadeOut(logoFadeTime);

				UI.loadUnit(game_ui_unit_id);
				UI.pushFadeIn(logoFadeTime);
				UI.allowUserInput();
			}

		};
		UI.pushTaskToLoader(task, ui_loader_listener);

	}

}
