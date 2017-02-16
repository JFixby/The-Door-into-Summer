
package com.jfixby.summer.fokker.desktop.run;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jfixby.r3.api.EngineParams.Assets;
import com.jfixby.r3.api.EngineParams.Settings;
import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.logic.BusinessLogic;
import com.jfixby.r3.api.shader.R3Shader;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIStarter;
import com.jfixby.r3.api.ui.unit.layer.LayerUtils;
import com.jfixby.r3.collide.RedCollisionsAlgebra;
import com.jfixby.r3.engine.core.Fokker;
import com.jfixby.r3.engine.core.unit.layers.RedLayerUtils;
import com.jfixby.r3.engine.core.unit.shader.R3FokkerShader;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.text.R3Text;
import com.jfixby.r3.ext.text.red.RedTriplaneText;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.assets.FokkerTextureLoader;
import com.jfixby.r3.fokker.assets.RedFokkerTextureLoader;
import com.jfixby.r3.fokker.fs.AssetsInfo;
import com.jfixby.r3.ui.RedUIManager;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.scene2d.RedScene2D;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManagerSpecs;
import com.jfixby.scarabei.api.collisions.Collisions;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileSystemSandBox;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.file.cache.FileCache;
import com.jfixby.scarabei.api.java.gc.GCFisher;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.red.filesystem.cache.RedFileCache;
import com.jfixby.scarabei.red.filesystem.sandbox.RedFileSystemSandBox;
import com.jfixby.scarabei.red.filesystem.virtual.InMemoryFileSystem;
import com.jfixby.scarabei.red.java.gc.RedGCFisher;
import com.jfixby.summer.assets.DoorPath;
import com.jfixby.summer.game.SummerTheGame;

public class SummerAssembler implements FokkerEngineAssembler {

	@Override
	public void assembleEngine () {

		try {
			this.installResources();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		Scene2D.installComponent(new RedScene2D());
		R3Text.installComponent(new RedTriplaneText());
		R3Shader.installComponent(new R3FokkerShader());

		// FileSystemPacker.installComponent(new RedFileSystemPacker());

		FileSystemSandBox.installComponent(new RedFileSystemSandBox());

		// String java_path_cache = "D:\\[DATA]\\[RED-ASSETS]\\cache";
		// File cache_path = LocalFileSystem.newFile(java_path_cache);

		// VirtualFileSystem vfs = new VirtualFileSystem();
		// cache_path = vfs;
		LayerUtils.installComponent(new RedLayerUtils());
		FileCache.installComponent(new RedFileCache());

		FokkerTextureLoader.installComponent(new RedFokkerTextureLoader());
		FokkerTextureLoader.register();
		AssetsManager.installComponent(new RedAssetsManager());

		ResourcesManager.registerPackageReader(Scene2D.getPackageReader());
		ResourcesManager.registerPackageReader(R3Text.getTTFFontPackageReader());
		ResourcesManager.registerPackageReader(R3Text.getStringsPackageReader());
		ResourcesManager.registerPackageReader(R3Shader.getPackageReader());

		// --
		{
			// PSDUnpacker.installComponent(new RedPSDUnpacker());
			// SpriteDecomposer.installComponent(new RedSpriteDecomposer());
			// ImageProcessing.installComponent(new FokkerImageProcessing());
			// TexturePacker.installComponent(new GdxTexturePacker());
		}

		final RedUIManager tinto_ui_starter = new RedUIManager();
		UIStarter.installComponent(tinto_ui_starter);
		UI.installComponent(tinto_ui_starter);
		BusinessLogic.installComponent(new SummerTheGame());

		// JBox2D box2d_j = new JBox2D(); //
		// JBox2DFloat box2d_j_float = new JBox2DFloat();
		// GDXBox2D box2d_gdx = new GDXBox2D();

		// Box2DComponent box2d = box2d_j;
		// Box2DComponent box2d = box2d_j_float;

		// Physics2DComponent r3_physics_gdx = new
		// RedTriplanePhysics(box2d_gdx);
		// Physics2DComponent r3_physics_jfloat = new RedTriplanePhysics(
		// box2d_j_float);
		// Physics2DComponent r3_physics_j = new RedTriplanePhysics(box2d_j);

		// RedTriplanePhysics r3_physics = r3_physics_gdx;
		// RedTriplanePhysics r3_physics = r3_physics_jfloat;

		// Physics2DComponent r3_physics = new RedTriplanePhysicsDuplex(
		// r3_physics_j, r3_physics_jfloat);

		// Physics2D.installComponent(r3_physics_j);

		// Box2D.installComponent(box2d);
		Collisions.installComponent(new RedCollisionsAlgebra());

		RedTriplane.installComponent(new Fokker());

		SystemSettings.setExecutionMode(ExecutionMode.EARLY_DEVELOPMENT);
		SystemSettings.setFlag(Settings.PrintLogMessageOnMissingSprite, true);
		SystemSettings.setFlag(Settings.ExitOnMissingSprite, false);
		SystemSettings.setFlag(Settings.AllowMissingRaster, true);
		SystemSettings.setFlag(AssetsManager.UseAssetSandBox, false);

		SystemSettings.setFlag(AssetsManager.ReportUnusedAssets, false);
		SystemSettings.setFlag(AssetsManagerFlags.AutoresolveDependencies, true);
		SystemSettings.setStringParameter(Assets.DefaultFont, "Arial");
		SystemSettings.setStringParameter(Assets.CLEAR_SCREEN_COLOR_ARGB, "#FF440044");
		SystemSettings.setLongParameter(GCFisher.DefaultBaitSize, 50 * 1024 * 1024);

		// /-----------------------------------------

		// ImageProcessing.installComponent(new DesktopImageProcessing());
		// ImageGWT.installComponent(new RedImageGWT());
		// JsonTest.test();
		GCFisher.installComponent(new RedGCFisher());
// final BaitInfo bait_info = GCFisher.throwBait();
// L.d("throw GC bait", bait_info);
	}

	private void installResources () throws IOException {
		this.printAssetsInfo();

		final RedResourcesManagerSpecs specs = new RedResourcesManagerSpecs();
		final RedResourcesManager res_manager = new RedResourcesManager(specs);
		ResourcesManager.installComponent(res_manager);

		final File dev_assets_home = LocalFileSystem.newFile(DoorPath.PACKED_ASSETS_HOME);

		// dev_assets_home = preload(dev_assets_home);

		{
			final File bank_folder = dev_assets_home.child("bank-summer");
			res_manager.findAndInstallResources(bank_folder);
		}

	}

	private File preload (File dev_assets_home) {
		final InMemoryFileSystem virtualFS = new InMemoryFileSystem();
		try {
			virtualFS.copyFolderContentsToFolder(dev_assets_home, virtualFS.ROOT());
			dev_assets_home = virtualFS.ROOT();
		} catch (final IOException e) {
			e.printStackTrace();
			Sys.exit();
		}
		return dev_assets_home;
	}

	private void printAssetsInfo () {
		final FileHandle fh = Gdx.files.internal(AssetsInfo.FILE_NAME);
		if (!fh.exists()) {
			return;
		}
		final String data = fh.readString();
		final AssetsInfo info = Json.deserializeFromString(AssetsInfo.class, data);
		info.print();
	}

}
