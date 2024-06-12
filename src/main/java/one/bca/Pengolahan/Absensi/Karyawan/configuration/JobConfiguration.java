package one.bca.Pengolahan.Absensi.Karyawan.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class JobConfiguration {

    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;
    private final EmployeeConfiguration employeeConfiguration;
    private final AttendanceConfiguration attendanceConfiguration;
    private final ReportConfiguration reportConfiguration;

    public JobConfiguration(JobRepository jobRepository,
                            DataSourceTransactionManager transactionManager,
                            EmployeeConfiguration employeeConfiguration,
                            AttendanceConfiguration attendanceConfiguration,
                            ReportConfiguration reportConfiguration) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.employeeConfiguration = employeeConfiguration;
        this.attendanceConfiguration = attendanceConfiguration;
        this.reportConfiguration = reportConfiguration;
    }

    @Bean(name = "job")
    public Job writeReportJob() {
        return new JobBuilder("writeReportJob V1", jobRepository)
                .start(employeeConfiguration.getEmployeeStep())
                .next(attendanceConfiguration.attendanceStep())
                .next(reportConfiguration.writeReportStep())
                .build();
    }


}
