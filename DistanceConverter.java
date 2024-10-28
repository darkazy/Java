public class DistanceConverter {
    public static double footToMeter(double foot) {
        return 0.305 * foot;
        }
        
    public static double meterToFoot(double meter) {
        return meter * 0.305;
        }
    
    public static void main(String[] args) {
        System.out.printf("%-10s%-10s%n", "Feet", "Meters");
        for (double foot = 1.0; foot <= 10.0; foot++) {
            double meter = footToMeter(foot);
            System.out.printf("%-10.1f%-10.3f%n", foot, meter);
            }

        System.out.printf("%-10s%-10s%n", "Meters", "Feet");
        for (double meter = 20.0; meter <= 65.0; meter += 5.0) {
            double feet = meterToFoot(meter);
            System.out.printf("%-10.1f%-10.3f%n", meter, feet);
        }
    }
        
}