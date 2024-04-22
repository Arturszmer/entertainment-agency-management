insert into project (end_date, salary, sign_date, start_date, creation_timestamp, id, modification_timestamp,
                     additional_information, contract_number, contract_type, created_by, last_modified,
                     subject_of_the_contract, status)
values ('2024-01-31', '3000', '2024-01-02', '2024-01-02', CURRENT_TIMESTAMP, 10, CURRENT_TIMESTAMP, 'Additional information',
        '2024/STY/PRO11', 'PROJECT', 'admin', CURRENT_TIMESTAMP, 'Subject of the contract', 'DRAFT');

insert into user_address (id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
values (21, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

INSERT INTO contractor (
    creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date, address_id, phone, email, contractor_description)
VALUES (
                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
                   'admin', 'fb75951a-fe54-11ee-92c8-0242ac120003', 'John', CURRENT_TIMESTAMP, 'Doe', '64050968198', '1964-05-09', 21, '500123456', 'email@email.com', 'description');
