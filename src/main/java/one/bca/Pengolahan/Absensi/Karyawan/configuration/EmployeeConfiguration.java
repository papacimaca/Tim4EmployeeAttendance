package one.bca.Pengolahan.Absensi.Karyawan.configuration;

import one.bca.Pengolahan.Absensi.Karyawan.listener.CustomRetryListener;
import one.bca.Pengolahan.Absensi.Karyawan.model.Employee;
import one.bca.Pengolahan.Absensi.Karyawan.reader.EmployeeReader;
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

import java.net.http.HttpConnectTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Configuration
public class EmployeeConfiguration {
    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;
    private final EmployeeReader employeeReader;

    public EmployeeConfiguration(JobRepository jobRepository,
                                 DataSourceTransactionManager transactionManager,
                                 EmployeeReader employeeReader) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.employeeReader = employeeReader;
    }

    public Step getEmployeeStep(){
        return new StepBuilder("getEmployeeStep",jobRepository)
                .<Employee, Employee>chunk(4, transactionManager)
                .reader(employeeReader.itemReader())
                .processor(new ItemProcessor<Employee, Employee>() {
                    @Override
                    public Employee process(Employee employee) throws Exception {
                        System.out.println("START PROCESSING EMPLOYEE FROM DB");
                        StepContext stepContext = StepSynchronizationManager.getContext();
                        List<Employee> processedEmployees = (List<Employee>) stepContext
                                .getStepExecution()
                                .getJobExecution()
                                .getExecutionContext()
                                .get("employeeList");
                        if (processedEmployees == null){
                            processedEmployees = new ArrayList<>();
                            processedEmployees.add(employee);
                        } else {
                            processedEmployees.add(employee);
                        }
                        System.out.println("START PUTTING ITEM FORM DB TO STEP CONTEXT");
                        stepContext
                                .getStepExecution()
                                .getJobExecution()
                                .getExecutionContext()
                                .put("employeeList", processedEmployees);

                        return employee;
                    }
                })
                .faultTolerant()
                .retryLimit(5)
                .retry(TimeoutException.class)
                .retry(HttpConnectTimeoutException.class)
                .listener(CustomRetryListener.class)
                .writer(new ItemWriter<Employee>() {
                    @Override
                    public void write(Chunk<? extends Employee> chunk) throws Exception {
                        System.out.println("START WRITE ITEM FROM DB");
                        StepContext stepContext = StepSynchronizationManager.getContext();
                        List<Employee> processedEmployees = (List<Employee>) stepContext
                                .getJobExecutionContext()
                                .get("employeeList");
                        System.out.println("Employee: " + chunk.getItems());
                    }
                }).build();
    }
}
