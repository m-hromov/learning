CREATE TABLE IF NOT EXISTS gift_certificate
(
    id SMALLSERIAL,
    name VARCHAR(255),
    description TEXT,
    price NUMERIC,
    duration BIGINT,
    create_date TIMESTAMP WITH TIME ZONE,
    last_update_date TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tag
(
    id SMALLSERIAL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    gift_certificate_id SMALLINT NOT NULL,
    tag_id SMALLINT NOT NULL,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);