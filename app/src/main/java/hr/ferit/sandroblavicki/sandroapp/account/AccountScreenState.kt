package hr.ferit.sandroblavicki.sandroapp.account

sealed class AccountScreenState()

class AccountErrorState(val errorText: String) : AccountScreenState()
class AccountLoadingState() : AccountScreenState()
class AccountUserInputState() : AccountScreenState()
