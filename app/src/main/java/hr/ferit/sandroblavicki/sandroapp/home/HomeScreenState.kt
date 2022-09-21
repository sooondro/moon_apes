package hr.ferit.sandroblavicki.sandroapp.home

sealed class HomeScreenState {
}

class HomeLoadingState() : HomeScreenState()
class HomeErrorState() : HomeScreenState()
class HomeUserInputState() : HomeScreenState()