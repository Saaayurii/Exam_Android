# Exam_Android

Экзаменационная работа по дисциплине **«Программирование мобильных устройств»**

| | |
|---|---|
| **Студент** | Долженко Роман Павлович |
| **Специальность** | 09.03.01 |
| **Группа** | ИВТ-1 |
| **Университет** | ДонГУ, Физико-технический факультет |
| **Билет** | №9 |

---

## Задания билета

### Практика 1 — Переключатель (Switch)
Реализован переключатель `SwitchCompat` с отображением текущего состояния:
- Цветной индикатор (зелёный — ВКЛ, красный — ВЫКЛ)
- Текстовое поле, обновляющееся в реальном времени

### Практика 2 — ViewPager с вкладками
Приложение с `ViewPager2` и `TabLayout` (3 вкладки):
- **Главная** — приветственный экран
- **Профиль** — данные студента
- **О нас** — информация о приложении

### Теория
**Вопрос:** Объясните работу адаптера в RecyclerView и ViewPager

Развёрнутый ответ в файлах:
- [`theory_ticket9.html`](theory_ticket9.html) — открыть в браузере или Word
- [`theory_ticket9.md`](theory_ticket9.md) — Markdown-версия

---

## Стек технологий

- **Java 17**
- **Android SDK 34** (minSdk 24)
- **ViewPager2** + **TabLayout** (Material Components)
- **ViewBinding**
- **Docker** — сборка без локальной установки Android Studio

---

## Структура проекта

```
Exam_Android/
├── Dockerfile                   # Docker-образ: JDK 17 + Android SDK
├── docker-compose.yml           # Оркестрация сборки
├── app/
│   └── src/main/
│       ├── java/com/example/ticket9/
│       │   ├── MainActivity.java          # Главное меню
│       │   ├── SwitchDemoActivity.java    # Практика 1
│       │   ├── ViewPagerActivity.java     # Практика 2
│       │   ├── adapter/
│       │   │   └── TabsPagerAdapter.java  # FragmentStateAdapter
│       │   └── fragment/
│       │       ├── HomeFragment.java
│       │       ├── ProfileFragment.java
│       │       └── AboutFragment.java
│       └── res/                           # XML-макеты и ресурсы
├── theory_ticket9.html          # Теоретический ответ
└── theory_ticket9.md
```

---

## Сборка через Docker

```bash
# Собрать образ и запустить сборку APK
docker-compose up --build

# Готовый APK будет по пути:
# app/build/outputs/apk/debug/app-debug.apk
```

## Сборка локально (Android Studio / Gradle)

```bash
./gradlew assembleDebug
```
