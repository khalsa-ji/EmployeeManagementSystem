//  Waheguru Ji!

package com.khalsa_ji.ems.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The class {@code SpringContext} <em>implements</em> {@code ApplicationContextAware}
 * and overrides the <code>setApplicationContext(ApplicationContext)</code> method.
 * It also provides a method to fetch the instance of {@code ApplicationContext},
 * currently being used.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see ApplicationContextAware
 * @see ApplicationContext
 */

@Component
public class SpringContext implements ApplicationContextAware {
    @Autowired
    static ApplicationContext context;

    /**
     * This is the custom definition for the overridden method <code>setApplicationContext</code> of the
     * {@code ApplicationContextAware} interface
     *
     * @param applicationContext An instance of the {@code ApplicationContext}
     * @throws BeansException An instance of the {@code BeansException}
     * @see ApplicationContext
     * @see ApplicationContextAware
     */

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Method to fetch an instance of the {@code ApplicationContext},
     * which refers to the current application's context
     *
     * @return An instance of the {@code ApplicationContext}
     * @see ApplicationContext
     */

    public static ApplicationContext getAppContext() {
        return context;
    }
}
