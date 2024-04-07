package br.ufca.edu.fighterz.input;

import br.ufca.edu.fighterz.PlayableCharacter;
import br.ufca.edu.fighterz.state.PlayerState;
import br.ufca.edu.fighterz.audio.AudioManager;

import com.badlogic.gdx.Input;

public final class Input1 extends InputHandler {
    public Input1(final PlayableCharacter playableCharacter,
                  final PlayerState playerState,
                  final AudioManager audioManager) {
        super(playableCharacter, playerState, audioManager,
                new int[] {Input.Keys.A, Input.Keys.D, Input.Keys.S},
                new int[] {Input.Keys.K, Input.Keys.J, Input.Keys.L, Input.Keys.I},
                new int[] {Input.Keys.U, Input.Keys.O, Input.Keys.P}
        );
    }
}
