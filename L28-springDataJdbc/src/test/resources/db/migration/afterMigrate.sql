insert into manager(id, label)
values ('mgr-1', 'Manager 1'),('mgr-2', 'Manager 2'),('mgr-3', 'Manager 3');

insert into client(order_column, name, manager_id)
values (1, 'Vasya', 'mgr-1'),(2, 'Sergey', 'mgr-1'),(3, 'Nikolay', 'mgr-1'),(1,'Olga','mgr-2');

insert into client_details(client_id, info)
select id, name || ' info' from client;

insert into table_with_pk(id_part1, id_part2, value)
select m.id, m.label, count(*) as value from manager as m
    inner join client as c on c.manager_id = m.id
group by m.id, m.label;