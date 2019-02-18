package com.treecrocs.flamabill.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class FlamaBill extends Sprite{

    public World world;
    public  static Body b2body;

    public FlamaBill(World world)
    {
        this.world = world;
        defineFlamaBill();
    }

    private void defineFlamaBill()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32, 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}
