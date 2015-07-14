select tb_department.name, tb_employee.name, sum(tb_month_record.id),
count(month)
from tb_employee, tb_month_record, tb_department
where tb_employee.id = tb_month_record.employee_id
    and tb_employee.department_id = tb_department.id
group by tb_employee.department_id, tb_month_record.employee_id


DROP TRIGGER trigger_abc;

CREATE TRIGGER trigger_abc
ON tb_test
for INSERT, UPDATE
AS 
DECLARE @columnInserted int, @columnUpdated int
select @columnInserted = column_test from tb_test inserted	
select @columnUpdated = column_test from tb_test updated	
IF @columnUpdated < 0 or @columnInserted < 0 BEGIN 
PRINT '列 column_test 不能为负数'
ROLLBACK TRANSACTION 
END


DROP TRIGGER trigger_abc;

CREATE TRIGGER trigger_abc
ON tb_test
FOR INSERT
AFTER UPDATE
AS 
DECLARE @columnInserted int, @columnUpdated int
select @columnInserted = column_test from tb_test inserted	
select @columnUpdated = column_test from tb_test updated	
IF @columnUpdated < 0 or @columnInserted < 0 BEGIN 
PRINT '列 column_test 不能为负数'
ROLLBACK TRANSACTION 
END

update tb_test set column_test = -1 where id = 3