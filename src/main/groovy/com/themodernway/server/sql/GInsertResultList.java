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

import java.util.List;

import com.themodernway.common.api.java.util.CommonOps;
import com.themodernway.server.core.json.JSONArray;

public class GInsertResultList extends AbstractGInsertList<GInsertResult>
{
    public GInsertResultList(final List<List<Object>> results)
    {
        super(CommonOps.toList(results.stream().map(list -> new GInsertResult(list))));
    }

    @Override
    public JSONArray toJSONArray()
    {
        return new JSONArray(CommonOps.toList(this.asList().stream().map(resu -> resu.toJSONArray())));
    }

    @Override
    public boolean equals(final Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof GInsertResultList)
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
}
