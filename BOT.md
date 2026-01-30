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
- Verification (build/compile): SUCCESS (Gradle build passing cleanly)
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
