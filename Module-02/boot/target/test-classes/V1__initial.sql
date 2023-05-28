DROP TABLE IF EXISTS gift_certificate CASCADE;
DROP TABLE IF EXISTS gift_certificate_tag CASCADE;
DROP TABLE IF EXISTS tag CASCADE;

CREATE TABLE IF NOT EXISTS gift_certificate
(
    id SMALLINT NOT NULL AUTO_INCREMENT,
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
    id SMALLINT NOT NULL AUTO_INCREMENT,
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

INSERT INTO gift_certificate ( name, description, price, duration, create_date, last_update_date) VALUES ( 'AB', 'DESC AB', 1000, 123, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO gift_certificate ( name, description, price, duration, create_date, last_update_date) VALUES ( 'DC', 'DESC DC', 1001, 124, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO tag ( name) VALUES ( 'BIRTHDAY');
INSERT INTO tag ( name) VALUES ( 'NOT_BIRTHDAY');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 1);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 2);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2, 2);

COMMIT ;