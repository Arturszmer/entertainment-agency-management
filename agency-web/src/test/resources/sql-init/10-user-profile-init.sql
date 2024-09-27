INSERT INTO user_profile (id,
    creation_timestamp, modification_timestamp,
                        created_by, email, first_name, last_modified, last_name, password, username, account_non_locked)
VALUES (10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'admin', 'admin@example.com', 'Admin', CURRENT_TIMESTAMP, 'Admin', 'password', 'admin', true);


INSERT INTO user_profile (id, creation_timestamp, modification_timestamp,
    created_by, email, first_name, last_modified, last_name, password, username, account_non_locked)
VALUES (11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'admin', 'test@example.com', 'userTest', CURRENT_TIMESTAMP, 'user', 'password', 'userTest', true);

INSERT INTO user_profile (id, creation_timestamp, modification_timestamp,
                          created_by, email, first_name, last_modified, last_name, password, username, account_non_locked)
VALUES (12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'admin', 'testLocked@example.com', 'userTestLocked', CURRENT_TIMESTAMP, 'user', 'password', 'userTestLocked', false);

INSERT INTO role (id, creation_timestamp, modification_timestamp, created_by, permission)
VALUES (11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin', '{EVENT_MANAGEMENT,USER_MANAGEMENT,ORGANIZER_MANAGEMENT,ORGANIZER_VIEW}');

INSERT INTO user_profile_roles (role_id, user_profile_id)
VALUES (11, 11);
