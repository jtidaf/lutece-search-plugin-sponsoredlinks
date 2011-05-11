--
-- Structure for table sponsoredlinks_group
--
DROP TABLE IF EXISTS sponsoredlinks_group;
CREATE TABLE sponsoredlinks_group (
  id_group INT DEFAULT 0 NOT NULL,
  title VARCHAR(255) DEFAULT '' NOT NULL,
  tags LONG VARCHAR,
  PRIMARY KEY (id_group)
);

--
-- Structure for table sponsoredlinks_set
--
DROP TABLE IF EXISTS sponsoredlinks_set;
CREATE TABLE sponsoredlinks_set (
  id_set INT DEFAULT 0 NOT NULL,
  title VARCHAR(255) DEFAULT '' NOT NULL,
  id_group INT,
  PRIMARY KEY (id_set)
);

--
-- Structure for table sponsoredlinks_template
--
DROP TABLE IF EXISTS sponsoredlinks_template;
CREATE TABLE sponsoredlinks_template (
  id_template INT DEFAULT 0 NOT NULL,
  description VARCHAR(255) DEFAULT '' NOT NULL,
  id_insertservice VARCHAR(255) DEFAULT '' NOT NULL,
  subcategory VARCHAR(100) DEFAULT '' NOT NULL,
  PRIMARY KEY (id_template)
);

--
-- Structure for table sponsoredlinks_link
--
DROP TABLE IF EXISTS sponsoredlinks_link;
CREATE TABLE sponsoredlinks_link (
  id_set INT,
  id_template INT,
  link LONG VARCHAR,
  PRIMARY KEY (id_set, id_template)
);