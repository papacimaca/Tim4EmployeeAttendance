CREATE TABLE public.employee (
    employee_id varchar(50) NULL,
    employee_name varchar(50) NULL,
    email varchar(50) NULL,
    leave_remaining int4 NULL
    );

INSERT INTO employee(employee_id,employee_name,email,leave_remaining) VALUES
('AA01','John Smith','johnsmith@email.com',14)
,('AA02','Emily Johnson','emilyjohnson@email.com',14)
,('AA03','Michael Brown','michaelbrown@email.com',14)
,('AA04','Sarah Davis','sarahdavis@email.com',14)
,('AA05','David Wilson','davidwilson@email.com',14)
,('AA06','Jessica Moore','jessicamoore@email.com',14)
,('AA07','Daniel Taylor','danieltaylor@email.com',14)
,('AA08','Laura Anderson','lauraanderson@email.com',14)
,('AA09','Robert Thomas','robertthomas@email.com',14)
,('AA10','Emma Jackson','emmajackson@email.com',14);

CREATE TABLE public.employee_attendance (
    employee_id varchar(50) NULL,
    employee_name varchar(50) NULL,
    date varchar(50) NULL,
    clock_in varchar(50) NULL,
    clock_out varchar(50) NULL,
    overtime int4 NULL,
    leave_status varchar(50) NULL
    );