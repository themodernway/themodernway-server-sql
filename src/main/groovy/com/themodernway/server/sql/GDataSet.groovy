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

package com.themodernway.server.sql

import com.themodernway.server.sql.support.GSQLSupport

import groovy.sql.DataSet
import groovy.transform.CompileStatic

@CompileStatic
public class GDataSet extends DataSet
{
    public GDataSet(GSQL gsql, Class type)
    {
        super(gsql, type)
    }
    
    public GDataSet(GSQL gsql, String table)
    {
        super(gsql, table)
    }
    
    private GDataSet(DataSet copy)
    {
        super(copy)
    }
    
    private GDataSet(DataSet copy, Closure where, Closure sort)
    {
        super(copy, where, sort)
    }
    
    public GSQL sql()
    {
        new GSQL(this)
    }
    
    public GDataSet find(Closure closure)
    {
       new GDataSet(this, closure, null)
    }
    
    @Override
    public GDataSet sort(Closure sort)
    {
       new GDataSet(this, null, sort)
    }
    
    @Override
    public GDataSet reverse()
    {
        new GDataSet(this)
    }
    
    public GDataSet view(Closure criteria)
    {
        new GDataSet(this, criteria, null)
    }
    
    public void forConnection(Closure closure)
    {
        GSQLSupport.getSQLSupport().forConnection(this, closure)
    }
    
    public void forTransaction(Closure closure)
    {
        GSQLSupport.getSQLSupport().forTransaction(this, closure)
    }
}
