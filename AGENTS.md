# Repository Guidelines

## Project Purpose

This is a learning-focused Java command-line task manager. Prefer small changes that introduce Java SE concepts only when the current feature needs them. Do not add Spring, Lombok, databases, or elaborate design patterns prematurely.

## Project Structure & Module Organization

The repository is currently empty apart from this guide. As code is added, use:

- `src/main/java/` for application classes, organized into lowercase packages.
- `src/test/java/` for tests mirroring the production package structure.
- `data/` only when file persistence is introduced; do not commit personal task data.
- `pom.xml` only after Maven becomes part of the learning path.

A `Task` model should precede collections, menus, persistence, and frameworks.

## Build, Test, and Development Commands

No build tool or runnable class exists yet. Until Maven is introduced, compile and run from the repository root, adapting class and package names as needed:

```powershell
javac -d out src/main/java/com/example/taskmanager/*.java
java -cp out com.example.taskmanager.Main
```

After Maven is added, prefer `mvn test` for tests and `mvn package` for a complete build. Document new commands here when tooling changes.

## Coding Style & Naming Conventions

Use four-space indentation, UTF-8 source files, and one public top-level class per file. Name classes with `PascalCase`, methods and variables with `camelCase`, constants with `UPPER_SNAKE_CASE`, and packages with lowercase components. Keep methods focused and fields `private` unless there is a demonstrated reason otherwise. Avoid wildcard imports and unexplained abstractions.

## Testing Guidelines

No test framework or coverage threshold is configured. When JUnit is introduced, place tests under `src/test/java`, mirror package names, and name test classes `*Test` (for example, `TaskTest`). Test observable behavior and edge cases rather than private implementation details.

## Commit & Pull Request Guidelines

There is no Git history from which to infer conventions. Use short, imperative commits such as `Add minimal Task model`. Keep each commit focused on one learning step. Pull requests should explain the behavior changed, how it was verified, and which Java concept it exercises. Include console output when it clarifies command-line behavior; screenshots are normally unnecessary.

## Agent-Specific Instructions

Treat the repository owner as the primary implementer. Unless explicitly asked to write a section, guide with questions and progressively stronger hints before providing complete code. Review existing work by explaining what is wrong, why Java behaves that way, and what the learner should change. Never implement future roadmap stages opportunistically.
