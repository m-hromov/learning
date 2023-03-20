public class Utils {
    public static boolean isAllPositiveNumbers(String... strs) {
        boolean isAllPositiveNumbers = true;
        for (String str : strs) {
            isAllPositiveNumbers = isAllPositiveNumbers && StringUtils.isPositiveNumber(str);
        }
        return isAllPositiveNumbers;
    }
}
