package one.bca.Pengolahan.Absensi.Karyawan;

import jakarta.annotation.Resource;
import one.bca.Pengolahan.Absensi.Karyawan.configuration.EmployeeConfiguration;
import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import one.bca.Pengolahan.Absensi.Karyawan.model.Employee;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@SpringBatchTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class PengolahanAbsensiKaryawanApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	@Qualifier("job")
	Job job;

	@Autowired
	private EmployeeConfiguration employeeConfiguration;

	// Launcher
	@Test
	void contextLoads() throws Exception {
		System.out.println("Run contextLoads test");
		mockMvc.perform(MockMvcRequestBuilders
						.get("/start-job")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Job
	@Test
	void contextLoadsJob() throws Exception {
		System.out.println("Run contextLoadsJob test");
		this.jobLauncherTestUtils.getUniqueJobParametersBuilder();
		this.jobLauncherTestUtils.setJob(job);

		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
		Assertions.assertEquals(ExitStatus.COMPLETED,  jobExecution.getExitStatus());
	}

	// Step Employee
	@Test
	public void contextLoadsStepEmployee() throws Exception{
		System.out.println("Run contextLoadsStepEmployee test");
		JobExecution stepExecutionEmployee = jobLauncherTestUtils.launchStep("getEmployeeStep");
		Assertions.assertEquals(ExitStatus.COMPLETED,  stepExecutionEmployee.getExitStatus());
	}

	// Step Attendance
	@Test
	public void contextLoadsStepAttendance() throws Exception{
		System.out.println("Run contextLoadsStepAttendance test");
		StepExecution stepExecutionEmployee = jobLauncherTestUtils.launchJob().
				createStepExecution("getEmployeeStep").getJobExecution().createStepExecution("attendanceStep");
		Assertions.assertEquals(ExitStatus.EXECUTING,  stepExecutionEmployee.getExitStatus());
	}

	// Step Report
	@Test
	public void contextLoadsStepReport() throws Exception{
		System.out.println("Run contextLoadsStepReport test");
		JobExecution stepExecutionReport = jobLauncherTestUtils.launchStep("writeReportStep");
		Assertions.assertEquals(ExitStatus.COMPLETED,  stepExecutionReport.getExitStatus());
	}

}
