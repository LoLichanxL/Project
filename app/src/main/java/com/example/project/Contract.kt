package com.example.project

import com.example.project.model.Advert
import java.util.*

interface Contract {

    interface LoginView{
        fun openSignUpFragment()
        fun openMainActivity()
        fun catchInvalidDataException()
        fun showSignInIsFailedSnackbar()
    }
    interface LoginPresenter{
        fun onLoginButtonIsPressed(userEmail:String, userPassword: String)
        fun onLoginActivityIsStarted()
        fun onLoginIsSuccessful()
        fun onLoginIsFailed()
    }
    interface RegistrationView{
        fun showEmailIsInvalidSnackBar()
        fun showPasswordIsInvalidSnackBar()
        fun showPasswordSnackBar()
        fun showRegistrationIsFailedSnackBar()
        fun closeFragment()
        fun openMainActivity()
    }
    interface RegistrationPresenter{
        fun onRegistrationButtonIsPressed(name:String, email:String, firstPassword:String, secondPassword:String)
        fun onRegistrationIsSuccessful(name: String)
        fun onRegistrationIsFailed()
    }

    interface MainView{
        fun openCreateAdvertFragment()
        fun closeCreateAdvertFragment()

        fun uploadAdvertImage(code: Int)
    }
    interface HomeView{
        fun createAdvertsRecyclerView(list: List<HashMap<String, Object>>)
    }

    interface HomePresenter{
        fun onDatabaseIsUploadAdverts(list: List<HashMap<String, Object>>)
        fun onFragmentIsStarted()
        fun onFloatingBarIsClicked()
    }

    interface AddAdvertView{
        fun dataIsEmptySnackbar()
    }
    interface AddAdvertPresenter{
        fun onArrowBackIsClicked()
        fun onUploadImageButtonIsPressed(code:Int)
        fun onAddAdvertButtonIsClicked(title:String, description:String, coast:String, category: String, breed:String, age:String, gender:String)
    }
}