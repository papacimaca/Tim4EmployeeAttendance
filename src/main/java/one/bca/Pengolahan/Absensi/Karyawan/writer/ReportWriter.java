package one.bca.Pengolahan.Absensi.Karyawan.writer;

import one.bca.Pengolahan.Absensi.Karyawan.model.Report;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ReportWriter {

    public static String[] names = new String[] {"employeeId", "employeeName",
            "attendanceCount", "overtimeHoursTotal",
            "leaveCount", "leaveRemaining"};

    public ItemWriter<Report> itemWriter() {
        FlatFileItemWriter<Report> itemWriter = new FlatFileItemWriter<>();

        itemWriter.setResource(new FileSystemResource("data/report_output.csv"));

        DelimitedLineAggregator<Report> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(";");

        BeanWrapperFieldExtractor<Report> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(names);
        aggregator.setFieldExtractor(fieldExtractor);

        itemWriter.setLineAggregator(aggregator);
        return itemWriter;
    }
}
