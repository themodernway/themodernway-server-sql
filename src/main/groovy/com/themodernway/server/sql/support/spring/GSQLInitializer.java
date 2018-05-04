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

import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import com.themodernway.server.sql.support.GSQLSupport;

public class GSQLInitializer extends DataSourceInitializer
{
    private IGSQLDescriptor m_descrp;

    public GSQLInitializer()
    {
    }

    public void setDescriptor(final IGSQLDescriptor descriptor)
    {
        m_descrp = descriptor;
    }

    public void setStartup(final GSQLPopulator populator)
    {
        setDatabasePopulator(populator);
    }

    public void setCleanup(final GSQLPopulator populator)
    {
        setDatabaseCleaner(populator);
    }

    public IGSQLDescriptor getDescriptor()
    {
        return m_descrp;
    }

    @Override
    public void afterPropertiesSet()
    {
        IGSQLDescriptor descriptor = getDescriptor();

        if (null == descriptor)
        {
            descriptor = GSQLSupport.getSQLSupport().getGSQLDescriptor();

            setDescriptor(descriptor);
        }
        if (null != descriptor)
        {
            setDataSource(descriptor.getDataSource());
        }
        super.afterPropertiesSet();
    }
}
