import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class ShortcutListener implements NativeKeyListener, NativeMouseInputListener{

	
	/**
	 * https://github.com/kwhat/jnativehook
	 * https://stackoverflow.com/questions/4167664/ability-to-click-through-a-java-app
	 */
	
	private int keyDown = -1;
	private int mouseClicks = 0;

	
	public static void main(String[] args) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ShortcutListener sl = new ShortcutListener();
			GlobalScreen.addNativeKeyListener(sl);
			GlobalScreen.addNativeMouseListener(sl);
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getRawCode() != keyDown) {
			System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode())+" ("+e.getKeyCode()+", "+e.getRawCode()+")" );
			keyDown = e.getRawCode();
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		keyDown = -1;
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		System.out.println();
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		mouseClicks++;
		System.out.println(mouseClicks);
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
	
	}
	

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		
	}

}
