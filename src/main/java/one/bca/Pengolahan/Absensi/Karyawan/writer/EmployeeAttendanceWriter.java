package one.bca.Pengolahan.Absensi.Karyawan.writer;

import one.bca.Pengolahan.Absensi.Karyawan.model.EmployeeAttendance;
import one.bca.Pengolahan.Absensi.Karyawan.setter.EmployeeAttendancePreparedStatementSetter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EmployeeAttendanceWriter {
    private final String INSERT_EMPLOYEE_ATTENDANCE_SQL = "INSERT INTO employee_attendance " +
            "(employee_id, employee_name, date, clock_in, clock_out, overtime, leave_status) " +
            "VALUES (?,?,?,?,?,?,?)";

    public ItemWriter<EmployeeAttendance> employeeAttendanceItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<EmployeeAttendance>()
                .dataSource(dataSource)
                .sql(INSERT_EMPLOYEE_ATTENDANCE_SQL)
                .itemPreparedStatementSetter(new EmployeeAttendancePreparedStatementSetter())
                .build();

    }
}
