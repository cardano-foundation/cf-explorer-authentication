-- Table: user
CREATE TABLE IF NOT EXISTS "user" (
	id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamptz(6) NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamptz(6) NULL,
	avatar text NULL,
	email varchar(64) NULL,
	is_deleted bool NOT NULL DEFAULT false,
	"password" varchar(256) NULL,
	stake_key varchar(255) NULL,
	status varchar(255) NULL,
	CONSTRAINT uk_user_email UNIQUE (email),
	CONSTRAINT user_pkey_id PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE IF NOT EXISTS "role" (
	id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamptz(6) NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamptz(6) NULL,
	description varchar(256) NOT NULL,
	"name" varchar(64) NOT NULL,
	CONSTRAINT role_pkey_id PRIMARY KEY (id),
	CONSTRAINT uk_role_name UNIQUE (name)
);

-- Table: user_role
CREATE TABLE IF NOT EXISTS user_role (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user"(id),
	CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES "role"(id)
);

-- Table: bookmark
CREATE TABLE IF NOT EXISTS bookmark (
	id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamptz(6) NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamptz(6) NULL,
	keyword varchar(255) NOT NULL,
	network varchar(255) NOT NULL,
	"type" varchar(255) NOT NULL,
	url_page varchar(255) NULL,
	user_id int8 NULL,
	CONSTRAINT bookmark_pkey PRIMARY KEY (id),
	CONSTRAINT fk_bookmark_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Table: private_note
CREATE TABLE IF NOT EXISTS private_note (
	id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamptz(6) NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamptz(6) NULL,
	network varchar(255) NOT NULL,
	note varchar(255) NULL,
	tx_hash varchar(255) NOT NULL,
	user_id int8 NULL,
	CONSTRAINT private_note_pkey PRIMARY KEY (id),
	CONSTRAINT fk_private_note_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Table: refresh_token
CREATE TABLE IF NOT EXISTS refresh_token (
	id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamptz(6) NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamptz(6) NULL,
	expiry_date timestamptz(6) NOT NULL,
	"token" varchar(255) NOT NULL,
	user_id int8 NULL,
	CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
	CONSTRAINT uk_refresh_token_token UNIQUE (token),
	CONSTRAINT fk_refresh_token_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Table: user_history
CREATE TABLE IF NOT EXISTS user_history (
	id bigserial NOT NULL,
	action_time timestamptz(6) NULL,
	user_action varchar(255) NULL,
	user_id int8 NULL,
	CONSTRAINT user_history_pkey PRIMARY KEY (id),
	CONSTRAINT fk_user_history_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);

-- Table: wallet
CREATE TABLE IF NOT EXISTS wallet (
	id bigserial NOT NULL,
	created_by varchar(255) NULL,
	created_date timestamptz(6) NOT NULL,
	modified_by varchar(255) NULL,
	modified_date timestamptz(6) NULL,
	address varchar(255) NOT NULL,
	expiry_date_nonce timestamptz(6) NOT NULL,
	network_id varchar(255) NULL,
	network_type varchar(255) NULL,
	nonce varchar(255) NOT NULL,
	nonce_encode varchar(255) NOT NULL,
	wallet_name varchar(255) NULL,
	user_id int8 NULL,
	CONSTRAINT uk_wallet_address UNIQUE (address),
	CONSTRAINT wallet_pkey PRIMARY KEY (id),
	CONSTRAINT fk_wallet_user_id FOREIGN KEY (user_id) REFERENCES "user"(id)
);

INSERT INTO "role" (created_by,created_date,modified_by,modified_date,description,"name")
SELECT NULL,'2022-10-10 14:21:35.73',NULL,'2022-10-10 14:21:35.73','1','ROLE_USER'
where not exists (select r.id from "role" r where r.name = 'ROLE_USER');

INSERT INTO "role" (created_by,created_date,modified_by,modified_date,description,"name")
SELECT NULL,'2022-10-10 14:21:35.73',NULL,'2022-10-10 14:21:35.73','2','ROLE_ADMIN'
where not exists (select r.id from "role" r where r.name = 'ROLE_ADMIN');



