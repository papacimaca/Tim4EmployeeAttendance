package one.bca.Pengolahan.Absensi.Karyawan.controller;

import one.bca.Pengolahan.Absensi.Karyawan.configuration.JobConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    private final JobLauncher jobLauncher;
    private final JobConfiguration jobConfiguration;

    public JobController(JobLauncher jobLauncher, JobConfiguration jobConfiguration) {
        this.jobLauncher = jobLauncher;
        this.jobConfiguration = jobConfiguration;
    }

    @GetMapping("/start-job")
    public String startJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(jobConfiguration.monthlyReportJob(), jobParameters);
            return "Job started successfully";
        } catch (Exception e) {
            return "Job failed to start: " + e.getMessage();
        }
    }

}
