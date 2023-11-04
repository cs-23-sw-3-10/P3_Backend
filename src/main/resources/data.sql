--Inserting data for 'schedule' table 
INSERT INTO schedule (id, is_active)
VALUES (1, TRUE),
       (2, FALSE);

 --Inserting data for 'blade_project' table
INSERT INTO blade_project (schedule_id, start_date, end_date, customer, project_leader, project_name)
VALUES (1, '20230101', '20230228', 'Customer A', 'Leader A', 'Project A'),
       (2, '20230201', '20230328', 'Customer B', 'Leader B', 'Project B');

 --Inserting data for 'blade_task' table with 3 consecutive tasks for each project
INSERT INTO blade_task ( blade_project_id, start_date, end_date, duration, test_type, attach_period, detach_period,
                        task_name, test_rig)
VALUES ( 1, '20230101', '20230115', 15, 'Type A', 2, 3, 'Task A1', 1),
       ( 1, '20230116', '20230130', 15, 'Type B', 2, 3, 'Task A2', 1),
       ( 1, '20230131', '20230330', 15, 'Type C', 2, 3, 'Task A3', 1),
       ( 2, '20230201', '20230215', 15, 'Type D', 2, 3, 'Task B1', 2),
       ( 2, '20230216', '20230228', 15, 'Type E', 2, 3, 'Task B2', 2),
       ( 2, '20230231', '20230331', 15, 'Type F', 2, 3, 'Task B3', 2);

 --Inserting data for 'equipment', 'engineer' and 'technician' tables
INSERT INTO equipment (id, type, calibration_expiration_date, name)
VALUES (1, 'Machine A', '20231231', 'Equipment A'),
       (2, 'Machine B', '20240131', 'Equipment B');

INSERT INTO engineer (id, name, work_hours, max_work_hours)
VALUES (1, 'Engineer A', 40, 160),
       (2, 'Engineer B', 30, 120);

INSERT INTO technician (id, type, work_hours, max_work_hours, count)
VALUES (1, 'Technician A', 40, 160, 1),
       (2, 'Technician B', 30, 120, 2);

 --Inserting data for 'booking' table with 4 bookings for each blade task
 --Each blade task should have a relation to only one of equipment, technician, or engineer
INSERT INTO booking (id, start_date, end_date, duration, resource_type, work_hours, blade_task_id, equipment_id,
                     engineer_id, technician_id)
VALUES (1, '20230101', '20230104', 4, 'Resource A', 10, 1, 1, NULL, NULL),
       (2, '20230105', '20230108', 4, 'Resource A', 10, 1, NULL, 1, NULL),
       (3, '20230109', '20230112', 4, 'Resource A', 10, 1, NULL, NULL, 1),
       (4, '20230113', '20230116', 4, 'Resource A', 10, 1, 2, NULL, NULL);
