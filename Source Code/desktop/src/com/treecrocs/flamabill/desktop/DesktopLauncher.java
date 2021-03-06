package com.treecrocs.flamabill.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.treecrocs.flamabill.Flamabill;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Flamabill";
		config.resizable = false;
		config.width = Flamabill.V_WIDTH;
		config.height = Flamabill.V_HEIGHT;
		config.fullscreen = true;

		new LwjglApplication(new Flamabill(), config);
	}
}
