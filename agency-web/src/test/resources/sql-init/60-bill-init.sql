insert into project (end_date, salary, sign_date, start_date, creation_timestamp, id, modification_timestamp,
                     additional_information, contract_number, contract_type, created_by, last_modified,
                     contract_subject, status, is_internal, public_id)
values ('2024-01-31', '3000', '2024-01-02', '2024-01-02', CURRENT_TIMESTAMP, 60, CURRENT_TIMESTAMP, 'Additional information',
        '2024/STY/PRO11', 'PROJECT', 'admin', CURRENT_TIMESTAMP, 'Subject of the contract', 'DRAFT', true, '4f6952dc-4b2a-4723-a649-353f1abb5b7e');

insert into user_address (id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
values (60, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

INSERT INTO contractor (
    id, creation_timestamp, modification_timestamp, created_by,
    public_id, first_name, last_modified, last_name, pesel, birth_date,
    address_id, phone, email, contractor_description)
VALUES (60, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin', 'fb75951a-fe54-11ee-92c8-0242ac120003', 'John',
        CURRENT_TIMESTAMP, 'Doe', '64050968198', '1964-05-09', 60, '500123456', 'email@email.com', 'description');

INSERT INTO project_contractor (contractor_id, project_id)
VALUES (60, 60);

INSERT INTO contract_work (end_date, salary, sign_date, start_date, with_copyrights, contractor_id, creation_timestamp, id,
                           modification_timestamp, public_id, additional_information, contract_number, contract_subject,
                           contract_type, created_by, last_modified, project_number, status, file_id_reference)
VALUES ('2024-01-31', 560.0, '2024-01-02', '2024-01-02', true,
        60, '2024-01-05 12:32:12.816541 +00:00', 60, '2024-01-05 12:32:12.816542 +00:00',
        '15eaa476-eff2-4e03-95a2-d225c3053f50', null, 'D2024/STY/UoD1',
        'Umowa 2', 'CONTRACT_WORK', 'admin', 'admin',
        '2024/STY/PRO11', 'CONFIRMED', null);

INSERT INTO public.tax_configuration (created_by, creation_timestamp, modification_timestamp, last_modified,
                                      disability_insurance_rate, first_tax_threshold_rate, health_insurance_rate,
                                      income_cost_copyrights_rate, income_cost_rate, pension_insurance_rate,
                                      second_tax_threshold_rate, sickness_insurance_rate, valid_from, valid_to)
VALUES ('ArturSz', null, null, null, 0.02,
        0.12, 0.09, 0.50, 0.20,
        0.20, 0.32, 0.02,
        '2000-01-01 00:00:00.000000 +00:00', '2050-12-31 00:00:00.000000 +00:00');
