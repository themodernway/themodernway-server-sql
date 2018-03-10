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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.themodernway.common.api.java.util.CommonOps;
import com.themodernway.common.api.java.util.StringOps;
import com.themodernway.server.core.json.JSONArray;
import com.themodernway.server.core.json.JSONObject;
import com.themodernway.server.sql.support.GSQLSupport;

import groovy.lang.Closure;
import groovy.lang.GString;
import groovy.sql.GroovyResultSet;
import groovy.sql.GroovyRowResult;
import groovy.sql.InOutParameter;
import groovy.sql.InParameter;
import groovy.sql.OutParameter;
import groovy.sql.ResultSetOutParameter;
import groovy.sql.Sql;

public class GSQL extends Sql
{
    private static IGSQLRowObjectMapper            rowmapper;

    private List<IGSQLStatementSetObjectHandler>   m_setobj_list;

    private List<IGSQLPreProcessConnectionHandler> m_precon_list;

    public static final InParameter GSQLINPARAMETER(final int type, final Object value)
    {
        return in(type, value);
    }

    public static final OutParameter GSQLOUTPARAMETER(final int type)
    {
        return out(type);
    }

    public static final InOutParameter GSQLINOUTPARAMETER(final int type, final Object value)
    {
        return inout(in(type, value));
    }

    public static final InOutParameter GSQLINOUTPARAMETER(final InParameter in)
    {
        return inout(in);
    }

    public static final ResultSetOutParameter GSQLRESULTSETOUTPARAMETER(final int type)
    {
        return () -> type;
    }

    public static final void setDefaultRowObjectMapper(final IGSQLRowObjectMapper mapper)
    {
        rowmapper = mapper;
    }

    GSQL(final Sql ds)
    {
        super(CommonOps.requireNonNull(ds, "DataSource was null"));
    }

    public GSQL(final DataSource ds)
    {
        super(CommonOps.requireNonNull(ds, "DataSource was null"));
    }

    public void setStatementSetObjectHandlers(final List<IGSQLStatementSetObjectHandler> list)
    {
        m_setobj_list = list;
    }

    public void setPreProcessConnectionHandlers(final List<IGSQLPreProcessConnectionHandler> list)
    {
        m_precon_list = list;
    }

    public GDataSet asDataSet(final String column)
    {
        return new GDataSet(this, CommonOps.requireNonNull(column));
    }

    public GDataSet asDataSet(final Class<?> type)
    {
        return new GDataSet(this, CommonOps.requireNonNull(type));
    }

    public void forConnection(final Closure<?> closure)
    {
        GSQLSupport.getSQLSupport().forConnection(this, closure);
    }

    public void forTransaction(final Closure<?> closure)
    {
        GSQLSupport.getSQLSupport().forTransaction(this, closure);
    }

    @Override
    protected void setObject(final PreparedStatement statement, final int i, final Object value) throws SQLException
    {
        if ((null == m_setobj_list) || (m_setobj_list.isEmpty()))
        {
            super.setObject(statement, i, value);
        }
        else
        {
            boolean done = false;

            for (final IGSQLStatementSetObjectHandler handler : m_setobj_list)
            {
                if (handler.setObject(statement, i, value))
                {
                    done = true;

                    break;
                }
            }
            if (false == done)
            {
                super.setObject(statement, i, value);
            }
        }
    }

    @Override
    protected Connection createConnection() throws SQLException
    {
        if ((null == m_precon_list) || (m_precon_list.isEmpty()))
        {
            return super.createConnection();
        }
        final Connection connection = super.createConnection();

        for (final IGSQLPreProcessConnectionHandler handler : m_precon_list)
        {
            handler.preProcessConnection(connection);
        }
        return connection;
    }

    public static final JSONObject json(final GroovyRowResult result) throws SQLException
    {
        return json(result, rowmapper);
    }

    public static final JSONObject json(final GroovyRowResult result, IGSQLRowObjectMapper mapper) throws SQLException
    {
        CommonOps.requireNonNull(result, "GroovyRowResult was null");

        final JSONObject object = new JSONObject();

        if (null == mapper)
        {
            mapper = rowmapper;
        }
        if (null == mapper)
        {
            for (final Object ikey : CommonOps.toKeys(CommonOps.rawmap(result)))
            {
                final String name = StringOps.toTrimOrNull(ikey.toString());

                if (null != name)
                {
                    object.put(name, result.get(ikey));
                }
            }
        }
        else
        {
            for (final Object ikey : CommonOps.toKeys(CommonOps.rawmap(result)))
            {
                final String name = StringOps.toTrimOrNull(ikey.toString());

                if (null != name)
                {
                    mapper.mapObject(object, name, result.get(ikey));
                }
            }
        }
        return object;
    }

    public static final JSONObject json(final GroovyResultSet rset) throws SQLException
    {
        return json(rset, rowmapper);
    }

    public static final JSONObject json(final GroovyResultSet rset, IGSQLRowObjectMapper mapper) throws SQLException
    {
        CommonOps.requireNonNull(rset, "GroovyResultSet was null");

        final JSONObject object = new JSONObject();

        final ResultSetMetaData meta = rset.getMetaData();

        final int cols = meta.getColumnCount();

        if (cols < 1)
        {
            return object;
        }
        if (null == mapper)
        {
            mapper = rowmapper;
        }
        if (null == mapper)
        {
            for (int i = 1; i <= cols; i++)
            {
                final String name = StringOps.toTrimOrNull(meta.getColumnLabel(i));

                if (null != name)
                {
                    object.put(name, rset.getObject(i));
                }
            }
        }
        else
        {
            for (int i = 1; i <= cols; i++)
            {
                final String name = StringOps.toTrimOrNull(meta.getColumnLabel(i));

                if (null != name)
                {
                    mapper.mapObject(object, name, rset.getObject(i));
                }
            }
        }
        return object;
    }

    public static final JSONArray jarr(final List<GroovyRowResult> list) throws SQLException
    {
        return jarr(list, rowmapper);
    }

    public static final JSONArray jarr(final List<GroovyRowResult> list, IGSQLRowObjectMapper mapper) throws SQLException
    {
        CommonOps.requireNonNull(list, "List<GroovyRowResult> was null");

        final JSONArray array = new JSONArray();

        if (null == mapper)
        {
            mapper = rowmapper;
        }
        for (final GroovyRowResult result : list)
        {
            array.add(json(result, mapper));
        }
        return array;
    }

    public static final JSONArray jarr(final GroovyResultSet rset) throws SQLException
    {
        return jarr(rset, rowmapper);
    }

    public static final JSONArray jarr(final GroovyResultSet rset, IGSQLRowObjectMapper mapper) throws SQLException
    {
        CommonOps.requireNonNull(rset, "GroovyResultSet was null");

        final JSONArray array = new JSONArray();

        final ResultSetMetaData meta = rset.getMetaData();

        final int cols = meta.getColumnCount();

        if (cols < 1)
        {
            return array;
        }
        final String[] labs = new String[cols + 1]; // this is hacky + 1 so I don't have to keep doing an index subtract - 1 in the loops.

        for (int i = 1; i <= cols; i++)
        {
            labs[i] = StringOps.toTrimOrNull(meta.getColumnLabel(i));
        }
        if (null == mapper)
        {
            mapper = rowmapper;
        }
        if (null == mapper)
        {
            while (rset.next())
            {
                final JSONObject object = new JSONObject();

                for (int i = 1; i <= cols; i++)
                {
                    final String name = labs[i];

                    if (null != name)
                    {
                        object.put(name, rset.getObject(i));
                    }
                }
                array.add(object);
            }
        }
        else
        {
            while (rset.next())
            {
                final JSONObject object = new JSONObject();

                for (int i = 1; i <= cols; i++)
                {
                    final String name = labs[i];

                    if (null != name)
                    {
                        mapper.mapObject(object, name, rset.getObject(i));
                    }
                }
                array.add(object);
            }
        }
        return array;
    }

    public static List<Object> CAST_PARAMS(final List<?> params)
    {
        return CommonOps.cast(CommonOps.requireNonNull(params, "params was null"));
    }

    public int update(final GString update) throws SQLException
    {
        return executeUpdate(CommonOps.requireNonNull(update, "update was null"));
    }

    public int update(final String update, final List<?> params) throws SQLException
    {
        return executeUpdate(CommonOps.requireNonNull(update, "update was null"), CAST_PARAMS(params));
    }

    public GInsertResultList insert(final GString insert) throws SQLException
    {
        return new GInsertResultList(executeInsert(CommonOps.requireNonNull(insert, "insert was null")));
    }

    public List<GroovyRowResult> insert(final GString insert, final GInsertColumns columns) throws SQLException
    {
        return executeInsert(CommonOps.requireNonNull(insert, "insert was null"), columns.asList());
    }

    public GInsertResultList insert(final String insert, final List<?> params) throws SQLException
    {
        return new GInsertResultList(executeInsert(CommonOps.requireNonNull(insert, "insert was null"), CAST_PARAMS(params)));
    }

    public List<GroovyRowResult> insert(final String insert, final List<?> params, final GInsertColumns columns) throws SQLException
    {
        return executeInsert(CommonOps.requireNonNull(insert, "insert was null"), CAST_PARAMS(params), columns.asList());
    }
}
