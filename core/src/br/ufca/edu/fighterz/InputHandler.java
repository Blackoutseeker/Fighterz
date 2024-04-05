package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.state.PlayerState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public final class InputHandler {
    private final PlayerState playerState;
    private final AudioManager audioManager;
    private final boolean isSecondPlayer;

    public InputHandler(final PlayerState playerState, AudioManager audioManager, boolean isSecondPlayer) {
        this.playerState = playerState;
        this.audioManager = audioManager;
        this.isSecondPlayer = isSecondPlayer;
    }

    public void handleInput(final float deltaTime) {
        playerState.isPlayerInputLocked = Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.W);
        if (!playerState.isPlayerInputLocked) {
            if ((Gdx.input.isKeyPressed(Input.Keys.A) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.LEFT) && isSecondPlayer) && !playerState.isAttacking) {
                playerState.isWalkingLeft = true;
                playerState.isWalkingRight = false;
            } else if ((Gdx.input.isKeyPressed(Input.Keys.D) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && isSecondPlayer) && !playerState.isAttacking) {
                playerState.isWalkingLeft = false;
                playerState.isWalkingRight = true;
            } else {
                playerState.isWalkingLeft = false;
                playerState.isWalkingRight = false;
            }
            
            if (!playerState.isAttacking) {
                if ((Gdx.input.isKeyJustPressed(Input.Keys.K) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5) && isSecondPlayer)) {
                    if(!isSecondPlayer)
                        audioManager.playCharacterSound(0, 0);
                    else
                        audioManager.playCharacterSound(1,0);
                    playerState.isLightPunching = true;
                    playerState.isAttacking = true;
                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.J) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4) && isSecondPlayer)) {
                    if(!isSecondPlayer)
                        audioManager.playCharacterSound(0, 1);
                    else
                        audioManager.playCharacterSound(1, 1);
                    playerState.isStrongPunching = true;
                    playerState.isAttacking = true;
                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.L) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6) && isSecondPlayer)) {
                    if(!isSecondPlayer)
                        audioManager.playCharacterSound(0,2);
                    else
                        audioManager.playCharacterSound(1, 2);
                    playerState.isLightKicking = true;
                    playerState.isAttacking = true;
                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.I) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8) && isSecondPlayer)) {
                    if(!isSecondPlayer)
                        audioManager.playCharacterSound(0,3);
                    else
                        audioManager.playCharacterSound(1, 3);
                    playerState.isStrongKicking = true;
                    playerState.isAttacking = true;
                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.U) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1) && isSecondPlayer)) {
                    if(!isSecondPlayer)
                        audioManager.playCharacterSound(0,7);
                    else
                        audioManager.playCharacterSound(1,7);
                    playerState.isDoingSpecial0 = true;
                    playerState.isAttacking = true;
                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.O) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2) && isSecondPlayer)) {
                    if(isSecondPlayer)
                        audioManager.playCharacterSound(0,8);
                    else
                        audioManager.playCharacterSound(1, 8);
                    playerState.isDoingSpecial1 = true;
                    playerState.isAttacking = true;
                } else if ((Gdx.input.isKeyJustPressed(Input.Keys.P) && !isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_3) && isSecondPlayer)) {
                    if(!isSecondPlayer)
                        audioManager.playCharacterSound(0,9);
                    else
                        audioManager.playCharacterSound(1,9);
                    playerState.isDoingSpecial2 = true;
                    playerState.isAttacking = true;
                }
            }


                playerState.isCrouching = (Gdx.input.isKeyPressed(Input.Keys.S) &&!isSecondPlayer) || (Gdx.input.isKeyPressed(Input.Keys.DOWN) && isSecondPlayer) && !playerState.isAttacking;

                if (!playerState.isWalkingLeft && !playerState.isWalkingRight
                        && !playerState.isCrouching && !playerState.isAttacking) {
                    playerState.idleTimer += deltaTime;
                } else {
                    playerState.idleTimer = 0f;
                    playerState.shouldPlayIdleAnimation = false;
                }
            }
        }
}