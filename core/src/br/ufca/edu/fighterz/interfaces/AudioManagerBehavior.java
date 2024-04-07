package br.ufca.edu.fighterz.interfaces;

import br.ufca.edu.fighterz.PlayableCharacter;

public interface AudioManagerBehavior {
    void load();
    void playCharacterSound(PlayableCharacter playableCharacter, int soundIndex);
    void playBackgroundMusic();
    void dispose();
}
