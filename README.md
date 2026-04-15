# SMPLE

A minimalist, notes-style gym companion for Android. Log workouts in seconds — open, type, save.

**Team:** BrayGo — Braydon Lafleur (469538), Shogo Hardy (467696)  
**Platform:** Android Studio · Kotlin · Jetpack Compose  
**Backend:** Supabase (PostgreSQL + Auth)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots / Wireframes](#screenshots--wireframes)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [Getting Started](#getting-started)
- [Dependencies](#dependencies)

---

## Overview

Traditional fitness apps overwhelm users with mandatory fields and cluttered interfaces. SMPLE solves this with a frictionless workflow designed for beginners and intermediate gym-goers who want speed without sacrificing structure.

**Core workflow:** Open → Type → Save

---

## Features

| Area | Details |
|------|---------|
| **Auth** | Sign up, login, password reset via Supabase Auth |
| **Home** | Monthly calendar highlighting training days + recent workout list |
| **Workout Plans** | Entries categorized as Push / Pull / Legs |
| **Workout Detail** | Timestamp, formatted exercise notes (e.g. `Bench 4×12 @ 225`), scrollable layout |
| **Profile** | User info, logout, delete account |
| **Offline Support** | Room local cache with sync-on-connect via `isSynced` flag |
| **Navigation** | Bottom nav bar: Home · Workouts · Profile |

---

## Screenshots / Wireframes

> Add wireframe/screenshot images to `docs/wireframes/` and update the paths below.

### Onboarding
<!-- ![Onboarding](docs/wireframes/onboarding.png) -->

### Login
<!-- ![Login](docs/wireframes/login.png) -->

### Sign Up
<!-- ![Sign Up](docs/wireframes/signup.png) -->

### Forgot Password
<!-- ![Forgot Password](docs/wireframes/forgot_password.png) -->

### Home (Calendar + Recent Logs)
<!-- ![Home](docs/wireframes/home.png) -->

### Workout List
<!-- ![Workout List](docs/wireframes/workout_list.png) -->

### Workout Detail
<!-- ![Workout Detail](docs/wireframes/workout_detail.png) -->

### Profile
<!-- ![Profile](docs/wireframes/profile.png) -->

---

## Architecture

SMPLE follows **MVVM** with a clean layered architecture:

```
Compose UI  ──►  ViewModel  ──►  Repository  ──►  Supabase (remote)
                                      │
                                      └──►  Room DAO (local cache)
```

| Layer | Responsibility |
|-------|---------------|
| `ui/` | Jetpack Compose screens and ViewModels — no business logic |
| `data/repository/` | Single source of truth; coordinates Room cache and Supabase API |
| `data/remote/` | Supabase client, DTOs, API interfaces |
| `data/local/` | Room database, entities, and DAOs |
| `domain/model/` | Pure Kotlin data classes — no framework dependencies |
| `navigation/` | Type-safe Nav graph and sealed `Screen` routes |

---

## Project Structure

```
app/src/main/java/com/example/smple/
│
├── SmpleApp.kt                        # Application class (Supabase + Room init)
│
├── navigation/
│   ├── Screen.kt                      # Sealed class — all app routes
│   └── NavGraph.kt                    # NavHost with every composable destination
│
├── domain/
│   └── model/
│       ├── Entry.kt                   # Core workout entry model
│       └── User.kt                    # Core user model
│
├── data/
│   ├── local/
│   │   ├── SmpleDatabase.kt           # Room database
│   │   ├── entity/
│   │   │   ├── EntryEntity.kt         # Room entity (includes isSynced flag)
│   │   │   └── UserEntity.kt
│   │   └── dao/
│   │       ├── EntryDao.kt            # CRUD + category filter + Flow queries
│   │       └── UserDao.kt
│   ├── remote/
│   │   ├── SupabaseClient.kt          # Supabase singleton
│   │   ├── dto/
│   │   │   ├── EntryDto.kt            # Serializable DTO matching Supabase schema
│   │   │   └── UserDto.kt
│   │   └── api/
│   │       ├── AuthApi.kt             # Auth operations interface
│   │       └── EntryApi.kt            # Remote CRUD interface
│   └── repository/
│       ├── AuthRepository.kt          # Auth contract
│       └── EntryRepository.kt         # Entry contract (cache + sync)
│
└── ui/
    ├── theme/
    │   ├── Color.kt
    │   ├── Theme.kt
    │   └── Type.kt
    ├── auth/
    │   ├── OnboardingScreen.kt
    │   ├── LoginScreen.kt
    │   ├── SignUpScreen.kt
    │   ├── ForgotPasswordScreen.kt
    │   └── AuthViewModel.kt
    ├── home/
    │   ├── HomeScreen.kt              # Calendar + recent entries
    │   └── HomeViewModel.kt
    ├── workouts/
    │   ├── WorkoutListScreen.kt       # Category tabs + entry list
    │   ├── WorkoutDetailScreen.kt     # Timestamp + formatted notes
    │   └── WorkoutViewModel.kt
    └── profile/
        ├── ProfileScreen.kt
        └── ProfileViewModel.kt
```

---

## Database Design

### ERD

```
ENTRIES                         USERS
───────────────────────         ────────────────────────
id          INT  PK      ──┐    id          INT  PK
user_id     INT  FK      ←─┘──► name        STRING
title       STRING              email       STRING (UNIQUE)
content     STRING              password    STRING
category    STRING              created_at  DATETIME
parsed_json JSON
created_at  DATETIME
updated_at  DATETIME
```

### Data Flow

```
Compose UI
    │  calls
    ▼
Repository
    ├── Read  → Room DAO (instant, offline-capable)
    ├── Write → Room DAO (mark isSynced = false) + Supabase (if online)
    └── Sync  → push unsynced entries to Supabase on reconnect
```

**Security:** Supabase Row Level Security (RLS) ensures users can only read and write their own entries.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17+
- A Supabase project with the schema above applied

### Setup

1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd SMPLE
   ```

2. Create a `local.properties` entry (or a `secrets.kt` / environment approach of your choice):
   ```
   SUPABASE_URL=https://<your-project>.supabase.co
   SUPABASE_ANON_KEY=<your-anon-key>
   ```

3. Open the project in Android Studio and let Gradle sync.

4. Run on an emulator or physical device (API 26+).

### Supabase Schema

```sql
create table users (
  id          serial primary key,
  name        text not null,
  email       text unique not null,
  password    text not null,
  created_at  timestamptz default now()
);

create table entries (
  id          serial primary key,
  user_id     int references users(id) on delete cascade,
  title       text not null,
  content     text not null,
  category    text not null,
  parsed_json jsonb,
  created_at  timestamptz default now(),
  updated_at  timestamptz default now()
);

-- Row Level Security
alter table entries enable row level security;
create policy "Users can only access their own entries"
  on entries for all using (auth.uid()::text = user_id::text);
```

---

## Dependencies

| Library | Purpose |
|---------|---------|
| Jetpack Compose | UI toolkit |
| Navigation Compose | In-app navigation |
| Room | Local SQLite cache |
| ViewModel + StateFlow | MVVM state management |
| Supabase Kotlin SDK | Auth + Postgrest remote backend |
| kotlinx.serialization | DTO serialization |
| Coroutines + Flow | Async data streams |
