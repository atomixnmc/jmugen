package org.lee.mugen.sprite.cns.eval.trigger.function.spriteCns;

import org.lee.mugen.core.GameFight;
import org.lee.mugen.parser.type.Valueable;
import org.lee.mugen.sprite.character.Sprite;
import org.lee.mugen.sprite.cns.eval.function.SpriteCnsTriggerFunction;
import org.lee.mugen.sprite.parser.Parser;
/**
 * 
 * @author Dr Wong
 * @category Trigger : Complete
 */
public class Sysvar extends SpriteCnsTriggerFunction {

	public Sysvar() {
		super("sysvar", new String[0]);
	}
	@Override
	public Object getValue(String spriteId, Valueable... params) {
		Sprite sprite = GameFight.getInstance().getSpriteInstance(spriteId);
		int index = Parser.getIntValue(params[0].getValue(spriteId));
		return sprite.getSpriteState().getVars().getSysVar(index + "");
	}
}
