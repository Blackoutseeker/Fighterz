package br.ufca.edu.fighterz.state;

public class PlayerState {
    public float stateTime = 0f;
    public float idleTimer = 0f;
    public float currentStateTime = 0f;
    public boolean shouldPlayIdleAnimation = false;
    public boolean isWalkingLeft = false;
    public boolean isWalkingRight = false;
    public boolean isCrouching = false;
    public boolean isFacingRight = true;
    public boolean isLightPunching = false;
    public  boolean isStrongPunching = false;
    public  boolean isLightKicking = false;
    public  boolean isStrongKicking = false;
    public boolean isAttacking = false;
    public boolean isGettingHit = false;
    public boolean isPlayerInputLocked = false;
    public boolean isDoingSpecial0 = false;
    public boolean isDoingSpecial1 = false;
    public boolean isDoingSpecial2 = false;

    public PlayerState() {}

    public PlayerState(boolean isFacingRight) {
        this.isFacingRight = isFacingRight;
    }
}
