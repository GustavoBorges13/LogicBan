package br.ufcat.logicban.main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private Map<Integer, Clip> clipMap = new HashMap<>();
    private URL soundURL[] = new URL[30];
    private FloatControl fc;
    public int volumeScale;
    private float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/assets/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/assets/sound/coin.wav");
        soundURL[2] = getClass().getResource("/assets/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/assets/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/assets/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/assets/sound/menu_music.wav");
        soundURL[6] = getClass().getResource("/assets/sound/cursor.wav");
        soundURL[7] = getClass().getResource("/assets/sound/chipwall.wav");
        soundURL[8] = getClass().getResource("/assets/sound/stairs.wav");
        soundURL[9] = getClass().getResource("/assets/sound/Dungeon.wav");
        soundURL[10] = getClass().getResource("/assets/sound/desert.wav");
        soundURL[11] = getClass().getResource("/assets/sound/Merchant.wav");
        soundURL[12] = getClass().getResource("/assets/sound/sf1.wav");
        soundURL[13] = getClass().getResource("/assets/sound/sf6.wav");
    }

    public void setFile(int i) {
        try {
            if (!clipMap.containsKey(i)) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                clipMap.put(i, clip);
            }
            checkVolume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(int i) {
        Clip clip = clipMap.get(i);
        if (clip != null) {
            clip.setFramePosition(0); // Reinicia o som
            clip.start();
        }
    }

    public void loop(int i) {
        Clip clip = clipMap.get(i);
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        for (Clip clip : clipMap.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop(); // Para o clip se estiver em execução
            }
        }
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        for (Clip clip : clipMap.values()) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                fc.setValue(volume);
            }
        }
    }

    public void closeAllClips() {
        for (Clip clip : clipMap.values()) {
            clip.stop();
            clip.close();
        }
        clipMap.clear();
    }
}