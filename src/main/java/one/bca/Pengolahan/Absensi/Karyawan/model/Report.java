package one.bca.Pengolahan.Absensi.Karyawan.model;

import lombok.Data;

@Data
public class Report {
    private String employeeId;
    private String employeeName;
    private String month;
    private Integer attendanceCount;
    private Integer overtimeHoursTotal;
    private Integer leaveCount;
    private Integer leaveRemaining;

}
