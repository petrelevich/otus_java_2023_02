package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    EntityClassMetaData <?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData <?> entityClassMetaDataClient) {
        this.entityClassMetaData = entityClassMetaDataClient;
    }
    public String getSelectAllSql(){
        return "SELECT * FROM " + entityClassMetaData.getName().toLowerCase();
    };

    public String getSelectByIdSql(){
        return "SELECT * FROM " + entityClassMetaData.getName().toLowerCase() + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    };

    public String getInsertSql(){
        return "INSERT INTO " + entityClassMetaData.getName().toLowerCase() + " (" + entityClassMetaData.getAllFields() + ") values (" + questions() + ")";
    };

    public String questions (){
        int questionsNumber = entityClassMetaData.getAllFields().size();
        StringBuilder questions = new StringBuilder();
        for (int i = 0; i<questionsNumber; i++){
            questions.append("?");
            if (i<questionsNumber-1){
            questions.append(", ");}
        }
        return questions.toString();
    };

    public String getUpdateSql() {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ")
                .append(entityClassMetaData.getName().toLowerCase())
                .append(" SET ");

        List<Field> fields = entityClassMetaData.getFieldsWithoutId();

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            updateSql.append(field.getName()).append(" = ?");
            if (i < fields.size() - 1) {
                updateSql.append(", ");
            }
        }
        return updateSql.toString();
    }

}
