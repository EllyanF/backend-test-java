CREATE TYPE vehicle_type AS ENUM ('CAR', 'MOTORCYCLE');

CREATE CAST (varchar as vehicle_type) WITH INOUT AS IMPLICIT;

CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    color TEXT NOT NULL,
    plate TEXT UNIQUE NOT NULL,
    type vehicle_type NOT NULL
);