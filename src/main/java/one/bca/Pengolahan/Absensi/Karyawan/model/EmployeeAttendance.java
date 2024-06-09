package one.bca.Pengolahan.Absensi.Karyawan.model;

import lombok.Data;

@Data
public class EmployeeAttendance {
    private String employeeId;
    private String employeeName;
    private String date;
    private String clockIn;
    private String clockOut;
    private Integer overtime;
    private String leaveStatus;
}
