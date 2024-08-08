insert into project (public_id, end_date, salary, sign_date, start_date, creation_timestamp, id, modification_timestamp,
                     additional_information, contract_number, contract_type, created_by, last_modified,
                     subject_of_the_contract, status, is_internal)
values ('585dee47-e5d0-4485-b72e-7c2ceca6d886', '2024-01-31', '3000', '2024-01-02', '2024-01-02', CURRENT_TIMESTAMP, 10, CURRENT_TIMESTAMP, 'Project for concert',
        '2024/STY/PRO10', 'PROJECT', 'admin', CURRENT_TIMESTAMP, 'Concert AC/DC', 'DRAFT', true);

insert into user_address (id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
values (30, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

insert into organizer (address_id, creation_timestamp, id, modification_timestamp, telephone, public_id, email,
                       contact_manager, organizer_name, notes, created_by, last_modified)
values (30, CURRENT_TIMESTAMP, 10, CURRENT_TIMESTAMP, '555-555-555', 'd4bfca9f-2056-4e49-9c15-1abc10015c2e', 'email@email.pl',
        'admin', 'Project organizer', 'note', 'admin', 'admin');

