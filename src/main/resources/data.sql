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


insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-1', '2024-05-14 13:47:53');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-2', '2025-12-08 15:13:36');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-3', '2024-11-02 05:01:31');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-4', '2023-04-15 12:41:14');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-5', '2025-12-31 09:14:04');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-6', '2025-03-23 13:15:01');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-7', '2023-04-20 14:09:43');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-8', '2024-10-23 05:19:22');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-9', '2028-07-09 08:18:04');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-10', '2024-03-19 14:27:43');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-11', '2026-09-30 17:10:03');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-12', '2023-04-15 09:36:31');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-13', '2028-10-13 13:05:25');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-14', '2028-07-29 21:59:06');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-15', '2024-08-27 15:36:01');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-16', '2023-08-09 15:25:43');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-17', '2028-07-09 15:11:47');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-18', '2028-09-11 13:36:08');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-19', '2027-09-03 16:19:33');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-20', '2027-07-18 06:33:05');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-21', '2026-05-27 04:37:26');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-22', '2022-11-20 02:20:03');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-23', '2027-08-18 19:17:06');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-24', '2023-10-05 04:29:36');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-25', '2025-01-18 12:28:25');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-26', '2024-04-17 02:05:20');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-27', '2024-01-18 06:03:06');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-28', '2024-09-10 15:46:34');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-29', '2026-03-27 23:05:01');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-30', '2025-11-18 22:37:48');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-31', '2025-01-08 21:33:00');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-32', '2026-05-07 13:50:30');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-33', '2027-04-01 23:30:17');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-34', '2028-05-30 06:38:15');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-35', '2028-10-06 05:39:35');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-36', '2023-10-30 08:06:49');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-37', '2028-07-10 14:46:58');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-38', '2023-04-21 16:22:18');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-39', '2023-07-13 01:31:33');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-40', '2025-07-11 18:15:41');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-41', '2026-09-01 15:59:06');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-42', '2023-02-28 08:37:28');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-43', '2028-08-24 22:35:03');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-44', '2026-09-21 08:35:01');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-45', '2026-01-19 12:08:21');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-46', '2025-04-14 14:59:22');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-47', '2024-01-13 21:11:32');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-48', '2027-09-04 16:53:19');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-49', '2022-12-13 21:21:32');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-50', '2027-05-02 08:59:00');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-51', '2025-01-10 19:56:04');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-52', '2026-06-02 05:36:40');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-53', '2028-07-10 07:31:31');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-54', '2023-08-21 20:50:28');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-55', '2025-05-20 06:06:17');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-56', '2026-06-29 17:04:08');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-57', '2024-12-03 18:57:47');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-58', '2025-05-11 05:24:24');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-59', '2022-11-12 03:53:05');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-60', '2028-08-22 17:00:17');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-61', '2023-02-01 18:44:28');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-62', '2025-01-11 13:28:45');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-63', '2023-05-21 09:08:45');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-64', '2027-01-28 07:56:39');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-65', '2024-10-02 02:30:38');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-66', '2023-01-18 23:12:55');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-67', '2026-12-22 06:45:29');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-68', '2026-06-16 20:39:53');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-69', '2026-12-09 05:57:05');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-70', '2024-01-10 06:52:17');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-71', '2027-04-21 08:09:36');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-72', '2024-05-03 20:00:46');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-73', '2027-02-16 00:47:06');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-74', '2023-02-03 07:54:48');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-75', '2027-03-19 19:36:30');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-76', '2027-10-18 00:29:39');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-77', '2028-03-15 03:50:04');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-78', '2028-08-14 14:03:41');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-79', '2026-01-13 15:11:51');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-80', '2026-07-10 00:31:59');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-81', '2024-05-17 02:20:34');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-82', '2024-11-10 09:59:11');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-83', '2028-07-21 03:18:01');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-84', '2024-08-12 19:51:28');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-85', '2028-07-16 13:46:02');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-86', '2025-07-08 19:47:13');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-87', '2028-09-11 18:48:20');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-88', '2027-07-22 00:05:59');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-89', '2026-04-14 09:21:23');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-90', '2026-08-18 04:19:50');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-91', '2023-02-14 21:38:18');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-92', '2026-11-08 10:26:41');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-93', '2026-03-18 04:37:08');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-94', '2027-03-14 17:40:18');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-95', '2028-03-11 14:41:45');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-96', '2024-06-06 20:43:03');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-97', '2026-03-31 15:54:16');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-98', '2028-05-06 15:26:23');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-99', '2028-06-21 12:12:00');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-100', '2026-12-30 11:23:42');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-101', '2025-10-31 10:17:15');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-102', '2024-03-16 13:32:50');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-103', '2023-08-26 11:46:03');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-104', '2023-03-22 19:56:19');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-105', '2024-11-06 18:11:36');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-106', '2025-05-03 01:57:35');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-107', '2024-04-02 07:19:00');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-108', '2028-04-07 04:27:59');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-109', '2024-09-08 04:32:49');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-110', '2027-05-09 04:20:18');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-111', '2027-03-24 03:06:02');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-112', '2028-06-20 16:29:03');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-113', '2025-05-26 12:23:03');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-114', '2026-11-30 05:09:40');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-115', '2024-07-02 23:27:17');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-116', '2023-04-20 02:28:23');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-117', '2027-01-28 00:07:09');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-118', '2025-10-13 11:38:55');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-119', '2023-06-11 14:43:48');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-120', '2025-04-23 02:44:27');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-121', '2027-05-15 06:57:18');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-122', '2024-06-28 06:33:52');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-123', '2027-09-02 07:33:39');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-124', '2024-08-14 14:24:26');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-125', '2025-02-08 18:20:34');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-126', '2027-02-22 22:59:37');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-127', '2024-05-31 15:55:37');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-128', '2027-03-02 00:32:09');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-129', '2025-02-27 10:28:37');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-130', '2028-04-04 22:16:37');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-131', '2027-07-23 19:53:47');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-132', '2027-05-16 11:55:42');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-133', '2028-09-05 19:02:00');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-134', '2028-04-01 12:58:13');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-135', '2027-04-10 18:40:37');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-136', '2023-06-26 22:14:54');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-137', '2026-10-21 15:42:19');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-138', '2026-08-09 11:33:04');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-139', '2025-04-22 15:26:30');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-140', '2026-02-17 13:17:38');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-141', '2025-08-10 11:52:05');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-142', '2028-05-21 21:52:24');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-143', '2023-09-24 02:23:58');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-144', '2027-04-10 11:55:35');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-145', '2028-02-08 05:07:51');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-146', '2025-06-17 00:58:51');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-147', '2027-07-29 09:26:21');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-148', '2024-08-09 10:18:11');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-149', '2024-05-30 22:30:32');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-150', '2024-08-03 13:36:37');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-151', '2026-04-07 16:53:34');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-152', '2024-04-30 23:24:53');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-153', '2023-08-27 08:43:02');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-154', '2028-01-14 05:02:37');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-155', '2025-12-22 19:50:16');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-156', '2025-03-29 07:29:34');
insert into equipment (type, name, calibration_expiration_date) values ('infrared camera', 'irc-157', '2026-01-29 04:24:00');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-158', '2025-06-07 23:45:30');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-159', '2026-11-03 14:38:02');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-160', '2023-02-24 12:21:26');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-161', '2027-07-12 17:15:17');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-162', '2024-09-03 08:23:10');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-163', '2026-06-20 00:28:03');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-164', '2024-06-26 16:52:24');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-165', '2028-03-09 10:10:32');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-166', '2023-07-15 12:57:56');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-167', '2027-04-23 19:15:07');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-168', '2023-03-05 13:30:12');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-169', '2024-08-26 13:44:48');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-170', '2028-09-06 08:17:09');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-171', '2023-10-12 00:00:40');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-172', '2026-07-05 17:01:22');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-173', '2027-07-31 13:51:03');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-174', '2023-02-21 08:07:38');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-175', '2023-06-07 23:10:49');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-176', '2025-03-14 23:21:24');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-177', '2025-02-15 07:08:54');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-178', '2026-08-29 02:24:09');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-179', '2027-09-27 23:51:45');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-180', '2023-03-07 20:54:42');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-181', '2024-08-05 20:44:30');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer b', 'sta-182', '2028-06-04 02:26:03');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-183', '2022-11-23 05:54:57');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-184', '2023-08-26 13:40:29');
insert into equipment (type, name, calibration_expiration_date) values ('stabilizer a', 'sta-185', '2024-01-03 17:42:24');
insert into equipment (type, name, calibration_expiration_date) values ('adapter a', 'ada-186', '2028-04-19 14:27:31');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-187', '2023-11-02 09:17:02');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-188', '2028-10-31 18:26:36');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-189', '2027-11-15 20:03:02');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-190', '2023-02-15 15:19:26');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter b', 'exc-191', '2026-08-17 09:14:04');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-192', '2028-03-07 06:16:06');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-193', '2025-01-28 11:08:27');
insert into equipment (type, name, calibration_expiration_date) values ('single axis exciter a', 'exc-194', '2026-04-24 06:05:52');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-195', '2025-11-04 07:28:48');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter a', 'exc-196', '2026-07-13 04:04:11');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-197', '2023-01-21 03:32:49');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-198', '2023-07-14 02:35:34');
insert into equipment (type, name, calibration_expiration_date) values ('adapter b', 'ada-199', '2028-01-06 20:03:03');
insert into equipment (type, name, calibration_expiration_date) values ('dual axis exciter b', 'exc-200', '2026-04-02 05:29:08');

--blade project 1--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name) values (2, '#77b280' , '2024-01-01', '2024-03-01', 'goldwind', 'curran chanter', 'gw-53');

--blade task 1--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-01-01', 9, '2024-01-09', 'flapwise static proof', 4, 2, 'gw-53_bt-1', 1, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-01', '9', '2024-01-09', 'equipment', 1, null, null, 1, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-01', '9', '2024-01-09', 'technician', 1, null, 1, null, 10, 'smith');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-01', '9', '2024-01-09', 'technician', 1, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-01', '9', '2024-01-09', 'engineer', 1, 1, null, null, 20, 'rodney serle');

--blade task 2--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-01-10', 11, '2024-01-20', 'edgewise static proof', 4, 2, 'gw-53_bt-2', 1, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-10', '11', '2024-01-20', 'equipment', 2, null, null, 2, 0, 'single axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-10', '11', '2024-01-20', 'technician', 2, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-10', '11', '2024-01-20', 'engineer', 2, 2, null, null, 20, 'garvey hush');

--blade task 3--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-01-21', 10, '2024-01-30', 'post fatigue static', 4, 2, 'gw-53_bt-3', 1, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-21', '10', '2024-01-30', 'equipment', 3, null, null, 3, 0, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-21', '10', '2024-01-30', 'technician', 3, null, 2, null, 10, 'electrician');

--blade task 4--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-01-31', 10, '2024-02-09', 'flapwise fatigue', 4, 2, 'gw-53_bt-4', 1, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-31', '10', '2024-02-09', 'equipment', 4, null, null, 4, 0, 'dual axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-31', '10', '2024-02-09', 'engineer', 4, 3, null, null, 20, 'hart giocannoni');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-31', '10', '2024-02-09', 'technician', 4, null, 2, null, 10, 'electrician');

--blade task 5--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-02-10', 10, '2024-02-19', 'edgewise fatigue', 4, 2, 'gw-53_bt-5', 1, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-10', '10', '2024-02-19', 'equipment', 5, null, null, 5, 0, 'adapter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-10', '10', '2024-02-19', 'technician', 5, null, 1, null, 10, 'smith');


--blade task 6--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, '2024-02-20', 10, '2024-03-01', 'flapwise static proof', 4, 2, 'gw-53_bt-6', 1, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-20', '10', '2024-03-01', 'equipment', 6, null, null, 6, 0, 'single axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-20', '10', '2024-03-01', 'technician', 6, null, 1, null, 10, 'smith');

--blade task 7 (not scheduled)--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (1, null, 10, null, 'flapwise static proof', 4, 2, 'gw-53_bt-6', null,false);

--blade project 2--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name) values (2, '#acb277' ,'2024-01-07', '2024-03-28', 'suzlon', 'clyve birley', 'su-95');

--blade task 7--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (2, '2024-01-07', 21, '2024-01-27', 'edgewise static proof', 5, 3, 'su-95_bt-1', 2, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '21', '2024-01-27', 'equipment', 7, null, null, 7, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '21', '2024-01-27', 'technician', 7, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-07', '21', '2024-01-27', 'engineer', 7, 4, null, null, 20, 'sybille franseco');

--blade task 8--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (2, '2024-01-28', 20, '2024-02-16', 'post fatigue static', 5, 3, 'su-95_bt-2', 2, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'equipment', 8, null, null, 8, 0, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'technician', 8, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-28', '20', '2024-02-16', 'engineer', 8, 5, null, null, 20, 'garvin vlasin');

--blade task 9--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (2, '2024-02-17', 21, '2024-03-08', 'flapwise fatigue', 5, 3, 'su-95_bt-3', 3, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-17', '21', '2024-03-08', 'equipment', 9, null, null, 9, 0, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-17', '21', '2024-03-08', 'equipment', 9, null, null, 30, 10, 'single axis exciter a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-17', '21', '2024-03-08', 'technician', 9, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-17', '21', '2024-03-08', 'engineer', 9, 6, null, null, 20, 'ingar mcmichell');

--blade task 10--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (2, '2024-03-09', 20, '2024-03-28', 'edgewise fatigue', 5, 3, 'su-95_bt-4', 3, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-03-09', '20', '2024-03-28', 'equipment', 10, null, null, 10, 0, 'adapter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-03-09', '20', '2024-03-28', 'equipment', 10, null, null, 33, 10, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-03-09', '20', '2024-03-28', 'technician', 10, null, 2, null, 10, 'electrician');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-03-09', '20', '2024-03-28', 'engineer', 10, 7, null, null, 20, 'moria allcott');


--blade project 3--
insert into blade_project (schedule_id,color, start_date, end_date, customer, project_leader, project_name) values (2, '#b28c77' ,'2023-12-15', '2024-02-19', 'goldwind', 'ringo fernando', 'gw-102');

--blade task 11--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (3, '2023-12-15', 20, '2024-01-03', 'flapwise static proof', 4, 2, 'gw-102_bt-1', 4, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-15', '20', '2024-01-03', 'equipment', 11, null, null, 11, 0, 'single axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-15', '20', '2024-01-03', 'technician', 11, null, 1, null, 10, 'smith');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-15', '20', '2024-01-03', 'engineer', 11, 1, null, null, 20, 'rodney serle');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2023-12-15', '20', '2024-01-03', 'engineer', 11, 2, null, null, 20, 'garvey hush');

--blade task 12--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (3, '2024-01-04', 18, '2024-01-21', 'edgewise static proof', 4, 2, 'gw-102_bt-2', 4, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-04', '18', '2024-01-21', 'equipment', 12, null, null, 12, 0, 'single axis exciter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-04', '18', '2024-01-21', 'equipment', 12, null, null, 60, 10, 'dual axis exciter a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-04', '18', '2024-01-21', 'technician', 12, null, 1, null, 10, 'smith');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-04', '18', '2024-01-21', 'engineer', 12, 3, null, null, 20, 'hart giocannoni');

--blade task 13--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (3, '2024-01-22', 10, '2024-01-31', 'post fatigue static', 4, 2, 'gw-102_bt-3', 4, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-22', '10', '2024-01-31', 'equipment', 13, null, null, 13, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-22', '10', '2024-01-31', 'equipment', 13, null, null, 23, 0, 'dual axis exciter a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-22', '10', '2024-01-31', 'equipment', 13, null, null, 33, 0, 'infrared camera');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-01-22', '10', '2024-01-31', 'technician', 13, null, 1, null, 10, 'smith');

--blade task 14--
insert into blade_task (blade_project_id, start_date, duration, end_date, test_type, attach_period, detach_period, task_name, test_rig, in_conflict) values (3, '2024-02-01', 19, '2024-02-19', 'flapwise fatigue', 4, 2, 'gw-102_bt-4', 6, 'false');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-01', '19', '2024-02-19', 'equipment', 14, null, null, 14, 0, 'adapter a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-01', '19', '2024-02-19', 'equipment', 14, null, null, 44, 0, 'adapter b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-01', '19', '2024-02-19', 'equipment', 14, null, null, 35, 0, 'stabilizer b');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-01', '19', '2024-02-19', 'equipment', 14, null, null, 66, 0, 'stabilizer a');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-01', '19', '2024-02-19', 'engineer', 14, 3, null, null, 20, 'hart giocannoni');
insert into booking (start_date, duration, end_date, resource_type, blade_task_id, engineer_id, technician_id, equipment_id, work_hours, resource_name) values ('2024-02-01', '19', '2024-02-19', 'engineer', 14, 7, null, null, 20, 'moria allcott');
















