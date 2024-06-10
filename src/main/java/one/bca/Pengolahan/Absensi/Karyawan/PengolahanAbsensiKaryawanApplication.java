package one.bca.Pengolahan.Absensi.Karyawan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PengolahanAbsensiKaryawanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PengolahanAbsensiKaryawanApplication.class, args);
	}

}
