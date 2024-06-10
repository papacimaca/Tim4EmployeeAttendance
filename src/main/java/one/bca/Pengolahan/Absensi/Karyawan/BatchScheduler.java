package one.bca.Pengolahan.Absensi.Karyawan;

import lombok.SneakyThrows;
import one.bca.Pengolahan.Absensi.Karyawan.configuration.JobConfiguration;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class BatchScheduler {
    private JobLauncher jobLauncher;

    @Autowired
    private JobConfiguration jobConfiguration;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @SneakyThrows
    //for testing job run every 15 seconds
    @Scheduled(cron = "0/15 * * * * ?")
    public void startJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(jobConfiguration.writeReportJob(), jobParameters);
            System.out.println ("Job started successfully");
        } catch (Exception e) {
            System.out.println("Job failed to start: " + e.getMessage());
        }

    }

//
//    // At 23:59 on day-of-month 30 in April, June, September, and November
//    @Scheduled(cron = "59 23 30 4,6,9,11 *")
//    public void startJob() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(jobConfiguration.writeReportJob(), jobParameters);
//            System.out.println ("Job started successfully");
//        } catch (Exception e) {
//            System.out.println("Job failed to start: " + e.getMessage());
//        }
//
//    }
//
//    //At 23:59 on day-of-month 31 in January, March, May, July, August, October, and December
//    @Scheduled(cron = "59 23 31 1,3,5,7,8,10,12 *")
//    public void startJob() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(jobConfiguration.writeReportJob(), jobParameters);
//            System.out.println ("Job started successfully");
//        } catch (Exception e) {
//            System.out.println("Job failed to start: " + e.getMessage());
//        }
//
//    }
//    //At 23:59 on day-of-month 28 in February
//    @Scheduled(cron = "59 23 28 2 *")
//    public void startJob() {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(jobConfiguration.writeReportJob(), jobParameters);
//            System.out.println ("Job started successfully");
//        } catch (Exception e) {
//            System.out.println("Job failed to start: " + e.getMessage());
//        }
//
//    }
}
