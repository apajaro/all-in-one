-- noinspection SqlNoDataSourceInspectionForFile

create table organization (
  id varchar(36) not null,
  name varchar(150) not null,
  slogan varchar(255),
  created_at timestamp DEFAULT NOW(),
  updated_at timestamp DEFAULT NOW(),
  CONSTRAINT organization_pkey primary key (id)
);

create table security_permission (
     id varchar(36) not null,
     name varchar(150) not null,
     slug varchar(150) not null,
     description varchar(255),
     language_code varchar(10),
     created_at timestamp DEFAULT NOW(),
     updated_at timestamp DEFAULT NOW(),
     CONSTRAINT security_permission_pkey primary key (id)
);

create table security_group (
    id varchar(36) not null,
    name varchar(150) not null,
    description varchar(255),
    organization_id varchar(36) not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT security_group_pkey primary key (id),
    CONSTRAINT security_group_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organization(id)
);

create table security_group_permission (
    security_group_id varchar(36) not null,
    security_permission_id varchar(36) not null,
    CONSTRAINT security_group_permission_pkey primary key (security_group_id, security_permission_id),
    CONSTRAINT sg_ps_security_group_id_fkey FOREIGN KEY (security_group_id) REFERENCES security_group(id) ON DELETE CASCADE,
    CONSTRAINT sg_ps_permission_id_fkey FOREIGN KEY (security_permission_id) REFERENCES security_permission(id) ON DELETE CASCADE
);

create table person (
    id varchar(36) not null,
    first_name varchar(150) not null,
    middle_name varchar(255),
    last_name varchar(150) not null,
    mothers_maiden_name varchar(255),
    identification varchar(255),
    identification_type varchar(255),
    birth_date date,
    deceased_date date,
    employment_status varchar(255),
    marital_status varchar(255),
    occupation varchar(255),
    gender varchar(255) not null,
    weight float4,
    height float4,
    email_address varchar(255),
    organization_id varchar(36) not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT person_pkey PRIMARY KEY (id),
    CONSTRAINT person_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organization(id)
);

create table contact_mech (
    id varchar(36) not null,
    contact_mech_type varchar(255) not null,
    info_string varchar(255),
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT contact_mech_pkey PRIMARY KEY (id)
);

create table telecom_number (
    contact_mech_id varchar(36) not null,
    countryCode varchar(255),
    areaCode varchar(255),
    contactNumber varchar(255) not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT telecom_number_pkey PRIMARY KEY (contact_mech_id),
    CONSTRAINT telecom_number_contact_mech_id_fkey FOREIGN KEY (contact_mech_id) REFERENCES contact_mech(id)
);

create table postal_address (
    contact_mech_id varchar(36) not null,
    address1 varchar(255) not null,
    address2 varchar(255),
    city varchar(255),
    state varchar(255),
    country varchar(255),
    postalCode varchar(255),
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT postal_address_pkey PRIMARY KEY (contact_mech_id),
    CONSTRAINT postal_address_contact_mech_id_fkey FOREIGN KEY (contact_mech_id) REFERENCES contact_mech(id)
);

create table person_contact_mech (
    person_id varchar(36) not null,
    contact_mech_id varchar(36) not null,
    contact_mech_purpose varchar(36) not null,
    from_date timestamp DEFAULT NOW() not null,
    thru_date timestamp,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT person_contact_mech_person_id_fkey FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT person_contact_mech_id_fkey FOREIGN KEY (contact_mech_id) REFERENCES contact_mech(id)
);

create table user_login (
    id varchar(36) not null,
    username varchar(150) not null,
    password varchar(255) not null,
    password_hint varchar(255),
    enabled BOOLEAN DEFAULT FALSE not null,
    successive_failed_logins int2 DEFAULT 0 not null,
    disabled_date_time timestamp,
    require_password_change BOOLEAN DEFAULT FALSE not null,
    last_currency_uom varchar(5) DEFAULT 'COP',
    last_locale varchar(5) DEFAULT 'es' not null,
    email_address varchar(100),
    person_id varchar(36) not null,
    organization_id varchar(36) not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT user_login_pkey primary key (id),
    CONSTRAINT user_login_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT user_login_person_id_fkey FOREIGN KEY (person_id) REFERENCES person(id)
);

create table user_login_security_group (
    user_login_id varchar(36) not null,
    security_group_id varchar(36) not null,
    CONSTRAINT user_login_security_group_pkey primary key (user_login_id, security_group_id),
    CONSTRAINT ulsg_user_login_id_fkey FOREIGN KEY (user_login_id) REFERENCES user_login(id) ON DELETE CASCADE,
    CONSTRAINT ulsg_security_group_id_fkey FOREIGN KEY (security_group_id) REFERENCES security_group(id) ON DELETE CASCADE
);

create table user_login_token (
    id varchar(36) not null,
    user_login_id varchar(36) not null,
    token varchar(255) not null,
    is_expired BOOLEAN DEFAULT FALSE not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT user_login_token_pkey primary key (id),
    CONSTRAINT user_login_token_user_login_id_fkey FOREIGN KEY (user_login_id) REFERENCES user_login(id) ON DELETE CASCADE
);

create table user_login_password_reset (
    id varchar(36) not null,
    user_login_id varchar(36) not null,
    token varchar(255) not null,
    is_used BOOLEAN DEFAULT FALSE not null,
    valid_until timestamp not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT user_login_password_reset_pkey primary key (id),
    CONSTRAINT user_login_password_reset_user_login_id_fkey FOREIGN KEY (user_login_id) REFERENCES user_login(id) ON DELETE CASCADE
);

CREATE TABLE voucher (
      id varchar(36) not null,
      voucher_type VARCHAR(20) NOT NULL,
      voucher_number BIGINT NOT NULL,
      city VARCHAR(50),
      voucher_date DATE DEFAULT NOW(),
      counter_party VARCHAR(150),
      delivered_by_signature_url VARCHAR(255),
      received_by_signature_url VARCHAR(255),
      organization_id varchar(36) not null,
      deleted_at timestamp,
      created_at timestamp DEFAULT NOW(),
      updated_at timestamp DEFAULT NOW(),
      CONSTRAINT voucher_pkey primary key (id),
      CONSTRAINT voucher_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE voucher_detail (
      id varchar(36) not null,
      description VARCHAR(255) NOT NULL,
      amount DECIMAL(20,2) NOT NULL,
      voucher_id varchar(36) not null,
      content_urls TEXT[],
      created_at timestamp DEFAULT NOW(),
      updated_at timestamp DEFAULT NOW(),
      FOREIGN KEY (voucher_id) REFERENCES voucher(id) ON DELETE CASCADE
);

CREATE TABLE voucher_sequence (
    id varchar(36) not null,
    voucher_type VARCHAR(20),
    sequence_number BIGINT NOT NULL DEFAULT 0,
    organization_id varchar(36) not null,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT voucher_sequence_pkey primary key (id),
    CONSTRAINT voucher_sequence_organization_id_fkey FOREIGN KEY (organization_id) REFERENCES organization(id)
);

CREATE TABLE file_metadata (
    id varchar(36) not null,
    size BIGINT NOT NULL DEFAULT 0,
    content_type  VARCHAR(20),
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    CONSTRAINT file_metadata_pkey primary key (id)
);