Hello everyone,

I trust you're doing well! Welcome to this project. If you're passionate about dogs and eager to explore various dog breeds, you're in the perfect spot. Here, you'll find detailed information and images for each breed, and you can even mark your favorite breeds for quick access. Plus, our Dog Breeds App works offline, so you can enjoy it anytime.

Initially, the app retrieves data using the [Dog CEO API](https://dog.ceo/dog-api/documentation/), which is then stored in the app's database for offline use.

## Architecture

This project employs the MVVM (Model View ViewModel) Clean Architecture. This design approach ensures that the codebase is well-organized and easy for other developers and reviewers to understand. Clean Architecture promotes a clear separation of concerns, abstracting the application's logic to streamline development. While Clean Architecture can be paired with MVP (Model View Presenter), we've opted for MVVM due to Android Architecture Components' built-in [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) class. This approach aligns with the [recommended app architecture](https://developer.android.com/topic/architecture#recommended-app-arch) guidelines from Android.

The app's architecture is structured into three main layers:
- **UI Layer (Presentation Layer)**
- **Domain Layer**
- **Data Layer**


## Screenshots
<img src="https://github.com/user-attachments/assets/a80cb8ab-4218-4edd-b555-3351010eab91" width=200/>|<img src="https://github.com/user-attachments/assets/4e73c033-e75f-48a2-b443-db262259294e" width=200/>|<img src="https://github.com/user-attachments/assets/502eb681-e02c-4de1-8688-24ff358e6422" width=200/>
