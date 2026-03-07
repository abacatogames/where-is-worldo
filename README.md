<h1>
    Where is Wordo?
    <img src="src/main/resources/static/wordov2.webp" width="75" style="vertical-align: middle; margin-right: 10px;">
</h1>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.x-purple)](https://kotlinlang.org/)
[![Ktor](https://img.shields.io/badge/Ktor-server-blue)](https://ktor.io/)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-build-02303A?logo=gradle&logoColor=white)](https://gradle.org/)
[![License](https://img.shields.io/badge/license-MIT-green)](https://opensource.org/licenses/MIT)

A server-side rendered browser game written entirely in **Kotlin**, powered by **Ktor** and rendered with **Kotlin HTML** and **CSS DSLs**.

---
## 🚀 Run

```bash
git clone https://github.com/abacatogames/where-is-worldo.git
cd where-is-worldo
./gradlew clean run
```

Open:

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