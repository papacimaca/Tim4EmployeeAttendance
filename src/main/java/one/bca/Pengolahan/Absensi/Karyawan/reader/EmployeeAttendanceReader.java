package one.bca.Pengolahan.Absensi.Karyawan.reader;


import one.bca.Pengolahan.Absensi.Karyawan.mapper.EmployeeAttendanceRowMapper;
import one.bca.Pengolahan.Absensi.Karyawan.mapper.ReportRowMapper;
import one.bca.Pengolahan.Absensi.Karyawan.model.EmployeeAttendance;
import one.bca.Pengolahan.Absensi.Karyawan.model.Report;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAttendanceReader {
    private final DataSourceTransactionManager transactionManager;

    public EmployeeAttendanceReader(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    public static String READ_EMPLOYEE_ATTENDANCE_SQL = "select "
            + "employee_id, employee_name, date, "
            + "clock_in, clock_out, overtime, leave_status "
            + "from employee_attendance order by employee_id";

    public ItemReader<EmployeeAttendance> itemReader() {
        System.out.println("START READ ITEM FROM DB");
        return new JdbcCursorItemReaderBuilder<EmployeeAttendance>()
                .dataSource(transactionManager.getDataSource())
                .name("jdbcCursorItemReader")
                .sql(READ_EMPLOYEE_ATTENDANCE_SQL)
                .rowMapper(new EmployeeAttendanceRowMapper())
                .build();

    }

}
