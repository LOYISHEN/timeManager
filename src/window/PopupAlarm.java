package window;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.HeadlessException;
import java.io.File;
import java.net.MalformedURLException;

public class PopupAlarm implements Runnable {

	public PopupAlarm(String alarmFileName) {
		try {
			this.audioClip = new Applet().newAudioClip(new File(alarmFileName).toURL());
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		this.audioClip.play();
	}
	
	private AudioClip audioClip;
}
