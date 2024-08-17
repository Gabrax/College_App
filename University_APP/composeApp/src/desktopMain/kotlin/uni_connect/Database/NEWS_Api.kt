package uni_connect.Database

import androidx.compose.runtime.Composable
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.enums.Country
import com.dfl.newsapi.model.ArticleDto
import io.reactivex.schedulers.Schedulers

val newsApiRepository = NewsApiRepository("78663553569543288392d3821a4a0953")

val technologyArticles = mutableListOf<ArticleDto>()
val scienceArticles = mutableListOf<ArticleDto>()
val healthArticles = mutableListOf<ArticleDto>()
val sportsArticles = mutableListOf<ArticleDto>()
val businessArticles = mutableListOf<ArticleDto>()
val entertainmentArticles = mutableListOf<ArticleDto>()

val allListsEmpty = listOf(
    technologyArticles,
    scienceArticles,
    healthArticles,
    sportsArticles,
    businessArticles,
    entertainmentArticles
).all { it.isEmpty() }

@Composable
fun techNews() {
    newsApiRepository.getTopHeadlines(category = Category.TECHNOLOGY, country = Country.US, pageSize = 1, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { article -> article.articles }
        .subscribe(
            { article ->
                technologyArticles.add(article)
            },
            { t -> println("getTopHeadlines error: ${t.message ?: "Unknown error"}") },
            {
                printSavedArticles()
            }
        )
}
@Composable
fun scienceNews() {
    newsApiRepository.getTopHeadlines(category = Category.SCIENCE, country = Country.US, pageSize = 1, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { article -> article.articles }
        .subscribe(
            { article ->
                scienceArticles.add(article)
            },
            { t -> println("getTopHeadlines error: ${t.message ?: "Unknown error"}") },
            {
                //printSavedArticles()
            }
        )
}
@Composable
fun healthNews() {
    newsApiRepository.getTopHeadlines(category = Category.HEALTH, country = Country.US, pageSize = 1, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { article -> article.articles }
        .subscribe(
            { article ->
                healthArticles.add(article)
            },
            { t -> println("getTopHeadlines error: ${t.message ?: "Unknown error"}") },
            {
                //printSavedArticles()
            }
        )
}
@Composable
fun sportsNews() {
    newsApiRepository.getTopHeadlines(category = Category.SPORTS, country = Country.US, pageSize = 1, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { article -> article.articles }
        .subscribe(
            { article ->
                sportsArticles.add(article)
            },
            { t -> println("getTopHeadlines error: ${t.message ?: "Unknown error"}") },
            {
                //printSavedArticles()
            }
        )
}
@Composable
fun businessNews() {
    newsApiRepository.getTopHeadlines(category = Category.BUSINESS, country = Country.US, pageSize = 1, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { article -> article.articles }
        .subscribe(
            { article ->
                businessArticles.add(article)
            },
            { t -> println("getTopHeadlines error: ${t.message ?: "Unknown error"}") },
            {
                //printSavedArticles()
            }
        )
}
fun entertainmentNews() {
    newsApiRepository.getTopHeadlines(category = Category.ENTERTAINMENT, country = Country.US, pageSize = 1, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { article -> article.articles }
        .subscribe(
            { article ->
                entertainmentArticles.add(article)
            },
            { t -> println("getTopHeadlines error: ${t.message ?: "Unknown error"}") },
            {
                //printSavedArticles()
            }
        )
}


fun printSavedArticles() {
    for (article in technologyArticles) {
        println("Title: ${article.title}")
        println("URL: ${article.url}")
        println("Description: ${article.description}")
        println("Image: ${article.urlToImage}")
        println("Published At: ${article.publishedAt}")
        println("----------") // Separator between articles
    }
}
