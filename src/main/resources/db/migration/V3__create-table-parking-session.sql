CREATE TYPE session_status AS ENUM('ACTIVE', 'COMPLETED');

CREATE CAST (varchar as session_status) WITH INOUT AS IMPLICIT;

CREATE TABLE parking_sessions (
    id SERIAL PRIMARY KEY NOT NULL,
    vehicle_id INTEGER REFERENCES vehicles (id),
    company_id INTEGER REFERENCES companies (id),
    status session_status NOT NULL,
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP
);