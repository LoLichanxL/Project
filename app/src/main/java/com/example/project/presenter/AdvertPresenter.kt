package com.example.project.presenter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import com.example.project.AddAdvertFragment
import com.example.project.Contract
import com.example.project.model.Advert
import com.example.project.model.Authentication
import com.example.project.model.animals.Animal
import com.example.project.model.database.Database
import java.io.ByteArrayOutputStream
import java.util.*
import com.google.android.gms.common.util.CollectionUtils.listOf

class AdvertPresenter(val view: AddAdvertFragment, val mainView:Contract.MainView): Contract.AddAdvertPresenter{
    override fun onArrowBackIsClicked() {
        mainView.closeCreateAdvertFragment()
    }

    override fun onUploadImageButtonIsPressed(code:Int) {
        mainView.uploadAdvertImage(code)
    }

    override fun onAddAdvertButtonIsClicked(
        title: String,
        description: String,
        coast: String,
        category: String,
        breed: String,
        age: String,
        gender: String) {
        if(title.length != 0 && description.length != 0  && category.length != 0 && breed.length != 0 && gender.length != 0) {
            val date:String = Date().toString()
            val id: String = Authentication().getUserID() + date

            if (view.binding.firstImageViewContainer.visibility == View.VISIBLE && view.binding.secondImageViewContainer.visibility == View.VISIBLE && view.binding.thirdImageViewContainer.visibility == View.VISIBLE) {
                uploadImage(1, id)
                uploadImage(2, id)
                uploadImage(3, id)
                Database.addAdvert(Advert(id, "placeID", title, description, coast, breed, category, age, gender, listOf("adverts/" + id + "/firstImage.jpg", "adverts/" + id + "/secondImage.jpg", "adverts/" + id + "/thirdImage.jpg"),
                    date, Authentication().getUserID()))
                mainView.closeCreateAdvertFragment()

            }
            if (view.binding.firstImageViewContainer.visibility == View.VISIBLE && view.binding.secondImageViewContainer.visibility == View.VISIBLE && view.binding.thirdImageViewContainer.visibility != View.VISIBLE) {
                uploadImage(1, id)
                uploadImage(2, id)
                Database.addAdvert(Advert(id, "placeID", title, description, coast, breed, category, age, gender, listOf("adverts/" + id + "/firstImage.jpg", "adverts/" + id + "/secondImage.jpg"), date, Authentication().getUserID()))
                mainView.closeCreateAdvertFragment()
            }
            if (view.binding.firstImageViewContainer.visibility == View.VISIBLE && view.binding.secondImageViewContainer.visibility != View.VISIBLE && view.binding.thirdImageViewContainer.visibility != View.VISIBLE) {
                uploadImage(1, id)
                Database.addAdvert(Advert(id, "placeID", title, description, coast, breed, category, age, gender, listOf("adverts/" + id + "/firstImage.jpg"), date, Authentication().getUserID()))
                mainView.closeCreateAdvertFragment()
            }
        }
            else{
            view.dataIsEmptySnackbar()
        }
    }
    fun uploadImage(count:Int, id:String){
        if (count == 1){
            view.binding.firstAdvertImageView.isDrawingCacheEnabled = true
            val firstBitmap = (view.binding.firstAdvertImageView.drawable as BitmapDrawable).bitmap
            val firstBaos = ByteArrayOutputStream()
            firstBitmap.compress(Bitmap.CompressFormat.JPEG, 100, firstBaos)
            Database.uploadImageAdvertImageFromImageView(id, 1, firstBaos.toByteArray())
        }
        if (count == 2){
            view.binding.secondAdvertImage.isDrawingCacheEnabled = true
            val secondBitmap = (view.binding.secondAdvertImage.drawable as BitmapDrawable).bitmap
            val secondBaos = ByteArrayOutputStream()
            secondBitmap.compress(Bitmap.CompressFormat.JPEG, 100, secondBaos)
            Database.uploadImageAdvertImageFromImageView(id, 2, secondBaos.toByteArray())
        }
        if (count == 3){
            view.binding.thirdAdvertImage.isDrawingCacheEnabled = true
            val thirdBitmap = (view.binding.thirdAdvertImage.drawable as BitmapDrawable).bitmap
            val thirdBaos = ByteArrayOutputStream()
            thirdBitmap.compress(Bitmap.CompressFormat.JPEG, 100, thirdBaos)
            Database.uploadImageAdvertImageFromImageView(id, 2, thirdBaos.toByteArray())
        }
    }
}