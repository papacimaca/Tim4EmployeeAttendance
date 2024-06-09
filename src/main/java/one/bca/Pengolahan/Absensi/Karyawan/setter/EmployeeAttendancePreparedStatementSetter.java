package one.bca.Pengolahan.Absensi.Karyawan.setter;

import one.bca.Pengolahan.Absensi.Karyawan.model.EmployeeAttendance;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeAttendancePreparedStatementSetter implements ItemPreparedStatementSetter<EmployeeAttendance> {

    @Override
    public void setValues(EmployeeAttendance employeeAttendance, PreparedStatement ps) throws SQLException {
        ps.setString(1, employeeAttendance.getEmployeeId());
        ps.setString(2, employeeAttendance.getEmployeeName());
        ps.setString(3, employeeAttendance.getDate());
        ps.setString(4,  employeeAttendance.getClockIn());
        ps.setString(5, employeeAttendance.getClockOut());
        ps.setInt(6,employeeAttendance.getOvertime());
        ps.setString(7, employeeAttendance.getLeaveStatus());

    }

}
