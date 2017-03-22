/*
 *    Copyright 2009-2012 The MyBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.umessage.letsgo.core.extensions.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;

import com.umessage.letsgo.core.extensions.spring.SpringUtil;

@Deprecated
public class PlainTextTypehandler extends BaseTypeHandler<Object> {
    private UnknownTypeHandler unknownTypeHandler =null;
    static final Pattern p=Pattern.compile("(Integer|String|Boolean)\\((.*)\\)");
    public UnknownTypeHandler getUnknowntypeHandler(){
        if(unknownTypeHandler==null){
            SqlSessionFactory sessionFactory = SpringUtil.getBean(SqlSessionFactory.class);
            unknownTypeHandler=new UnknownTypeHandler(sessionFactory.getConfiguration().getTypeHandlerRegistry());
        }
        return unknownTypeHandler;
    }
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
         getUnknowntypeHandler().setNonNullParameter(ps, i, parameter, jdbcType);
    }
    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Matcher m=p.matcher(columnName);
        if(m.matches()){
            String type=m.group(1);
            String value=m.group(2);
            if("Integer".equals(type)){
                return Integer.valueOf(value);
            }else if("String".equals(type)){
                return value;
            }else if("Boolean".equals(type)){
                return Boolean.valueOf(value);
            }
        }
        return getUnknowntypeHandler().getNullableResult(rs, columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getUnknowntypeHandler().getNullableResult(rs, columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getUnknowntypeHandler().getResult(cs, columnIndex);
    }
}
