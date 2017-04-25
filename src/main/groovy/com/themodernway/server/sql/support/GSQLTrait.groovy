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
import com.themodernway.server.sql.GDataSet
import com.themodernway.server.sql.GSQL
import com.themodernway.server.sql.support.spring.GSQLContextInstance
import com.themodernway.server.sql.support.spring.IGSQLContext
import com.themodernway.server.sql.support.spring.IGSQLDescriptor
import com.themodernway.server.sql.support.spring.IGSQLProvider

import groovy.sql.GroovyRowResult
import groovy.transform.CompileStatic
import groovy.transform.Memoized

@CompileStatic
public trait GSQLTrait
{
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

    public GSQL gsql(final String name)
    {
        final IGSQLDescriptor desc = getSQLDescriptor(Objects.requireNonNull(name))

        if (desc)
        {
            return desc.make()
        }
        null
    }

    public GSQL gsql()
    {
        final IGSQLDescriptor desc = getSQLDescriptor()

        if (desc)
        {
            return desc.make()
        }
        null
    }
    
    public void forConnection(GSQL gsql, Closure closure)
    {
        gsql.cacheConnection {
                
            closure(gsql)
        }
    }
    
    public void forTransaction(GSQL gsql, Closure closure)
    {
        gsql.withTransaction {
                
            closure(gsql)
        }
    }
    
    public void forConnection(GDataSet data, Closure closure)
    {
        data.cacheConnection {
                
            closure(data)
        }
    }
    
    public void forTransaction(GDataSet data, Closure closure)
    {
        data.withTransaction {
                
            closure(data)
        }
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
        new JSONObject(GSQL.jarr(Objects.requireNonNull(list)))
    }
}
