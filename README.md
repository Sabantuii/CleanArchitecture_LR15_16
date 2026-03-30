# ЛР 17-18 
## Основная идея - ранее в лр 15-16 мы создавали зависимости вручную, но теперь мы делаем это автоматически через Hilt
## Все компоненты, которые осталось без изменений:
* TaskApi.kt — интерфейс API
* TaskDto.kt — DTO
* TaskMapper.kt — маппер
* TaskRepositoryImpl.kt — реализация репозитория
* Task.kt — модель
* TaskRepository.kt — интерфейс
* AddTaskUseCase.kt, GetTasksUseCase.kt — UseCase
* TasksViewModel.kt — ViewModel
* TasksScreen.kt — Compose UI 
* MainActivity.kt — Activity
## Что изменилось
* build.gradle.kts — добавил плагины и зависимости Hilt
* TasksViewModel.kt — добавил @HiltViewModel и @Inject в конструктор
* TaskRepositoryImpl.kt — добавил @Inject в конструктор
* MainActivity.kt — добавил @AndroidEntryPoint (убрано ручное создание зависимостей)
* DI-модули (RepositoryModule.kt, NetworkModule.kt, DispatcherModule.kt)
## Также хочу выделить, что:
При работе возникли конфликты версий и в таких проектах с большим кол-вом компонентов это неудивительно.
## Как было решено:
1. Самое главное - поменял версию JVM с 21 на 17. Заработал KSP
2. В файле gradle-wrapper.properties добавил версию Gradle 8.2
3. Версии gradle специально были подобраны, чтобы ликвидировать конфликт несовместимости
## Далее прикрепляю видеоотчет работы приложения
### [Видеоотчет](video/Запись%20ЛР%2017-18%20Компос.mp4)
Честно скажу, не знаю почему, но если в задаче есть английские символы, то выведет ошибку, если на русском только, то, как можно видеть в Logcat, все работает.
Запросы к серверу проходят успешно.




