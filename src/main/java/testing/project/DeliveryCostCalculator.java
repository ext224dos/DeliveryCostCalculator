package testing.project;

public class DeliveryCostCalculator {
    static private double deliveryCost;
    static private final double MINIMAL_DELIVERY_COST = 400.0;
    static private final double MAXIMUM_DISTANCE_FOR_FRAGILE_DELIVERY = 30;
    static private final double OVERHEAD_PRICE_FOR_FRAGILE = 300.0;
    static private final double COEFF_OVERHEAD_PRICE_FOR_VERY_HIGH_LOAD_FACTOR = 1.6;
    static private final double COEFF_OVERHEAD_PRICE_FOR_HIGH_LOAD_FACTOR = 1.4;
    static private final double COEFF_OVERHEAD_PRICE_FOR_INCREASED_LOAD_FACTOR = 1.2;
    static private final double OVERHEAD_PRICE_FOR_MORE_THAN_30_KM = 300.0;
    static private final double OVERHEAD_PRICE_FOR_MORE_THAN_10_KM = 200.0;
    static private final double OVERHEAD_PRICE_FOR_MORE_THAN_2_KM = 100.0;
    static private final double OVERHEAD_PRICE_FOR_LESS_THAN_2_KM = 50.0;
    static private final double OVERHEAD_PRICE_FOR_BIG_SIZE = 200.0;
    static private final double OVERHEAD_PRICE_FOR_SMALL_SIZE = 100.0;

    public static double calculateDeliveryCost(double distance, Size size, Fragility fragility, DeliveryLoadLevel deliveryLoadLevel) {
        deliveryCost = 0;

        if (distance <= 0) return -1;

        if (distance > 30) {
            deliveryCost += OVERHEAD_PRICE_FOR_MORE_THAN_30_KM;
        } else if (distance > 10) {
            deliveryCost += OVERHEAD_PRICE_FOR_MORE_THAN_10_KM;
        } else if (distance > 2) {
            deliveryCost += OVERHEAD_PRICE_FOR_MORE_THAN_2_KM;
        } else {
            deliveryCost += OVERHEAD_PRICE_FOR_LESS_THAN_2_KM;
        }

        if (fragility == Fragility.YES && distance >= MAXIMUM_DISTANCE_FOR_FRAGILE_DELIVERY) {
            return -1;
        }

        if (fragility == Fragility.YES) {
            deliveryCost += OVERHEAD_PRICE_FOR_FRAGILE;
        }

        switch (size) {
            case BIG:
                deliveryCost += OVERHEAD_PRICE_FOR_BIG_SIZE;
                break;
            case SMALL:
                deliveryCost += OVERHEAD_PRICE_FOR_SMALL_SIZE;
                break;
        }

        switch (deliveryLoadLevel) {
            case NORMAL:
                break;
            case VERY_HIGH:
                deliveryCost *= COEFF_OVERHEAD_PRICE_FOR_VERY_HIGH_LOAD_FACTOR;
                break;
            case HIGH:
                deliveryCost *= COEFF_OVERHEAD_PRICE_FOR_HIGH_LOAD_FACTOR;
                break;
            case INCREASED:
                deliveryCost *= COEFF_OVERHEAD_PRICE_FOR_INCREASED_LOAD_FACTOR;
                break;
        }

        return (double) (Math.round(Math.max(deliveryCost, MINIMAL_DELIVERY_COST)) * 100) * 0.01;
    }
}
