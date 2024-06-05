package one.bca.Pengolahan.Absensi.Karyawan.configuration;

import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import one.bca.Pengolahan.Absensi.Karyawan.reader.AttendanceReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
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
public class JobConfiguration {

    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;
    private final AttendanceReader attendanceReader;

    public JobConfiguration(JobRepository jobRepository,
                              DataSourceTransactionManager transactionManager,
                              AttendanceReader attendanceReader) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.attendanceReader = attendanceReader;
    }

    public Job monthlyReportJob() {
        return new JobBuilder("monthlyReportJob", jobRepository)
                .start(getAttendanceStep())
                .build();
    }

    public Step getAttendanceStep(){
        return new StepBuilder("getAttendanceStep",jobRepository)
                .<Attendance, Attendance>chunk(4, transactionManager)
                .reader(attendanceReader.itemReader())
                .processor(new ItemProcessor<Attendance, Attendance>() {
                    @Override
                    public Attendance process(Attendance item) throws Exception {
                        //TODO: LOGIC
                        return item;
                    }
                })
                .writer(new ItemWriter<Attendance>() {
                    @Override
                    public void write(Chunk<? extends Attendance> chunk) throws Exception {
                        System.out.println("START WRITE ITEM FROM DB");

                        System.out.println("Attendance data: " + chunk.getItems());
                    }
                }).build();
    }

}
