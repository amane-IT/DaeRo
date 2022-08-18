package com.ssafy.daero.common.util;

public class DistanceUtil {
    public static class ReverseHaversine {
        private ReverseHaversine() {
        }

        public static double[] calculateCoordinate(double latitude, double longitude, double distance) {
            double dif = distance * 0.007;
            return new double[]{latitude - dif, latitude + dif, longitude - dif, longitude + dif};
        }
    }
}
