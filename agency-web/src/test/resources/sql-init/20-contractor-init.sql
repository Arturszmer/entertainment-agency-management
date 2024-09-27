insert into user_address (id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
values (20, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', 'fb75951a-fe54-11ee-92c8-0242ac120002', 'search-one', CURRENT_TIMESTAMP, 'search', '64050968198', '1964-05-09', 20, '500123456', 'email@email.com', 'description');
