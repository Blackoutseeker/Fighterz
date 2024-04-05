package br.ufca.edu.fighterz;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;


public class AudioManager {

    private final AssetManager assetManager;
    private Music backgroundMusic;
    private Sound[][] characterSounds;

    public AudioManager() {
        assetManager = new AssetManager();
    }

    public void load () {
        try {
            loadSounds();
            loadMusic();
            assetManager.finishLoading();
        } catch (Exception e) {
            System.err.println("Erro ao carregar assets: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSounds() {

        characterSounds = new Sound[2][11];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 11; j++) {
                String characterName = (i == 0) ? "ryu" : "sol_badguy";
                String soundFileName = getSoundType(j) + ".mp3";
                AssetDescriptor<Sound> assetDescriptor = new AssetDescriptor<>(
                        "sounds/" +
                                characterName + "/" + soundFileName, Sound.class);
                assetManager.load(assetDescriptor);
            }
        }
    }

    private String getSoundType(int index) {
        switch (index) {
            case 0: return "light_punch";
            case 1: return "strong_punch";
            case 2: return "light_kick";
            case 3: return "strong_kick";
            case 4: return "getting_hit";
            case 5: return "getting_heavy_hit";
            case 6: return "death";
            case 7: return "special_one";   // ryu - Hadouken; sol_badguy - Gun_flame ISO
            case 8: return "special_two";   // ryu - Shoryuken; sol_badguy - Meteor SFX
            case 9: return "special_three"; // ryu - Tatsumaki_SenpuuKyaku; sol_badguy - Bandit revolver SFX
            case 10: return "taunt_one";
            case 11: return "taunt_two";
            default: return "";
        }
    }

    private void loadMusic() {

        AssetDescriptor<Music> assetDescriptor = new AssetDescriptor<>(
                "sounds/Theme_background.mp3", Music.class);
        assetManager.load(assetDescriptor);
    }

    public void playCharacterSound(int characterIndex, int soundIndex) {
        if (characterIndex >= 0 && characterIndex < characterSounds.length &&
                soundIndex >= 0 && soundIndex < characterSounds[characterIndex].length) {
            Sound sound = assetManager.get(
                    "sounds/" + ((characterIndex == 0) ?
                            "ryu" : "sol_badguy") + "/" + getSoundType(soundIndex) + ".mp3", Sound.class);
            sound.play(0.6f);
        }
    }

    public void playBackgroundMusic() {
        try {
            backgroundMusic = assetManager.get("sounds/Theme_background.mp3", Music.class);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.4f);
            backgroundMusic.play();
        } catch (Exception e) {
            System.err.println("Erro ao reproduzir backgroundMusic" + e.getMessage());
        }
    }
    public void dispose() {
        assetManager.dispose();
    }
}