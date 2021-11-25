package sk.fei.beskydky.cryollet.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState (val usernameError: Int? = null,
                           val isDataValid: Boolean = false)