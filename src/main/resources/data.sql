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

INSERT INTO engineer (id, name, work_hours, max_work_hours)
VALUES (1, 'Engineer A', 40, 160),
       (2, 'Engineer B', 30, 120);

INSERT INTO technician (id, type, work_hours, max_work_hours, count)
VALUES (1, 'Technician A', 40, 160, 1),
       (2, 'Technician B', 30, 120, 2);

insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR33', 'Framing (Steel)', '26-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO06', 'Ornamental Railings', '20-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY81', 'Masonry', '19-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR21', 'Asphalt Paving', '07-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentU51', 'Framing (Wood)', '02-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ46', 'Fire Sprinkler System', '25-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM07', 'RF Shielding', '07-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR51', 'Curb & Gutter', '15-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL77', 'EIFS', '21-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH97', 'Construction Clean and Final Clean', '30-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW16', 'Termite Control', '27-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentB46', 'Electrical', '11-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentB29', 'Site Furnishings', '29-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG41', 'Hard Tile & Stone', '18-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentF88', 'Structural and Misc Steel (Fabrication)', '01-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG62', 'Marlite Panels (FED)', '18-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO77', 'HVAC', '19-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT93', 'Marlite Panels (FED)', '06-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM79', 'Drywall & Acoustical (FED)', '03-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN32', 'Casework', '20-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO70', 'Waterproofing & Caulking', '13-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ46', 'Asphalt Paving', '26-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD66', 'EIFS', '11-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA49', 'Electrical', '04-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW91', 'Structural & Misc Steel Erection', '07-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA30', 'Site Furnishings', '09-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN46', 'Glass & Glazing', '14-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX11', 'Prefabricated Aluminum Metal Canopies', '20-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA98', 'Structural and Misc Steel (Fabrication)', '16-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ79', 'Asphalt Paving', '31-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX36', 'Masonry', '16-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN88', 'Granite Surfaces', '01-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP57', 'Epoxy Flooring', '17-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY13', 'Termite Control', '30-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT79', 'Granite Surfaces', '15-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentK02', 'Doors, Frames & Hardware', '17-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP38', 'Landscaping & Irrigation', '06-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN32', 'Rebar & Wire Mesh Install', '23-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL46', 'Electrical and Fire Alarm', '30-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS06', 'Painting & Vinyl Wall Covering', '13-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentC85', 'Landscaping & Irrigation', '21-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX82', 'Rebar & Wire Mesh Install', '11-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS83', 'Glass & Glazing', '03-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO55', 'Marlite Panels (FED)', '28-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentU32', 'Plumbing & Medical Gas', '28-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW22', 'Framing (Wood)', '05-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT30', 'RF Shielding', '30-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX94', 'Soft Flooring and Base', '15-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentU89', 'EIFS', '08-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ13', 'Glass & Glazing', '02-11-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV97', 'Framing (Wood)', '27-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentB61', 'Marlite Panels (FED)', '26-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR70', 'Hard Tile & Stone', '11-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG84', 'Masonry', '27-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW27', 'Plumbing & Medical Gas', '03-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD28', 'Structural and Misc Steel (Fabrication)', '08-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentZ47', 'Site Furnishings', '13-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR10', 'Asphalt Paving', '14-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG63', 'Granite Surfaces', '08-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ60', 'Site Furnishings', '19-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO03', 'Landscaping & Irrigation', '26-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentI73', 'Painting & Vinyl Wall Covering', '06-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL60', 'Roofing (Metal)', '15-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD34', 'Masonry & Precast', '15-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentC33', 'Granite Surfaces', '16-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD33', 'RF Shielding', '07-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA35', 'Structural and Misc Steel (Fabrication)', '01-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentI00', 'Masonry & Precast', '23-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP90', 'Rebar & Wire Mesh Install', '11-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentC25', 'Retaining Wall and Brick Pavers', '21-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV13', 'Prefabricated Aluminum Metal Canopies', '21-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV40', 'Curb & Gutter', '19-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentI58', 'Waterproofing & Caulking', '31-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentB28', 'RF Shielding', '29-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM28', 'Overhead Doors', '17-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ84', 'Overhead Doors', '18-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA69', 'Granite Surfaces', '02-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV03', 'Epoxy Flooring', '23-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL58', 'Glass & Glazing', '01-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR27', 'Structural and Misc Steel (Fabrication)', '07-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN31', 'Electrical', '04-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS90', 'Roofing (Asphalt)', '13-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV74', 'Asphalt Paving', '19-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN06', 'Masonry & Precast', '05-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR64', 'Rebar & Wire Mesh Install', '31-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR98', 'Masonry & Precast', '18-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO59', 'Casework', '31-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL36', 'Site Furnishings', '25-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM60', 'Drywall & Acoustical (FED)', '29-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentE55', 'Drywall & Acoustical (FED)', '04-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM80', 'Prefabricated Aluminum Metal Canopies', '03-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ80', 'Wall Protection', '11-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW74', 'Glass & Glazing', '18-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentK42', 'Roofing (Asphalt)', '10-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR23', 'Casework', '21-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV33', 'Curb & Gutter', '31-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ15', 'Ornamental Railings', '06-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH55', 'Curb & Gutter', '05-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH37', 'Sitework & Site Utilities', '28-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM51', 'Landscaping & Irrigation', '07-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM90', 'Curb & Gutter', '08-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH68', 'Masonry & Precast', '30-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS86', 'Drywall & Acoustical (FED)', '02-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL85', 'EIFS', '28-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ56', 'Structural & Misc Steel Erection', '29-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN56', 'Masonry & Precast', '22-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM57', 'Painting & Vinyl Wall Covering', '04-11-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentQ59', 'Doors, Frames & Hardware', '02-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentQ86', 'Asphalt Paving', '26-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentB62', 'Plumbing & Medical Gas', '16-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH64', 'Electrical', '28-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentZ85', 'Retaining Wall and Brick Pavers', '29-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM75', 'Marlite Panels (FED)', '24-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM38', 'Hard Tile & Stone', '29-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ02', 'Drilled Shafts', '11-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY65', 'Construction Clean and Final Clean', '13-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA10', 'Fire Protection', '06-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ57', 'Sitework & Site Utilities', '14-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR92', 'Plumbing & Medical Gas', '25-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentU64', 'Glass & Glazing', '03-11-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY20', 'Termite Control', '08-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentE33', 'Epoxy Flooring', '27-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN35', 'Ornamental Railings', '29-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentU73', 'Asphalt Paving', '18-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT38', 'Landscaping & Irrigation', '05-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS58', 'Prefabricated Aluminum Metal Canopies', '17-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY43', 'Soft Flooring and Base', '13-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN02', 'Wall Protection', '18-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG22', 'Waterproofing & Caulking', '10-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT37', 'Waterproofing & Caulking', '01-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentL91', 'Granite Surfaces', '04-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY15', 'Drilled Shafts', '26-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV84', 'Glass & Glazing', '11-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP47', 'HVAC', '17-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM09', 'RF Shielding', '02-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM20', 'Construction Clean and Final Clean', '26-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW35', 'Retaining Wall and Brick Pavers', '19-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY55', 'Roofing (Asphalt)', '16-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP20', 'Structural & Misc Steel Erection', '12-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO82', 'Framing (Wood)', '20-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY33', 'Drywall & Acoustical (MOB)', '08-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentE04', 'Roofing (Asphalt)', '11-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT22', 'Ornamental Railings', '02-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentE82', 'Marlite Panels (FED)', '09-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentY53', 'Wall Protection', '28-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX32', 'Construction Clean and Final Clean', '08-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS84', 'Construction Clean and Final Clean', '09-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR93', 'Drywall & Acoustical (MOB)', '18-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentK54', 'Ornamental Railings', '02-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentI42', 'Prefabricated Aluminum Metal Canopies', '25-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM52', 'RF Shielding', '20-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO84', 'Asphalt Paving', '25-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentQ29', 'Framing (Steel)', '26-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP15', 'Plumbing & Medical Gas', '01-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentZ59', 'Electrical and Fire Alarm', '18-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV09', 'HVAC', '31-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentK94', 'Drilled Shafts', '19-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentE19', 'Granite Surfaces', '07-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentA11', 'Plumbing & Medical Gas', '23-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG88', 'Glass & Glazing', '07-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentO94', 'Masonry', '13-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD34', 'Framing (Wood)', '24-09-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH69', 'Hard Tile & Stone', '06-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD39', 'Doors, Frames & Hardware', '21-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW82', 'Drywall & Acoustical (MOB)', '20-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentG41', 'Roofing (Metal)', '09-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX14', 'EIFS', '24-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW51', 'Landscaping & Irrigation', '01-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ38', 'Roofing (Asphalt)', '31-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentX77', 'Construction Clean and Final Clean', '18-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentI89', 'Framing (Wood)', '11-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP28', 'Termite Control', '23-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS82', 'Waterproofing & Caulking', '24-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ13', 'Exterior Signage', '26-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentS80', 'HVAC', '20-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentU40', 'RF Shielding', '25-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH73', 'EIFS', '22-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentV11', 'Elevator', '07-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentQ69', 'Marlite Panels (FED)', '21-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW90', 'Roofing (Metal)', '08-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH67', 'Temp Fencing, Decorative Fencing and Gates', '03-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ16', 'Drywall & Acoustical (MOB)', '24-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentF30', 'Fire Sprinkler System', '06-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD66', 'Wall Protection', '25-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentD73', 'Casework', '03-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentW48', 'Framing (Steel)', '13-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH19', 'Sitework & Site Utilities', '16-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentF49', 'Wall Protection', '13-08-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentT01', 'Granite Surfaces', '25-04-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentQ78', 'Landscaping & Irrigation', '05-07-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentR78', 'Fire Protection', '20-02-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentN34', 'Casework', '05-05-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentM84', 'Masonry', '18-06-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentH86', 'Termite Control', '27-11-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ47', 'Granite Surfaces', '01-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentE76', 'Electrical', '06-01-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentK25', 'Overhead Doors', '19-03-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentJ39', 'Doors, Frames & Hardware', '18-10-2023');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentQ22', 'Painting & Vinyl Wall Covering', '03-12-2022');
insert into equipment (name, type, calibration_expiration_date) values ('EquipmentP19', 'Site Furnishings', '30-07-2023');

INSERT INTO booking (duration, equipment_id, work_hours, start_date,end_date,resource_type, blade_task_id)
VALUES (10,1,34,2,3,'test',1),
       (11,1,34,2,3,'test',1),
       (14,1,34,2,3,'test',1),
       (16,1,34,2,3,'test',1),
       (18,1,34,2,3,'test',1);