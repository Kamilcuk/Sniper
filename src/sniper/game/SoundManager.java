/*
 * Copyright (C) 2015 Kamil Cukrowski
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package sniper.game;

import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Odpowiedzialne za odwarzanie muzyki.
 * @author Kamil Cukrowski
 */
public class SoundManager {
    private static ExecutorService soundPool = Executors.newFixedThreadPool(100);
    private static final Map<String, AudioClip> soundEffectsMap = new HashMap<>();
	
	/**
	 * Ustawia ilość threadów odpowiedzzialnych za odtwarzanie muzyki.
	 * @param numberOfThreads 
	 */
	public static void setNumberOfThreads(int numberOfThreads) {
        soundPool = Executors.newFixedThreadPool(numberOfThreads);
	}

	/**
	 * przypisuje id określonemu dźwiękowi.
	 * @param id
	 * @param fileName ścieżka do pliku dźwiękowego
	 */
    public static void loadSoundEffects(String id, String fileName) {
		//URL url = getClass().getClassLoader().getResource(fileName);
        AudioClip sound = new AudioClip(fileName);
        soundEffectsMap.put(id, sound);
    }

	/**
	 * Odtwarza wcześniej przypisaną id muzykę z ustawioną głośnością;
	 * @param id pliku dźwiękowego
	 * @param volume głośćność
	 */
    public static void playSound(final String id, double volume) {
        Runnable soundPlay = () -> {
            soundEffectsMap.get(id).play(volume);
        };
        soundPool.execute(soundPlay);
    }
	/**
	 * Odtwarza wcześniej przypisaną id muzykę z defaultową głośnością.
	 * @param id plik dźwiękowego
	 */
    public static void playSound(final String id) {
		SoundManager.playSound(id, 100);
    }

	/**
	 * Wyłącza
	 */
    public static void shutdown() {
        soundPool.shutdown();
    }
}
