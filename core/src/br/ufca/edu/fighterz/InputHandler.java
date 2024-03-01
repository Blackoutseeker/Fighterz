package br.ufca.edu.fighterz;

import br.ufca.edu.fighterz.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
public final class InputHandler {
    private float idleTimer = 0f;
    private boolean isWalkingLeft;
    private boolean isWalkingRight;
    private boolean isStrongPunching = false;
    private boolean isLightPunching = false;
    private boolean isLightKicking = false;
    private boolean isStrongKicking = false;
    private boolean isCrouching;
    private boolean isPlayerInputLocked = false;
    //    private final boolean isAirCrouching;
    //    private final boolean isAirLightPunching;
    //    private final boolean isAirStrongPunching;
    private Character character;

    public boolean getIsStrongKicking() {
        return isStrongKicking;
    }

    public boolean getIsLightKicking() {
        return isLightKicking;
    }

    public boolean getIsStrongPunching() {
        return isStrongPunching;
    }

    public boolean getIsLightPunching() {
        return isLightPunching;
    }

    public void setIsLightPunching(boolean state) {
        isLightPunching = state;
    }

    public boolean getIsWalkingLeft() {
        return isWalkingLeft;
    }

    public boolean getIsWalkingRight() {
        return isWalkingRight;
    }

    public boolean getIsCrouching() {
        return isCrouching;
    }

    public InputHandler(boolean isLightPunching, boolean isStrongKicking, boolean isLightKicking, float idleTimer, boolean isWalkingLeft, boolean isWalkingRight, boolean isCrouching, boolean isStrongPunching) {
        this.idleTimer = idleTimer;
        this.isWalkingLeft = isWalkingLeft;
        this.isWalkingRight = isWalkingRight;
        this.isCrouching = isCrouching;
        this.isLightPunching = isLightPunching;
        this.isStrongPunching = isStrongPunching;
        this.isLightKicking = isLightKicking;
        this.isStrongKicking = isStrongKicking;
    }

    public void handleInput(float deltaTime, boolean shouldPlayIdleAnimation) {
        isPlayerInputLocked = Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.W);
        if (!isPlayerInputLocked) {
            if (Gdx.input.isKeyPressed(Input.Keys.A) && !isLightPunching) {
                isWalkingLeft = true;
                isWalkingRight = false;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) && !isLightPunching) {
                isWalkingLeft = false;
                isWalkingRight = true;
            } else {
                isWalkingLeft = false;
                isWalkingRight = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.K) && !isLightPunching) {
                isLightPunching = true;
            }

            isCrouching = Gdx.input.isKeyPressed(Input.Keys.S) && !isLightPunching;
            // AUTO_TAUNT
            if (!isWalkingLeft && !isWalkingRight && !isCrouching && !isLightPunching) {
                idleTimer += deltaTime;
            } else {
                idleTimer = 0f;
                shouldPlayIdleAnimation = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.J)) {
                isStrongPunching = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                isLightKicking = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                isStrongKicking = true;
            } else {
                if (Gdx.input.isKeyPressed(Input.Keys.E) && Gdx.input.isKeyPressed(Input.Keys.Q)) {
                    Gdx.app.log("Usou o super", "Show de bola");
                }


            }
        }
    }
}