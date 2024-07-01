CREATE TABLE IF NOT EXISTS token_auth
(
    id         bigint        NOT NULL,
    user_id    varchar(255),
    token      varchar(2048) NOT NULL,
    blacklist  BOOLEAN       NOT NULL,
    type varchar(20)   NOT NULL,
    created_at timestamp     NOT NULL,
    CONSTRAINT token_auth_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS token_auth_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE token_auth_id_seq OWNED BY token_auth.id;

CREATE INDEX IF NOT EXISTS idx_token_auth_token ON token_auth USING hash(token);
CREATE INDEX IF NOT EXISTS idx_token_auth_type ON token_auth USING hash(type);


CREATE TABLE IF NOT EXISTS user_role
(
    id         bigint    NOT NULL,
    user_id    varchar(255),
    role_id    varchar(255),
    created_at timestamp NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE user_role_id_seq OWNED BY user_role.id;

CREATE INDEX IF NOT EXISTS idx_user_role_user_id ON user_role USING hash(user_id);
CREATE INDEX IF NOT EXISTS idx_user_role_role_id ON user_role USING hash(role_id);