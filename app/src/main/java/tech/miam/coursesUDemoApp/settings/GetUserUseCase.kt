package tech.miam.coursesUDemoApp.settings

class GetUserUseCase(private val userLocalDataSource: UserLocalDataSource) {

    fun getCurrentUserId(): String? = userLocalDataSource.getUserId()

    fun setUserId(userId: String) {
        userLocalDataSource.setUserId(userId)
    }

    fun isLogged() = userLocalDataSource.isLogged()
}