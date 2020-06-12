package com.example.rssnews.data.source.local

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime
import com.example.rssnews.data.Article as ArticleModel

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val link: String,
    val creator: String,

    val categories: String,
    val description: String,

    val date: String
)

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Update
    fun updateArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticles(): List<Article>

}

fun List<Article>.convertAllToModel(): MutableList<ArticleModel> = this.map { i ->
    convertToModel(i)
} as MutableList<ArticleModel>

fun List<ArticleModel>.convertAllToDbModel(): MutableList<Article> = this.map { i ->
    convertFromModel(i)
} as MutableList<Article>

fun convertToModel(serverModel: Article): ArticleModel =
    ArticleModel(
        serverModel.id,
        serverModel.title,
        serverModel.link,
        serverModel.creator,
        Gson().fromJson(serverModel.categories, object : TypeToken<List<String?>?>() {}.type),
        serverModel.description,
        LocalDateTime.parse(serverModel.date)

    )

fun convertFromModel(model: ArticleModel): Article =
    Article(
        model.id,
        model.title,
        model.link,
        model.creator,
        Gson().toJson(model.categories),
        model.description,
        model.date.toString()

    )



