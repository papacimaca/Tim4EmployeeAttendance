package one.bca.Pengolahan.Absensi.Karyawan.configuration;

import one.bca.Pengolahan.Absensi.Karyawan.listener.CustomRetryListener;
import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import one.bca.Pengolahan.Absensi.Karyawan.model.Employee;
import one.bca.Pengolahan.Absensi.Karyawan.model.EmployeeAttendance;
import one.bca.Pengolahan.Absensi.Karyawan.reader.FlatAttendanceReader;
import one.bca.Pengolahan.Absensi.Karyawan.writer.EmployeeAttendanceWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Configuration
@Component
public class AttendanceConfiguration {
    private final JobRepository jobRepository;
    private final DataSourceTransactionManager transactionManager;
    private final FlatAttendanceReader flatAttendanceReader;
    private final EmployeeAttendanceWriter employeeAttendanceWriter;

    public AttendanceConfiguration(JobRepository jobRepository,
                                   DataSourceTransactionManager transactionManager,
                                   FlatAttendanceReader flatAttendanceReader,
                                   EmployeeAttendanceWriter employeeAttendanceWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.flatAttendanceReader = flatAttendanceReader;
        this.employeeAttendanceWriter = employeeAttendanceWriter;
    }

    public Step attendanceStep(){
        return new StepBuilder("attendanceStep",jobRepository)
                .<Attendance, EmployeeAttendance>chunk(4, transactionManager)
                .reader(flatAttendanceReader.itemReader())
                .processor(
                        new ItemProcessor<Attendance, EmployeeAttendance>() {
                            @Override
                            public EmployeeAttendance process(Attendance attendance) throws Exception {
                                StepContext stepContext = StepSynchronizationManager.getContext();
                                List<Employee> processedEmployees = (List<Employee>) stepContext
                                        .getStepExecution()
                                        .getJobExecution()
                                        .getExecutionContext()
                                        .get("employeeList");

                                EmployeeAttendance employeeAttendance = new EmployeeAttendance();
                                processedEmployees.forEach(employee -> {
                                    if (employee.getEmployeeId().equalsIgnoreCase(attendance.getEmployeeId())){
                                        employeeAttendance.setEmployeeId(employee.getEmployeeId());
                                        employeeAttendance.setEmployeeName(employee.getEmployeeName());
                                        employeeAttendance.setDate(attendance.getDate());
                                        employeeAttendance.setClockIn(attendance.getClockIn());
                                        employeeAttendance.setClockOut(attendance.getClockOut());
                                        employeeAttendance.setOvertime(attendance.getOvertime());
                                        employeeAttendance.setLeaveStatus(attendance.getLeaveStatus());
                                    }
                                });

                                return employeeAttendance;
                            }
                        }
                )
                .faultTolerant()
                .retryLimit(5)
                .retry(TimeoutException.class)
                .retry(HttpConnectTimeoutException.class)
                .listener(CustomRetryListener.class)
                .writer(employeeAttendanceWriter.employeeAttendanceItemWriter(transactionManager.getDataSource()))
                .taskExecutor(taskExecutor())
                .build();
    }

    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("attendanceMultithreadingTaskExecutor");
    }
}