package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    public final EntityClassMetaData<?> metaData;

    private String joinFields2String(List<Field> fields) {
        return String.join(", ", fields.stream().map(Field::getName).collect(Collectors.toList()));
    }

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String getSelectAllSql() {
        var query = new StringBuilder();
        query.append("select");
        query.append(" " + joinFields2String(metaData.getAllFields()));
        query.append("\n from " + metaData.getName());
        return query.toString();
    }

    @Override
    public String getSelectByIdSql() {
        var whereClause = "\n where (" + metaData.getIdField().getName() + "=?)";
        var res = getSelectAllSql() + whereClause;
        return res;
    }

    @Override
    public String getInsertSql() {
        var query = new StringBuilder();
        query.append("insert into " + metaData.getName() + " (" + joinFields2String(metaData.getFieldsWithoutId()) + ")");
        query.append("\n values (");
        query.append(
                String.join(", ",
                        this.metaData.getFieldsWithoutId().stream().map(e -> "?").collect(Collectors.toList())
                )
        );
        query.append("\n)");
        return query.toString();
    }

    @Override
    public String getUpdateSql() {
        var idF = metaData.getIdField().getName();
        var query = new StringBuilder();
        query.append("update " + metaData.getName());
        query.append("\n set");
        var flds = this.metaData.getAllFields().stream()
                .map(f -> f.getName())
                .filter(s -> s != idF)
                .map(s -> "\n " + s + "=?")
                .collect(Collectors.toList());
        query.append(String.join(", ", flds));
        query.append("\n where " + idF + "=?");
        return query.toString();
    }
}
