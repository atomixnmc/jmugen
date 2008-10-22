package org.lee.mugen.core.renderer.game;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.lee.mugen.core.StateMachine;
import org.lee.mugen.renderer.GraphicsWrapper;
import org.lee.mugen.renderer.Renderable;
import org.lee.mugen.renderer.GameWindow.MouseCtrl;
import org.lee.mugen.sprite.background.Stage;
import org.lee.mugen.sprite.base.AbstractSprite;
import org.lee.mugen.sprite.character.Sprite;
import org.lee.mugen.sprite.character.SpriteCns;
import org.lee.mugen.sprite.character.SpriteHelper;
import org.lee.mugen.sprite.common.resource.FontParser;
import org.lee.mugen.sprite.common.resource.FontProducer;
import org.lee.mugen.sprite.entity.ExplodSprite;
import org.lee.mugen.sprite.entity.ExplodSub;


public class DebugExplodRender implements Renderable {
	public static final DebugExplodRender debugRender = new DebugExplodRender();
	
	
	
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

	public boolean isProcess() {
		return true;
	}

	public boolean remove() {
		return false;
	}

	
	
	
	public void render() {
		if (!isDisplay())
			return;
		try {
			MouseCtrl mouse = GraphicsWrapper.getInstance().getInstanceOfGameWindow().getMouseStatus();
//			FontParser.getFontProducer().draw(100, 100, GraphicsWrapper.getInstance(), mouse.getX() + " " + mouse.getY());
			List<ExplodSprite> explods = new ArrayList<ExplodSprite>();
			for (AbstractSprite s: StateMachine.getInstance().getOtherSprites()) {
				if (s instanceof ExplodSprite) {
					explods.add((ExplodSprite) s);
				}
			}

			List<String> strings = new LinkedList<String>();
			for (ExplodSprite sprite: explods) {
				ExplodSub sub = sprite.getExplod();
				String line = 
//				sprite.hashCode() + " - " + 
				"id " + sprite.getExplod().getId() + " - " + 
				"Parent " + 
				(sprite.getExplod().getSprite() instanceof SpriteHelper? 
						"Helper name=" + ((SpriteHelper)sprite.getExplod().getSprite()).getHelperSub().getName() + " id=" + ((SpriteHelper)sprite.getExplod().getSprite()).getHelperSub().getId(): "") + 
						" " + sprite.getExplod().getSprite().getSpriteState().getCurrentState().getId() + " - " +
				"Spr " + (sprite.getExplod().getSprite() instanceof SpriteHelper? StateMachine.getInstance().getRootId(sprite.getExplod().getSprite()): sprite.getExplod().getSprite().getSpriteId())
				+ " - " + "IsProcess " + sprite.isProcess();

				strings.add(line);
			}

			FontProducer fp = FontParser.getFontProducer();
			int addX = 0;
			int x = 10;
			int y = 10;
			int i = 0;
			String[] lines = new String[0];
			for (String s: strings.toArray(new String[0])) {
				if (mouse.getY() >= y && mouse.getY() < y+fp.getSize().height
						&& 
						mouse.getX() >= x && mouse.getX() < x+fp.getSize().width * s.length() 
						&& mouse.isLeftRelease()
				) {
					explods.get(i).setProcess(!explods.get(i).isProcess());
				}
				if (mouse.getY() >= y && mouse.getY() < y+fp.getSize().height
						&& 
						mouse.getX() >= x && mouse.getX() < x+fp.getSize().width * s.length() 
				) {
					ExplodSprite sprite = explods.get(i);
					lines = new String[] {
							"Postype " + sprite.getExplod().getPostype(),
							"Offset " + sprite.getExplod().getPos().x + ", " + sprite.getExplod().getPos().y,
							"Pos " + sprite.getRealXPos() + "," + sprite.getRealYPos(),
							"Anim " + sprite.getSprAnimMng().getAction()
					};
					

				}
				
				fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), s);
				addX = Math.max(addX, s.length());
				i++;
			}		
			
			
			addX = 0;
			x = 10;
			y = 200;
			i = 0;
			for (String s: lines) {
				fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), s);
				addX = Math.max(addX, s.length());
				i++;
			}	

		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("Info Err");
		}

	
	}

	private boolean displayHelp = false;
	public boolean isDisplay() {
		return displayHelp;
	}

	public void setDisplay(boolean displayHelp) {
		this.displayHelp = displayHelp;
	}

	private void displayHelp() throws Exception {
		if (displayHelp) {
			FontProducer fp = FontParser.getFontProducer();
			int x = 10;
			int y = 50;
			fp.draw(x, y+=fp.getSize().height*2,GraphicsWrapper.getInstance(),"F1         : Display Help");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL '+/-' : +/- FPS");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL '*'   : Reset FPS");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "Space      : Init");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL-D     : Switch Sprite Informations");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL-C     : Show Cns Box");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL-X     : Show Cns Attack Box");

			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL-P     : Debug Pause");
			fp.draw(x, y+=fp.getSize().height, GraphicsWrapper.getInstance(), "CTRL-A     : If Debug Pause Advance Frame By Frame");
			
		}

	}
	
	public void setPriority(int p) {
	}

}