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

import static com.themodernway.common.api.java.util.CommonOps.cast;
import static com.themodernway.common.api.java.util.CommonOps.isNull;
import static com.themodernway.common.api.java.util.CommonOps.requireNonNull;
import static com.themodernway.common.api.java.util.CommonOps.toList;
import static com.themodernway.common.api.java.util.CommonOps.toUnmodifiableList;

import java.util.List;

import org.slf4j.Logger;

import com.themodernway.common.api.types.FixedListIterable;
import com.themodernway.server.core.json.IJSONEnabled;
import com.themodernway.server.core.json.JSONArray;
import com.themodernway.server.core.json.binder.JSONBinder.CoreObjectMapper;
import com.themodernway.server.core.logging.IHasLogging;
import com.themodernway.server.core.logging.LoggingOps;

import groovy.lang.GString;

public abstract class AbstractGInsertList<R> extends FixedListIterable<R> implements IHasLogging, IJSONEnabled
{
    private final Logger m_logger = LoggingOps.getLogger(getClass());

    protected AbstractGInsertList(final List<R> result)
    {
        super(toUnmodifiableList(toList(result)));
    }

    @Override
    public Logger logger()
    {
        return m_logger;
    }

    @Override
    public String toString()
    {
        return toJSONString();
    }

    @Override
    public String toJSONString()
    {
        return toJSONString(false);
    }

    @Override
    public String toJSONString(final boolean strict)
    {
        return toJSONArray().toJSONString(strict);
    }

    @Override
    public boolean equals(final Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof AbstractGInsertList)
        {
            return super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    protected <T> T toType(final Object object, final Class<T> type)
    {
        requireNonNull(type);

        if (isNull(object))
        {
            return null;
        }
        if (object == this)
        {
            if (type == List.class)
            {
                return cast(this.asList());
            }
            if (type == JSONArray.class)
            {
                return cast(this.toJSONArray());
            }
        }
        if (type == Object.class)
        {
            return cast(object);
        }
        if (type.isInstance(object))
        {
            return type.cast(object);
        }
        if ((type == String.class) || (type == GString.class) || (type == CharSequence.class))
        {
            return cast(object.toString());
        }
        try
        {
            return new CoreObjectMapper().convertValue(object, type);
        }
        catch (final Exception e)
        {
            if (logger().isErrorEnabled())
            {
                logger().error(LoggingOps.THE_MODERN_WAY_MARKER, String.format("type (%s) cannot be coerced into type (%s).", object.getClass().getName(), type.getName()), e);
            }
        }
        return null;
    }

    public <T> T asType(final Class<T> type)
    {
        return toType(this, type);
    }

    public <T> T asType(final int index, final Class<T> type)
    {
        return toType(get(index), type);
    }

    public abstract JSONArray toJSONArray();
}
