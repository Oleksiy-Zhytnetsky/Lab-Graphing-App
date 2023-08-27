/*
 * Author: Oleksiy Zhytnetsky
 * File: utils.ExtremePair.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package utils;

public final class ExtremePair {
    public ExtremePair(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public final float min, max;
}
