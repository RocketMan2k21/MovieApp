# Movies App

## Technologies Used
- Kotlin
- Android Jetpack (ViewModel, StateFlow, Room, Paging 3)
- Coroutines & Flow
- Hilt (Dependency Injection)
- Retrofit (Networking)
- Room for caching, RemoteMediator for paging

### Suggested improvements
  - Current ui messages (errors, success) couldn't be all translated on different devices, since hardcoded strings are in use for simplicity ( not from String resources). I can create smht like UiStringWrapper to access the string resources from viewmodel, or map it in UI
  - The API key is publicly exposed. I have commited the api key so you can test movie api without creating your own api key. Ideally it's better store api keys locally only
    

## Screenshots

<img width="300" alt="image" src="https://github.com/user-attachments/assets/6fad3a64-f128-46c8-973d-5307d644b7a4" />
<img width="300" alt="Screenshot_20260405_124300" src="https://github.com/user-attachments/assets/bb6885e3-b7a3-49e1-b568-7df99e4c7c9d" />
<img width="300" alt="image" src="https://github.com/user-attachments/assets/bd05c04b-76b7-4241-bb51-4f4e0594bd88" />
