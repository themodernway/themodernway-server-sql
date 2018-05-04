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

package com.themodernway.server.sql.test

import com.themodernway.server.core.NanoTimer
import com.themodernway.server.core.support.CoreGroovyTrait
import com.themodernway.server.core.support.spring.testing.spock.ServerCoreSpecification
import com.themodernway.server.sql.support.GSQLGroovyTrait
import com.themodernway.server.sql.test.util.UsersBean

public class SQLTestsSpecification extends ServerCoreSpecification implements CoreGroovyTrait, GSQLGroovyTrait
{
    def setupSpec()
    {
        setupServerCoreDefault(SQLTestsSpecification,
            "classpath:/com/themodernway/server/sql/test/ApplicationContext.xml",
            "classpath:/com/themodernway/server/core/config/CoreApplicationContext.xml",
            "classpath:/com/themodernway/server/sql/config/SQLApplicationContext.xml"
        )
        def users = getBeanSafely("UsersBean", UsersBean).setup()
    }

    def cleanupSpec()
    {
        closeServerCoreDefault()
    }

    def "Test Users 0"()
    {
        setup:
        def time = new NanoTimer()
        def answ = jquery('users', 'SELECT * FROM users')
        echo time

        expect:
        true == true

        cleanup:
        echo answ.getAsArray('users').size()
    }

    def "Test Users 1"()
    {
        setup:
        def time = new NanoTimer()
        def answ = jquery('users', 'SELECT * FROM users')
        echo time

        expect:
        true == true

        cleanup:
        echo answ.getAsArray('users').size()
    }

    def "Test Users 2"()
    {
        setup:
        def time = new NanoTimer()
        def answ = jquery('users', 'SELECT * FROM users WHERE VALUE < 10')
        echo time

        expect:
        true == true

        cleanup:
        echo answ
    }

    def "Test Employees 0"()
    {
        setup:
        def time = new NanoTimer()
        def answ = jquery('employees', 'SELECT * FROM employees')
        echo time

        expect:
        true == true

        cleanup:
        echo answ
    }
}
