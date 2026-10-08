# Learning Dashboard

A small native Android Learning Dashboard application built using Kotlin and Jetpack Compose.

## Architecture

The application follows a lightweight MVVM architecture with a repository layer:

UI (Jetpack Compose)
↓
ViewModel
↓
Repository
↓
Remote API + Local Room Database

- **UI:** Jetpack Compose screens for Login, Dashboard, and Course Details.
- **ViewModel:** Manages UI state and handles user actions using Kotlin Coroutines.
- **Repository:** Acts as the single source of data and handles remote/local data sources.
- **Remote:** Mock course API used to fetch course data.
- **Local:** Room database used for offline caching and persisting course progress.
- **Dependency Injection:** Koin is used to provide repositories, APIs, database components, and ViewModels.

## Offline Support

After courses are successfully fetched, they are stored in the local Room database.

If the network/API is unavailable, the Repository falls back to the cached courses from Room. Course progress and completion status are also persisted locally, so the user can continue viewing previously loaded courses while offline.

## Authentication & Token Storage

The current assignment uses a mock login flow.

In a production application, authentication tokens should not be stored directly in plain text or hardcoded in the source code. Access/refresh tokens should be stored using Android's secure storage mechanisms, such as Android Keystore-backed encrypted storage, with appropriate token expiry and refresh handling.

## Scaling

For 1 million users and hundreds of courses, I would keep the mobile client lightweight and move scalability concerns to the backend.

- Use stateless backend services behind a load balancer.
- Use pagination instead of loading all courses at once.
- Add database indexing and appropriate caching on the backend.
- Use CDN/cache layers where appropriate.
- Use asynchronous/background processing for non-critical operations.
- Keep API responses small and versioned.
- On the mobile side, use Room caching, pagination, and incremental updates to reduce network usage.

## Second Platform

The same backend API and data contracts could be reused for iOS.

The iOS application could be implemented using **Swift and SwiftUI**, following a similar MVVM structure:

SwiftUI → ViewModel → Repository → API + Local Persistence

The business requirements and API contracts would remain platform-independent while the UI and platform-specific persistence/authentication implementations would be native to iOS.

## Testing

A unit test is included for the `CourseRepository` to verify offline fallback behavior. The test simulates a network failure and verifies that cached course data is returned from the local data source.