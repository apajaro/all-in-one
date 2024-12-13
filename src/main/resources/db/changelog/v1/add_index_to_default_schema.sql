CREATE INDEX IF NOT EXISTS security_group_organization_id ON security_group (organization_id);
CREATE INDEX IF NOT EXISTS person_organization_id ON person (organization_id);
CREATE INDEX IF NOT EXISTS user_login_organization_id ON user_login (organization_id);
CREATE INDEX IF NOT EXISTS voucher_organization_id ON voucher (organization_id);
CREATE INDEX IF NOT EXISTS voucher_sequence_organization_id ON voucher_sequence (organization_id);