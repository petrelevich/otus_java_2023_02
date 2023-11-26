package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaDataManager) {
    }

    @Override
    public String getSelectAllSql() {
        return getSelectAllSql();
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectByIdSql();
    }

    @Override
    public String getInsertSql() {
        return  getInsertSql();
    }

    @Override
    public String getUpdateSql() {
        return getUpdateSql();
    }
}
