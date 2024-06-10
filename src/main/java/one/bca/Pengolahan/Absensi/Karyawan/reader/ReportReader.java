package one.bca.Pengolahan.Absensi.Karyawan.reader;

import one.bca.Pengolahan.Absensi.Karyawan.mapper.ReportRowMapper;
import one.bca.Pengolahan.Absensi.Karyawan.model.Report;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class ReportReader {
    private final DataSourceTransactionManager transactionManager;

    public ReportReader(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public static String READ_REPORT_SQL = "select distinct on (employee_id) " +
            "employee_id, " +
            "employee_name, " +
            "(select count(leave_status) from public.employee_attendance where leave_status = 'Present' and employee_id = ea.employee_id) as attendance_count, " +
            "(select sum(overtime) from public.employee_attendance where employee_id = ea.employee_id) as overtime_hours_total, " +
            "(select count(leave_status) from public.employee_attendance where leave_status ='Absent' and employee_id = ea.employee_id) as leave_count, " +
            "(select (select sum(leave_remaining) from public.employee where employee_id = ea.employee_id) " +
            "- (select count(leave_status) from public.employee_attendance where leave_status ='Absent' and employee_id = ea.employee_id) as leave_remaining) " +
            "from public.employee_attendance ea";

    public ItemReader<Report> itemReader() {
        System.out.println("START READ ITEM FROM DB");
        return new JdbcCursorItemReaderBuilder<Report>()
                .dataSource(transactionManager.getDataSource())
                .name("jdbcCursorItemReader")
                .sql(READ_REPORT_SQL)
                .rowMapper(new ReportRowMapper())
                .build();

    }
}
