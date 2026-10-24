# Old World Roster Builder

A web application for creating and managing army rosters for Warhammer: The Old World.

Players build rosters from a unit catalog, pick upgrades, see unit stats after upgrades, and check the army against the composition rules. Rosters can be exported to PDF.

> **Factions in the catalog:** Skaven, Empire, Dwarfs, Orcs & Goblins, Vampire Counts, High Elves, Dark Elves, Lizardmen, Bretonnia, Warriors of Chaos, Tomb Kings, Wood Elves, Chaos Dwarfs, Beastmen, Ogre Kingdoms, Daemons of Chaos (Warhammer Fantasy 8th edition data).

## Features

- Rosters per user: faction, points limit, units, unit sizes, upgrades.
- Unit stats recalculated from upgrade effects (e.g. Shield: armour save +1, ward save 6+).
- Composition rules: characters (lords + heroes) up to 50%, core at least 25%, special up to 50%, rare up to 25%, the same Special unit at most 3 times and Rare 2 times (doubled from 3000 points).
- Upgrade rules: one weapon team per unit, one BSB per army, one item of each magic category (weapon, armour, talisman, enchanted, arcane, banner), magic items unique in the army, magic banners only with a BSB or a standard bearer and within the unit's banner limit, magic item points limit for lords (100) and heroes (50) unless the unit has its own.
- Upgrade groups: one mount, one weapon option, one armour, one Mark of Chaos, one wizard level, one Virtue, Vampiric Powers up to 100 / 50 points.
- Dwarf runes: up to 3 runes and 1 master rune per item, runes on one item count as one item, master runes unique, lords 125 / heroes 75 points.
- Empire detachments: up to 2 per Halberdiers, Spearmen or Swordsmen regiment, at least 5 models and at most half the regiment.
- PDF export of a roster.
- Admin panel for adding and deleting catalog units.

## Tech stack

- **Backend:** Java 21, Spring Boot 4, Spring Security (JWT in an httpOnly cookie), Spring Data JPA, PostgreSQL, Flyway, iText, Gradle.
- **Frontend:** React, React Router, Axios, Bootstrap, Vite (in `src/frontend`).
- **Tests:** JUnit 5, Mockito, Testcontainers.

## Running locally

Requirements: Java 21, Docker, Node.js 22.

```bash
# 0. Local secrets: copy the example and fill in DB_PASSWORD and JWT_SECRET (at least 32 characters).
#    The app reads .env from the project directory; real environment variables override it.
cp .env.example .env

# 1. Database (PostgreSQL 17 on localhost:5432, database and user: postgres)
docker compose --env-file .env up -d

# 2. Backend on http://localhost:8080
./gradlew bootRun

# 3. Frontend on http://localhost:5173
cd src/frontend
npm install
npm run dev
```

On Windows use `.\gradlew.bat`. In IntelliJ use the shared run configuration **RosterBuilderApplication** (from `.run/`); it needs no environment variables thanks to `.env`.

Skip step 1 if you already have PostgreSQL on port 5432. Other databases can be used with `DB_URL` and `DB_USERNAME`. Flyway needs an empty database on the first start. Behind HTTPS set `JWT_COOKIE_SECURE=true`.

New users get the `ROLE_USER` role. To use the admin panel, change the role in the database:

```sql
UPDATE login_user SET role = 'ROLE_ADMIN' WHERE username = 'your-name';
```

## Database

The schema and data are managed only by Flyway (`src/main/resources/db/migration`). Hibernate validates the mapping and never changes tables.

| Migration | Content |
|---|---|
| `V1__create_schema.sql` | Tables, keys, indexes |
| `V2__skaven_units.sql` | Skaven units, their stats, upgrade catalog and per-unit upgrade prices |
| `V3__upgrade_stat_modifiers.sql` | Stat effects of upgrades |
| `V4__fix_catalog_names.sql` | Typos in upgrade names |
| `V5__empire.sql`, `V6__dwarfs.sql`, `V7__orcs_and_goblins.sql` | Units, options and army specific items of the three armies |
| `V8__common_magic_items.sql` | 8th edition rulebook magic items for Empire and Orcs & Goblins characters (Dwarfs use runes) |
| `V9__upgrade_effects_for_new_armies.sql` | Stat effects of the new upgrades |
| `V10__army_loading_functions.sql` | `roster_begin_army()` / `roster_load_army(faction)`, used by every later army migration |
| `V11`–`V19` | Vampire Counts, High Elves, Dark Elves, Lizardmen, Bretonnia, Warriors of Chaos, Tomb Kings, Wood Elves, Chaos Dwarfs |
| `V20__common_magic_items_for_new_armies.sql` | Rulebook magic items for the armies from V11–V19 |
| `V21__upgrade_effects_for_more_armies.sql` | Stat effects of their upgrades |
| `V22`–`V24` | Beastmen, Ogre Kingdoms, Daemons of Chaos (daemons use their own gifts instead of rulebook items) |
| `V25__common_magic_items_for_beastmen_and_ogres.sql` | Rulebook magic items for Beastmen and Ogre Kingdoms |
| `V26__upgrade_effects_for_last_armies.sql` | Stat effects of their upgrades |
| `V27__upgrade_rules.sql` | Upgrade groups, magic item categories, items unique in the army, unit limits for magic items and banners |
| `V28__dwarf_runes.sql` | Rune kinds, more runes, Dwarf rune allowances |
| `V29__empire_detachments.sql` | Regiments and detachments |

A new army is one migration: `SELECT roster_begin_army();`, inserts into `staging_unit` and `staging_unit_upgrade`, then `SELECT roster_load_army('FACTION');`. The loader stops the migration if an upgrade names a unit that does not exist.

Upgrades are stored once in a catalog (`upgrade`). The price for a given unit lives in `unit_upgrade`, and the stat effects in `upgrade_stat_modifier` (`stat`, `operation`, `modifier_value`). Choice limits live in `upgrade_group` (`max_choices`, `lord_points_limit`, `hero_points_limit`), so a new "pick one of" rule is also only data. Adding an upgrade with a new effect is a data change, not a code change. Operations are applied in phases, `SET` first, then `ADD`/`IMPROVE`, then `BEST_OF`, so the result does not depend on the order in which upgrades were chosen.

A change to an existing migration needs a new migration file (`V4__...`), never an edit of an applied one.

## Tests

```bash
./gradlew test
```

Integration tests (`*IntegrationTest`) start the whole application against PostgreSQL in Docker (Testcontainers), with the same migrations and data as the app. Without a running Docker they are skipped. CI (`.github/workflows/ci.yml`) runs all tests and builds the frontend on every push.

## API overview

Errors always have the same body: `timestamp`, `status`, `message`, `path`, `errors` (field validation errors).

| Method | Path | Description |
|---|---|---|
| POST | `/register`, `/login`, `/logout` | Account creation, login (sets the `jwt` cookie), logout |
| GET | `/me` | Logged-in user |
| PATCH / DELETE | `/account/password`, `/account/email`, `/account/username`, `/account` | Account changes, confirmed with the current password |
| GET / POST | `/armies` | Own rosters, create a roster |
| GET / DELETE | `/armies/{id}` | Roster details, delete a roster |
| PATCH | `/armies/{id}/name`, `/armies/{id}/points-limit` | Rename, change the points limit |
| GET | `/armies/{id}/points` | Points per category, rule violations, overall validity |
| GET | `/armies/{id}/pdf` | Roster as PDF |
| POST | `/armies/{id}/units/{unitId}` | Add a catalog unit to the roster |
| POST | `/army-units/{id}/increase`, `/army-units/{id}/decrease` | Change unit size |
| POST | `/army-units/{id}/detachments/{unitId}` | Add a detachment to a regiment |
| DELETE | `/army-units/{id}` | Remove a unit (and its detachments) from the roster |
| GET | `/army-units/{id}/stats` | Stats after upgrades |
| GET | `/army-units/{id}/upgrades` | Available upgrades with `selected`, `allowed` and `notAllowedReason` |
| POST / DELETE | `/army-units/{id}/upgrades/{unitUpgradeId}` | Select or remove an upgrade |
| GET | `/units?faction=SKAVEN`, `/units/{id}` | Unit catalog |
| GET | `/units/factions` | Factions that have units in the catalog |
| POST / DELETE | `/units`, `/units/{id}` | Catalog changes (admin only) |

## Project structure

Code is grouped by feature (`army`, `armyUnit`, `unit`, `upgrade`, `selectedUpgrade`, `unitStats`, `loginUser`, `pdfExport`), not by layer. Classes are package-private unless another feature needs them.

## License

No license has been specified yet.
