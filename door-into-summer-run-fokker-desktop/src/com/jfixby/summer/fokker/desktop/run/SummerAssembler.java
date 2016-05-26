
package com.jfixby.summer.fokker.desktop.run;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jfixby.cmns.adopted.gdx.GdxSimpleTriangulator;
import com.jfixby.cmns.adopted.gdx.base64.GdxBase64;
import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.base64.Base64;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collisions.Collisions;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileSystemSandBox;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.file.cache.FileCache;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.graphs.Graphs;
import com.jfixby.cmns.api.input.UserInput;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.java.gc.BaitInfo;
import com.jfixby.cmns.api.java.gc.GCFisher;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.math.MathTools;
import com.jfixby.cmns.api.math.SimpleTriangulator;
import com.jfixby.cmns.api.md5.MD5;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.sys.settings.ExecutionMode;
import com.jfixby.cmns.api.sys.settings.SystemSettings;
import com.jfixby.cmns.api.taskman.TaskManager;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.RedTriplaneParams;
import com.jfixby.r3.api.logic.BusinessLogic;
import com.jfixby.r3.api.shader.R3Shader;
import com.jfixby.r3.api.ui.UI;
import com.jfixby.r3.api.ui.UIStarter;
import com.jfixby.r3.api.ui.unit.layer.LayerUtils;
import com.jfixby.r3.collide.RedCollisionsAlgebra;
import com.jfixby.r3.ext.api.font.R3Font;
import com.jfixby.r3.ext.api.scene2d.Scene2D;
import com.jfixby.r3.ext.api.text.R3Text;
import com.jfixby.r3.ext.font.gdx.ft.GdxR3Font;
import com.jfixby.r3.ext.text.red.RedTriplaneText;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsSpawner;
import com.jfixby.r3.fokker.api.assets.FokkerAtlasLoader;
import com.jfixby.r3.fokker.api.assets.FokkerRasterDataRegister;
import com.jfixby.r3.fokker.api.assets.FokkerTextureLoader;
import com.jfixby.r3.fokker.backend.RedUnitSpawner;
import com.jfixby.r3.ui.RedUIManager;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.AssetsManagerFlags;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.color.RedColors;
import com.jfixby.red.debug.RedDebug;
import com.jfixby.red.desktop.collections.DesktopCollections;
import com.jfixby.red.desktop.filesystem.win.WinFileSystem;
import com.jfixby.red.desktop.log.DesktopLogger;
import com.jfixby.red.desktop.math.DesktopFloatMath;
import com.jfixby.red.desktop.sys.DesktopSystem;
import com.jfixby.red.engine.core.Fokker;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.engine.core.unit.layers.RedLayerUtils;
import com.jfixby.red.engine.core.unit.shader.R3FokkerShader;
import com.jfixby.red.engine.scene2d.RedScene2D;
import com.jfixby.red.err.RedError;
import com.jfixby.red.filesystem.cache.RedFileCache;
import com.jfixby.red.filesystem.sandbox.RedFileSystemSandBox;
import com.jfixby.red.filesystem.virtual.InMemoryFileSystem;
import com.jfixby.red.geometry.RedGeometry;
import com.jfixby.red.graphs.RedGraphs;
import com.jfixby.red.input.RedInput;
import com.jfixby.red.io.RedIO;
import com.jfixby.red.java.gc.RedGCFisher;
import com.jfixby.red.math.RedAngles;
import com.jfixby.red.math.RedIntegerMath;
import com.jfixby.red.math.RedMathTools;
import com.jfixby.red.name.RedAssetsNamespace;
import com.jfixby.red.sys.RedSystemSettings;
import com.jfixby.red.sys.RedTaskManager;
import com.jfixby.red.triplane.resources.fsbased.FileSystemBasedResource;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;
import com.jfixby.red.util.RedJUtils;
import com.jfixby.red.util.md5.AlpaeroMD5;
import com.jfixby.redtriplane.fokker.assets.GdxAtlasReader;
import com.jfixby.redtriplane.fokker.assets.GdxTextureReader;
import com.jfixby.redtriplane.fokker.assets.RedFokkerRasterDataRegister;
import com.jfixby.redtriplane.fokker.fs.AssetsInfo;
import com.jfixby.summer.assets.DoorPath;
import com.jfixby.summer.game.SummerTheGame;

public class SummerAssembler implements FokkerEngineAssembler {

	@Override
	public void assembleEngine () {
		L.installComponent(new DesktopLogger());

		Err.installComponent(new RedError());
		Debug.installComponent(new RedDebug());
		Collections.installComponent(new DesktopCollections());

		JUtils.installComponent(new RedJUtils());
		FloatMath.installComponent(new DesktopFloatMath());
		TaskManager.installComponent(new RedTaskManager());
		Sys.installComponent(new DesktopSystem());
		SystemSettings.installComponent(new RedSystemSettings());

		IntegerMath.installComponent(new RedIntegerMath());
		Names.installComponent(new RedAssetsNamespace());
		IO.installComponent(new RedIO());
		Graphs.installComponent(new RedGraphs());
		SimpleTriangulator.installComponent(new GdxSimpleTriangulator());
		Angles.installComponent(new RedAngles());

		UserInput.installComponent(new RedInput());

		final RedGeometry geometry = new RedGeometry();
		Geometry.installComponent(geometry);
		Colors.installComponent(new RedColors());
		MathTools.installComponent(new RedMathTools());
		// --
		UnitsSpawner.installComponent(new RedUnitSpawner());
		Json.installComponent(new RedJson());
		Base64.installComponent(new GdxBase64());
		MD5.installComponent(new AlpaeroMD5());

		LocalFileSystem.installComponent(new WinFileSystem());

		this.installResources();

		Scene2D.installComponent(new RedScene2D());
		R3Font.installComponent(new GdxR3Font());
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
		FokkerRasterDataRegister.installComponent(new RedFokkerRasterDataRegister());

		FokkerAtlasLoader.installComponent(new GdxAtlasReader());
		FokkerTextureLoader.installComponent(new GdxTextureReader());
		AssetsManager.installComponent(new RedAssetsManager());
		FokkerAtlasLoader.register();
		FokkerTextureLoader.register();

		ResourcesManager.registerPackageReader(Scene2D.getPackageReader());
		ResourcesManager.registerPackageReader(R3Font.getPackageReader());
		ResourcesManager.registerPackageReader(R3Text.getPackageReader());
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
		SystemSettings.setFlag(RedTriplaneParams.PrintLogMessageOnMissingSprite, true);
		SystemSettings.setFlag(RedTriplaneParams.ExitOnMissingSprite, false);
		SystemSettings.setFlag(RedTriplaneParams.AllowMissingRaster, true);
		SystemSettings.setFlag(AssetsManager.UseAssetSandBox, false);

		SystemSettings.setFlag(AssetsManager.ReportUnusedAssets, false);
		SystemSettings.setFlag(AssetsManagerFlags.AutoresolveDependencies, true);
		SystemSettings.setStringParameter(RedTriplaneParams.DefaultFont, "Arial");
		SystemSettings.setStringParameter(RedTriplaneParams.CLEAR_SCREEN_COLOR_ARGB, "#FF440044");
		SystemSettings.setLongParameter(GCFisher.DefaultBaitSize, 50 * 1024 * 1024);

		// /-----------------------------------------

		// ImageProcessing.installComponent(new DesktopImageProcessing());
		// ImageGWT.installComponent(new RedImageGWT());
		// JsonTest.test();
		GCFisher.installComponent(new RedGCFisher());
		final BaitInfo bait_info = GCFisher.throwBait();
		L.d("throw GC bait", bait_info);
	}

	private void installResources () {
		this.printAssetsInfo();

		final RedResourcesManager res_manager = new RedResourcesManager();
		ResourcesManager.installComponent(res_manager);

		final File dev_assets_home = LocalFileSystem.newFile(DoorPath.PACKED_ASSETS_HOME);

		// dev_assets_home = preload(dev_assets_home);

		{
			final File bank_folder = dev_assets_home.child("bank-summer");
			if (bank_folder.exists()) {
				final FileSystemBasedResource resource = new FileSystemBasedResource(bank_folder);
				res_manager.installResource(resource);
			}
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
