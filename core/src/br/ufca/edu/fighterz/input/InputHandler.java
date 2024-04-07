package br.ufca.edu.fighterz.input;

import br.ufca.edu.fighterz.PlayableCharacter;
import br.ufca.edu.fighterz.interfaces.KeyboardInputProcessor;
import br.ufca.edu.fighterz.state.PlayerState;
import br.ufca.edu.fighterz.audio.AudioManager;

import com.badlogic.gdx.Gdx;

public abstract class InputHandler implements KeyboardInputProcessor {
    private final PlayableCharacter playableCharacter;
    private final PlayerState playerState;
    private final AudioManager audioManager;
    private final int[] movementKeys;
    private final int[] attackKeys;
    private final int[] specialAttacksKeys;

    InputHandler(final PlayableCharacter playableCharacter,
                 final PlayerState playerState, final AudioManager audioManager,
                 final int[] movementKeys, final int[] attackKeys,
                 final int[] specialAttacksKeys) {
        this.playableCharacter = playableCharacter;
        this.playerState = playerState;
        this.audioManager = audioManager;
        this.movementKeys = movementKeys;
        this.attackKeys = attackKeys;
        this.specialAttacksKeys = specialAttacksKeys;
    }

    private void handleMovementKeys() {
        if (Gdx.input.isKeyPressed(movementKeys[0]) && !playerState.isAttacking) {
            playerState.isWalkingLeft = true;
            playerState.isWalkingRight = false;
            resetAutoTauntAnimation();
        }
        else if (Gdx.input.isKeyPressed(movementKeys[1]) && !playerState.isAttacking) {
            playerState.isWalkingLeft = false;
            playerState.isWalkingRight = true;
            resetAutoTauntAnimation();
        }
        else {
            playerState.isWalkingLeft = false;
            playerState.isWalkingRight = false;
        }
        if (Gdx.input.isKeyPressed(movementKeys[2]) && !playerState.isAttacking) {
            playerState.isCrouching = true;
            resetAutoTauntAnimation();
        }
        else playerState.isCrouching = false;
    }

    private void handleAttackKeys() {
        if (Gdx.input.isKeyJustPressed(attackKeys[0])) {
            audioManager.playCharacterSound(playableCharacter, 0);
            playerState.isLightPunching = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
        else if (Gdx.input.isKeyJustPressed(attackKeys[1])) {
            audioManager.playCharacterSound(playableCharacter, 1);
            playerState.isStrongPunching = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
        else if (Gdx.input.isKeyJustPressed(attackKeys[2])) {
            audioManager.playCharacterSound(playableCharacter, 2);
            playerState.isLightKicking = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
        else if (Gdx.input.isKeyJustPressed(attackKeys[3])) {
            audioManager.playCharacterSound(playableCharacter, 3);
            playerState.isStrongKicking = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
    }

    private void handleSpecialAttacksKeys() {
        if (Gdx.input.isKeyJustPressed(specialAttacksKeys[0])) {
            audioManager.playCharacterSound(playableCharacter,7);
            playerState.isDoingSpecial0 = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
        else if (Gdx.input.isKeyJustPressed(specialAttacksKeys[1])) {
            audioManager.playCharacterSound(playableCharacter, 8);
            playerState.isDoingSpecial1 = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
        else if (Gdx.input.isKeyJustPressed(specialAttacksKeys[2])) {
            audioManager.playCharacterSound(playableCharacter,9);
            playerState.isDoingSpecial2 = true;
            playerState.isAttacking = true;
            resetAutoTauntAnimation();
        }
    }

    @Override
    public void handleInput(float deltaTime) {
        if (!playerState.isPlayerInputLocked && !playerState.isGettingHit) {
            handleMovementKeys();
            if (!playerState.isAttacking) {
                handleAttackKeys();
                handleSpecialAttacksKeys();
            }
        }
        if (!playerState.isWalkingLeft && !playerState.isWalkingRight
                && !playerState.isCrouching && !playerState.isAttacking) {
            playerState.idleTimer += deltaTime;
        }
        else {
            resetAutoTauntAnimation();
        }
    }

    private void resetAutoTauntAnimation() {
        playerState.idleTimer = 0f;
        playerState.autoTauntStateTime = 0f;
        playerState.shouldPlayAutoTauntAnimation = false;
    }
}
