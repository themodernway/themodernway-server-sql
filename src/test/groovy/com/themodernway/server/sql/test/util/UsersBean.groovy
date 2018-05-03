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

import org.slf4j.Logger

import com.themodernway.server.core.logging.IHasLogging
import com.themodernway.server.core.logging.LoggingOps
import com.themodernway.server.core.support.CoreGroovyTrait
import com.themodernway.server.sql.GSQL
import com.themodernway.server.sql.support.SQLTrait

public class UsersBean implements CoreGroovyTrait, SQLTrait, IHasLogging, Closeable
{
    private int m_rows

    private boolean m_open = false

    private final Logger m_logger = LoggingOps.getLogger(getClass())

    public UsersBean(final int rows)
    {
        m_rows = rows
    }

    public boolean open()
    {
        m_open
    }

    public UsersBean clean()
    {
        if (logger().isDebugEnabled())
        {
            logger().debug("clean(${open()}).")
        }
        gsql().forConnection { GSQL conn ->

            conn.execute('DROP INDEX IF EXISTS undex')

            conn.execute('DROP TABLE IF EXISTS users')

            conn.execute('CREATE TABLE users (ID NUMERIC NOT NULL AUTO_INCREMENT, NAME VARCHAR (256) NOT NULL, COUNT NUMERIC UNIQUE NOT NULL)')

            conn.execute('CREATE UNIQUE INDEX undex ON users (COUNT)')
        }
        m_open = true

        this
    }

    public UsersBean setup()
    {
        if (false == open())
        {
            clean()
        }
        if (logger().isDebugEnabled())
        {
            logger().debug("setup(${open()}).")
        }
        gsql().forConnection { GSQL conn ->

            m_rows.times { int i ->

                conn.execute("INSERT INTO users (NAME, COUNT) VALUES ('Dean S Jones', ${i})")
            }
        }
        this
    }

    @Override
    public void close() throws IOException
    {
        if (logger().isDebugEnabled())
        {
            logger().debug("close(${open()}).")
        }
        m_open = false
    }

    @Override
    public Logger logger()
    {
        m_logger
    }
}
