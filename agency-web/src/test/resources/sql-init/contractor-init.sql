insert into user_address (created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
values ('admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, contractor_description)
VALUES (
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', 'fb75951a-fe54-11ee-92c8-0242ac120002', 'firstName', CURRENT_TIMESTAMP, 'lastName', '81021758606', '1981-02-17', 1, '500123456', 'description');
