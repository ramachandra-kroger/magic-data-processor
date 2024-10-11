
--
-- SPRING BATCH TABLES
--
CREATE TABLE store_price.BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID BIGINT       NOT NULL PRIMARY KEY,
    VERSION         BIGINT,
    JOB_NAME        VARCHAR(100) NOT NULL,
    JOB_KEY         VARCHAR(32)  NOT NULL,
    constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
);

CREATE TABLE store_price.BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID BIGINT    NOT NULL PRIMARY KEY,
    VERSION          BIGINT,
    JOB_INSTANCE_ID  BIGINT    NOT NULL,
    CREATE_TIME      DATETIME2(6) NOT NULL,
    START_TIME       DATETIME2(6) DEFAULT NULL,
    END_TIME         DATETIME2(6) DEFAULT NULL,
    STATUS           VARCHAR(10),
    EXIT_CODE        VARCHAR(2500),
    EXIT_MESSAGE     VARCHAR(2500),
    LAST_UPDATED     DATETIME2(6),
    constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
        references store_price.BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
);

CREATE TABLE store_price.BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID BIGINT       NOT NULL,
    PARAMETER_NAME   VARCHAR(100) NOT NULL,
    PARAMETER_TYPE   VARCHAR(100) NOT NULL,
    PARAMETER_VALUE  VARCHAR(2500),
    IDENTIFYING      VARCHAR(1)      NOT NULL,
    constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
        references store_price.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

CREATE TABLE store_price.BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID  BIGINT       NOT NULL PRIMARY KEY,
    VERSION            BIGINT       NOT NULL,
    STEP_NAME          VARCHAR(100) NOT NULL,
    JOB_EXECUTION_ID   BIGINT       NOT NULL,
    CREATE_TIME        DATETIME2(6)    NOT NULL,
    START_TIME         DATETIME2(6) DEFAULT NULL,
    END_TIME           DATETIME2(6) DEFAULT NULL,
    STATUS             VARCHAR(10),
    COMMIT_COUNT       BIGINT,
    READ_COUNT         BIGINT,
    FILTER_COUNT       BIGINT,
    WRITE_COUNT        BIGINT,
    READ_SKIP_COUNT    BIGINT,
    WRITE_SKIP_COUNT   BIGINT,
    PROCESS_SKIP_COUNT BIGINT,
    ROLLBACK_COUNT     BIGINT,
    EXIT_CODE          VARCHAR(2500),
    EXIT_MESSAGE       VARCHAR(2500),
    LAST_UPDATED       DATETIME2(6),
    constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
        references store_price.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

CREATE TABLE store_price.BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID  BIGINT        NOT NULL PRIMARY KEY,
    SHORT_CONTEXT      VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT NVARCHAR(MAX),
    constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
        references store_price.BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
);

CREATE TABLE store_price.BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID   BIGINT        NOT NULL PRIMARY KEY,
    SHORT_CONTEXT      VARCHAR(2500) NOT NULL,
    SERIALIZED_CONTEXT NVARCHAR(MAX),
    constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
        references store_price.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);

CREATE SEQUENCE store_price.BATCH_STEP_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE store_price.BATCH_JOB_EXECUTION_SEQ MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE store_price.BATCH_JOB_SEQ MAXVALUE 9223372036854775807 NO CYCLE;


--
-- Store bad data from magic file
--
CREATE TABLE store_price.magic_file_bad_records
(
    created_timestamp   DATETIME2(6) NOT NULL,
    division_id         VARCHAR(3) NOT NULL,
    text_line           VARBINARY(MAX) NOT NULL,
    job_id              BIGINT NOT NULL,
);