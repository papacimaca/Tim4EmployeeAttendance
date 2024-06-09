package one.bca.Pengolahan.Absensi.Karyawan.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class JobConfiguration {

    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;
    private final EmployeeConfiguration employeeConfiguration;
    private final AttendanceConfiguration attendanceConfiguration;

    public JobConfiguration(JobRepository jobRepository,
                              DataSourceTransactionManager transactionManager,
                              EmployeeConfiguration employeeConfiguration,
                              AttendanceConfiguration attendanceConfiguration) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.employeeConfiguration = employeeConfiguration;
        this.attendanceConfiguration = attendanceConfiguration;
    }

    public Job monthlyReportJob() {
        return new JobBuilder("monthlyReportJob", jobRepository)
                .start(employeeConfiguration.getEmployeeStep())
                .next(attendanceConfiguration.attendanceStep())
                .build();
    }

//    public Step getAttendanceStep(){
//        return new StepBuilder("getAttendanceStep",jobRepository)
//                .<EmployeeAttendance, EmployeeAttendance>chunk(4, transactionManager)
//                .reader(attendanceReader.itemReader())
//                .processor(new ItemProcessor<EmployeeAttendance, EmployeeAttendance>() {
//                    @Override
//                    public EmployeeAttendance process(EmployeeAttendance item) throws Exception {
//                        //TODO: LOGIC
//                        return item;
//                    }
//                })
//                .writer(new ItemWriter<EmployeeAttendance>() {
//                    @Override
//                    public void write(Chunk<? extends EmployeeAttendance> chunk) throws Exception {
//                        System.out.println("START WRITE ITEM FROM DB");
//
//                        System.out.println("Attendance data: " + chunk.getItems());
//                    }
//                }).build();
//    }

}
