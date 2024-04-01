package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.state.PlayerState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public final class InputHandler {
    private final PlayerState playerState;

    public InputHandler(final PlayerState playerState) {
        this.playerState = playerState;
    }

    public void handleInput(final float deltaTime) {
        playerState.isPlayerInputLocked = Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.W);
        if (!playerState.isPlayerInputLocked) {
            if (Gdx.input.isKeyPressed(Input.Keys.A) && !playerState.isAttacking) {
                playerState.isWalkingLeft = true;
                playerState.isWalkingRight = false;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.D) && !playerState.isAttacking) {
                playerState.isWalkingLeft = false;
                playerState.isWalkingRight = true;
            }
            else {
                playerState.isWalkingLeft = false;
                playerState.isWalkingRight = false;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.K) && !playerState.isAttacking) {
                playerState.isLightPunching = true;
                playerState.isAttacking = true;
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.J) && !playerState.isAttacking) {
                playerState.isStrongPunching = true;
                playerState.isAttacking = true;
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.L) && !playerState.isAttacking) {
                playerState.isLightKicking = true;
                playerState.isAttacking = true;
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.I) && !playerState.isAttacking) {
                playerState.isStrongKicking = true;
                playerState.isAttacking = true;
            }

            playerState.isCrouching = Gdx.input.isKeyPressed(Input.Keys.S) && !playerState.isAttacking;

            if (!playerState.isWalkingLeft && !playerState.isWalkingRight
                    && !playerState.isCrouching && !playerState.isAttacking) {
                playerState.idleTimer += deltaTime;
            }
            else {
                playerState.idleTimer = 0f;
                playerState.shouldPlayIdleAnimation = false;
            }
        }
    }
}
