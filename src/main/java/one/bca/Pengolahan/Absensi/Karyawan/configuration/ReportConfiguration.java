package one.bca.Pengolahan.Absensi.Karyawan.configuration;

import one.bca.Pengolahan.Absensi.Karyawan.model.Employee;
import one.bca.Pengolahan.Absensi.Karyawan.model.EmployeeAttendance;
import one.bca.Pengolahan.Absensi.Karyawan.model.Report;
import one.bca.Pengolahan.Absensi.Karyawan.reader.EmployeeAttendanceReader;
import one.bca.Pengolahan.Absensi.Karyawan.reader.EmployeeReader;
import one.bca.Pengolahan.Absensi.Karyawan.reader.ReportReader;
import one.bca.Pengolahan.Absensi.Karyawan.writer.ReportWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ReportConfiguration {
    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;
    private final EmployeeAttendanceReader employeeAttendanceReader;
    private final ReportReader reportReader;
    private final ReportWriter reportWriter;

    public ReportConfiguration(JobRepository jobRepository,
                               DataSourceTransactionManager transactionManager,
                               EmployeeAttendanceReader employeeAttendanceReader,
                               ReportReader reportReader,
                               ReportWriter reportWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.employeeAttendanceReader = employeeAttendanceReader;
        this.reportReader = reportReader;
        this.reportWriter = reportWriter;
    }

    public Step writeReportStep(){
        return new StepBuilder("writeReportStep", jobRepository)
                .<Report, Report>chunk(4, transactionManager)
                .reader(reportReader.itemReader())
                .writer(reportWriter.itemWriter())
                .build();
    }
}
