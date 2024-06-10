package one.bca.Pengolahan.Absensi.Karyawan.mapper;

import one.bca.Pengolahan.Absensi.Karyawan.model.Employee;
import one.bca.Pengolahan.Absensi.Karyawan.model.Report;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRowMapper implements RowMapper<Report> {

    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        Report report = new Report();
        report.setEmployeeId(rs.getString("employee_id"));
        report.setEmployeeName(rs.getString("employee_name"));
        report.setAttendanceCount(rs.getInt("attendance_count"));
        report.setOvertimeHoursTotal(rs.getInt("overtime_hours_total"));
        report.setLeaveCount(rs.getInt("leave_count"));
        report.setLeaveRemaining(rs.getInt("leave_remaining"));
        return report;
    }
}
