package com.vaadin.incubator.simpleshop.util;

import com.vaadin.incubator.simpleshop.data.LogMsg;
import com.vaadin.incubator.simpleshop.data.LogMsg.LogLevel;
import com.vaadin.incubator.simpleshop.facade.FacadeFactory;

public class LogUtil {

    public static boolean LOG_TO_CONSOLE = true;

    public static void debug(Exception e) {
        if (LOG_TO_CONSOLE) {
            e.printStackTrace();
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        LogMsg log = createLog(e.getMessage(), stackTraceElements);
        log.setLevel(LogLevel.DEBUG);
        FacadeFactory.getFacade().store(log);
    }

    public static void debug(String msg) {
        if (LOG_TO_CONSOLE) {
            System.out.println(msg);
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        LogMsg log = createLog(msg, stackTraceElements);
        log.setLevel(LogLevel.DEBUG);
        FacadeFactory.getFacade().store(log);
    }

    public static void warning(String msg) {
        if (LOG_TO_CONSOLE) {
            System.out.println(msg);
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        LogMsg log = createLog(msg, stackTraceElements);
        log.setLevel(LogLevel.WARNING);
        FacadeFactory.getFacade().store(log);
    }

    public static void error(Exception e) {
        if (LOG_TO_CONSOLE) {
            e.printStackTrace();
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        LogMsg log = createLog(e.getMessage(), stackTraceElements);
        log.setLevel(LogLevel.ERROR);
        FacadeFactory.getFacade().store(log);
    }

    public static void error(String msg) {
        if (LOG_TO_CONSOLE) {
            System.out.println(msg);
        }

        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();

        LogMsg log = createLog(msg, stackTraceElements);
        log.setLevel(LogLevel.ERROR);
        FacadeFactory.getFacade().store(log);
    }

    private static LogMsg createLog(String msg, StackTraceElement[] elements) {
        LogMsg log = new LogMsg();
        boolean next = false;

        for (StackTraceElement element : elements) {
            if (next) {
                log.setClassName(element.getClassName());
                log.setMethodName(element.getMethodName());
                log.setLineNumber(element.getLineNumber());
                break;
            }

            if (element.getClassName().equals(LogUtil.class.getName())) {
                next = true;
            }
        }

        log.setMessage(msg);
        return log;
    }

}
