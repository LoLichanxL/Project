package com.example.project.model

import com.example.project.model.animals.Animal

class Advert (val id: String,
              val placeID:String,
              val title:String,
              val description:String,
              val coast:Double,
              val animal: Animal,
              val photosUrl:List<String>,
              val date: String,
              val UID:String
              ) {

}