package one.bca.Pengolahan.Absensi.Karyawan.reader;


import one.bca.Pengolahan.Absensi.Karyawan.mapper.AttendanceRowMapper;
import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class AttendanceReader {
    private final DataSourceTransactionManager transactionManager;

    public AttendanceReader(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    public static String ORDER_SQL = "select "
            + "employee_id, employee_name, date, "
            + "clock_in, clock_out, overtime, leave_status "
            + "from attendance order by employee_id";

    public ItemReader<Attendance> itemReader() {
        System.out.println("START READ ITEM FROM DB");
        return new JdbcCursorItemReaderBuilder<Attendance>()
                .dataSource(transactionManager.getDataSource())
                .name("jdbcCursorItemReader")
                .sql(ORDER_SQL)
                .rowMapper(new AttendanceRowMapper())
                .build();

    }

}
