package br.ufca.edu.fighterz.state;

import br.ufca.edu.fighterz.interfaces.PlayerStateBehavior;

public final class PlayerState implements PlayerStateBehavior {
    public float stateTime = 0f;
    public float idleTimer = 0f;
    public float currentStateTime = 0f;
    public float autoTauntStateTime = 0f;
    public boolean shouldPlayAutoTauntAnimation = false;
    public boolean isWalkingLeft = false;
    public boolean isWalkingRight = false;
    public boolean isCrouching = false;
    public boolean isFacingRight = true;
    public boolean isLightPunching = false;
    public boolean isStrongPunching = false;
    public boolean isLightKicking = false;
    public boolean isStrongKicking = false;
    public boolean isAttacking = false;
    public boolean isGettingHit = false;
    public boolean isBlocking = false;
    public boolean isPlayerInputLocked = false;
    public boolean isDoingSpecial0 = false;
    public boolean isDoingSpecial1 = false;
    public boolean isDoingSpecial2 = false;
    public float life = 200;
    public boolean isDefeated = false;
    public boolean isWinning = false;

    @Override
    public void resetAllStates() {
        stateTime = 0f;
        idleTimer = 0f;
        currentStateTime = 0f;
        autoTauntStateTime = 0f;
        shouldPlayAutoTauntAnimation = false;
        isWalkingLeft = false;
        isWalkingRight = false;
        isCrouching = false;
        isFacingRight = true;
        isLightPunching = false;
        isStrongPunching = false;
        isLightKicking = false;
        isStrongKicking = false;
        isAttacking = false;
        isGettingHit = false;
        isBlocking = false;
        isPlayerInputLocked = false;
        isDoingSpecial0 = false;
        isDoingSpecial1 = false;
        isDoingSpecial2 = false;
        life = 200;
        isDefeated = false;
        isWinning = false;
    }
}
