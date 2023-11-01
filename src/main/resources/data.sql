INSERT INTO booking (booking_date) VALUES ('2019-01-01');
INSERT INTO booking (booking_date) VALUES ('2019-01-02');
INSERT INTO booking (booking_date) VALUES ('2019-01-03');

INSERT INTO blade_project (start_date, end_date, blade_name, project_leader, customer) VALUES
('2023-01-01', '2023-12-31', 'Blade Project 1', 'Leader A', 'Customer A'),
('2023-01-02', '2023-12-30', 'Blade Project 2', 'Leader B', 'Customer B'),
('2023-01-03', '2023-12-29', 'Blade Project 3', 'Leader C', 'Customer C');

INSERT INTO blade_task
(start_date, end_date, duration, task_name, attached_period, attached_task, bladeprojectid)
VALUES
    (20230101, 20231231, 365, 'Task 1 for Project 1', 1, 1, 1),
    (20230102, 20231230, 364, 'Task 2 for Project 1', 2, 2, 1),
    (20230103, 20231229, 363, 'Task 3 for Project 1', 3, 3, 1);

-- 3 tasks for BladeProject with ID 2
INSERT INTO blade_task
(start_date, end_date, duration, task_name, attached_period, attached_task, bladeprojectid)
VALUES
    (20230104, 20231228, 362, 'Task 1 for Project 2', 4, 4, 2),
    (20230105, 20231227, 361, 'Task 2 for Project 2', 5, 5, 2),
    (20230106, 20231226, 360, 'Task 3 for Project 2', 6, 6, 2);

-- 3 tasks for BladeProject with ID 3
INSERT INTO blade_task
(start_date, end_date, duration, task_name, attached_period, attached_task, bladeprojectid)
VALUES
    (20230107, 20231225, 359, 'Task 1 for Project 3', 7, 7, 3),
    (20230108, 20231224, 358, 'Task 2 for Project 3', 8, 8, 3),
    (20230109, 20231223, 357, 'Task 3 for Project 3', 9, 9, 3);

-- Add more data as needed