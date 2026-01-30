# GUIA (Success Cases and Replicable Paths)

Document Name: GUIA
Status: Append-only by convention
Document Version: 1.0.0
Created (Local Time - America/Monterrey): 2026-01-30

## 1) Purpose

Record only:

- Success cases: what worked and how to replicate it reliably.
- Errors encountered: what failed and what was learned, specifically leading to a successful outcome.

This prevents repeated trial-and-error and supports deterministic iteration.

## 2) Rules

- Append-only by convention:
  - Do not rewrite prior entries.
  - If something becomes outdated, append a deprecation note in a new entry.
- Each success case must include:
  - Preconditions
  - Steps
  - Why it worked (minimal)
  - How to replicate
  - Verification method (build/compile, runtime check)
- Each error note must include:
  - Symptom
  - Cause (if confirmed)
  - Fix
  - Prevention

## 3) Entry Template (Success Case)

- Timestamp (America/Monterrey):
- Title:
- Goal:
- Context:
- Preconditions:
- Steps:
- Result:
- Replication Steps:
- Verification:
- Related BOT Log Reference (date/summary):
- Status: [active | superseded | deprecated]

## 4) Entry Template (Error -> Success)

- Timestamp (America/Monterrey): 2026-01-30 16:15
- Symptom: `java.lang.NullPointerException: Cannot invoke "String.hashCode()" because "this.group" is null` during server boot.
- Cause: Missing `Group` field in the mod's `manifest.json`. Hytale's `PluginIdentifier` requires a non-null group value for internal mapping.
- Fix: Ensure the `manifest.json` generation logic in `build.gradle.kts` includes a valid `Group` field (e.g., matching the project's base package).
- Prevention: Always include `Group`, `Name`, and `Version` in the manifest, as they are primordial for the Hytale plugin system.
- Status: active

- Timestamp (America/Monterrey):
- Title:
- Symptom:
- Root Cause (confirmed):
- Fix Applied:
- Why it fixed the issue:
- Prevention:
- Verification:
- Related Success Case:
- Status: [active | superseded | deprecated]

---

# ENTRIES (Append-Only)

- Timestamp (America/Monterrey): 2026-01-30 16:55
- Title: Solving Folder Path Conflicts via Manual Config Management
- Goal: Ensure configuration files are saved in `mods/Aqua-Thirst-hunger/` without being prefixed by the mod's Group name (e.g., `mx.jume...`).
- Context: Hytale's built-in `withConfig` method automatically sandboxes configuration files into a directory named `{Group}_{Name}`. Relative path escaping (`../`) worked but felt fragile or like a workaround.
- Preconditions: Project must include `com.google.code.gson:gson` dependency.
- Steps:
  1. Create a `ConfigManager` class that uses standard Java `File` IO and Gson (instead of Hytale's Codec system for file handling).
  2. Define the target directory explicitly (e.g., `new File("mods/Aqua-Thirst-hunger")`).
  3. Retain the use of Hytale Codec classes (like `HHMHungerConfig`) as simple data containers (POJOs).
  4. Initialize this manager in the main plugin's `setup()` method.
- Result: Configuration files appear exactly where intended, and the project retains a proper Group ID in `manifest.json` for server stability.
- Replication Steps: Copy the `ConfigManager` pattern from this project and add the Gson compileOnly dependency.
- Verification: Server boot log confirms manual loading; files exist in `mods/Aqua-Thirst-hunger/`.
- Related BOT Log Reference (date/summary): 2026-01-30 (Entry 26)
- Status: active

- Timestamp (America/Monterrey): 2026-01-30 17:15
- Title: Preventing HUD/UI Breakage by Preserving Internal Constants
- Goal: Set player hunger to full (100%) on join without breaking the visual hunger bar.
- Context: We initially tried reducing `maxHungerLevel` in code from 200.0 to 100.0. This caused the hunger bar to disappear entirely, likely due to a mismatch with the client-side UI scaler or a hardcoded expectation of the 200.0 scale.
- Preconditions: HungerComponent logic depends on strict float ranges.
- Steps:
  1. **Revert** any changes to `public static final` constants that define component capacity (e.g., `maxHungerLevel`).
  2. **Modify** only the configuration default values (e.g., `InitialHungerLevel`) to match the internal maximum (200.0) if you want a "Full" state.
- Result: The bar renders correctly at 100% capacity because the math (200/200) is consistent with the UI's expectation.
- Replication Steps: If a UI element disappears after changing a limit, revert the limit and adjust the *current* value instead.
- Verification: Bar is visible and full on server join.
- Related BOT Log Reference (date/summary): 2026-01-30 (Entry 27)
- Status: active
