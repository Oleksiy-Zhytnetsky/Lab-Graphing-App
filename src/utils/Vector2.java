/*
 * Author: Oleksiy Zhytnetsky
 * File: utils.Vector2.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package utils;

public class Vector2<T1, T2> {
    public Vector2(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public final T1 first;
    public final T2 second;
}
