package org.vaadin.simpleshop.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * Entity class for log messages.
 * 
 * @author Kim
 * 
 */
@Entity
public class LogMsg extends AbstractPojo {

    private static final long serialVersionUID = -5069487222956859611L;

    /** Log levels for the message **/
    public static enum LogLevel {
        DEBUG,
        WARNING,
        ERROR;
    }

    @Column(length = 3000)
    private String message;

    @Enumerated(EnumType.STRING)
    private LogLevel level;

    @Column(length = 1000)
    private String className;

    @Column(length = 500)
    private String methodName;

    private int lineNumber;

    public LogMsg() {

    }

    /**
     * Get the log message.
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message for this log.
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the log level of this log message
     * 
     * @return
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Set the log level of this message
     * 
     * @param level
     */
    public void setLevel(LogLevel level) {
        this.level = level;
    }

    /**
     * Returns the class name of the class from where the logger was called -
     * class name from where the log message originated.
     * 
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name of the class from where the logger was called.
     * 
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Returns the method name of the method which called the logger - method
     * name from where the log message originated.
     * 
     * @return
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets the method name of the method which called the logger.
     * 
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Get the source file line number from where the logger was called - the
     * line where the log message originated.
     * 
     * @return
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Set the source file line number from where the logger was called.
     * 
     * @param lineNumber
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

}
