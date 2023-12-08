--Inserting data for 'schedule' table
INSERT INTO schedule (id, is_active)
VALUES (1,TRUE),
       (2,FALSE);

INSERT INTO dictionary (category, label)
VALUES ('testType', 'Flapwise Static Proof'),
       ('testType', 'Edgewise Static Proof'),
       ('testType', 'Post Fatigue Static'),
       ('testType', 'Flapwise Fatigue'),
       ('testType', 'Edgewise Fatigue'),
       ('technician', 'Blacksmith'),
       ('technician', 'Mechanical'),
       ('equipmentType','Stabilizer A'),
       ('equipmentType','Stabilizer B'),
       ('equipmentType','Single Axis Exciter A'),
       ('equipmentType','Single Axis Exciter B'),
       ('equipmentType','Adapter A'),
       ('equipmentType','Adapter B'),
       ('equipmentType','Infrared Camera'),
       ('testRigs', '6');



insert into engineer (name, work_hours, max_work_hours) values ('rodney serle', 39, 96);
insert into engineer (name, work_hours, max_work_hours) values ('garvey hush', 66, 120);
insert into engineer (name, work_hours, max_work_hours) values ('hart giocannoni', 137, 53);
insert into engineer (name, work_hours, max_work_hours) values ('sybille franseco', 25, 80);
insert into engineer (name, work_hours, max_work_hours) values ('garvin vlasin', 6, 60);
insert into engineer (name, work_hours, max_work_hours) values ('ingar mcmichell', 56, 89);
insert into engineer (name, work_hours, max_work_hours) values ('moria allcott', 108, 47);
insert into engineer (name, work_hours, max_work_hours) values ('sibylla l''oiseau', 138, 112);
insert into engineer (name, work_hours, max_work_hours) values ('marie-jeanne roselli', 104, 97);
insert into engineer (name, work_hours, max_work_hours) values ('gabriello saban', 63, 62);

insert into technician (type, work_hours, max_work_hours, count) values ('smith', 34, 32, 10);
insert into technician (type, work_hours, max_work_hours, count) values ('electrician', 11, 34, 16);





insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-a-185', '2024-01-03 17:42:24');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-a-186', '2024-01-03 17:42:24');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-b-187', '2024-01-03 17:42:24');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-b-188', '2024-01-03 17:42:24');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-184', '2023-08-26 13:40:29');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-185', '2023-08-26 13:40:29');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-186', '2022-11-23 05:54:57');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-187', '2022-11-23 05:54:57');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-187', '2023-11-02 09:17:02');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-188', '2023-11-02 09:17:02');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-191', '2026-08-17 09:14:04');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-192', '2026-08-17 09:14:04');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-195', '2025-11-04 07:28:48');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-196', '2025-11-04 07:28:48');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-197', '2023-01-21 03:32:49');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-198', '2023-01-21 03:32:49');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-157', '2026-01-29 04:24:00');



--View --
--blade project 1--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name, in_conflict) values (1, '#77b280' , '2023-12-30', '2024-01-20', 'goldwind', 'curran chanter', 'gw-53', false);

--blade task 1--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2023-12-30', 12, '2024-01-10', 'flapwise static proof', 4, 2, 'gw-53_bt-1', 2, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'equipment', 1, null, null, 1, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'technician', 1, null, 1, null, 10, 'smith');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'technician', 1, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'engineer', 1, 1, null, null, 20, 'rodney serle');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 1, 0, 'stabilizer a', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 1, 10, 'smith', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 1, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 1, 20, 'rodney serle', 'engineer' );

insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 1);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 1);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 2);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 2);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 3);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 3);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 4);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 4);

--blade task 2--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-01-11', 10, '2024-01-20', 'edgewise static proof', 4, 2, 'gw-53_bt-2', 2, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-11', '10', '2024-01-20', 'equipment', 2, null, null, 11, 0, 'single axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-11', '10', '2024-01-20', 'technician', 2, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-11', '10', '2024-01-20', 'engineer', 2, 2, null, null, 20, 'garvey hush');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 2, 0, 'single axis exciter b', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 2, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 2, 20, 'garvey hush', 'engineer' );


insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 5);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 5);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 6);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 6);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 7);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 7);


--blade project 2--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name, in_conflict) values (1, '#acb277' ,'2024-01-07', '2024-02-16', 'suzlon', 'clyve birley', 'su-95', false);

--blade task 3--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (2, '2024-01-07', 20, '2024-01-27', 'edgewise static proof', 5, 3, 'su-95_bt-1', 5, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '20', '2024-01-27', 'equipment', 3, null, null, 1, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '20', '2024-01-27', 'technician', 3, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '20', '2024-01-27', 'engineer', 3, 4, null, null, 20, 'sybille franseco');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 3, 0, 'stabilizer a', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 3, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 3, 20, 'sybille franseco', 'engineer' );



insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 8);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 8);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 9);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 9);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 10);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 10);



--blade task 4--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (2, '2024-01-28', 20, '2024-02-16', 'post fatigue static', 5, 3, 'su-95_bt-2', 5, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'equipment', 4, null, null, 17, 0, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'technician', 4, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'engineer', 4, 5, null, null, 20, 'garvin vlasin');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 3, 0, 'infrared camera', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 3, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 3, 20, 'garvin vlasin', 'engineer' );

insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 11);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 11);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 12);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 12);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 13);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 13);


--blade project 3--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name, in_conflict) values (1, '#b28c77' ,'2023-12-15', '2024-02-19', 'goldwind', 'ringo fernando', 'gw-102', false);


--Edit--
--blade project 1--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name, in_conflict) values (2, '#77b280' , '2023-12-30', '2024-01-20', 'goldwind', 'curran chanter', 'gw-53', false);
--blade task 5--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (4, '2023-12-30', 12, '2024-01-10', 'flapwise static proof', 4, 2, 'gw-53_bt-1', 2, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'Equipment', 5,  null, null, 1, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'technician', 5 ,null, 1, null, 10, 'smith');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'technician', 5, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-30', '12', '2024-01-10', 'engineer', 5, 1, null, null, 20, 'rodney serle');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 5, 0, 'stabilizer a', 'Equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 5, 10, 'smith', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 5, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 5, 20, 'rodney serle', 'engineer' );


insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 14);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 14);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 15);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 15);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 16);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 16);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 17);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 17);


--blade task 6--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (4, '2024-01-11', 10, '2024-01-20', 'edgewise static proof', 4, 2, 'gw-53_bt-2', 2, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-11', '10', '2024-01-20', 'equipment', 6, null, null, 11, 0, 'single axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-11', '10', '2024-01-20', 'technician', 6, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-11', '10', '2024-01-20', 'engineer', 6, 2, null, null, 20, 'garvey hush');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 6, 0, 'single axis exciter b', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 6, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 6, 20, 'garvey hush', 'engineer' );

insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 18);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 18);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 19);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 19);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 20);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 20);

--blade project 2--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name, in_conflict) values (2, '#acb277' ,'2024-01-07', '2024-02-16', 'suzlon', 'clyve birley', 'su-95', false);

--blade task 7--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (5, '2024-01-07', 20, '2024-01-27', 'edgewise static proof', 5, 3, 'su-95_bt-1', 5, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '20', '2024-01-27', 'equipment', 7, null, null, 1, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '20', '2024-01-27', 'technician', 7, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '20', '2024-01-27', 'engineer', 7, 4, null, null, 20, 'sybille franseco');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 7, 0, 'stabilizer a', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 7, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 7, 20, 'sybille franseco', 'engineer' );

insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 21);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 21);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 22);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 22);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 23);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 23);


--blade task 8--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (5, '2024-01-28', 20, '2024-02-16', 'post fatigue static', 5, 3, 'su-95_bt-2', 5, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'equipment', 8, null, null, 17, 0, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'technician', 8, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'engineer', 8, 5, null, null, 20, 'garvin vlasin');

insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 8, 0, 'infrared camera', 'equipment' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 8, 10, 'electrician', 'technician' );
insert into resource_order( blade_task_id, work_hours, resource_name, resource_type) values ( 8, 20, 'garvin vlasin', 'engineer' );

insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 24);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 24);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 25);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 25);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 26);
insert into resource_order_equipment_assignment_status(equipment_assignment_status, resource_order_id) values (true, 26);

--blade project 3--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name, in_conflict) values (2, '#b28c77' ,'2023-12-15', '2024-02-19', 'general electric', 'ringo fernando', 'ge-21', false);





