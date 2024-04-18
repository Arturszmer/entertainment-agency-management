INSERT INTO user_profile (
    creation_timestamp, modification_timestamp,
                        created_by, email, first_name, last_modified, last_name, password, username, account_non_locked)
VALUES (
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', 'admin@example.com', 'Admin', CURRENT_TIMESTAMP, 'Admin', 'password', 'admin', true);


INSERT INTO user_profile (creation_timestamp, modification_timestamp,
    created_by, email, first_name, last_modified, last_name, password, username, account_non_locked)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'admin', 'test@example.com', 'userTest', CURRENT_TIMESTAMP, 'user', 'password', 'userTest', true);

INSERT INTO user_profile (creation_timestamp, modification_timestamp,
                          created_by, email, first_name, last_modified, last_name, password, username, account_non_locked)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'admin', 'testLocked@example.com', 'userTestLocked', CURRENT_TIMESTAMP, 'user', 'password', 'userTestLocked', false);
