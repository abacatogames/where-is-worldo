<h1>
    <img src="src/main/resources/static/wordov3.webp" width="75" style="vertical-align: middle; margin-right: 10px;">
    Where is Wordo?
</h1>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/Ktor-server-blue?logo=ktor)](https://ktor.io/)
[![Gradle](https://img.shields.io/badge/Gradle-build-02303A?logo=gradle&logoColor=white)](https://gradle.org/)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-green)](https://opensource.org/licenses/MIT)

A server-side rendered browser game written entirely in **Kotlin for the JVM**, powered by **Ktor** and **Kotlin HTML /
CSS DSLs**.

### [Play now!](https://where-is-wordo.abacatogames.com/)

---

## 🛠️ Build and test

```bash
./gradlew clean build
```

---

## 🚀 Run

```bash
./gradlew clean run
```

### Open:

```
http://localhost:8080
```

---

## 🐳 Docker

Build and run the Docker image:

```bash
docker build -t where-is-worldo .
docker run -p 8080:8080 where-is-worldo
```

---

> ⚠️ Work-in-progress. Open-source for learning and experimentation. Use at your own risk.
