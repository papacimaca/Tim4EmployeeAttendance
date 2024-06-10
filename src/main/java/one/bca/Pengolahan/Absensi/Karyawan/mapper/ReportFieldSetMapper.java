package one.bca.Pengolahan.Absensi.Karyawan.mapper;

import one.bca.Pengolahan.Absensi.Karyawan.model.Report;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ReportFieldSetMapper implements FieldSetMapper<Report> {
    // FieldSet - binding of fields from a file resource
    @Override
    public Report mapFieldSet(FieldSet fieldSet) throws BindException {
        Report report = new Report();
        report.setEmployeeId(fieldSet.readString("employee_id"));
        report.setEmployeeName(fieldSet.readString("employee_name"));
        report.setAttendanceCount(fieldSet.readInt("attendance_count"));
        report.setOvertimeHoursTotal(fieldSet.readInt("overtime_hours_total"));
        report.setLeaveCount(fieldSet.readInt("leave_count"));
        report.setLeaveRemaining(fieldSet.readInt("leave_remaining"));

        return report;
    }

}