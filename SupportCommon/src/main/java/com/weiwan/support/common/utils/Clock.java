/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.weiwan.support.common.utils;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Date: 2019/12/03
 * Company: www.dtstack.com
 * <p>
 * this class is copied from (https://github.com/debezium/debezium).
 *
 * @author tudou
 */
public interface Clock {


    static final com.weiwan.common.utils.Clock SYSTEM = new com.weiwan.common.utils.Clock() {
        @Override
        public long currentTimeInMillis() {
            return System.currentTimeMillis();
        }

        @Override
        public long currentTimeInNanos() {
            return System.nanoTime();
        }

        @Override
        public Instant currentTimeAsInstant() {
            return Instant.now();
        }
    };

    /**
     * Get the {@link com.weiwan.common.utils.Clock} instance that uses the {@link System} methods.
     * @return the system clock; never null
     */
    static com.weiwan.common.utils.Clock system() {
        return SYSTEM;
    }

    default Instant currentTime() {
        return Instant.ofEpochMilli(currentTimeInMillis());
    }
    /**
     * Get the current time in nanoseconds.
     * @return the current time in nanoseconds.
     */
    default long currentTimeInNanos() {
        return currentTimeInMillis() * 1000000L;
    }

    /**
     * Get the current time in microseconds.
     * @return the current time in microseconds.
     */
    default long currentTimeInMicros() {
        return TimeUnit.MICROSECONDS.convert(currentTimeInMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Get the current time as an instant
     *
     * @return the current time as an instant.
     */
    default Instant currentTimeAsInstant() {
        return Instant.ofEpochMilli(currentTimeInMillis());
    }

    /**
     * Get the current time in milliseconds.
     * @return the current time in milliseconds.
     */
    public long currentTimeInMillis();

}