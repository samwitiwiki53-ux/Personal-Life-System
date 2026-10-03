# Upgrade Plan: Personal-Life-System (20261003163544)

- **Generated**: 2026-10-03 16:35
- **HEAD Branch**: main
- **HEAD Commit ID**: Not retrieved
- **HEAD Commit ID**: N/A (not provided by version-control status)

## Available Tools

**JDKs**
- JDK 17: not available (baseline will be skipped)
- JDK 25.0.4.1: C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot\bin (target runtime)

**Build Tools**
- Maven 3.9.16: C:\Program Files\Maven\apache-maven-3.9.16\bin
- Maven Wrapper: not present

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20261003163544
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java runtime/compiler target: 25 (latest LTS as of 2026-10-03)

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| --------------------- | ------- | ---------------------- | ---------------- |
| Java | 17 | 25 | User requested latest LTS |
| Maven | 3.9.16 | 3.9.16 | Compatible with Java 25 |
| maven-compiler-plugin (implicit) | Maven super POM default | 3.14.1 | Pin a compiler plugin version with Java 25 support |
| JavaFX | 21 | 21 | No change required for requested Java runtime upgrade |
| MongoDB Java Driver | 4.11.0 | 4.11.0 | No change required for requested Java runtime upgrade |
| jbcrypt | 0.4 | 0.4 | No change required for requested Java runtime upgrade |
| javafx-maven-plugin | 0.0.8 | 0.0.8 | No change required for requested Java runtime upgrade |

## Derived Upgrades

- Set the Maven compiler release to 25 and use maven-compiler-plugin 3.14.1 so compilation targets Java 25 APIs with a plugin version that understands the target.
- No Maven or JDK installation is required; JDK 25 and Maven 3.9.16 are already available.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|-----------|---------|--------|--------|--------|
| pom.xml | maven.compiler.source / maven.compiler.target | 17 / 17 | replace | maven.compiler.release=25 | Set Java 25 language, bytecode, and API level together |
| pom.xml | maven-compiler-plugin | Implicit Maven super POM default | add | 3.14.1 | Ensure explicit support for compiling with Java 25 |

### Source Code Changes

No source changes identified. Searched Java sources for internal JDK packages, reflective access patterns, and SecurityManager usage; none were found.

### Configuration Changes

No application runtime configuration changes identified.

### CI/CD Changes

No CI/CD or container configuration files are present in the repository.

### Risks & Warnings

- **JavaFX runtime compatibility**: The project uses JavaFX 21 with JDK 25. Compilation alone cannot establish native toolkit startup compatibility. **Mitigation**: Run the Maven test lifecycle on JDK 25 and attempt the application launch if the environment supports a graphical desktop; document any headless-environment limitation.
- **Baseline unavailable**: JDK 17 is not installed, so the pre-upgrade baseline cannot be established. **Mitigation**: Run clean compilation and tests after the upgrade using the installed JDK 25.
- **Existing untracked user file**: `src/main/java/com/lifesystem/controllers/HubController.java` is untracked. **Mitigation**: Preserve it through the session's atomic branch preparation; do not stage or alter its contents as part of the upgrade.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Confirm required JDK 25 and Maven are ready before build steps.
  - **Changes to Make**: None; both tools are already installed.
  - **Verification**: List installed JDKs and Maven; JDK 25 and Maven 3.9.16 are available.

- Step 2: Setup Baseline
  - **Rationale**: Capture pre-upgrade compile/test results when the current JDK is available.
  - **Changes to Make**: None.
  - **Verification**: Skipped because JDK 17 is unavailable; baseline will be skipped.

- Step 3: Set Java 25 Compilation Target
  - **Rationale**: Upgrade compiler/API target and pin a compiler plugin that supports Java 25.
  - **Changes to Make**: Apply the Dependency Changes in Impact Analysis to `pom.xml`.
  - **Verification**: `mvn clean test-compile -q` using JDK 25; expected success.

- Step 4: CVE Validation & Fix
  - **Rationale**: Scan direct dependencies and remediate any reported vulnerabilities as required by the upgrade workflow.
  - **Changes to Make**: Update only dependencies with confirmed CVEs and available patched versions.
  - **Verification**: Dependency CVE scan, then `mvn clean test-compile -q` and a follow-up scan if fixes are needed.

- Step 5: Final Validation
  - **Rationale**: Verify the final Java 25 project compiles and all tests pass.
  - **Changes to Make**: Resolve any Java 25 build or test failures discovered during validation.
  - **Verification**: `mvn clean test-compile -q && mvn clean test -q` using JDK 25; expected success.
