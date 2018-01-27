import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
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
	
	/** After how many strokes write to file */
	private final int SAVE_TIMES = 10;
	
	/** After how many mouse clicks save to file */
	private int SAVE_TIMES_MOUSE = 10;
	
	private int keyDown = -1;
	private int mouseClicks = 0;
	private int[] list = new int[SAVE_TIMES];
	private int listcounter = 0;



	
	
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

	private void addKey(int key) {
		list[listcounter++] = key;
		if(listcounter==list.length) {
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < list.length; i++) {
				output.append(String.valueOf(list[i])+";");
			}
			output.append("\r\n");
			try {
				FileUtils.writeStringToFile(new File("log.txt"), output.toString(), (Charset) null, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			listcounter = 0;
		}
	}
	
	private void addMouseClick() {
		mouseClicks++;
		if(mouseClicks%SAVE_TIMES_MOUSE == 0) {
			try {
				FileUtils.writeStringToFile(new File("mouse.txt"), String.valueOf(mouseClicks), (Charset) null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getRawCode() != keyDown) {
			System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode())+" ("+e.getKeyCode()+", "+e.getRawCode()+")" );
			addKey(e.getRawCode());
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
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		System.out.println(mouseClicks);
		addMouseClick();
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
