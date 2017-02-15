/*
 * Copyright (c) 2017, The Modern Way. All rights reserved.
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

package com.themodernway.server.sql.support

import com.themodernway.server.core.json.JSONObject
import com.themodernway.server.core.support.CoreGroovySupport
import com.themodernway.server.sql.GSQL
import com.themodernway.server.sql.support.spring.GSQLContextInstance
import com.themodernway.server.sql.support.spring.IGSQLContext
import com.themodernway.server.sql.support.spring.IGSQLDescriptor
import com.themodernway.server.sql.support.spring.IGSQLProvider

import groovy.sql.GroovyRowResult
import groovy.transform.CompileStatic
import groovy.transform.Memoized

@CompileStatic
public class GSQLSupport extends CoreGroovySupport
{
    private static final GSQLSupport INSTANCE = new GSQLSupport()

    @Memoized
    public static final GSQLSupport getSQLSupport()
    {
        INSTANCE
    }

    @Memoized
    public IGSQLContext getGSQLContext()
    {
        GSQLContextInstance.getGSQLContextInstance()
    }

    @Memoized
    public IGSQLProvider getGSQLProvider()
    {
        getGSQLContext().getGSQLProvider()
    }

    @Memoized
    public IGSQLDescriptor getSQLDescriptor(String name)
    {
        getGSQLProvider().getSQLDescriptor(Objects.requireNonNull(name))
    }

    @Memoized
    public IGSQLDescriptor getSQLDescriptor()
    {
        getGSQLProvider().getSQLDescriptor(getDefaultSQLDescriptorName())
    }

    @Memoized
    public String getDefaultSQLDescriptorName()
    {
        getGSQLProvider().getDefaultSQLDescriptorName()
    }

    @Memoized
    public GSQL gsql(final String name)
    {
        final IGSQLDescriptor desc = getSQLDescriptor(Objects.requireNonNull(name))

        if (desc)
        {
            return desc.make()
        }
        null
    }

    @Memoized
    public GSQL gsql()
    {
        final IGSQLDescriptor desc = getSQLDescriptor()

        if (desc)
        {
            return desc.make()
        }
        null
    }

    public JSONObject jsql(GString query)
    {
        jrows(gsql().rows(Objects.requireNonNull(query)))
    }

    public JSONObject jsql(String name, GString query)
    {
        jrows(gsql(Objects.requireNonNull(name)).rows(Objects.requireNonNull(query)))
    }

    public JSONObject jsql(String query)
    {
        jrows(gsql().rows(Objects.requireNonNull(query)))
    }

    public JSONObject jsql(String name, String query)
    {
        jrows(gsql(Objects.requireNonNull(name)).rows(Objects.requireNonNull(query)))
    }

    public JSONObject jsql(GString query, List<?> params)
    {
        jrows(gsql().rows(Objects.requireNonNull(query), Objects.requireNonNull(params)))
    }

    public JSONObject jsql(String name, GString query, List<?> params)
    {
        jrows(gsql(Objects.requireNonNull(name)).rows(Objects.requireNonNull(query), Objects.requireNonNull(params)))
    }

    public JSONObject jsql(String query, List<?> params)
    {
        jrows(gsql().rows(Objects.requireNonNull(query), Objects.requireNonNull(params)))
    }

    public JSONObject jsql(String name, String query, List<?> params)
    {
        jrows(gsql(Objects.requireNonNull(name)).rows(Objects.requireNonNull(query), Objects.requireNonNull(params)))
    }

    public JSONObject jrows(List<GroovyRowResult> list)
    {
        json(GSQL.jarr(Objects.requireNonNull(list)))
    }
}
