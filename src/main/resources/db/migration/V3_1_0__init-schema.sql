ALTER TABLE "user"
ALTER COLUMN avatar TYPE text;

INSERT INTO "role" (created_by,created_date,modified_by,modified_date,description,"name") VALUES
     (NULL,'2022-10-10 14:21:35.73',NULL,'2022-10-10 14:21:35.73','1','ROLE_USER'),
     (NULL,'2022-10-10 14:21:35.73',NULL,'2022-10-10 14:21:35.73','2','ROLE_ADMIN');

