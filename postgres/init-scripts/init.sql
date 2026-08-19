-- ---------- 1. Create roles ----------
DO $$
BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'boop_service_marketplace') THEN
CREATE ROLE boop_service_marketplace LOGIN PASSWORD '12345678';
END IF;

BEGIN
    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'boop_pet_specialist') THEN
CREATE ROLE boop_pet_specialist LOGIN PASSWORD '12345678';
END IF;

    IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'boop_pet_owner') THEN
CREATE ROLE boop_pet_owner LOGIN PASSWORD '12345678';
END IF;
END
$$;

-- ---------- 2. Create databases ----------
CREATE DATABASE pet_service_marketplace_db;
CREATE DATABASE pet_specialist_db;
CREATE DATABASE pet_owner_db;

-- ============================================================
-- Apply permissions for each database
-- ============================================================

-------------------------------
-- Setup for pet_service_marketplace_db database
-------------------------------
\connect pet_service_marketplace_db

-- Remove unsafe default privileges
REVOKE ALL ON DATABASE pet_service_marketplace_db FROM PUBLIC;
REVOKE CREATE ON SCHEMA public FROM PUBLIC;

ALTER SCHEMA public OWNER TO boop_service_marketplace;

GRANT USAGE, CREATE ON SCHEMA public TO boop_service_marketplace;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO boop_service_marketplace;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO boop_service_marketplace;

-- Allow connections
GRANT CONNECT ON DATABASE pet_service_marketplace_db TO boop_service_marketplace;

------------------------------------------------------------
-- Setup for pet_specialist_db
------------------------------------------------------------
\connect pet_specialist_db

-- Remove unsafe default privileges
REVOKE ALL ON DATABASE pet_specialist_db FROM PUBLIC;
REVOKE CREATE ON SCHEMA public FROM PUBLIC;

ALTER SCHEMA public OWNER TO boop_pet_specialist;

GRANT USAGE, CREATE ON SCHEMA public TO boop_pet_specialist;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO boop_pet_specialist;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO boop_pet_specialist;

-- Allow connections
GRANT CONNECT ON DATABASE pet_specialist_db TO boop_pet_specialist;

-------------------------------
-- Setup for pet_owner_db database
-------------------------------
\connect pet_owner_db

-- Remove unsafe default privileges
REVOKE ALL ON DATABASE pet_owner_db FROM PUBLIC;
REVOKE CREATE ON SCHEMA public FROM PUBLIC;

ALTER SCHEMA public OWNER TO boop_pet_owner;

GRANT USAGE, CREATE ON SCHEMA public TO boop_pet_owner;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO boop_pet_owner;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO boop_pet_owner;

-- Allow connections
GRANT CONNECT ON DATABASE pet_owner_db TO boop_pet_owner;