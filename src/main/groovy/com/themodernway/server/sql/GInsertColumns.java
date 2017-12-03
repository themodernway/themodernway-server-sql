/*
 * Copyright (c) 2017, 2018, The Modern Way. All rights reserved.
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

import java.util.Collection;

import com.themodernway.common.api.java.util.CommonOps;
import com.themodernway.server.core.json.JSONArray;

public class GInsertColumns extends AbstractGInsertList<String>
{
    public GInsertColumns(final Collection<String> columns)
    {
        super(CommonOps.toList(columns));
    }

    public GInsertColumns(final String... columns)
    {
        super(CommonOps.toList(columns));
    }

    @Override
    public JSONArray toJSONArray()
    {
        return new JSONArray(this.asList());
    }

    @Override
    public boolean equals(final Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof GInsertColumns)
        {
            return super.equals(other);
        }
        return false;
    }
}
