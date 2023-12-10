package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DataTemplateManagerJdbc implements DataTemplate<Manager> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateManagerJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<Manager> findById(Connection connection, long no) {
        return dbExecutor.executeSelect(connection, "select no, label, param1 from manager where no  = ?", List.of(no), rs -> {
            try {
                if (rs.next()) {
                    return new Manager (rs.getLong("no"), rs.getString("label"), rs.getString("param1"));
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<Manager> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, "select * from manager", Collections.emptyList(), rs -> {
            var managerList = new ArrayList<Manager>();
            try {
                while (rs.next()) {
                    managerList.add(new Manager(rs.getLong("no"), rs.getString("label"), rs.getString("param1")));
                }
                return managerList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, Manager manager) {
        try {
            return dbExecutor.executeStatement(connection, "insert into manager(label, param1) values (?, ?)",
                    List.of(manager.getLabel(), manager.getParam1()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, Manager manager) {
        try {
            dbExecutor.executeStatement(connection, "update manager set label = ?, param1 = ? where no = ?",
                    List.of(manager.getLabel(), manager.getParam1(), manager.getNo()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}