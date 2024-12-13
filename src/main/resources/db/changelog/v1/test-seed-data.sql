-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO organization (id, name, slogan)
VALUES
    ('c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29', 'First organization', 'The important one');

INSERT INTO security_permission (id, name, description, slug, language_code)
VALUES
    ('5716a161-752f-4d98-b5ff-2fcab6c77f1a', 'Manage Roles', 'Allows viewing, creating, editing, and deleting roles', 'roles.write', 'en'),
    ('67f60f8b-fa60-4ea2-b3ba-1715dce369a6', 'Manage Users', 'Allows viewing, creating, editing, and deleting users', 'users.write', 'en'),
    ('61d70e1a-0b46-4f93-a937-b73e7c361764', 'Manage People', 'Allows viewing, creating, editing, and deleting people', 'people.write', 'en'),
    ('aa6f5738-5b5a-4b1b-97df-c3b10694c405', 'Manage Payment Methods', 'Allows viewing, creating, editing, and deleting payment methods', 'payments.write', 'en'),
    ('475e16f8-9226-4dea-a0b7-21cb1b3d91ea', 'Manage Subscriptions', 'Allows subscribing, canceling subscription, or updating the plan', 'subscriptions.write', 'en'),
    ('b4a7b7ac-7eff-4af7-affb-9f1b4d8094ca', 'Manage Vouchers', 'Allows managing vouchers', 'vouchers.write', 'en');

INSERT INTO security_group (id, name, description, organization_id)
VALUES
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', 'Administrator', 'Contains all permissions in the system', 'c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29');

INSERT INTO security_group_permission(security_group_id, security_permission_id)
VALUES
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', '5716a161-752f-4d98-b5ff-2fcab6c77f1a'),
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', '67f60f8b-fa60-4ea2-b3ba-1715dce369a6'),
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', '61d70e1a-0b46-4f93-a937-b73e7c361764'),
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', 'aa6f5738-5b5a-4b1b-97df-c3b10694c405'),
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', 'b4a7b7ac-7eff-4af7-affb-9f1b4d8094ca'),
    ('98bcfdf8-076d-451f-9aba-c454ff1426ae', '475e16f8-9226-4dea-a0b7-21cb1b3d91ea');

-- Seed data for person table
INSERT INTO person (id, first_name, middle_name, last_name, mothers_maiden_name, identification, identification_type, birth_date, deceased_date, employment_status, marital_status, occupation, gender, weight, height, email_address, organization_id)
VALUES
    ('9b17f91d-a8d9-4520-a33e-2c7a16b2adda', 'Joe', 'Michael', 'Doe', 'Smith', '123456789', 'CC', '1980-01-15', NULL, 'FULL_TIME', 'MARRIED', 'Engineer', 'MALE', 75.5, 180.0, 'test@allinone.com', 'c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29'),
    ('b7501a37-6317-49e2-abbd-9eb795d2029e', 'Jane', 'Elizabeth', 'Smith', 'Taylor', '987654321', 'CC', '1985-05-20', NULL, 'UNEMPLOYED', 'SINGLE', 'Teacher', 'FEMALE', 65.0, 160.0, 'jane.e2@allinone.com', 'c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29'),
    ('c4e68224-5728-4e3b-b520-bcad0aa05811', 'Robert', 'Andrew', 'Johnson', 'Clark', '555111333', 'CE', '1970-08-10', '2022-03-25', 'RETIRED', 'LIVING_COMMON_LAW', 'Accountant', 'MALE', 80.0, 175.0, null, 'c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29');

-- Seed data for user_login table
INSERT INTO user_login (id, username, password, enabled, email_address, person_id, organization_id)
VALUES
    ('e4382a6c-f173-450b-b072-3a30ce4f953e', '111222', '$2a$10$fhG0YSiqMWXDzFfFyibBi.CN/kTX1JffCSXWTsznlRE3zTGKjTlAK', TRUE, 'test@allinone.com', '9b17f91d-a8d9-4520-a33e-2c7a16b2adda', 'c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29');

INSERT INTO user_login_security_group (user_login_id, security_group_id)
VALUES
    ('e4382a6c-f173-450b-b072-3a30ce4f953e', '98bcfdf8-076d-451f-9aba-c454ff1426ae');

INSERT INTO voucher_sequence (id, voucher_type, organization_id, sequence_number)
VALUES ('bd99503a-409a-4602-afbc-2bcae97cf1e1', 'CREDIT', 'c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29', 3);