package one.bca.Pengolahan.Absensi.Karyawan.mapper;

import one.bca.Pengolahan.Absensi.Karyawan.model.Attendance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceRowMapper implements RowMapper<Attendance> {

    @Override
    public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(rs.getString("employee_id"));
        attendance.setEmployeeName(rs.getString("employee_name"));
        attendance.setDate(rs.getString("date"));
        attendance.setClockIn(rs.getString("clock_in"));
        attendance.setClockOut(rs.getString("clock_out"));
        attendance.setOvertime(rs.getInt("overtime"));
        attendance.setLeaveStatus(rs.getString("leave_status"));
        return attendance;
    }

}
