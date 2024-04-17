INSERT INTO user_profile (
    creation_timestamp, modification_timestamp,
                        created_by, email, first_name, last_modified, last_name, password, username
) VALUES (
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', 'admin@example.com', 'Admin', CURRENT_TIMESTAMP, 'Admin', 'password', 'admin'
    );
