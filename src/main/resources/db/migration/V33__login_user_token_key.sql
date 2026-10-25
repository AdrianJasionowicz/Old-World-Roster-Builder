ALTER TABLE login_user ADD COLUMN token_key VARCHAR(36);

UPDATE login_user SET token_key = gen_random_uuid()::text;

ALTER TABLE login_user ALTER COLUMN token_key SET NOT NULL;
