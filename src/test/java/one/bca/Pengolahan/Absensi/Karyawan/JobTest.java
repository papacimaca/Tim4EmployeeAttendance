package one.bca.Pengolahan.Absensi.Karyawan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
public class JobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    @Qualifier("job")
    Job job;

    @Test
    void contextLoads() throws Exception {
        this.jobLauncherTestUtils.getUniqueJobParametersBuilder();
        this.jobLauncherTestUtils.setJob(job);

        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
        Assertions.assertEquals(ExitStatus.COMPLETED,  jobExecution.getExitStatus());
    }


}
