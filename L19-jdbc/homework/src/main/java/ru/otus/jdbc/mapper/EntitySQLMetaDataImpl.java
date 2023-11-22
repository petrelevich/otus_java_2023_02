package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaDataManager) {
    }

    @Override
    public String getSelectAllSql() {
        return null;
    }

    @Override
    public String getSelectByIdSql() {
        return null;
    }

    @Override
    public String getInsertSql() {
        return null;
    }

    @Override
    public String getUpdateSql() {
        return null;
    }
}
