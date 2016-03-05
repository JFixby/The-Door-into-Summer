package com.jfixby.summer.ui;

import com.jfixby.r3.api.game.GameUIComponent;
import com.jfixby.r3.api.game.LoadTask;
import com.jfixby.r3.api.ui.UILoaderListener;
import com.jfixby.r3.api.ui.UIStarterComponent;

public class SummerUIManager implements UIStarterComponent, GameUIComponent {

	@Override
	public void showLoadingScreen() {
	}

	@Override
	public LoadTask prepareLoadGameUITask() {
		return null;
	}

	@Override
	public void pushTaskToLoader(LoadTask task, UILoaderListener ui_loader_listener) {
	}

	@Override
	public void pushFadeOut(long period) {
	}

	@Override
	public void switchToGameUI() {
	}

	@Override
	public void pushFadeIn(long period) {
	}

	@Override
	public void allowUserInput() {
	}

	@Override
	public void showTextTestScene() {
	}

	@Override
	public void showShaderTestScene() {
	}

	@Override
	public void onUIStart() {
	}

}
