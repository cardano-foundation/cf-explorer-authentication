-- Table: dev.user

-- DROP TABLE IF EXISTS dev."user";

CREATE TABLE IF NOT EXISTS dev."user"
(
    id bigint NOT NULL DEFAULT nextval('dev.user_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL,
    modified_by character varying(255) COLLATE pg_catalog."default",
    modified_date timestamp without time zone,
    avatar character varying(255) COLLATE pg_catalog."default",
    email character varying(64) COLLATE pg_catalog."default" NOT NULL,
    is_deleted boolean NOT NULL DEFAULT false,
    phone character varying(20) COLLATE pg_catalog."default" NOT NULL,
    username character varying(64) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev."user"
    OWNER to postgres;

-- Table: dev.role

-- DROP TABLE IF EXISTS dev.role;

CREATE TABLE IF NOT EXISTS dev.role
(
    id bigint NOT NULL DEFAULT nextval('dev.role_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL,
    modified_by character varying(255) COLLATE pg_catalog."default",
    modified_date timestamp without time zone,
    description character varying(256) COLLATE pg_catalog."default" NOT NULL,
    name character varying(64) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.role
    OWNER to postgres;

-- Table: dev.user_role

-- DROP TABLE IF EXISTS dev.user_role;

CREATE TABLE IF NOT EXISTS dev.user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id)
        REFERENCES dev."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id)
        REFERENCES dev.role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.user_role
    OWNER to postgres;

-- Table: dev.bookmark

-- DROP TABLE IF EXISTS dev.bookmark;

CREATE TABLE IF NOT EXISTS dev.bookmark
(
    id bigint NOT NULL DEFAULT nextval('dev.bookmark_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL,
    modified_by character varying(255) COLLATE pg_catalog."default",
    modified_date timestamp without time zone,
    access_time timestamp without time zone NOT NULL,
    keyword character varying(255) COLLATE pg_catalog."default",
    tittle_page character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    url_page character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id bigint,
    CONSTRAINT bookmark_pkey PRIMARY KEY (id),
    CONSTRAINT fk3ogdxsxa4tx6vndyvpk1fk1am FOREIGN KEY (user_id)
        REFERENCES dev."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.bookmark
    OWNER to postgres;

-- Table: dev.refresh_token

-- DROP TABLE IF EXISTS dev.refresh_token;

CREATE TABLE IF NOT EXISTS dev.refresh_token
(
    id bigint NOT NULL DEFAULT nextval('dev.refresh_token_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL,
    modified_by character varying(255) COLLATE pg_catalog."default",
    modified_date timestamp without time zone,
    expiry_date timestamp without time zone NOT NULL,
    stake_address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id bigint,
    CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token),
    CONSTRAINT fkfgk1klcib7i15utalmcqo7krt FOREIGN KEY (user_id)
        REFERENCES dev."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.refresh_token
    OWNER to postgres;

-- Table: dev.user_history

-- DROP TABLE IF EXISTS dev.user_history;

CREATE TABLE IF NOT EXISTS dev.user_history
(
    id bigint NOT NULL DEFAULT nextval('dev.user_history_id_seq'::regclass),
    action_time timestamp without time zone,
    ip_address character varying(255) COLLATE pg_catalog."default",
    is_success boolean,
    user_action character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT user_history_pkey PRIMARY KEY (id),
    CONSTRAINT fkaa6ilb6iqih95bntoeyysb2pc FOREIGN KEY (user_id)
        REFERENCES dev."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.user_history
    OWNER to postgres;

-- Table: dev.wallet

-- DROP TABLE IF EXISTS dev.wallet;

CREATE TABLE IF NOT EXISTS dev.wallet
(
    id bigint NOT NULL DEFAULT nextval('dev.wallet_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default",
    created_date timestamp without time zone NOT NULL,
    modified_by character varying(255) COLLATE pg_catalog."default",
    modified_date timestamp without time zone,
    balance_at_login numeric(19,2),
    expiry_date_nonce timestamp without time zone NOT NULL,
    is_deleted boolean NOT NULL DEFAULT false,
    network_id character varying(255) COLLATE pg_catalog."default",
    network_type character varying(255) COLLATE pg_catalog."default",
    nonce character varying(255) COLLATE pg_catalog."default" NOT NULL,
    nonce_encode character varying(255) COLLATE pg_catalog."default" NOT NULL,
    stake_address character varying(255) COLLATE pg_catalog."default" NOT NULL,
    wallet_name character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT wallet_pkey PRIMARY KEY (id),
    CONSTRAINT fkbs4ogwiknsup4rpw8d47qw9dx FOREIGN KEY (user_id)
        REFERENCES dev."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.wallet
    OWNER to postgres;

-- Table: dev.wallet_history

-- DROP TABLE IF EXISTS dev.wallet_history;

CREATE TABLE IF NOT EXISTS dev.wallet_history
(
    id bigint NOT NULL DEFAULT nextval('dev.wallet_history_id_seq'::regclass),
    action_time timestamp without time zone,
    ip_address character varying(255) COLLATE pg_catalog."default",
    is_success boolean,
    wallet_action character varying(255) COLLATE pg_catalog."default",
    wallet_id bigint,
    CONSTRAINT wallet_history_pkey PRIMARY KEY (id),
    CONSTRAINT fkaypgevyjgo8rhxa57cx8x9m58 FOREIGN KEY (wallet_id)
        REFERENCES dev.wallet (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dev.wallet_history
    OWNER to postgres;