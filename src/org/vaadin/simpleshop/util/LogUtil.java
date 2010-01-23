package org.vaadin.simpleshop.util;

import org.vaadin.simpleshop.data.LogMsg;
import org.vaadin.simpleshop.data.LogMsg.LogLevel;
import org.vaadin.simpleshop.facade.FacadeFactory;

/**
 * Logging utility class.
 * 
 * @author Kim
 * 
 */
public class LogUtil {

    // Define whether or not the log message should be printed to the console
    public static boolean LOG_TO_CONSOLE = true;

    /**
     * Log an exception as e debug-level message
     * 
     * @param e
     */
    public static void debug(Exception e) {
        // Print the stack trace if console logging is enabled
        if (LOG_TO_CONSOLE) {
            e.printStackTrace();
        }

        // Get the stacktrace of the call TO THIS METHOD
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // Create a log message
        LogMsg log = createLog(e.getMessage(), stackTraceElements);
        // Set the level of the log message
        log.setLevel(LogLevel.DEBUG);

        // Store the log message
        FacadeFactory.getFacade().store(log);
    }

    /**
     * Log a string message as debug level
     * 
     * @param msg
     */
    public static void debug(String msg) {
        // Print the message if console logging is enabled
        if (LOG_TO_CONSOLE) {
            System.out.println(msg);
        }

        // Get the stacktrace of the call TO THIS METHOD
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // Create a log message
        LogMsg log = createLog(msg, stackTraceElements);
        // Set the level of the log message
        log.setLevel(LogLevel.DEBUG);

        // Store the log message
        FacadeFactory.getFacade().store(log);
    }

    /**
     * Log a string message as warning level
     * 
     * @param msg
     */
    public static void warning(String msg) {
        // Print the message if console logging is enabled
        if (LOG_TO_CONSOLE) {
            System.out.println(msg);
        }

        // Get the stacktrace of the call TO THIS METHOD
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // Create a log message
        LogMsg log = createLog(msg, stackTraceElements);
        // Set the level of the log message
        log.setLevel(LogLevel.WARNING);

        // Store the log message
        FacadeFactory.getFacade().store(log);
    }

    /**
     * Log an exception as a warning level message
     * 
     * @param e
     */
    public static void warning(Exception e) {
        // Print the stack trace if console logging is enabled
        if (LOG_TO_CONSOLE) {
            e.printStackTrace();
        }

        // Get the stacktrace of the call TO THIS METHOD
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // Create a log message
        LogMsg log = createLog(e.getMessage(), stackTraceElements);
        // Set the level of the log message
        log.setLevel(LogLevel.WARNING);

        // Store the log message
        FacadeFactory.getFacade().store(log);
    }

    /**
     * Log an exception as an error level message
     * 
     * @param e
     */
    public static void error(Exception e) {
        // Print the stack trace if console logging is enabled
        if (LOG_TO_CONSOLE) {
            e.printStackTrace();
        }

        // Get the stacktrace of the call TO THIS METHOD
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // Create a log message
        LogMsg log = createLog(e.getMessage(), stackTraceElements);
        // Set the level of the log message
        log.setLevel(LogLevel.ERROR);

        // Store the log message
        FacadeFactory.getFacade().store(log);
    }

    /**
     * Log a string message as error level
     * 
     * @param msg
     */
    public static void error(String msg) {
        if (LOG_TO_CONSOLE) {
            System.out.println(msg);
        }

        // Get the stacktrace of the call TO THIS METHOD
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        // Create a log message
        LogMsg log = createLog(msg, stackTraceElements);
        // Set the level of the log message
        log.setLevel(LogLevel.ERROR);

        // Store the log message
        FacadeFactory.getFacade().store(log);
    }

    /**
     * Find the place where the logger was called and creates the log message
     * 
     * @param msg
     *            The message we want to log
     * @param elements
     *            StackTrace to this LogUtil call
     * @return
     */
    private static LogMsg createLog(String msg, StackTraceElement[] elements) {
        // Create a new instance of the LogMsg entity
        LogMsg log = new LogMsg();

        // Inner variable used for the next loop
        boolean next = false;

        // Loop through the stack trace and search the place from where LogUtil
        // was called
        for (StackTraceElement element : elements) {
            // If next is true, then we know that this element contains the
            // information we need.
            if (next) {
                log.setClassName(element.getClassName());
                log.setMethodName(element.getMethodName());
                log.setLineNumber(element.getLineNumber());
                break;
            }

            // If the element's class is this class, then we know that the next
            // element in the stack trace is the place form where the log
            // request originated.
            if (element.getClassName().equals(LogUtil.class.getName())) {
                next = true;
            }
        }

        // Set message and return log
        log.setMessage(msg);
        return log;
    }

}
