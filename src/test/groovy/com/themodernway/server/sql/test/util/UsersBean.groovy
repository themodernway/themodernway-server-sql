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

import com.themodernway.server.sql.GSQL

public class UsersBean extends AbstractGSQLBean<UsersBean> {

    public UsersBean(final int rows) {

        super(rows, 'users')
    }

    @Override
    public UsersBean delete(final GSQL gsql) {

        gsql.execute(format('DROP INDEX IF EXISTS %s', getIndex()))

        gsql.execute(format('DROP TABLE IF EXISTS %s', getTable()))

        this
    }

    @Override
    public UsersBean create(final GSQL gsql) {

        final String table = getTable()

        final String index = getIndex()

        gsql.execute(format("CREATE TABLE %s (ID NUMERIC NOT NULL AUTO_INCREMENT, NAME VARCHAR(256) NOT NULL, VALUE NUMERIC UNIQUE NOT NULL)", table))

        gsql.execute(format("CREATE UNIQUE INDEX %s ON %s (VALUE)", index, table))

        getRows().times { int value ->

            gsql.execute(format("INSERT INTO %s (NAME, VALUE) VALUES ('Dean S Jones', %d)", table, value))
        }
        this
    }
}
