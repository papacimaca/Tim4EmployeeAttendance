package one.bca.Pengolahan.Absensi.Karyawan.mapper;

import one.bca.Pengolahan.Absensi.Karyawan.model.EmployeeAttendance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeAttendanceRowMapper implements RowMapper<EmployeeAttendance> {

    @Override
    public EmployeeAttendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeAttendance employeeAttendance = new EmployeeAttendance();
        employeeAttendance.setEmployeeId(rs.getString("employee_id"));
        employeeAttendance.setEmployeeName(rs.getString("employee_name"));
        employeeAttendance.setDate(rs.getString("date"));
        employeeAttendance.setClockIn(rs.getString("clock_in"));
        employeeAttendance.setClockOut(rs.getString("clock_out"));
        employeeAttendance.setOvertime(rs.getInt("overtime"));
        employeeAttendance.setLeaveStatus(rs.getString("leave_status"));
        return employeeAttendance;
    }

}
