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

package com.themodernway.server.sql;

import static groovy.sql.Sql.in;

import java.sql.Types;

import groovy.sql.InParameter;
import groovy.sql.OutParameter;

public interface ISQLConstants
{
    public static final OutParameter ARRAY         = () -> Types.ARRAY;

    public static final OutParameter BIGINT        = () -> Types.BIGINT;

    public static final OutParameter BINARY        = () -> Types.BINARY;

    public static final OutParameter BIT           = () -> Types.BIT;

    public static final OutParameter BLOB          = () -> Types.BLOB;

    public static final OutParameter BOOLEAN       = () -> Types.BOOLEAN;

    public static final OutParameter CHAR          = () -> Types.CHAR;

    public static final OutParameter CLOB          = () -> Types.CLOB;

    public static final OutParameter DATALINK      = () -> Types.DATALINK;

    public static final OutParameter DATE          = () -> Types.DATE;

    public static final OutParameter DECIMAL       = () -> Types.DECIMAL;

    public static final OutParameter DISTINCT      = () -> Types.DISTINCT;

    public static final OutParameter DOUBLE        = () -> Types.DOUBLE;

    public static final OutParameter FLOAT         = () -> Types.FLOAT;

    public static final OutParameter INTEGER       = () -> Types.INTEGER;

    public static final OutParameter JAVA_OBJECT   = () -> Types.JAVA_OBJECT;

    public static final OutParameter LONGVARBINARY = () -> Types.LONGVARBINARY;

    public static final OutParameter LONGVARCHAR   = () -> Types.LONGVARCHAR;

    public static final OutParameter NULL          = () -> Types.NULL;

    public static final OutParameter NUMERIC       = () -> Types.NUMERIC;

    public static final OutParameter OTHER         = () -> Types.OTHER;

    public static final OutParameter REAL          = () -> Types.REAL;

    public static final OutParameter REF           = () -> Types.REF;

    public static final OutParameter SMALLINT      = () -> Types.SMALLINT;

    public static final OutParameter STRUCT        = () -> Types.STRUCT;

    public static final OutParameter TIME          = () -> Types.TIME;

    public static final OutParameter TIMESTAMP     = () -> Types.TIMESTAMP;

    public static final OutParameter TINYINT       = () -> Types.TINYINT;

    public static final OutParameter VARBINARY     = () -> Types.VARBINARY;

    public static final OutParameter VARCHAR       = () -> Types.VARCHAR;

    public static InParameter ARRAY(final Object value)
    {
        return in(Types.ARRAY, value);
    }

    public static InParameter BIGINT(final Object value)
    {
        return in(Types.BIGINT, value);
    }

    public static InParameter BINARY(final Object value)
    {
        return in(Types.BINARY, value);
    }

    public static InParameter BIT(final Object value)
    {
        return in(Types.BIT, value);
    }

    public static InParameter BLOB(final Object value)
    {
        return in(Types.BLOB, value);
    }

    public static InParameter BOOLEAN(final Object value)
    {
        return in(Types.BOOLEAN, value);
    }

    public static InParameter CHAR(final Object value)
    {
        return in(Types.CHAR, value);
    }

    public static InParameter CLOB(final Object value)
    {
        return in(Types.CLOB, value);
    }

    public static InParameter DATALINK(final Object value)
    {
        return in(Types.DATALINK, value);
    }

    public static InParameter DATE(final Object value)
    {
        return in(Types.DATE, value);
    }

    public static InParameter DECIMAL(final Object value)
    {
        return in(Types.DECIMAL, value);
    }

    public static InParameter DISTINCT(final Object value)
    {
        return in(Types.DISTINCT, value);
    }

    public static InParameter DOUBLE(final Object value)
    {
        return in(Types.DOUBLE, value);
    }

    public static InParameter FLOAT(final Object value)
    {
        return in(Types.FLOAT, value);
    }

    public static InParameter INTEGER(final Object value)
    {
        return in(Types.INTEGER, value);
    }

    public static InParameter JAVA_OBJECT(final Object value)
    {
        return in(Types.JAVA_OBJECT, value);
    }

    public static InParameter LONGVARBINARY(final Object value)
    {
        return in(Types.LONGVARBINARY, value);
    }

    public static InParameter LONGVARCHAR(final Object value)
    {
        return in(Types.LONGVARCHAR, value);
    }

    public static InParameter NULL(final Object value)
    {
        return in(Types.NULL, value);
    }

    public static InParameter NUMERIC(final Object value)
    {
        return in(Types.NUMERIC, value);
    }

    public static InParameter OTHER(final Object value)
    {
        return in(Types.OTHER, value);
    }

    public static InParameter REAL(final Object value)
    {
        return in(Types.REAL, value);
    }

    public static InParameter REF(final Object value)
    {
        return in(Types.REF, value);
    }

    public static InParameter SMALLINT(final Object value)
    {
        return in(Types.SMALLINT, value);
    }

    public static InParameter STRUCT(final Object value)
    {
        return in(Types.STRUCT, value);
    }

    public static InParameter TIME(final Object value)
    {
        return in(Types.TIME, value);
    }

    public static InParameter TIMESTAMP(final Object value)
    {
        return in(Types.TIMESTAMP, value);
    }

    public static InParameter TINYINT(final Object value)
    {
        return in(Types.TINYINT, value);
    }

    public static InParameter VARBINARY(final Object value)
    {
        return in(Types.VARBINARY, value);
    }

    public static InParameter VARCHAR(final Object value)
    {
        return in(Types.VARCHAR, value);
    }
}
