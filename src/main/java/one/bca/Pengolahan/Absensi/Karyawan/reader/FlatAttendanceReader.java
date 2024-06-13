package one.bca.Pengolahan.Absensi.Karyawan.reader;

import one.bca.Pengolahan.Absensi.Karyawan.mapper.AttendanceFieldSetMapper;
import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class FlatAttendanceReader {
    public static String[] tokens = new String[] {"employee_id", "date",
            "clock_in", "clock_out", "overtime", "leave_status"};

    public ItemReader<Attendance> itemReader() {
        FlatFileItemReader<Attendance> itemReader = new FlatFileItemReader<Attendance>();
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new FileSystemResource("data/attendance_data.csv"));

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(tokens);
        tokenizer.setDelimiter(";");

        DefaultLineMapper<Attendance> lineMapper = new DefaultLineMapper<Attendance>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new AttendanceFieldSetMapper());

        itemReader.setLineMapper(lineMapper);
        return itemReader;

    }
}