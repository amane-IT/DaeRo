package com.ssafy.daero.util;

public class DistanceUtil {
    public static class ReverseHaversine {
        private ReverseHaversine() {
        }

        public static double[] calculateCoordinate(double latitude, double longitude, int distance) {
            double dif = distance * 0.007;
            return new double[]{latitude - dif, latitude + dif, longitude - dif, longitude + dif};
        }
    }
}
