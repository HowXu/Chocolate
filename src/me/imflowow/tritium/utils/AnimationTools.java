package me.imflowow.tritium.utils;

public class AnimationTools {
    public static float clamp(float number, float min, float max) {
        return number < min ? min : Math.min(number, max);
    }
}

