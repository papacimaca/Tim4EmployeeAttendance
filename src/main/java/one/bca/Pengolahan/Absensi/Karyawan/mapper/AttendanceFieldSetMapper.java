package one.bca.Pengolahan.Absensi.Karyawan.mapper;

import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class AttendanceFieldSetMapper implements FieldSetMapper<Attendance> {
    @Override
    public Attendance mapFieldSet(FieldSet fieldSet) throws BindException {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(fieldSet.readString("employee_id"));
        attendance.setDate(fieldSet.readString("date"));
        attendance.setClockIn(fieldSet.readString("clock_in"));
        attendance.setClockOut(fieldSet.readString("clock_out"));
        attendance.setOvertime(fieldSet.readInt("overtime"));
        attendance.setLeaveStatus(fieldSet.readString("leave_status"));

        return attendance;
    }

}