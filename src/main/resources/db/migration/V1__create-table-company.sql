CREATE TABLE IF NOT EXISTS company (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    cnpj TEXT UNIQUE NOT NULL,
    address TEXT NOT NULL,
    city TEXT NOT NULL,
    cep TEXT NOT NULL,
    state TEXT NOT NULL,
    phone TEXT NOT NULL,
    car_spaces INTEGER NOT NULL,
    motorcycle_spaces INTEGER NOT NULL
);