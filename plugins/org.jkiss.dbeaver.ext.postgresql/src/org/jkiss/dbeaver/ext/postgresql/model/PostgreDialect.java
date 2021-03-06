/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2017 Serge Rider (serge@jkiss.org)
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
package org.jkiss.dbeaver.ext.postgresql.model;

import org.jkiss.code.NotNull;
import org.jkiss.code.Nullable;
import org.jkiss.dbeaver.ext.postgresql.PostgreConstants;
import org.jkiss.dbeaver.ext.postgresql.model.data.PostgreBinaryFormatter;
import org.jkiss.dbeaver.model.DBPKeywordType;
import org.jkiss.dbeaver.model.data.DBDBinaryFormatter;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCDatabaseMetaData;
import org.jkiss.dbeaver.model.impl.jdbc.JDBCDataSource;
import org.jkiss.dbeaver.model.impl.jdbc.JDBCSQLDialect;
import org.jkiss.dbeaver.model.sql.SQLConstants;

import java.util.Arrays;
import java.util.Collections;

/**
* PostgreSQL dialect
*/
class PostgreDialect extends JDBCSQLDialect {

    public PostgreDialect(JDBCDatabaseMetaData metaData) {
        super("PostgreSQL", metaData);
        addSQLKeyword("SHOW");
        addSQLKeyword("TYPE");
        addSQLKeyword("USER");
        addSQLKeyword("COMMENT");
        addSQLKeyword("MATERIALIZED");

        addFunctions(Collections.singleton("CURRENT_DATABASE"));

        removeSQLKeyword("PUBLIC");
        removeSQLKeyword("LENGTH");
        removeSQLKeyword("LANGUAGE");
    }

/*
    @NotNull
    @Override
    public MultiValueInsertMode getMultiValueInsertMode() {
        return MultiValueInsertMode.GROUP_ROWS;
    }
*/

    @Nullable
    @Override
    public String getBlockToggleString() {
        return "$" + SQLConstants.KEYWORD_PATTERN_CHARS + "$";
    }

    @Override
    public String[][] getBlockBoundStrings() {
        return null;
    }

    @Override
    public boolean supportsAliasInSelect() {
        return true;
    }

    @Override
    public boolean supportsCommentQuery() {
        return true;
    }

    @NotNull
    @Override
    public DBDBinaryFormatter getNativeBinaryFormatter() {
        return PostgreBinaryFormatter.INSTANCE;
    }

    @Override
    protected void loadDataTypesFromDatabase(JDBCDataSource dataSource) {
        super.loadDataTypesFromDatabase(dataSource);
        addDataTypes(PostgreConstants.DATA_TYPE_ALIASES.keySet());
    }
}
