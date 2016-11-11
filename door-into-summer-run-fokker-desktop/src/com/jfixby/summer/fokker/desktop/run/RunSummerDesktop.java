package com.jfixby.summer.fokker.desktop.run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.jfixby.r3.engine.core.FokkerStarter;
import com.jfixby.r3.engine.core.FokkerStarterConfig;
import com.jfixby.r3.fokker.adaptor.GdxAdaptor;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;

public class RunSummerDesktop {
	public static void main(String[] arg) {
		FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();

		FokkerEngineAssembler engine_assembler = new SummerAssembler();
		config.setEngineAssembler(engine_assembler);

		FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
		UnitsMachineExecutor machine = triplane_starter
				.getUnitsMachineExecutor();

		GdxAdaptor adaptor = new GdxAdaptor(machine);

		final FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
		cfg.title = "Test";
		cfg.useGL30 = false;
		cfg.width = 600;
		cfg.height = 400;

		ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();

		// gdx_listener = new HttpRequestTest();

		new FokkerLwjglApplication(gdx_listener, cfg);

	}
}
