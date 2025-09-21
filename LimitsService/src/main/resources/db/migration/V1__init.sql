DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS daily_limits (
id BIGINT GENERATED  BY  DEFAULT AS IDENTITY NOT  NULL,
user_id BIGINT NOT NULL UNIQUE,
limit_value BIGINT NOT NULL DEFAULT 1000000,
reset_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payments (
id BIGINT GENERATED  BY  DEFAULT AS IDENTITY NOT  NULL,
payment_id UUID NOT NULL UNIQUE,
user_id BIGINT NOT NULL,
amount BIGINT NOT NULL,
status  VARCHAR(16)  NOT NULL
);


DO $$
    BEGIN
        FOR i IN 1..100 LOOP
                INSERT INTO daily_limits (user_id, limit_value,  reset_date)
                VALUES (i, 1000000, current_date::timestamp)
                ON CONFLICT (user_id) DO NOTHING;
            END LOOP;
END $$;