package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.state.PlayerState;
import br.ufca.edu.fighterz.audio.AudioManager;

import com.badlogic.gdx.Input;

public class Input2 extends InputHandler {
    public Input2(final PlayableCharacter playableCharacter,
                  final PlayerState playerState,
                  final AudioManager audioManager) {
        super(playableCharacter, playerState, audioManager,
                new int[] {Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.DOWN},
                new int[] {Input.Keys.NUMPAD_5, Input.Keys.NUMPAD_4, Input.Keys.NUMPAD_6, Input.Keys.NUMPAD_8},
                new int[] {Input.Keys.NUMPAD_1, Input.Keys.NUMPAD_2, Input.Keys.NUMPAD_3}
        );
    }
}
