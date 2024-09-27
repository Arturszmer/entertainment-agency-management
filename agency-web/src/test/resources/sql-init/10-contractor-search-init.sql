-- Address 2
INSERT INTO user_address (id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
VALUES (10, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

-- Address 2
INSERT INTO user_address (
    id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
    city, house_number, street, zip_code
)
VALUES (
           11, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2', 'Kraków', '2', 'Słoneczna', '30-002'
       );

-- Address 3
INSERT INTO user_address (
    id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
    city, house_number, street, zip_code
)
VALUES (
           12, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '3', 'Gdańsk', '3', 'Nadmorska', '80-003'
       );

-- Address 4
INSERT INTO user_address (
    id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
    city, house_number, street, zip_code
)
VALUES (
           13, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '4', 'Poznań', '4', 'Polna', '60-004'
       );

-- Address 5
INSERT INTO user_address (
    id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
    city, house_number, street, zip_code
)
VALUES (
           14, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '5', 'Wrocław', '5', 'Kościelna', '50-005'
       );

-- Contractor 1
INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', '13ff2322-ff08-11ee-92c8-0242ac120002', 'firstName', CURRENT_TIMESTAMP, 'lastName', '03260785766', '1981-02-17', 10, '500123456', 'email@email.com', 'description');

-- Contractor 2
INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', '13ff2323-ff08-11ee-92c8-0242ac120002', 'Alice', CURRENT_TIMESTAMP, 'Smith', '12345678901', '1990-05-15', 11, '600123456', 'alice@email.com', 'Description 2');

-- Contractor 3
INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', '13ff2324-ff08-11ee-92c8-0242ac120002', 'Bob', CURRENT_TIMESTAMP, 'Johnson', '98765432109', '1985-10-20', 12, '700123456', 'bob@email.com', 'Description 3');

-- Contractor 4
INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', '13ff2325-ff08-11ee-92c8-0242ac120002', 'Emma', CURRENT_TIMESTAMP, 'Davis', '24680135790', '1982-11-10', 13, '800123456', 'emma@email.com', 'Description 4');

-- Contractor 5
INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', '13ff2326-ff08-11ee-92c8-0242ac120002', 'James', CURRENT_TIMESTAMP, 'Taylor', '13579246801', '1995-08-25', 14, '900123456', 'james@email.com', 'Description 5');
