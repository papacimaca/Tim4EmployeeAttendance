package one.bca.Pengolahan.Absensi.Karyawan.service;

import lombok.SneakyThrows;
import one.bca.Pengolahan.Absensi.Karyawan.configuration.JobConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;

public class CronJobService {

    private JobLauncher jobLauncher;
    private JobConfiguration jobConfiguration;

    @SneakyThrows
    @Scheduled()
    public void startJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(jobConfiguration.monthlyReportJob(), jobParameters);
            System.out.println ("Job started successfully");
        } catch (Exception e) {
            System.out.println("Job failed to start: " + e.getMessage());
        }

    }
}
