# MasterPlan
[![Android Release](https://github.com/Leturgone/MasterPlan/actions/workflows/release.yml/badge.svg?branch=main)](https://github.com/Leturgone/MasterPlan/actions/workflows/release.yml)

Клиент серверное приложения для контроля выполнения планов мероприятий. Серверная часть - [MasterPlanApi](https://github.com/Leturgone/MasterPlanApi)

## Оглавление

- [Описание](#описание)
- [Технологический стек](#технологический-стек)
- [Установка](#установка)
- [Функционал](#функционал)


## Описание

[<img src="app/src/main/res/ic_launcher-playstore.png" align="left"
width="200" hspace="10" vspace="10">](app/src/main/res/icon/ic_launcher-playstore.png)

MasterPlan это мобильное приложение для Android написанное на языке Kotlin, позволяющее упростить контроль выполнения планов мероприятий в подразделении предприятия.<br>
<br><br><br><br><br><br>

## Технологический стек

<img src="/demo.gif" align="right" width="250" hspace="10" vspace="10"/>

- **Android** – ОС, на которой разработано приложение
- **Android SDK 36** – Средство разработки приложения
- **Kotlin** – Основной язык, на котором разработано прилжение
- **Jetpack Compose** - набор инструментов для построения UI
- **Clean Architecture** - разделение по слоям domain, data, framework (андроид компоненты)
- **MVVM** - Архитектурный паттерн
- **Retrofit** - для обращения к [MasterPlanApi](https://github.com/Leturgone/MasterPlanApi)
- **Dagger Hilt** - для внедрения зависимостей (DI)
- **Datastore** - для хранения токена автризации и состояния темы  
- **Coroutines + Flow**
- **Android Services** - для создания уведомлений был создан WebsocketNotificationService наследуемый от Service
- **CI/CD** - создание релиза автоматически через github actions

<br><br><br><br><br><br>

## Установка
1. Скачать apk из последнего релиза;
2. Установить на устройство.
  
## Функционал

Для приложения реализован ролевой доступ. Для представления функций предоставлен Use Case

<img width="1443" height="963" alt="изображение" src="https://github.com/user-attachments/assets/d93e3f1f-883f-44a2-9165-dd4e8ea759b6" />



