CREATE FUNCTION roster_begin_army() RETURNS VOID
    LANGUAGE plpgsql AS
$$
BEGIN
    CREATE TEMPORARY TABLE staging_unit
    (
        name         VARCHAR(255),
        points       DOUBLE PRECISION,
        min_quantity INTEGER,
        unit_type    VARCHAR(255),
        m            INTEGER,
        ws           INTEGER,
        bs           INTEGER,
        s            INTEGER,
        t            INTEGER,
        w            INTEGER,
        i            INTEGER,
        a            INTEGER,
        ld           INTEGER,
        basic_save   INTEGER,
        ward_save    INTEGER
    );

    CREATE TEMPORARY TABLE staging_unit_upgrade
    (
        unit_name    VARCHAR(255),
        upgrade_name VARCHAR(255),
        upgrade_type VARCHAR(255),
        points       DOUBLE PRECISION,
        description  VARCHAR(1000)
    );
END
$$;

CREATE FUNCTION roster_load_army(faction VARCHAR) RETURNS VOID
    LANGUAGE plpgsql AS
$$
DECLARE
    staged   RECORD;
    stats_id BIGINT;
    unknown  VARCHAR;
BEGIN
    SELECT su.unit_name
    INTO unknown
    FROM staging_unit_upgrade su
    WHERE NOT EXISTS (SELECT 1 FROM staging_unit s WHERE s.name = su.unit_name)
    LIMIT 1;
    IF unknown IS NOT NULL THEN
        RAISE EXCEPTION '% upgrade refers to an unknown unit: %', faction, unknown;
    END IF;

    FOR staged IN SELECT * FROM staging_unit
        LOOP
            INSERT INTO unit_stats (m, ws, bs, s, t, w, i, a, ld, basic_save, ward_save)
            VALUES (staged.m, staged.ws, staged.bs, staged.s, staged.t, staged.w, staged.i, staged.a, staged.ld,
                    staged.basic_save, staged.ward_save)
            RETURNING id INTO stats_id;

            INSERT INTO unit (name, points_cost_per_unit, min_quantity, unit_type, unit_faction, unit_stats_id)
            VALUES (staged.name, staged.points, staged.min_quantity, staged.unit_type, faction, stats_id);
        END LOOP;

    INSERT INTO upgrade (name, upgrade_type, description)
    SELECT DISTINCT ON (upgrade_name, upgrade_type) upgrade_name, upgrade_type, description
    FROM staging_unit_upgrade
    ON CONFLICT (name, upgrade_type) DO NOTHING;

    INSERT INTO unit_upgrade (unit_id, upgrade_id, points_cost)
    SELECT u.id, up.id, su.points
    FROM staging_unit_upgrade su
    JOIN unit u ON u.name = su.unit_name AND u.unit_faction = faction
    JOIN upgrade up ON up.name = su.upgrade_name AND up.upgrade_type = su.upgrade_type;

    DROP TABLE staging_unit_upgrade;
    DROP TABLE staging_unit;
END
$$;
