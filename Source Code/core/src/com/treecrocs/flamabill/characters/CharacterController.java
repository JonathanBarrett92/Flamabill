package com.treecrocs.flamabill.characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/*
    TODO: This class is broken since Player 2 implementation, fix for both instances to
        get rid of the determine movement function which is somewhat sloppy.
 */

public class CharacterController implements InputProcessor {

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean keyProcessed;

//    private int leftKey;
//    private int rightKey;
//    private int jumpKey;
//
//    public CharacterController(){
//
//    }

    @Override
    public boolean keyDown(int keycode) {
        keyProcessed = false;
        switch (keycode){
            case Input.Keys.A:
                left = true;
                keyProcessed = true;
                break;

            case Input.Keys.D:
                right = true;
                keyProcessed = true;
                break;

            case Input.Keys.W:
                up = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyProcessed = false;
        switch (keycode){
            case Input.Keys.A:
                left = false;
                keyProcessed = true;
                break;

            case Input.Keys.D:
                right = false;
                keyProcessed = true;
                break;

            case Input.Keys.W:
                up = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
