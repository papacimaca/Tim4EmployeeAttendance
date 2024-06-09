package one.bca.Pengolahan.Absensi.Karyawan.reader;


import one.bca.Pengolahan.Absensi.Karyawan.mapper.EmployeeRowMapper;
import one.bca.Pengolahan.Absensi.Karyawan.model.Employee;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReader {
    private final DataSourceTransactionManager transactionManager;

    public EmployeeReader(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    public static String READ_EMPLOYEE_SQL = "select "
            + "employee_id, employee_name, "
            + "email, leave_remaining "
            + "from employee order by employee_id";

    public ItemReader<Employee> itemReader() {
        return new JdbcCursorItemReaderBuilder<Employee>()
                .dataSource(transactionManager.getDataSource())
                .name("jdbcCursorItemReader")
                .sql(READ_EMPLOYEE_SQL)
                .rowMapper(new EmployeeRowMapper())
                .build();

    }

}
