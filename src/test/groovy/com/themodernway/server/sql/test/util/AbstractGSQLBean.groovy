/*
 * Copyright (c) 2018, The Modern Way. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.themodernway.server.sql.test.util

import java.util.concurrent.atomic.AtomicBoolean

import org.slf4j.Logger

import com.themodernway.common.api.types.ICloseable
import com.themodernway.server.core.logging.IHasLogging
import com.themodernway.server.core.logging.LoggingOps
import com.themodernway.server.sql.GSQL
import com.themodernway.server.sql.support.GSQLSupport

import ch.qos.logback.classic.Level
import groovy.transform.CompileStatic
import groovy.transform.Memoized

@CompileStatic
public abstract class AbstractGSQLBean<T extends AbstractGSQLBean<T>> extends GSQLSupport implements ICloseable, IHasLogging {

    private final int               m_rows

    private final String            m_tabl

    private final AtomicBoolean     m_open = new AtomicBoolean(false)

    private final Logger            m_logs = LoggingOps.getLogger(getClass())

    protected AbstractGSQLBean(final int rows, final String tabl) {

        m_rows = Math.max(0, rows)

        m_tabl = requireTrimOrNull(tabl)
    }

    @Memoized
    public int getRows() {

        m_rows
    }

    @Memoized
    public String getTable() {

        m_tabl
    }

    @Memoized
    public String getIndex() {

        getTable() + "_index"
    }

    public T setup() {

        if (false == isOpen()) {

            clean()
        }
        m_open.set(true)

        if (logger().isDebugEnabled()) {

            logger().debug("setup(${isOpen()}).")
        }
        forTransaction(gsql()) { GSQL gsql ->

            create(gsql)
        }
        this as T
    }

    public abstract T create(final GSQL gsql)

    public abstract T delete(final GSQL gsql)

    public T clean() {

        if (logger().isDebugEnabled()) {

            logger().debug("clean(${isOpen()}).")
        }
        forTransaction(gsql()) { GSQL gsql ->

            delete(gsql)
        }
        this as T
    }

    public T level(Level level = Level.INFO) {

        LoggingOps.setLevel(logger(), level)

        this as T
    }

    @Override
    public void close() throws IOException {

        if (logger().isDebugEnabled()) {

            logger().debug("close(${isOpen()}).")
        }
        m_open.set(false)
    }

    @Override
    public boolean isOpen() {

        m_open.get()
    }

    @Memoized
    public Logger logger() {

        m_logs
    }
}
