insert into user_address (id, created_by, creation_timestamp, modification_timestamp, last_modified, apartment_number,
                          city, house_number, street, zip_code)
values (50, 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', 'Warszawa', '1', 'Zwycięstwa', '00-001');

insert into agency_details (address_id, creation_timestamp, modification_timestamp, created_by, krs, last_modified,
                            agency_name, nip, pesel, first_name, last_name, regon)
values (50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin', null, CURRENT_TIMESTAMP, 'agency', '1234567891', null, null,null, null);
