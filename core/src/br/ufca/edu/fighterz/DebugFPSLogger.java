package br.ufca.edu.fighterz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.TimeUtils;

public final class DebugFPSLogger extends FPSLogger {
    public enum Type {
        CONSOLE,
        WINDOW
    }

    private final Type type;
    private final String title;
    private final long startTime;

    public DebugFPSLogger(Type type, String title) {
        this.type = type;
        this.title = title;
        startTime = TimeUtils.nanoTime();
    }

    @Override
    public void log() {
        // 1_000_000_000 == 1 second
        if (TimeUtils.nanoTime() - startTime > 1_000_000_000) {
            if (type == Type.CONSOLE) {
                Gdx.app.log("FPSLogger", "FPS: " + Gdx.graphics.getFramesPerSecond());
                return;
            }
            Gdx.graphics.setTitle(String.format("%s - FPS: %d", title, Gdx.graphics.getFramesPerSecond()));
        }
        super.log();
    }
}
