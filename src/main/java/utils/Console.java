package utils;

/**
 * Logger class
 */
public class Console {
    /**
     * Display a log message in console
     * @param sourceClass Object
     * @param message String
     */
    public static void log(Object sourceClass, String message){
        System.out.println("log:                                                               " + sourceClass.getClass().getSimpleName() + ":   " + message);
    }

    /**
     * Display an error message in console
     * @param sourceClass Object
     * @param message String
     */
    public static void error(Object sourceClass, String message){
        System.out.println("error:                                                             " + sourceClass.getClass().getSimpleName() + message);
    }
}
