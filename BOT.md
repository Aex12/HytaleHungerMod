# BOT (Cognitive Orchestration System)

Document Name: BOT
Status: Canonical, immutable-by-edit
Document Version: 1.0.0
Created (Local Time - America/Monterrey): 2026-01-30

## 1) Purpose

This document is the primordial base of understanding between the AI and the user.
It defines the operating rules, constraints, and protocols for Hytale mod development.

## 2) Immutability and Governance (Append-Only)

- This document must never be deleted.
- This document must always be consulted before making decisions or changes.
- This document is append-only:
  - No corrections, rewrites, or deletions are allowed.
  - Any change in rules or direction must be recorded as a new log entry.
  - Rollbacks are handled by appending a note that something is deprecated, canceled, reverted, or superseded.

## 3) Non-Contamination Rule (Hytale Only)

- The project is 100% focused on Hytale.
- Never assume the code, architecture, or APIs relate to Minecraft or any other development ecosystem.
- Do not import terminology, design patterns, or mechanics from other games unless explicitly required by Hytale docs and confirmed by sources.

## 4) Language Contract

- User-facing explanations: Spanish.
- Programming artifacts (code, identifiers, filenames): English.
- Code comments: minimal, ASCII only, no emojis.

## 5) Development Focus

- Primary domain: Hytale mod development.
- Architecture: ECS (data-oriented), not OOP.
  - Entities are ids (e.g., int).
  - Components are data-only.
  - Systems contain logic and operate over component sets.

## 6) Java Runtime Baseline

- Java JDK: 25
- This must be used until:
  - The user modifies this document, OR
  - The Java section explicitly states otherwise via a new append-only log entry.

## 7) Sources and Verification (No Invention Policy)

- Sources must be researched before stating API behavior.
- Do not invent programming behaviors, API signatures, lifecycle hooks, or server behaviors.
- The authoritative API and documentation source is:
  <https://github.com/Henry-Bonikowsky/hytale-docs>
- If something is uncertain, it must be verified in sources or asked to the user (with concrete questions).

## 8) Repository and Publishing Policy

- No changes may be published to the cloud (GitHub push, PR, releases, remote sync) unless the user explicitly permits it.
- Default stance: local-only changes and guidance.

## 9) Build / Compilation Requirement

- After finishing any change (code or configuration), the project must be compiled/build-verified.
- The exact build command depends on the project toolchain (Gradle/Maven/other).
- If the build command is not known, it must be defined as part of the next change and recorded in the log.

## 10) Loop Avoidance and Action Log Consultation

- Before proposing or executing a step, consult the action log in this document.
- Objective:
  - Avoid repeating identical actions.
  - Avoid falling into loops.
  - Keep decisions consistent with prior recorded context.

## 11) Multimedia Inputs

- If any multimedia is required (images, audio, video, diagrams), the user must provide it.
- The assistant must request it with clear specs:
  - format (png/jpg/wav/etc),
  - dimensions/resolution,
  - duration (if media),
  - and any required content constraints.

## 12) Uncertainty Handling

- If there are doubts in any aspect:
  - Request additional information from the user, including links to similar repositories or concrete references to analyze.
  - Do not guess.

## 13) System Output Standard

- Provide code in fenced blocks.
- Keep explanations short and concrete unless the user requests expansion.
- Keep ECS alignment explicit (data-only components, logic in systems).

## 14) Git Exclusions and Pre-Compilation

- Non-relevant files (build artifacts, IDE configs, logs, etc.) must always be excluded via `.gitignore`.
- The `.gitignore` file must be verified and updated before any compilation to ensure a clean and consistent repository state.

## 15) Feature Tracking (features.md)

- Each time a functionality is successfully added and confirmed by the USER, it must be recorded in `features.md`.
- `features.md` is a local-only file (should be ignored by git if not already) used to track the progress of the mod's features.
- This file provides a clear overview of the current state of the mod's capabilities.

## 16) Code Quality and Best Practices

- All programming artifacts (code, configurations, build scripts) must follow industry best practices.
- Avoid "junk code", redundant logic, or temporary hacks unless absolutely necessary and documented.
- Maintain a clean and idiomatic `build.gradle.kts`, avoiding manual resource manipulation where Gradle's built-in features suffice.
- Code must be readable, maintainable, and strictly follow the ECS pattern for Hytale.

---

# LOG (Append-Only)

All entries below are immutable. Add new entries at the end only.

## Log Entry Template

- Timestamp (America/Monterrey):
- Actor:
- Type: [rule-change | decision | implementation | rollback | deprecation | build | research]
- Summary:
- Details:
- Affected artifacts:
- Verification (build/compile):
- Status: [active | superseded | deprecated | canceled]

## 2026-01-30

- Timestamp (America/Monterrey): 2026-01-30
- Actor: user
- Type: rule-change
- Summary: Established BOT as canonical append-only contract for Hytale ECS mod development.
- Details:
  - BOT is never deletable and always consulted.
  - BOT is append-only (no edits/deletes); rollbacks recorded via deprecation/cancel notes.
  - Hytale-only; no Minecraft or other ecosystem assumptions.
  - Java baseline fixed to JDK 25 until BOT changes.
  - Sources must be researched; no invented API behavior; authoritative docs link provided.
  - No GitHub/cloud publishing without explicit user approval.
  - Always compile/build verify after changes.
  - Maintain version control expectations with future config changes respected.
  - Consult action log to avoid loops/repetition.
  - Multimedia must be requested from user with format/measure specs.
  - If doubts arise, request similar repos or specific repos for analysis.
  - Spanish explanations, English code.
- Affected artifacts: BOT.md (this document)
- Verification (build/compile): not applicable (documentation-only)
- Verification (build/compile): N/A
- Status: active

## 2026-01-30 (Entry 3)

- Timestamp (America/Monterrey): 2026-01-30 14:52
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Corrected README.md formula and JSON key consistency.
- Details:
  - Fixed incomplete parentheses in the hunger depletion formula.
  - Aligned the `ItemHungerRestoration` and `ItemMaxHungerSaturation` keys in the example to match the documentation table and `HHMFoodValuesConfig.java` current implementation.
- Affected artifacts: README.md
- Verification (build/compile): Manual verification of code-to-doc alignment. Build verification pending (requires local 'libs' dependencies).
- Status: active

## 2026-01-30 (Entry 4)

- Timestamp (America/Monterrey): 2026-01-30 14:55
- Actor: AI (Antigravity)
- Type: research
- Summary: Prepared libs folder and waiting for HytaleServer.jar.
- Details:
  - Created `libs` directory for external dependencies.
  - Acknowledged that `HytaleServer.jar` will be placed manually by the user.
  - Attempted to fetch dependencies from suggested repos; determined they contain source/docs, not pre-compiled JARs.
- Affected artifacts: /libs/
- Verification (build/compile): PENDING (Waiting for HytaleServer.jar)
- Status: active

## 2026-01-30 (Entry 5)

- Timestamp (America/Monterrey): 2026-01-30 15:05
- Actor: AI (Antigravity)
- Type: build
- Summary: Successful project compilation with manual dependencies.
- Details:
  - Verified presence of `HytaleServer.jar` and `MultipleHUD-1.0.4.jar` in `libs/`.
  - Executed `clean build` using Gradle and Java JDK 25.
  - Build confirmed as **SUCCESSFUL**.
- Affected artifacts: Project binaries (build/ folder)
- Verification (build/compile): SUCCESS (Gradle build)
- Status: active

## 2026-01-30 (Entry 6)

- Timestamp (America/Monterrey): 2026-01-30 15:10
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Renamed project from HytaleHungerMod to Aqua-Thirst-hunger.
- Details:
  - Updated `settings.gradle.kts` (rootProject.name).
  - Renamed main class file to `AquaThirstHunger.java`.
  - Refactored all class references from `HytaleHungerMod` to `AquaThirstHunger`.
  - Updated `build.gradle.kts` manifest generation to point to the new main class.
- Affected artifacts: settings.gradle.kts, build.gradle.kts, src/main/java/**/*
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 7)

- Timestamp (America/Monterrey): 2026-01-30 15:15
- Actor: AI (Antigravity)
- Type: rollback
- Summary: Reverted project renaming from Entry 6.
- Details:
  - Reverted `settings.gradle.kts` rootProject.name to `HytaleHungerMod`.
  - Renamed `AquaThirstHunger.java` back to `HytaleHungerMod.java`.
  - Refactored all class references back to `HytaleHungerMod`.
  - Reverted `build.gradle.kts` manifest main class to `es.xcm.hunger.HytaleHungerMod`.
  - Entry 6 is now considered SUPERSEDED/CANCELED.
- Affected artifacts: settings.gradle.kts, build.gradle.kts, src/main/java/**/*
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 8)

- Timestamp (America/Monterrey): 2026-01-30 15:20
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Created 'forkok' save point and reapplied Aqua-Thirst-hunger renaming.
- Details:
  - Created Git tag `forkok` as a stable recovery point.
  - Re-implemented renaming from `HytaleHungerMod` to `Aqua-Thirst-hunger`.
  - Process: (1) Bulk string replacement across all files, (2) Renamed main class file, (3) Updated build scripts.
  - Refactored all internal references to use `AquaThirstHunger`.
  - Updated `build.gradle.kts` manifest website and main class.
- Affected artifacts: Global source, settings.gradle.kts, build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing), manual log analysis confirmed the root cause.
- Status: active

## 2026-01-30 (Entry 31)

- Timestamp (America/Monterrey): 2026-01-30 18:15
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Fixed missing Thirst HUD initialization.
- Details:
  - **Issue**: User reported no Thirst HUD visible.
  - **Cause**: `HHMPlayerReady.handle` was only initializing `HHMHud` (Hunger), not `HHMThirstHud`.
  - **Fix**: Added `mx.jume.aquahunger.ui.HHMThirstHud.createPlayerHud(store, ref, playerRef, player);` to `HHMPlayerReady.java`.
- Affected artifacts: HHMPlayerReady.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-30 (Entry 32)

- Timestamp (America/Monterrey): 2026-01-30 18:25
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Changed default Thirst HUD position to avoid overlap.
- Details:
  - **Issue**: User reported Thirst HUD overlapping with Hunger HUD.
  - **Cause**: Default position `AboveHotbarCentered` (0, 140) was used for both, causing overlap at X=0.
  - **Fix**: Updated `HHMThirstConfig.java` default to `new HudPosition.Custom(366, 140)`, explicitly offsetting it to the right.
- Affected artifacts: HHMThirstConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing). Note: User must regenerate config or manually update it if file already exists.
- Status: active

## 2026-01-30 (Entry 33)

- Timestamp (America/Monterrey): 2026-01-30 18:30
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Added BottomRight and Right-side HUD Presets.
- Details:
  - **Issue**: User requested "BottomRight" preset and verified "toda la zona right".
  - **Fix**:
    - Added `BottomRight` (366, 12), `AboveHotbarRight` (366, 140), `BelowHotbarRight` (366, 8) to `HudPosition` enum.
    - Updated `HHMThirstConfig.java` to default to `HudPosition.Preset.AboveHotbarRight`.
- Affected artifacts: HudPosition.java, HHMThirstConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-30 (Entry 9)

- Timestamp (America/Monterrey): 2026-01-30 15:25
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Reduced default food values by 50%.
- Details:
  - Modified `HHMFoodValuesConfig.java` constructor to set new default values for `tierHungerRestoration` and `tierMaxHungerSaturation`.
  - All tiers (Common through Unique) now restore 50% less hunger and provide 50% less maximum saturation buffer.
- Affected artifacts: HHMFoodValuesConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 10)

- Timestamp (America/Monterrey): 2026-01-30 15:30
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Set default initial and respawn hunger to 50.
- Details:
  - Modified `HHMHungerConfig.java` to set `initialHungerLevel` default value to `50.0f`.
  - This ensures players start and respawn with 50 hunger points when `ResetHungerOnDeath` is enabled.
- Affected artifacts: HHMHungerConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 11)

- Timestamp (America/Monterrey): 2026-01-30 15:45
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Reduced food values by another 50% (Total 75% reduction from original).
- Details:
  - Modified `HHMFoodValuesConfig.java` to further reduce `tierHungerRestoration` and `tierMaxHungerSaturation`.
  - Values are now 25% of their initial original values.
- Affected artifacts: HHMFoodValuesConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 12)

- Timestamp (America/Monterrey): 2026-01-30 15:52
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Explicitly forced hunger to 50 on respawn.
- Details:
  - Modified `OnDeathSystem.java` to explicitly set `50.0f` as the hunger value upon respawn when `ResetHungerOnDeath` is enabled.
  - Updated the "anti-death-loop" threshold in the same system to `50.0f` to ensure players never wake up with critical hunger.
- Affected artifacts: OnDeathSystem.java, HHMHungerConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 13)

- Timestamp (America/Monterrey): 2026-01-30 15:55
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Added 'RespawnHungerLevel' as a configurable option.
- Details:
  - Added `RespawnHungerLevel` to `HHMHungerConfig.java` and its CODEC. Default set to `50.0f`.
  - Updated `OnDeathSystem.java` to use this new config option instead of a hardcoded value.
  - This allows the user to change the hunger on respawn via the JSON config file.
- Affected artifacts: HHMHungerConfig.java, OnDeathSystem.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 14)

- Timestamp (America/Monterrey): 2026-01-30 16:00
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Updated README and clarified new config paths.
- Details:
  - Updated `README.md` to reflect the new project name `Aqua-Thirst-hunger`.
  - Updated expected configuration paths in the documentation to `mods/es.xcm_Aqua-Thirst-hunger/`.
  - Synchronized documentation with current default values (reduced food restoration and respawn hunger).
  - Added explicit logging in `AquaThirstHunger.java` to confirm when configurations are saved.
- Affected artifacts: README.md, AquaThirstHunger.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 15)

- Timestamp (America/Monterrey): 2026-01-30 16:05
- Actor: AI (Antigravity)
- Type: rule-change
- Summary: Implemented feature tracking via features.md.
- Details:
  - Added Section 15 to BOT protocols: all confirmed features must be logged in `features.md`.
  - Created `features.md` with current feature list (Renaming, Balance, Respawn Hunger).
  - Added `features.md` to `.gitignore` to maintain it as a local-only file.
- Affected artifacts: BOT.md, features.md, .gitignore
- Verification (build/compile): N/A
- Status: active

## 2026-01-30 (Entry 16)

- Timestamp (America/Monterrey): 2026-01-30 16:10
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Simplified configuration folder name.
- Details:
  - Modified `build.gradle.kts` manifest generator to use an empty string for the "Group" field.
  - This results in the Hytale server naming the configuration folder exactly `Aqua-Thirst-hunger/` instead of `es.xcm_Aqua-Thirst-hunger/`.
  - Updated `README.md` to show the new simplified file paths.
- Affected artifacts: build.gradle.kts, README.md
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 17)

- Timestamp (America/Monterrey): 2026-01-30 16:15
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Removed Group field from manifest to fix folder prefixing.
- Details:
  - Completely removed the `Group` field from the `manifest.json` generation logic in `build.gradle.kts`.
  - Hytale was using an underscore prefix when the Group was an empty string. Removing the field entirely forces the server to use only the `Name` for the directory structure.
- Affected artifacts: build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing)
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 18)

- Timestamp (America/Monterrey): 2026-01-30 16:15
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Refactored base package to 'mx.jume.aquahunger'.
- Details:
  - Moved all Java source files from `es.xcm.hunger` to the new directory structure `mx/jume/aquahunger`.
  - Bulk updated all `package` and `import` declarations in the codebase.
  - Updated `build.gradle.kts` group to `mx.jume.aquahunger` and manifest main class to `mx.jume.aquahunger.AquaThirstHunger`.
  - Verified successful compilation with JDK 25.
- Affected artifacts: All Java source files, build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 19)

- Timestamp (America/Monterrey): 2026-01-30 16:20
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Fixed manifest Group field to resolve NullPointerException.
- Details:
  - Re-introduced the `Group` field in `manifest.json` generation with the value `mx.jume.aquahunger`.
  - Identified that Hytale's `PluginIdentifier.hashCode()` throws an NPE if `Group` is null or missing.
  - Aligned documentation in `README.md` to point to the correct source file paths in the new package.
  - Recorded error/fix pattern in `GUIA.md`.
- Affected artifacts: build.gradle.kts, README.md, GUIA.md
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 20)

- Timestamp (America/Monterrey): 2026-01-30 16:25
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Thoroughly refactored build.gradle and metadata files to fix boot NPE.
- Details:
  - Cleaned up "junk code" in `build.gradle.kts` by simplifying resource generation and task structure.
  - Implemented dual metadata support by generating both `manifest.json` and `mod.json`.
  - Used both CamelCase and lowercase keys (`Group`/`group`, `Main`/`mainClass`) to ensure compatibility with Hytale's plugin loader.
  - Added explicit Plugin metadata to the JAR's `MANIFEST.MF`.
  - Confirmed all source files are correctly moved to `mx.jume.aquahunger` and verified build success.
- Affected artifacts: build.gradle.kts, BOT.md
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 21)

- Timestamp (America/Monterrey): 2026-01-30 16:30
- Actor: AI (Antigravity)
- Type: rule-change
- Summary: Established Code Quality Section 16 and cleaned build script.
- Details:
  - Added Section 16 to BOT protocols: all artifacts must follow best practices and avoid "junk code".
  - Completely refactored `build.gradle.kts` to be idiomatic, removed redundant `mod.json` and duplicated JSON keys.
  - Implemented proper task wiring using Gradle's `TaskProvider` and output directories.
  - Verified final `manifest.json` content and JAR integrity.
- Affected artifacts: BOT.md, build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 24)

- Timestamp (America/Monterrey): 2026-01-30 16:45
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Implemented raw meat poison effect.
- Details:
  - Modified `FeedInteraction.java` to check for raw meat item IDs on successful consumption.
  - Updated `HHMUtils.java` to provide safe access to the `Poison_T1` entity effect asset.
  - Implemented application via `EffectControllerComponent.addEffect` as per Hytale engine standards.
  - Added fallback logging if the asset is missing.
- Affected artifacts: FeeInteraction.java, HHMUtils.java, BOT.md
- Verification (build/compile): SUCCESS (Gradle compileJava passing)
- Status: active

## 2026-01-30 (Entry 22)

- Timestamp (America/Monterrey): 2026-01-30 16:35
- Actor: AI (Antigravity)
- Type: decision
- Summary: Confirmed package refactor and build logic.
- Details:
  - USER confirmed the mod boots and functions correctly with the new `mx.jume.aquahunger` package and clean build script.
  - Updated `features.md` to include confirmed features: Namespace Refactor, Clean Build Logic, and Simplified Config Path.
- Affected artifacts: features.md, BOT.md
- Verification (build/compile): N/A (Confirmed by USER)
- Status: active

## 2026-01-30 (Entry 25)

- Timestamp (America/Monterrey): 2026-01-30 16:50
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Corrected config folder naming via relative pathing in Java.
- Details:
  - Cleaned `build.gradle.kts` to use a single, minimal `manifest.json`.
  - Re-introduced `Group: mx.jume.aquahunger` to prevent server boot NPE.
  - Implemented a path escape in `AquaThirstHunger.java` using `../Aqua-Thirst-hunger/` in `withConfig`.
  - This ensures Hytale creates/uses the `mods/Aqua-Thirst-hunger` folder for configuration, even if the internal data directory uses the group prefix.
- Affected artifacts: build.gradle.kts, AquaThirstHunger.java, BOT.md
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 23)

- Timestamp (America/Monterrey): 2026-01-30 16:40
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Created save point 'aqua2'.
- Details:
  - Committed all changes related to the package refactor, build script cleaning, and metadata fixes.
  - Tagged the current commit as `aqua2`.
- Affected artifacts: Repository state
- Verification (build/compile): Tagged successfully
- Status: active

## 2026-01-30 (Entry 26)

- Timestamp (America/Monterrey): 2026-01-30 16:55
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Migrated to manual ConfigManager (Gson) to fix directory issues.
- Details:
  - Replaced Hytale's built-in `withConfig` system with a custom `ConfigManager.java`.
  - Added `com.google.code.gson:gson:2.10.1` dependency to `build.gradle.kts`.
  - Restored `Group: mx.jume.aquahunger` in `build.gradle.kts` properly.
  - The new manager writes configuration files directly to `mods/Aqua-Thirst-hunger/`, bypassing Hytale's internal data directory sandbox/prefixing behavior entirely.
  - Updated `AquaThirstHunger.java` to initialize this manager in `setup()`.
- Affected artifacts: ConfigManager.java, AquaThirstHunger.java, build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 27)

- Timestamp (America/Monterrey): 2026-01-30 17:15
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Configured Initial Hunger to Full (200.0) without changing internal max logic.
- Details:
  - Modified `HHMHungerConfig.java` default value for `InitialHungerLevel` to `200.0f`.
  - Left `maxHungerLevel` constant in `HungerComponent.java` at `200.0f` to prevent UI/Bar rendering issues.
  - This ensures players join the world or switch to Creative with a full hunger bar (200/200), while respawn remains configured separately (default 50).
  - Created safety tag `aqua4` after changes.
- Affected artifacts: HHMHungerConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 28)

- Timestamp (America/Monterrey): 2026-01-30 17:45
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Implemented External Mod Support via ExternalFoodsConfig.json.
- Details:
  - Created `HHMExternalFoodsConfig.java` to handle `ExternalFoodsConfig.json`.
  - Added auto-generation of default values for 'AndieChef' items (soya, nigiri, etc.).
  - Integrated into `FeedInteraction.java` as a **High Priority Override**:
    1. External Config (Exact Match -> Glob Match)
    2. User Config (FoodValuesConfig.json)
    3. Mod/Asset defaults
  - Exposed via `ConfigManager` and `AquaThirstHunger` main class.
  - Documented in `features.md` (Section 6).
  - Created safety tag `aqua5` after changes.
- Affected artifacts: HHMExternalFoodsConfig.java, ConfigManager.java, FeedInteraction.java, AquaThirstHunger.java
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 29)

- Timestamp (America/Monterrey): 2026-01-30 17:55
- Actor: AI (Antigravity)
- Type: implementation
- Summary: Implemented Thirst System (Component, System, Config, HUD).
- Details:
  - **ECS**: Created `ThirstComponent` (0-100 logic) and `ThirstSystem` (time-based depletion, dehydration damage).
  - **Config**: Created `HHMThirstConfig` with `ThirstConfig.json` support. Default position set to avoid overlap (Left: 366).
  - **HUD**: Cloned Hunger HUD to `Thirst.ui` via PowerShell, implemented `HHMThirstHud.java` stripped of saturation, side-by-side layout.
  - **Consumption**: Updated `FeedInteraction` to check `ExternalFoodsConfig` for `thirstRestoration` and apply it.
  - **Assets**: Duplicated hunger assets to `Thirst*` variants.
  - Documented in `features.md` (Section 7).
- Affected artifacts: ThirstComponent, ThirstSystem, HHMThirstConfig, HHMThirstHud, FeedInteraction, ExternalFoodsConfig, resources/Common/UI/Custom/Hungry/HUD/*
- Verification (build/compile): SUCCESS (Gradle build passing)
- Status: active

## 2026-01-30 (Entry 34)

- Timestamp (America/Monterrey): 2026-01-30 18:48
- Actor: USER
- Type: manual_edit
- Summary: User refined HUD Position Presets.
- Details:
  - **Change**: Manually updated `HudPosition.java`.
  - **New Values**:
    - `AboveHotbarRight`: (1020, 140)
    - `BelowHotbarRight`: (966, 8)
- Affected artifacts: HudPosition.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-30 (Entry 35)

- Timestamp (America/Monterrey): 2026-01-30 19:40
- Actor: AI (Antigravity)
- Type: feature_implementation
- Summary: Implemented Thirst Management Commands.
- Details:
  - **Commands**:
    - `/thirst` (Base command)
    - `/thirst set <value>` (Set own thirst)
    - `/thirst set <player> <value>` (Set other player's thirst)
  - **Permissions**:
    - `hungry.thirst.base`
    - `hungry.thirst.set.self`
    - `hungry.thirst.set.other`
  - **Integration**: Added `HHMUtils.setPlayerThirstLevel` to handle logic and HUD updates.
  - **Registration**: Registered command in `AquaThirstHunger.java` and added permissions to Singleplayer debug set.
- Affected artifacts: HHMUtils.java, SetThirstCommand.java, ThirstyCommand.java, AquaThirstHunger.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-30 (Entry 36)

- Timestamp (America/Monterrey): 2026-01-30 19:48
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Fixed Thirst HUD GameMode switching and Creative restoration.
- Details:
  - **Issue**: Thirst HUD images/state did not update when switching between Adventure and Creative modes.
  - **Fix**:
    - Added `HHMThirstHud.updatePlayerGameMode` static method.
    - Updated `GameModePacketWatcher` to call `HHMThirstHud.updatePlayerGameMode`.
    - Added logic to `GameModePacketWatcher` to restore Thirst to max when switching to Creative (matching Hunger behavior).
- Affected artifacts: HHMThirstHud.java, GameModePacketWatcher.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-30 (Entry 37)

- Timestamp (America/Monterrey): 2026-01-30 22:05
- Actor: USER
- Type: save_point
- Summary: Save Point aqua6 - Testing Successful.
- Details:
  - **Protocol Verified**: User confirmed testing is successful for Thirst system features (Commands, HUD, Gamemode interactions).
  - **Action**: Created git tag `aqua6`.
- Affected artifacts: All
- Verification (manual): User Approved.
- Status: sealed

## 2026-01-30 (Entry 38)

- Timestamp (America/Monterrey): 2026-01-30 22:08
- Actor: AI (Antigravity)
- Type: documentation_update
- Summary: Updated features.md with Thirst Commands and GameMode behavior.
- Details:
  - **features.md**: Added mention of `/thirst` commands and GameMode switching logic to Section 7.
- Affected artifacts: features.md
- Verification (manual): Verified content.
- Status: sealed

## 2026-01-30 (Entry 39)

- Timestamp (America/Monterrey): 2026-01-30 22:45
- Actor: AI (Antigravity)
- Type: feature_implementation
- Summary: Implemented Thirst Respawn Logic.
- Details:
  - **Config Update**: Added `ResetThirstOnDeath` (boolean, default true) and `RespawnThirstLevel` (float, default 50.0) to `HHMThirstConfig.java`.
  - **System Update**: Updated `OnDeathSystem.java` to handle thirst restoration logic on respawn, mirroring hunger behavior.
- Affected artifacts: HHMThirstConfig.java, OnDeathSystem.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-30 (Entry 40)

- Timestamp (America/Monterrey): 2026-01-30 22:58
- Actor: AI (Antigravity)
- Type: feature_implementation
- Summary: Implemented Thirst Restore Configuration.
- Details:
  - **Config Update**: Created `HHMThirstFoodValuesConfig.java` to handle thirst restoration values for items.
  - **Integration**: Integrated `HHMThirstFoodValuesConfig` into `ConfigManager`.
  - **Logic Update**: Updated `FeedInteraction.java` to implement `getThirstRestoration` logic, including tier-based values, item overrides, and fruit multiplier.
  - **Fix**: Resolved compilation error using `toString()` for `ItemResourceType` check.
- Affected artifacts: HHMThirstFoodValuesConfig.java, ConfigManager.java, FeedInteraction.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-31 (Entry 41)

- Timestamp (America/Monterrey): 2026-01-31 01:29
- Actor: AI (Antigravity)
- Type: save_point
- Summary: Save Point 'aqua7'. Verified Thirst Restoration mechanics and Updated Features.
- Details:
  - **Save Point**: `aqua7` established.
  - **Features**: Updated `features.md` to include Section 8: Thirst Restoration Mechanics.
  - **Verification**: Compilation verified (`BUILD SUCCESSFUL`).
- Affected artifacts: features.md
- Verification (manual): Verified content.
- Status: sealed

## 2026-01-31 (Entry 42)

- Timestamp (America/Monterrey): 2026-01-31 01:40
- Actor: AI (Antigravity)
- Type: modification
- Summary: Renamed main command to /aquahunger and disabled subcommands.
- Details:
  - **Command**: Renamed `HungryCommand` registration from `/hunger` to `/aquahunger` in `HungryCommand.java`.
  - **Cleanup**: Disabled `hide`, `show`, and `position` subcommands by commenting out their registration logic to simplify the user experience during testing. Kept code for future potential re-enablement.
- Affected artifacts: HungryCommand.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-31 (Entry 43)

- Timestamp (America/Monterrey): 2026-01-31 01:50
- Actor: AI (Antigravity)
- Type: feature_implementation
- Summary: Implemented Atomic Config Reload Command.
- Details:
  - **Logic**: Implemented `ConfigManager.reload()` which attempts to load all configuration files into memory first (atomic check). If successful, it applies them; if any file fails JSON validation/IO, it aborts without changing state.
  - **Command**: Created `/aquahunger reload` (`HungryReloadCommand`) tied to `aquahunger.command.reload` permission.
  - **API**: Exposed `AquaThirstHunger.reloadConfig()` to bridge the command and the manager.
  - **Feedback**: Command sends success/failure messages to the executor and logs full stack traces to the server console.
- Affected artifacts: HungryCommand.java, HungryReloadCommand.java, ConfigManager.java, AquaThirstHunger.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: active

## 2026-01-31 (Entry 44)

- Timestamp (America/Monterrey): 2026-01-31 01:55
- Actor: USER
- Type: save_point
- Summary: Save Point 'aqua8' - Commands & Reload System.
- Details:
  - **Save Point**: `aqua8` formalized.
  - **Contents**: Rename to `/aquahunger`, disabled unused subcommands, and atomic configuration reload implementation.
  - **Verification**: User verified functionality.
- Affected artifacts: All
- Verification (manual): User Approved.
- Status: sealed

## 2026-01-31 (Entry 45)

- Timestamp (America/Monterrey): 2026-01-31 01:59
- Actor: USER
- Type: feature_implementation
- Summary: Feature - Runtime Toggle for Hunger/Thirst.
- Details:
  - **Configs**: Added `enableHunger` to `HungerConfig.json` and `enableThirst` to `ThirstConfig.json`. Defaults: `true`.
  - **Systems**: `StarveSystem` and `ThirstSystem` now abide by these flags (early return in tick).
  - **Interactions**: `FeedInteraction` will not restore/saturate disabled stats.
  - **HUD**: HUDs for hunger/thirst auto-hide if their respective feature is disabled.
- Verification (build/compile): SUCCESS (Gradle build passing).
- Verification (manual): Pending user runtime test.
- Status: sealed

## 2026-01-31 (Entry 46)

- Timestamp (America/Monterrey): 2026-01-31 02:08
- Actor: USER
- Type: documentation
- Summary: Config Documentation & Default Value Refinement.
- Details:
  - **Documentation**: Created `config_docs/` with markdown documentation for `HungerConfig`, `ThirstConfig`, `FoodValuesConfig`, and `ThirstFoodValuesConfig`.
  - **Defaults Updates** (set in Code):
    - `HungerConfig`: HUD Position -> `BelowHotbarLeft`.
    - `ThirstConfig`: HUD Position -> `BelowHotbarRight`.
    - `FoodValuesConfig`: Updated Default Tier Restoration/Saturation values.
    - `ThirstFoodValuesConfig`: Updated Default Tier Thirst Restoration values.
  - **Verification**: Gradle build pass.
- Verification (manual): User to verify new config generation or manually update existing files.
- Status: sealed

## 2026-01-31 (Entry 47)

- Timestamp (America/Monterrey): 2026-01-31 02:20
- Actor: USER
- Type: feature_refinement
- Summary: Config File Generation & Commented Examples.
- Details:
  - **Manual Config Generation**: Generated `mods/Aqua-Thirst-hunger/*.json` with strict JSON content matching requested defaults.
  - **Commented Configs**: Generated `mods/Aqua-Thirst-hunger/*.jsonc` files corresponding to each config, providing line-by-line documentation for all keys.
  - **Defaults Enforced**:
    - `HungerConfig`: `BelowHotbarLeft`.
    - `ThirstConfig`: `BelowHotbarRight`.
    - `FoodValues`: Updated Tier values.
    - `ThirstFoodValues`: Updated Tier values.
  - **Parsing Safety**: Actual runtime files remain strict JSON to ensure compatibility.
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 48)

- Timestamp (America/Monterrey): 2026-01-31 03:00
- Actor: USER
- Type: save_point
- Summary: Save Point 'aqua9' - Toggles, Docs & Defaults.
- Details:
  - **Save Point**: `aqua9` formalized.
  - **Verified**: User confirmed functionality ("funciona").
  - **Contents**:
    - Runtime toggles for Hunger/Thirst.
    - Comprehensive Markdown documentation.
    - JSONC commented config examples.
    - Refined default values.
- Affected artifacts: Configs, Documentation, Systems, HUD.
- Verification (manual): User Approved.
- Status: sealed

## 2026-01-31 (Entry 49)

- Timestamp (America/Monterrey): 2026-01-31 03:25
- Actor: USER
- Type: feature_implementation
- Summary: Implemented "LifePerHunger" Mechanic.
- Details:
  - **Feature**: Health regeneration from hunger (1.0 HP / 2.0s, Cost: 1.0 Hunger).
  - **Logic**: `HungerLifeSystem` runs parallel checks. Conditions: Alive, Not Invulnerable, Hunger > 6.0, Life < Max.
  - **Config**: Added `LifePerHunger` (boolean, default false) to `HungerConfig.json`.
  - **Documentation**: Updated `HungerConfig.jsonc` with new key details.
  - **Optimization**: Uses reflection for protected Health API access.
- Affected artifacts: HungerConfig.json(c), HungerLifeSystem.java, HHMHungerConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 50)

- Timestamp (America/Monterrey): 2026-01-31 03:35
- Actor: USER
- Type: modification
- Summary: Changed LifePerHunger default to TRUE.
- Details:
  - **Config Update**: Updated `HHMHungerConfig.java` to set `lifePerHunger = true` by default.
  - **Manual Configs**: Updated `HungerConfig.json` and `HungerConfig.jsonc` to reflect `true` as the default value.
  - **Intent**: Feature should be enabled out-of-the-box for new installations or when resetting configs.
- Affected artifacts: HHMHungerConfig.java, HungerConfig.json/c
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 51)

- Timestamp (America/Monterrey): 2026-01-31 03:45
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Fixed LifePerHunger healing logic and reflection error.
- Details:
  - **Issue**: Attempted to use non-existent `addStatValue` method, then import error for `Predictable`.
  - **Correction**: Reverted to using reflection (via `getDeclaredMethod("set", float.class)`) as the most robust way to modify health given API access constraints.
  - **Implementation**: `HungerLifeSystem` now calculates `Math.min(current + valid, max)` and sets the health value directly using reflection.
  - **Debug**: Added temporary rate-limited debug logging (every 60s) to monitor health/hunger status in console.
- Affected artifacts: HungerLifeSystem.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 52)

- Timestamp (America/Monterrey): 2026-01-31 03:55
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Replaced Reflection with EntityStatMap Public API.
- Details:
  - **Correction**: Replaced reflection-based health modification with `EntityStatMap.setStatValue(int, float)`. This ensures proper synchronization with the game state.
  - **Implementation**: `HungerLifeSystem` uses `setStatValue` to update health.
  - **Cleanup**: Removed unused imports and reflection helper methods.
  - **Debug**: Retained rate-limited debug helper for verification.
- Affected artifacts: HungerLifeSystem.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 53)

- Timestamp (America/Monterrey): 2026-01-31 04:10
- Actor: AI (Antigravity)
- Type: feature_adjustment
- Summary: Adjusted LifePerHunger Costs (4 Hunger, 1 Thirst).
- Details:
  - **Mechanic Update**: Increased regeneration cost:
    - **Hunger**: -4.0 per pulse (was 1.0). Logic drains "saturation" (hunger > 100) naturally by reducing the total value.
    - **Thirst**: -1.0 per pulse (new requirement).
  - **Gates**: Regeneration now requires `Hunger >= 4.0` AND `Thirst >= 1.0`.
  - **Config**: Updated `HungerConfig.jsonc` comments to reflect new default behavior. NO new config keys added.
  - **Logic**: Updated `HungerLifeSystem` to include `ThirstComponent` in query/logic.
- Affected artifacts: HungerLifeSystem.java, HungerConfig.jsonc
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 54)

- Timestamp (America/Monterrey): 2026-01-31 04:15
- Actor: AI (Antigravity)
- Type: feature_adjustment
- Summary: Refined LifePerHunger Costs (2.0 Hunger, 0.5 Thirst).
- Details:
  - **Mechanic Update**: Fine-tuned regeneration costs based on user feedback.
    - **Hunger**: -2.0 per pulse (was 4.0).
    - **Thirst**: -0.5 per pulse (was 1.0).
  - **Gates**: Regeneration now requires `Hunger >= 2.0` AND `Thirst >= 0.5`.
  - **Config**: Updated `HungerConfig.jsonc` comments to accurate reflect the current mechanic.
- Affected artifacts: HungerLifeSystem.java, HungerConfig.jsonc
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 55)

- Timestamp (America/Monterrey): 2026-01-31 04:20
- Actor: AI (Antigravity)
- Type: enhancement
- Summary: Renamed Thirst command and cleaned up debug logs.
- Details:
  - **Command Rename**: Changed `/thirst` to `/aquathirst` to avoid conflicts and improve clarity.
  - **Cleanup**: Removed rate-limited debug logging from `HungerLifeSystem` as it is no longer needed.
  - **Code Quality**: Removed unused fields (`debugTimer`) from `HungerLifeSystem`.
- Affected artifacts: HungerLifeSystem.java, ThirstyCommand.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 56)

- Timestamp (America/Monterrey): 2026-01-31 04:30
- Actor: AI (Antigravity)
- Type: configuration
- Summary: Updated ExternalFoodsConfig.json with Andiechef values.
- Details:
  - **Configuration**: Populated `ExternalFoodsConfig.json` with specific Hunger/Saturation/Thirst values for `Andiechef` items (Susbhi, Soya, Sake, etc.).
  - **Correction**: Resolved conflicting values for `Andiechef_Food_Item_Soya` (used Thirst -2.0).
- Affected artifacts: ExternalFoodsConfig.json
- Verification (manual): JSON file created.
- Status: sealed

## 2026-01-31 (Entry 57)

- Timestamp (America/Monterrey): 2026-01-31 04:35
- Actor: AI (Antigravity)
- Type: enhancement
- Summary: Added authors to mod manifest.
- Details:
  - **Metadata**: Updated `build.gradle.kts` to include `"Authors": ["jume", "andiemg", "antigravity"]` in the generated `manifest.json`.
- Affected artifacts: build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 58)

- Timestamp (America/Monterrey): 2026-01-31 04:45
- Actor: AI (Antigravity)
- Type: enhancement
- Summary: Added visual icon for Dehydration effect.
- Details:
  - **New Asset**: Created `Server/Entity/Effects/Status/Dehydration.json` which applies the `ThirstIcon.png` as a status effect.
  - **System Update**: Modified `ThirstSystem.java` to apply this non-damaging visual effect when thirst is 0, mirroring the logic for Starvation (but separate from the damage logic).
  - **Utils**: Added `Dehydration` effect retrieval in `HHMUtils.java`.
- Affected artifacts: Dehydration.json, ThirstSystem.java, HHMUtils.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 59)

- Timestamp (America/Monterrey): 2026-01-31 04:55
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Proper formatting for Authors in manifest definition.
- Details:
  - **Bugfix**: Changed `Authors` field in `build.gradle.kts` from string array `["jume", ...]` to object array `[{ "Name": "jume" }, ...]` to match Hytale's manifest schema and prevent boot crash.
- Affected artifacts: build.gradle.kts
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 60)

- Timestamp (America/Monterrey): 2026-01-31 05:00
- Actor: User
- Type: tuning
- Summary: Update Starving.json asset.
- Details:
  - **Asset Update**: User manually updated `Server/Entity/Effects/Status/Starving.json`.
  - **Changes**: Set `DamageCalculatorCooldown` to 3.
- Affected artifacts: Starving.json
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 61)

- Timestamp (America/Monterrey): 2026-01-31 05:05
- Actor: AI (Antigravity)
- Type: bugfix
- Summary: Corrected properites for Dehydration effect asset.
- Details:
  - **Asset Fix**: Reset `Dehydration.json` to a minimal configuration containing `Debuff: true` and the icon path. This aims to resolve the issue where the effect was appearing as a buff and ensures no conflicting fields from the previous copy-paste.
- Affected artifacts: Dehydration.json
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 62)

- Timestamp (America/Monterrey): 2026-01-31 05:15
- Actor: AI (Antigravity)
- Type: configuration
- Summary: Add missing items to ExternalFoodsConfig.
- Details:
  - **Correction**: Updated `HHMExternalFoodsConfig.java` to include missing "Andiechef_Yakimeshi..." items and correct specific values for existing items (Nigiri, Onigiri, Rollo, SalsaSoya, Wasabi, Soya_Fermentada) as per user request.
- Affected artifacts: HHMExternalFoodsConfig.java
- Verification (build/compile): SUCCESS (Gradle build passing).
- Status: sealed

## 2026-01-31 (Entry 63)

- Timestamp (America/Monterrey): 2026-01-31 05:25
- Actor: AI (Antigravity)
- Type: milestone
- Summary: Punto de Guardado - Consolidación de Features.
- Details:
  - **Mecánicas**: Costes de regeneración ajustados (Hambre 2.0, Sed 0.5).
  - **Comandos**: `/aquathirst` operativo y limpio de logs.
  - **Metadatos**: Autores configurados en manifiesto con formato de objeto.
  - **Visuales**: Icono de deshidratación funcional (Debuff).
  - **Configuración**: `ExternalFoodsConfig.json` sincronizado con todos los items de Andiechef.
- Affected artifacts: HungerLifeSystem.java, ThirstyCommand.java, build.gradle.kts, ThirstSystem.java, HHMUtils.java, Dehydration.json, ExternalFoodsConfig.json, HHMExternalFoodsConfig.java
- Verification (build/compile): SUCCESS (Confirmado por el usuario).
- Status: active
