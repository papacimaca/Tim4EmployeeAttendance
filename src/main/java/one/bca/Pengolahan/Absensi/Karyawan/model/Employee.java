package one.bca.Pengolahan.Absensi.Karyawan.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private String employeeId;
    private String employeeName;
    private String email;
    private Integer leaveRemaining;
}
