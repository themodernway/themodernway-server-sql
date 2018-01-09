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

package com.themodernway.server.sql.support.spring;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.themodernway.common.api.java.util.CommonOps;
import com.themodernway.common.api.java.util.StringOps;
import com.themodernway.common.api.types.Activatable;
import com.themodernway.server.sql.GSQL;
import com.themodernway.server.sql.IGSQLPreProcessConnectionHandler;
import com.themodernway.server.sql.IGSQLRowObjectMapper;
import com.themodernway.server.sql.IGSQLStatementSetObjectHandler;

@ManagedResource
public class GSQLDescriptor extends Activatable implements IGSQLDescriptor
{
    private String                                 m_name;

    private final DataSource                       m_data_source;

    private List<IGSQLStatementSetObjectHandler>   m_setobj_list;

    private List<IGSQLPreProcessConnectionHandler> m_precon_list;

    private IGSQLRowObjectMapper                   m_row_object_mapper;

    private String                                 m_description = "Generic GSQLDescriptor";

    public GSQLDescriptor(final DataSource datasource)
    {
        super(true);

        m_data_source = CommonOps.requireNonNull(datasource);
    }

    @Override
    public DataSource getDataSource()
    {
        return m_data_source;
    }

    @Override
    public void setStatementObjectHandlers(final List<IGSQLStatementSetObjectHandler> list)
    {
        m_setobj_list = list;
    }

    @Override
    public void setPreProcessConnectionHandlers(final List<IGSQLPreProcessConnectionHandler> list)
    {
        m_precon_list = list;
    }

    @Override
    @ManagedOperation(description = "Get GSQLDescriptor name.")
    public final String getName()
    {
        return m_name;
    }

    @Override
    public final void setName(final String name)
    {
        m_name = StringOps.requireTrimOrNull(name, "GSQLDescriptor name is null");
    }

    @Override
    public GSQL make()
    {
        if (false == isActive())
        {
            throw new IllegalArgumentException("GSQLDescriptor [" + getDescription() + "] is not active.");
        }
        final GSQL gsql = new GSQL(getDataSource());

        gsql.setStatementSetObjectHandlers(getStatementSetObjectHandlers());

        gsql.setPreProcessConnectionHandlers(getPreProcessConnectionHandlers());

        return gsql;
    }

    @Override
    public void close() throws IOException
    {
        setActive(false);
    }

    @Override
    public void setDescription(String description)
    {
        description = StringOps.toTrimOrNull(description);

        if (null != description)
        {
            m_description = description;
        }
    }

    @Override
    @ManagedOperation(description = "Get GSQLDescriptor description.")
    public String getDescription()
    {
        return m_description;
    }

    @Override
    public IGSQLRowObjectMapper getRowObjectMapper()
    {
        return m_row_object_mapper;
    }

    @Override
    public void setRowObjectMapper(final IGSQLRowObjectMapper row_object_mapper)
    {
        m_row_object_mapper = row_object_mapper;
    }

    @Override
    public List<IGSQLStatementSetObjectHandler> getStatementSetObjectHandlers()
    {
        return m_setobj_list;
    }

    @Override
    public List<IGSQLPreProcessConnectionHandler> getPreProcessConnectionHandlers()
    {
        return m_precon_list;
    }

    @Override
    @ManagedOperation(description = "Is GSQLDescriptor active.")
    public final boolean isActive()
    {
        return super.isActive();
    }

    @Override
    @ManagedOperation(description = "Set GSQLDescriptor active.")
    public final boolean setActive(final boolean active)
    {
        return super.setActive(active);
    }
}
