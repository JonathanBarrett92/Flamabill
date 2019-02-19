package com.treecrocs.flamabill.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.treecrocs.flamabill.Flamabill;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dans Branch";
		config.width = 1200;
		config.height = 800;
		new LwjglApplication(new Flamabill(), config);
	}
}
