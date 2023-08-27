/*
 * Author: Oleksiy Zhytnetsky
 * File: enums.ValidationModes.java
 * Problem description:
 * 1) rho = b + a*cos(phi)
 * 2) x = rho * cos(phi)
 * 3) y = rho * sin(phi)
 * 4) phi = angle range constant, user-set (consider | test limiting?)
 * 5) a = constant, user-set
 * 6) b = constant, user-set
 */

package enums;

public enum ValidationModes {
    REAL_NUMBER,
    RANGE
}
