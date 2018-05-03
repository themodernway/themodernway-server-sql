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
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.themodernway.server.core.ICoreBase;
import com.themodernway.server.core.io.IO;
import com.themodernway.server.core.logging.IHasLogging;
import com.themodernway.server.core.logging.LoggingOps;

public class GSQLProvider implements BeanFactoryAware, IGSQLProvider, ICoreBase, IHasLogging
{
    private final Logger                                 m_logger      = LoggingOps.getLogger(getClass());

    private final String                                 m_default;

    private final LinkedHashMap<String, IGSQLDescriptor> m_descriptors = new LinkedHashMap<>();

    public GSQLProvider(final String name)
    {
        m_default = toTrimOrNull(name);

        if (logger().isInfoEnabled())
        {
            logger().info(LoggingOps.THE_MODERN_WAY_MARKER, format("Default ISQLDescriptor(%s)", m_default));
        }
    }

    @Override
    public IGSQLDescriptor getSQLDescriptor(String name)
    {
        name = toTrimOrNull(name);

        if (null != name)
        {
            final IGSQLDescriptor desc = m_descriptors.get(name);

            if (null != desc)
            {
                return desc;
            }
            if (logger().isErrorEnabled())
            {
                logger().error(LoggingOps.THE_MODERN_WAY_MARKER, format("ISQLDescriptor(%s) not found.", name));
            }
            return null;
        }
        if (logger().isErrorEnabled())
        {
            logger().error(LoggingOps.THE_MODERN_WAY_MARKER, "ISQLDescriptor null name.");
        }
        return null;
    }

    @Override
    public List<String> getSQLDescriptorNames()
    {
        return toUnmodifiableList(m_descriptors.keySet());
    }

    @Override
    public List<IGSQLDescriptor> getSQLDescriptors()
    {
        return toUnmodifiableList(m_descriptors.values());
    }

    @Override
    public void setBeanFactory(final BeanFactory factory) throws BeansException
    {
        if (factory instanceof DefaultListableBeanFactory)
        {
            for (String name : ((DefaultListableBeanFactory) factory).getBeansOfType(IGSQLDescriptor.class).keySet())
            {
                name = toTrimOrNull(name);

                if (null != name)
                {
                    final IGSQLDescriptor descriptor = factory.getBean(name, IGSQLDescriptor.class);

                    if (null != descriptor)
                    {
                        descriptor.setName(name);

                        if (logger().isInfoEnabled())
                        {
                            logger().info(LoggingOps.THE_MODERN_WAY_MARKER, format("Found ISQLDescriptor class(%s).", descriptor.getClass().getName()));
                        }
                        m_descriptors.put(name, descriptor);
                    }
                }
            }
        }
    }

    @Override
    public void close() throws IOException
    {
        IO.close(m_descriptors.values());
    }

    @Override
    public String getDefaultSQLDescriptorName()
    {
        return m_default;
    }

    @Override
    public Logger logger()
    {
        return m_logger;
    }
}
